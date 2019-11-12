package hu.symlink.bracelet.service;

import hu.symlink.bracelet.Measure;
import hu.symlink.bracelet.Settings;
import hu.symlink.bracelet.soap.BraceletMobileServiceSoap;
import hu.symlink.bracelet.soap.Configuration;
import hu.symlink.bracelet.soap.Credentials;
import hu.symlink.bracelet.soap.MobileEvent;
import hu.symlink.bracelet.soap.MobileMeasure;
import hu.symlink.bracelet.soap.MobileTolerance;
import hu.symlink.bracelet.soap.UploadFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

public class BraceletServiceHelper {

	private static final String TAG = "BraceletServiceHelper";
	private static String MEASURES_TABLE_NAME = "MEASURES";
	//private static String SERVICE_URL = "http://192.168.1.2:9876/BraceletWeb/BraceletMobileService.asmx";
	private static String SERVICE_URL = "http://bracelet.symlink.hu/BraceletMobileService.asmx";
	private static final int RECORD_LENGTH = 30;

	// EventCodes
	public static final int EVENT_CODE_TOLERANCE_VIOLATION = 1;
	public static final int EVENT_CODE_PANIC = 2;

	public static Boolean uploadMeasure(Measure m, Settings s) {
		// Configure webservice client
		Configuration.setConfiguration(SERVICE_URL);

		BraceletMobileServiceSoap srv = new BraceletMobileServiceSoap();

		// Create credentials
		Credentials cred = new Credentials();
		cred.setUserName(s.getUsername());
		cred.setPassword(s.getPassword());

		Boolean loginOK = false;

		try {
			loginOK = srv.login(cred);
		} catch (Exception e1) {
			Log.w(TAG, "Failed to call login", e1);
		}

		if (loginOK) {
			// Create MobileMeasure
			MobileMeasure mm = new MobileMeasure();

			mm.setAcceleration(new Double(m.getAcceleration()));
			mm.setBloodOxygen(new Double(m.getBloodoxygen()));
			mm.setTemperature(new Double(m.getTemperature()));
			mm.setPulse(new Integer(m.getPulse()));
			mm.setMeasured(m.getMeasured());
			mm.setSensorID(m.getSensorid());

			// Upload measure
			try {
				Boolean res = srv.storeMeasure(mm, cred);

				if (res)
					return true;
				else
					return false;

			} catch (Exception e) {

				Log.w(TAG, "Failed to upload measure", e);
				return false;
			}
		} else
			return false;

	}

	public static int raiseEvent(Settings s, int level, int eventCode,
			String description, Location loc) {

		// Configure webservice client
		Configuration.setConfiguration(SERVICE_URL);

		BraceletMobileServiceSoap srv = new BraceletMobileServiceSoap();

		// Create credentials
		Credentials cred = new Credentials();
		cred.setUserName(s.getUsername());
		cred.setPassword(s.getPassword());

		Boolean loginOK = false;

		try {
			loginOK = srv.login(cred);
		} catch (Exception e1) {
			Log.w(TAG, "Failed to call login", e1);
		}

		if (loginOK) {
			// Create MobileMeasure
			MobileEvent me = new MobileEvent();
			me.setLevel(level);
			me.setEventCode(eventCode);
			me.setDescription(description);
			me.setLongitude(loc.getLongitude());
			me.setLattitude(loc.getLatitude());

			// Upload measure
			try {
				int eventID = srv.raiseEvent(me, cred);

				return eventID;

			} catch (Exception e) {

				Log.w(TAG, "Failed to raise event", e);
				return -1;
			}
		} else
			return -1;

	}

	public static Boolean uploadRecording(Settings s, UploadFile file,
			int eventID) {

		// Configure webservice client
		Configuration.setConfiguration(SERVICE_URL);

		BraceletMobileServiceSoap srv = new BraceletMobileServiceSoap();

		// Create credentials
		Credentials cred = new Credentials();
		cred.setUserName(s.getUsername());
		cred.setPassword(s.getPassword());

		Boolean loginOK = false;

		try {
			loginOK = srv.login(cred);
		} catch (Exception e1) {
			Log.w(TAG, "Failed to call login", e1);
		}

		if (loginOK) {

			// Upload file
			try {
				Boolean result = srv.uploadRecording(file, eventID, cred);
				return result;

			} catch (Exception e) {

				Log.w(TAG, "Failed to upload recording", e);
				return false;
			}
		} else
			return false;

	}

	public static Boolean isForceMeasureActionPending(Settings s) {

		// Configure webservice client
		Configuration.setConfiguration(SERVICE_URL);

		BraceletMobileServiceSoap srv = new BraceletMobileServiceSoap();

		// Create credentials
		Credentials cred = new Credentials();
		cred.setUserName(s.getUsername());
		cred.setPassword(s.getPassword());

		Boolean loginOK = false;

		try {
			loginOK = srv.login(cred);
		} catch (Exception e1) {
			Log.w(TAG, "Failed to call login", e1);
		}

		if (loginOK) {

			// Upload file
			try {
				int res = srv.isActionQueued(cred);

				// ActionType: ForceMeasure -> 1
				if (res == 1)
					return true;
				else
					return false;

			} catch (Exception e) {

				Log.w(TAG, "Failed to check action queue", e);
				return false;
			}
		} else
			return false;

	}

	public static Boolean updateTolerance(Settings s) {

		// Configure webservice client
		Configuration.setConfiguration(SERVICE_URL);

		BraceletMobileServiceSoap srv = new BraceletMobileServiceSoap();

		// Create credentials
		Credentials cred = new Credentials();
		cred.setUserName(s.getUsername());
		cred.setPassword(s.getPassword());

		Boolean loginOK = false;

		try {
			loginOK = srv.login(cred);
		} catch (Exception e1) {
			Log.w(TAG, "Failed to call login", e1);
		}

		if (loginOK) {

			// Download tolerance settings
			try {
				MobileTolerance mt = srv.getTolerance(cred);

				if (mt != null) {

					// Update settings
					s.setToleranceAcceleration((float) mt.getAccelerationMax());
					s.setToleranceBloxMin((float) mt.getBloodOxygenMin());
					s.setToleranceBloxMax((float) mt.getBloodOxygenMax());
					s.setTolerancePulseMin(mt.getPulseMin());
					s.setTolerancePulseMax(mt.getPulseMax());
					s.setToleranceTempMin((float) mt.getTempMin());
					s.setToleranceTempMax((float) mt.getTempMax());

					// Save settings
					s.saveSettings();

					// Everything went well
					return true;
				} else
					return false;

			} catch (Exception e) {

				Log.w(TAG, "Failed to get tolerances", e);
				return false;
			}
		} else
			return false;

	}

	public static int getUpdateFreqInMinutes(Settings s) {

		switch (s.getUpdateFrequency()) {
		case 0:
			return 1;

		case 1:
			return 5;

		case 2:
			return 15;

		case 3:
			return 30;

		case 4:
			return 60;

		default:
			return 1;
		}

	}

	public static Boolean storeMeasure(Measure p_measure, Context ctx) {

		// Initialize database
		SQLiteDatabase measuresDB = null;

		try {
			// Open database
			measuresDB = ctx.openOrCreateDatabase("NAME", Context.MODE_PRIVATE,
					null);

			// Create table if neccessary
			measuresDB.execSQL("CREATE TABLE IF NOT EXISTS "
					+ MEASURES_TABLE_NAME + "(PULSE INTEGER, "
					+ " TEMP FLOAT, " + " ACC FLOAT," + " BLOX FLOAT, "
					+ " MEASURED VARCHAR);");

			SimpleDateFormat stdFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");

			measuresDB.execSQL("INSERT INTO " + MEASURES_TABLE_NAME
					+ " Values ('" + p_measure.getPulse() + "','"
					+ p_measure.getTemperature() + "','"
					+ p_measure.getAcceleration() + "','"
					+ p_measure.getBloodoxygen() + "',datetime('"
					+ stdFormat.format(p_measure.getMeasured()) + "'));");

			// Delete old measures (older than 30 days)
			measuresDB
					.rawQuery(
							"DELETE FROM "
									+ MEASURES_TABLE_NAME
									+ " WHERE datetime(MEASURED)  < datetime('now', '-30 day') ",
							null);

			// Everything went well
			return true;

		} catch (Exception e) {
			Log.e("BraceletServiceHelper", "Failed to insert measure", e);
			return false;
		} finally {
			// Always close database
			if (measuresDB != null)
				measuresDB.close();
		}

	}

	public static void checkPanicAndToleranceViolation(BraceletService serv,
			Measure p_measure, final Settings settings, final Context contex) {

		int eventCode = -1;
		String eventDescription = "";
		Boolean needForceUpload = false;

		// Check for panic
		if (p_measure.isPanic()) {

			eventCode = EVENT_CODE_PANIC;
			Location loc = serv.getCurrentLocation();
			eventDescription = "Panic event detected!";

			// Force measure upload
			needForceUpload = true;

			// Create event
			final int eventID = raiseEvent(settings, 1, eventCode,
					eventDescription, loc);

			// Start record
			if (eventID > 0) {

				// Recording Task
				class RecordTask extends AsyncTask<String, Integer, Long> {
					protected Long doInBackground(String... args) {

						// Start recording
						AudioRecorder rec = new AudioRecorder(contex);
						rec.startRecording();

						// Sleep for X seconds
						try {
							Thread.sleep(RECORD_LENGTH * 1000L);
						} catch (InterruptedException e) {
						}

						// Stop recording
						rec.stopRecording();

						// Upload recording
						File recordedFile = rec.getRecordedFile();

						UploadFile uf = new UploadFile();
						uf.setFileName(recordedFile.getName());
						try {
							uf.setFile(Base64.encodeToString(
									getBytesFromFile(recordedFile),
									Base64.DEFAULT));
						} catch (IOException e) {
							Log.e("BraceletServiceHelper",
									"Failed to get bytes of recorded file", e);
						}

						uploadRecording(settings, uf, eventID);

						return new Long(0);
					}

					protected void onProgressUpdate(Integer... progress) {

					}

					protected void onPostExecute(Long result) {
						// After done
					}
				}

				// Start recorder / uploader
				new RecordTask().execute("none");

			}

			// Notify listeners
			serv.notifyPanicListeners();
		}

		// Check for tolerance violation - Acceleration
		if (p_measure.getAcceleration() > settings.getToleranceAcceleration()) {
			eventCode = EVENT_CODE_TOLERANCE_VIOLATION;
			eventDescription = "Tolerance level exceeded: Acceleration";

			// Force measure upload
			needForceUpload = true;

			// Create event
			raiseEvent(settings, 1, eventCode, eventDescription,
					serv.getCurrentLocation());
		}

		// Check for tolerance violation - Temperature
		if (p_measure.getTemperature() > settings.getToleranceTempMax()
				|| p_measure.getTemperature() < settings.getToleranceTempMin()) {
			eventCode = EVENT_CODE_TOLERANCE_VIOLATION;
			eventDescription = "Tolerance level exceeded: Temperature";

			// Force measure upload
			needForceUpload = true;

			// Create event
			raiseEvent(settings, 1, eventCode, eventDescription,
					serv.getCurrentLocation());
		}

		// Check for tolerance violation - BloodOxygen
		if (p_measure.getBloodoxygen() > settings.getToleranceBloxMax()
				|| p_measure.getBloodoxygen() < settings.getToleranceBloxMin()) {
			eventCode = EVENT_CODE_TOLERANCE_VIOLATION;
			eventDescription = "Tolerance level exceeded: BloodOxygen";

			// Force measure upload
			needForceUpload = true;

			// Create event
			raiseEvent(settings, 1, eventCode, eventDescription,
					serv.getCurrentLocation());
		}

		// Check for tolerance violation - Pulse
		if (p_measure.getPulse() > settings.getTolerancePulseMax()
				|| p_measure.getPulse() < settings.getTolerancePulseMin()) {
			eventCode = EVENT_CODE_TOLERANCE_VIOLATION;
			eventDescription = "Tolerance level exceeded: Pulse";

			// Force measure upload
			needForceUpload = true;

			// Create event
			raiseEvent(settings, 1, eventCode, eventDescription,
					serv.getCurrentLocation());
		}

		// Force measure upload - if needed
		if (needForceUpload)
			uploadMeasure(p_measure, settings);

	}

	private static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

}
