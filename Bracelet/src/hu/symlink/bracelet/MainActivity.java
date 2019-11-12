package hu.symlink.bracelet;

import hu.symlink.bracelet.service.BluetoothCommService;
import hu.symlink.bracelet.service.BraceletService;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Debugging
	private static final String TAG = "Bracelet";
	public static final boolean D = true;

	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;

	// Service API connection
	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(TAG, "Service connection established");

			// that's how we get the client side of the IPC connection
			api = BraceletServiceAPI.Stub.asInterface(service);
			try {
				// Add listener
				api.addListener(braceletServiceListener);

				// Initalize GUI first
				initializeUI();

			} catch (RemoteException e) {
				Log.e(TAG, "Failed to add listener", e);
			}

			// updateView();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i(TAG, "Service connection closed");
		}
	};

	// BraceletService API & Callbacks
	private BraceletServiceAPI api;
	private BraceletListener.Stub braceletServiceListener = new BraceletListener.Stub() {

		@Override
		public void latestMeasureUpdated() throws RemoteException {
			// Get measure
			Measure m = api.getLatestMeasure();

			// Update UI
			updateMeasureValues(m);
		}

		@Override
		public void connectionStateChanged() throws RemoteException {
			int cstatus = api.getConnectionStatus();

			// Update UI
			updateConnectionStateUI(cstatus);
		}

		@Override
		public void batteryLevelChanged(int level) throws RemoteException {
			updateBatteryLevel(level);
		}

		@Override
		public void panicOccured() throws RemoteException {
			// Show panic dialog
			runOnUiThread(new Runnable() {
				public void run() {
					// Set view
					showDialog(DIALOG_PANIC);
				}
			});
		}
	};

	// Method for first UI initialization
	private void initializeUI() {

		// Get data from service
		Measure m = new Measure();
		int status = 0;
		try {
			m = api.getLatestMeasure();
			status = api.getConnectionStatus();
		} catch (RemoteException e) {
		}

		// Update UI
		updateMeasureValues(m);
		updateConnectionStateUI(status);

	}

	private void updateBatteryLevel(final int level) {

		runOnUiThread(new Runnable() {
			public void run() {
				// Set view
				((TextView) findViewById(R.id.textViewBatteryLevel))
						.setText(level + " %");
			}
		});
	}

	private void updateMeasureValues(Measure measure) {

		final Measure m = measure;

		runOnUiThread(new Runnable() {
			public void run() {
				// Set view
				((TextView) findViewById(R.id.textViewBlox)).setText(Float
						.valueOf(m.getBloodoxygen()).toString());
				((TextView) findViewById(R.id.textViewAcc)).setText(Float
						.valueOf(m.getAcceleration()).toString());
				((TextView) findViewById(R.id.textViewPulse)).setText(Integer
						.valueOf(m.getPulse()).toString());
				((TextView) findViewById(R.id.textViewTemp)).setText(Float
						.valueOf(m.getTemperature()).toString());
			}
		});
	}

	private void updateConnectionStateUI(int status) {
		final TextView tv = (TextView) findViewById(R.id.textViewConnState);
		String newValue = "";

		// Update device mac
		final TextView tvMac = (TextView) findViewById(R.id.textViewMacAddr);
		final Settings settings = new Settings(getApplicationContext());

		// Update connection state textview
		switch (status) {
		case BluetoothCommService.STATE_NONE:
			if (tv != null)
				newValue = getString(R.string.title_not_connected);
			break;

		case BluetoothCommService.STATE_CONNECTING:
			if (tv != null)
				newValue = getString(R.string.title_connecting);
			break;

		case BluetoothCommService.STATE_CONNECTED:
			if (tv != null)
				newValue = getString(R.string.title_connected_to);
			break;
		}

		final String finalNewValue = newValue;

		// Update UI
		runOnUiThread(new Runnable() {
			public void run() {
				tv.setText(finalNewValue);

				tvMac.setText(settings.getDeviceMac());
			}
		});
	}

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	private static final int DIALOG_PANIC = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth nem elérhetõ", Toast.LENGTH_LONG)
					.show();

			// TODO: remove in production
			// finish();
			// return;
		}

		// Run Bracelet Service if not already running
		/* This is the important part */
		Intent intent = new Intent(BraceletService.class.getName());
		startService(intent);

		// Create connection to the service
		bindService(intent, serviceConnection, 0);

		// Restore UI state
		@SuppressWarnings("unchecked")
		HashMap<String, Object> savedValues = (HashMap<String, Object>) this
				.getLastNonConfigurationInstance();
		if (savedValues != null)
			loadLastUIState(savedValues);

	}

	private void loadLastUIState(HashMap<String, Object> savedValues) {
		((TextView) findViewById(R.id.textViewConnState))
				.setText((String) savedValues.get("connTitle"));
		((TextView) findViewById(R.id.textViewAcc))
				.setText((String) savedValues.get("accValue"));
		((TextView) findViewById(R.id.textViewBlox))
				.setText((String) savedValues.get("bloxValue"));
		((TextView) findViewById(R.id.textViewTemp))
				.setText((String) savedValues.get("tempValue"));
		((TextView) findViewById(R.id.textViewPulse))
				.setText((String) savedValues.get("pulseValue"));
		((TextView) findViewById(R.id.textViewMacAddr))
			.setText((String) savedValues.get("deviceMac"));
		((TextView) findViewById(R.id.textViewBatteryLevel))
			.setText((String) savedValues.get("batteryLevel"));
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		HashMap<String, Object> savedValues = new HashMap<String, Object>();
		savedValues.put("connTitle",
				((TextView) findViewById(R.id.textViewConnState)).getText());
		savedValues.put("pulseValue",
				((TextView) findViewById(R.id.textViewPulse)).getText());
		savedValues.put("bloxValue",
				((TextView) findViewById(R.id.textViewBlox)).getText());
		savedValues.put("accValue",
				((TextView) findViewById(R.id.textViewAcc)).getText());
		savedValues.put("tempValue",
				((TextView) findViewById(R.id.textViewTemp)).getText());
		savedValues.put("batteryLevel",
				((TextView) findViewById(R.id.textViewBatteryLevel)).getText());
		savedValues.put("deviceMac",
				((TextView) findViewById(R.id.textViewMacAddr)).getText());
		
		return savedValues;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (D)
			Log.e(TAG, "++ ON START ++");
	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		if (D)
			Log.e(TAG, "+ ON RESUME +");
	}

	@Override
	public synchronized void onPause() {
		super.onPause();

		if (D)
			Log.e(TAG, "- ON PAUSE -");
	}

	@Override
	public void onStop() {
		super.onStop();
		if (D)
			Log.e(TAG, "-- ON STOP --");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (D)
			Log.e(TAG, "--- ON DESTROY ---");

		// Disconnect from service here
		try {
			api.removeListener(braceletServiceListener);
			unbindService(serviceConnection);
		} catch (Throwable t) {
			// catch any issues, typical for destroy routines
			// even if we failed to destroy something, we need to continue
			// destroying
			Log.w(TAG, "Failed to unbind from the service", t);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.options:
			// Launch Options activity
			Intent optionsIntent = new Intent(this, OptionsActivity.class);
			startActivityForResult(optionsIntent, -1);
			return true;

		case R.id.measures:
			// Launch Options activity
			Intent measuresIntent = new Intent(this, MeasureListActivity.class);
			startActivity(measuresIntent);
			return true;

		case R.id.exit:
			this.finish();
			return true;

		case R.id.about:
			try {
				AlertDialog dialog = AboutDialogBuilder.create(this);
				dialog.show();
			} catch (NameNotFoundException e) {
				Log.e("Cannot find about dialog name",e.getLocalizedMessage());
				return false;
			}
			return true;
			
		default:
			return false;
		}
	}

	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DIALOG_PANIC:
			final Dialog dialog = new Dialog(this);
			dialog.setContentView(R.layout.panic_dialog);
			dialog.setTitle(getString(R.string.panic_occured));

			// Close button
			Button closeButton = (Button) dialog
					.findViewById(R.id.panicDialogDismissButton);
			closeButton.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View view) {
					dialog.dismiss();
				}
			});
			return dialog;
		}

		return null;
	}
}