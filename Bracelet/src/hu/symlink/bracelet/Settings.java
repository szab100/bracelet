package hu.symlink.bracelet;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
	
	public enum UpdateFrequency { ONE_MINUTE, FIVE_MINUTES, FIFTEEN_MINUTES, THIRTY_MINUTES, ONE_HOUR};
	
	private Context context = null;
	
	// General options
	private String username;
	private String password;
	private Boolean updateEnabled = false;
	private String deviceMac;
	private int updateFrequency = 0;
	
	// Tolerance values
	private float toleranceAcceleration;
	private int tolerancePulseMin;
	private int tolerancePulseMax;
	private float toleranceTempMin;
	private float toleranceTempMax;
	private float toleranceBloxMin;
	private float toleranceBloxMax;
	
	public Settings(Context c) {
		
		// Store context for future use
		context = c;
		
		// First, load all settings
		loadSettings();
	}
	
	public void loadSettings() {
		// Try to read all settings
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		// General options
		username = prefs.getString("srv_username", "");
		password = prefs.getString("srv_password", "");
		updateEnabled = prefs.getBoolean("update_enabled", false);
		deviceMac = prefs.getString("device_mac", "");
		updateFrequency = prefs.getInt("update_frequency", 0);
		
		// Tolerance values
		toleranceAcceleration = prefs.getFloat("tolerance_acc", 3);
		toleranceBloxMin = prefs.getFloat("tolerance_blox_min", 50);
		toleranceBloxMax = prefs.getFloat("tolerance_blox_max", 100);
		tolerancePulseMin = prefs.getInt("tolerance_pulse_min", 30);
		tolerancePulseMax = prefs.getInt("tolerance_pulse_max", 120);
		toleranceTempMin = prefs.getFloat("tolerance_temp_min", 30);
		toleranceTempMax = prefs.getFloat("tolerance_temp_max", 38);
		
	}
	
	public void saveSettings() {
		// Try to read all settings
		SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(context).edit();
		
		// General options
		prefs.putString("srv_username", username);
		prefs.putString("srv_password", password);
		prefs.putBoolean("update_enabled", updateEnabled);
		prefs.putString("device_mac", deviceMac);
		prefs.putInt("update_frequency", updateFrequency);
		
		// Tolerance values
		prefs.putFloat("tolerance_acc", toleranceAcceleration);
		prefs.putFloat("tolerance_blox_min", toleranceBloxMin);
		prefs.putFloat("tolerance_blox_max", toleranceBloxMax);
		prefs.putFloat("tolerance_temp_min", toleranceTempMin);
		prefs.putFloat("tolerance_temp_max", toleranceTempMax);
		prefs.putInt("tolerance_pulse_min", tolerancePulseMin);
		prefs.putInt("tolerance_pulse_max", tolerancePulseMax);
		
		// Commit changes
		prefs.commit();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getUpdateEnabled() {
		return updateEnabled;
	}

	public void setUpdateEnabled(Boolean updateEnabled) {
		this.updateEnabled = updateEnabled;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public int getUpdateFrequency() {
		return updateFrequency;
	}

	public void setUpdateFrequency(int updateFrequency) {
		this.updateFrequency = updateFrequency;
	}

	public float getToleranceAcceleration() {
		return toleranceAcceleration;
	}

	public void setToleranceAcceleration(float toleranceAcceleration) {
		this.toleranceAcceleration = toleranceAcceleration;
	}

	public int getTolerancePulseMin() {
		return tolerancePulseMin;
	}

	public void setTolerancePulseMin(int tolerancePulseFrom) {
		this.tolerancePulseMin = tolerancePulseFrom;
	}

	public int getTolerancePulseMax() {
		return tolerancePulseMax;
	}

	public void setTolerancePulseMax(int tolerancePulseTo) {
		this.tolerancePulseMax = tolerancePulseTo;
	}

	public float getToleranceTempMin() {
		return toleranceTempMin;
	}

	public void setToleranceTempMin(float toleranceTempFrom) {
		this.toleranceTempMin = toleranceTempFrom;
	}

	public float getToleranceTempMax() {
		return toleranceTempMax;
	}

	public void setToleranceTempMax(float toleranceTempTo) {
		this.toleranceTempMax = toleranceTempTo;
	}

	public float getToleranceBloxMin() {
		return toleranceBloxMin;
	}

	public void setToleranceBloxMin(float toleranceBloxFrom) {
		this.toleranceBloxMin = toleranceBloxFrom;
	}

	public float getToleranceBloxMax() {
		return toleranceBloxMax;
	}

	public void setToleranceBloxMax(float toleranceBloxTo) {
		this.toleranceBloxMax = toleranceBloxTo;
	}
}
