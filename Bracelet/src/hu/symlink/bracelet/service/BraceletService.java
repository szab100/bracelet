package hu.symlink.bracelet.service;

import hu.symlink.bracelet.BraceletListener;
import hu.symlink.bracelet.BraceletServiceAPI;
import hu.symlink.bracelet.Measure;
import hu.symlink.bracelet.Settings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class BraceletService extends Service {

	private static final String TAG = "BraceletService";
	private Timer timerChecker;
	private Timer timerMeasureHandler;
	private UploadTask measureUploaderTask;

	private static final boolean D = false;
	
	// Checker task update interval in seconds
	private static final int CHECKER_INTERVAL = 10;

	// Store latest measure values for doing client updates
	private final Object measureLock = new Object();
	private Measure latestMeasure = new Measure();

	// Store connection status
	private final Object connectedLock = new Object();
	private int connectionStatus = BluetoothCommService.STATE_NONE;

	// Store old frequency
	private int oldUpdateFreq = -1;

	// Acquire a reference to the system Location Manager
	private LocationManager locationManager;
	private final Object locationLock = new Object();
	private Location currentLocation = null;

	// Name of the connected device
	private String mConnectedDeviceName = null;

	// Preferences
	Settings settings;

	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;

	// Message interpreter
	private BraceletMessageHandler messageHandler = new BraceletMessageHandler();

	// Store measures
	private ArrayList<Measure> measures = new ArrayList<Measure>();

	// Member object for the chat services
	private static final Object mCommServiceLock = new Object();
	private BluetoothCommService mCommService = null;

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Key names received from the BluetoothCommService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	private TimerTask checkerTask = new TimerTask() {
		@Override
		public void run() {
			Log.i(TAG, "Service checker");

			// Get local Bluetooth adapter
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

			// Start bluetooth comm service if it's not yet started
			if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
				synchronized (mCommServiceLock) {
					if (mCommService == null) {

						// Start comm service
						setupComm();
					}
				}
			}
			
			// Update tolerance settings at first start / restart
			BraceletServiceHelper.updateTolerance(settings);

			// (Re)load settings
			settings = new Settings(getApplicationContext());

			// Initialize measureHandlerTask if enabled
			if (settings.getUpdateEnabled()) {

				// Update frequency if needed
				int updateFreq = BraceletServiceHelper
						.getUpdateFreqInMinutes(settings);

				if (oldUpdateFreq < 0 || oldUpdateFreq != updateFreq) {
					if (timerMeasureHandler != null)
						timerMeasureHandler.cancel();
					timerMeasureHandler = new Timer(
							"BraceletServiceMeasureUploaderTimer", true);
					measureUploaderTask = new UploadTask();
					timerMeasureHandler.schedule(measureUploaderTask, 1000L,
							updateFreq * 60 * 1000L);

					// Store current updatefreq
					oldUpdateFreq = updateFreq;
				}
			}

			// Check pending actions on server - Make force upload if such an action is pending
			if(BraceletServiceHelper.isForceMeasureActionPending(settings)) uploadMeasures();

			// Connect if a device mac is available
			if (!settings.getDeviceMac().equals("")) {

				// Try to connect to device..
				if (mCommService != null
						&& mCommService.getState() != mCommService.STATE_CONNECTED) {
					BluetoothDevice device = mBluetoothAdapter
							.getRemoteDevice(settings.getDeviceMac());
					mCommService.connect(device);
				}
			}

		}
	};

	private class UploadTask extends TimerTask {

		@Override
		public void run() {
			Log.i(TAG, "Measure uploader");

			// Update Tolerance settings
			BraceletServiceHelper.updateTolerance(settings);

			// (Re)load settings
			settings.loadSettings();

			// Upload measures
			uploadMeasures();
		}
	};

	@Override
	public IBinder onBind(Intent intent) {

		if (BraceletService.class.getName().equals(intent.getAction())) {
			Log.d(TAG, "Bound by intent " + intent);
			return apiEndpoint;
		} else {
			return null;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "BraceletService creating");

		// TODO Wait for debugger -- DEBUG ONLY
		if(D) android.os.Debug.waitForDebugger();

		// Initialize settings here
		settings = new Settings(this);

		timerChecker = new Timer("BraceletServiceCheckerTimer");
		timerChecker.schedule(checkerTask, 1000L, CHECKER_INTERVAL * 1000L);

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// If BT is not on, request that it be enabled.
		if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {

			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			enableIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(enableIntent);
		}

		// Register the listener with the Location Manager to receive location
		// updates
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

	}

	private void uploadMeasures() {

		// Loop through measures
		ArrayList<Measure> measuresCopy = new ArrayList<Measure>();
		synchronized (measureLock) {
			// Copy into new arraylist
			for (int i = 0; i < measures.size(); i++)
				measuresCopy.add(measures.get(i));
		}

		for (int i = measuresCopy.size() - 1; i >= 0; i--) {

			// Get actual item
			Measure m = measuresCopy.get(i);

			// Process upload
			if (m != null) {
				Boolean success = BraceletServiceHelper.uploadMeasure(m,
						settings);

				// Remove this measure if upload succeeded
				synchronized (measureLock) {
					if (success)
						measures.remove(i);
				}
			}
		}
	}

	public void setupComm() {

		synchronized (mCommServiceLock) {
			// Initialize the BluetoothChatService to perform bluetooth
			// connections
			mCommService = new BluetoothCommService(this, mHandler);

			// Start service
			mCommService.start();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Service destroying");

		// Stop bluetooth comm service gracefully
		synchronized (mCommServiceLock) {
			if (mCommService != null)
				mCommService.stop();
		}

		timerChecker.cancel();
		timerChecker = null;
	}

	// The Handler that gets information back from the BluetoothChatService
	private final Handler mHandler = new Handler() {

		private Object synclock = new Object();

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				if (D)
					Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BluetoothCommService.STATE_CONNECTED:
					synchronized (connectedLock) {
						connectionStatus = BluetoothCommService.STATE_CONNECTED;
					}

					// Notify listeners
					notifyConnectionChangeListeners();
					break;

				case BluetoothCommService.STATE_CONNECTING:
					synchronized (connectedLock) {
						connectionStatus = BluetoothCommService.STATE_CONNECTING;
					}

					// Notify listeners
					notifyConnectionChangeListeners();
					break;
				case BluetoothCommService.STATE_NONE:
					synchronized (connectedLock) {
						connectionStatus = BluetoothCommService.STATE_NONE;
					}

					// Notify listeners
					notifyConnectionChangeListeners();
					break;
				}
				break;

			case MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				String writeMessage = new String(writeBuf);

				break;

			case MESSAGE_READ:

				synchronized (synclock) {

					byte[] readBuf = (byte[]) msg.obj;

					// construct a string from the valid bytes in the buffer
					String readMessage = new String(readBuf, 0, msg.arg1);

					// Process message
					try {
						messageHandler.processMessage(readMessage,
								BraceletService.this);
					} catch (Exception e) {
						Log.i(TAG, "BraceletService - EXA: " + e.getMessage());
					}

				}

				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				break;

			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	private List<BraceletListener> listeners = new ArrayList<BraceletListener>();

	public void notifyMeasureListeners() {
		synchronized (listeners) {
			for (BraceletListener listener : listeners) {
				try {
					listener.latestMeasureUpdated();
				} catch (RemoteException e) {
					Log.w(TAG, "Failed to notify listener " + listener, e);
				}
			}
		}
	}

	public void notifyBatteryLevelListeners(int level) {
		synchronized (listeners) {
			for (BraceletListener listener : listeners) {
				try {
					listener.batteryLevelChanged(level);
				} catch (RemoteException e) {
					Log.w(TAG, "Failed to notify listener " + listener, e);
				}
			}
		}
	}

	public void notifyConnectionChangeListeners() {
		synchronized (listeners) {
			for (BraceletListener listener : listeners) {
				try {
					listener.connectionStateChanged();
				} catch (RemoteException e) {
					Log.w(TAG, "Failed to notify listener " + listener, e);
				}
			}
		}
	}

	public void notifyPanicListeners() {
		synchronized (listeners) {
			for (BraceletListener listener : listeners) {
				try {
					listener.panicOccured();
				} catch (RemoteException e) {
					Log.w(TAG, "Failed to notify listener " + listener, e);
				}
			}
		}
	}

	private BraceletServiceAPI.Stub apiEndpoint = new BraceletServiceAPI.Stub() {

		@Override
		public void connectBluetoothDevice(String address)
				throws RemoteException {
			synchronized (mCommServiceLock) {

				// Restart mCommService (will connect to the new device)
				restartService();
			}
		}

		@Override
		public Measure getLatestMeasure() throws RemoteException {
			synchronized (measureLock) {
				return latestMeasure;
			}
		}

		@Override
		public int getConnectionStatus() throws RemoteException {
			synchronized (connectedLock) {
				return connectionStatus;
			}
		}

		@Override
		public void addListener(BraceletListener listener)
				throws RemoteException {

			synchronized (listeners) {
				listeners.add(listener);
			}
		}

		@Override
		public void removeListener(BraceletListener listener)
				throws RemoteException {

			synchronized (listeners) {
				listeners.remove(listener);
			}
		}

		@Override
		public void restartService() throws RemoteException {

			// Stop & remove mCommService (Checker process will recreate it)
			if (mCommService != null) {
				mCommService.stop();
				mCommService = null;
			}

		}

	};

	public void setMeasure(Measure p_measure) {

		// Notify batteryLevel listeners
		notifyBatteryLevelListeners(p_measure.getPower());

		// Offset from UTC in seconds
		int utcOffsetInSecs = (int) Math.round(((float) TimeZone.getDefault()
				.getRawOffset() / 1000.0));

		// Add daylight savings if available
		if (TimeZone.getDefault().useDaylightTime())
			utcOffsetInSecs += 3600;

		// Set measured date to current (Local / Daylight) date
		Calendar localCal = GregorianCalendar.getInstance();
		localCal.add(Calendar.SECOND, utcOffsetInSecs);
		p_measure.setMeasured(localCal.getTime());

		// Set SensorID
		if (mCommService != null)
			p_measure.setSensorid(mCommService.actualDevice);

		// Set actual measure & add to upload list
		synchronized (measureLock) {

			// Set & store measure
			latestMeasure = p_measure;
			measures.add(latestMeasure);

			// Notify listeners
			notifyMeasureListeners();
		}

		// Store measure to database
		Boolean storeResult = BraceletServiceHelper.storeMeasure(p_measure,
				getApplicationContext());
		if (!storeResult)
			Log.e("BraceletService", "Cannot store measure to database!");

		// Check for panic & tolerance violation, do necessary tasks there
		BraceletServiceHelper.checkPanicAndToleranceViolation(this, p_measure,
				settings, getApplicationContext());

	}

	// Get current location
	public Location getCurrentLocation() {
		synchronized (locationLock) {
			return currentLocation;
		}
	}

	// Define a listener that responds to location updates
	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			// Called when a new location is found by the network location
			// provider.
			synchronized (locationLock) {
				currentLocation = location;
			}
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

		}
	};

}
