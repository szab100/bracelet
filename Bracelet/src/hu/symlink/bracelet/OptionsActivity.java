package hu.symlink.bracelet;

import hu.symlink.bracelet.service.BraceletService;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.TabActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsActivity extends TabActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

	private Settings settings = null;
	private DecimalFormat floatFormat = new DecimalFormat("0.##");
	private DecimalFormat intFormat = new DecimalFormat("0");
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final String TAG = "OptionsActivity";
	
    // Service API connection
    private ServiceConnection serviceConnection = new ServiceConnection() {
    	  @Override
    	  public void onServiceConnected(ComponentName name, IBinder service) {
    	    Log.i(TAG, "Service connection established");
    	 
    	    // that's how we get the client side of the IPC connection
    	    api = BraceletServiceAPI.Stub.asInterface(service);
    	  }
    	 
    	  @Override
    	  public void onServiceDisconnected(ComponentName name) {
    	    Log.i(TAG, "Service connection closed");
    	  }
    };
    	
    // BraceletService API & Callbacks
    private BraceletServiceAPI api;
    
	@Override
	public void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    
	    // Setup the window
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.options);
	
	    Resources res = getResources();
	    TabHost tabHost = getTabHost();
	
	    // General tab
	    tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(res.getString(R.string.options_tab_general),
	            res.getDrawable(R.drawable.general)).setContent(R.id.tabGeneralLayout));
	    
	    // Tolerance tab
	    tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(res.getString(R.string.options_tab_tolerance),
	            res.getDrawable(R.drawable.tolerance)).setContent(R.id.tabToleranceLayout));
	    
	    tabHost.setCurrentTab(0);
	    
	    // Create connection to the service (Must be running, so don't need to start it)
	    Intent intent = new Intent(BraceletService.class.getName());
        bindService(intent, serviceConnection, 0);
	    
	    // Load settings at startup
	    settings = new Settings(getApplicationContext());
	    loadUIFromSettings();
	    
	    // Set button listeners - Save
	    final Button saveButton = (Button) findViewById(R.id.buttonOptionsSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Save settings
            	saveSettings();
            	
            	// Show saved successfully message
            	Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.options_saved_successfully), Toast.LENGTH_SHORT);
            	toast.show();
            	
            	// Try to restart service because settings are changed
            	try {
					api.restartService();
				} catch (RemoteException e) {

					Log.e("Bracelet - OptionsActivity", "Failed to call restartService()");
				}
            	
            	// It's time to disappear
            	finish();
            }
        });
        
        // Set button listeners - Cancel
        final Button cancelButton = (Button) findViewById(R.id.buttonOptionsCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Finished here..
            	finish();
            }
        });
        
        // Set button listeners - Search (Bluetooth device)
        final Button searchDevButton = (Button) findViewById(R.id.buttonOptionsDeviceSearch);
        searchDevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	// Launch the DeviceListActivity to see devices and do scan
	            Intent serverIntent = new Intent(OptionsActivity.this, DeviceListActivity.class);
	            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            }
        });
	}
	 
	private void loadUIFromSettings() {
		
		// General tab
		((EditText) findViewById(R.id.editTextUsername)).setText(settings.getUsername()); 
		((EditText) findViewById(R.id.editTextPassword)).setText(settings.getPassword());
		((CheckBox) findViewById(R.id.checkBoxUpdateEnable)).setChecked(settings.getUpdateEnabled());
		((Spinner) findViewById(R.id.SpinnerFrequency)).setSelection(settings.getUpdateFrequency());
		((TextView) findViewById(R.id.textViewDeviceMac)).setText(settings.getDeviceMac());
		
		// Tolerance tab
		((TextView) findViewById(R.id.toleranceTextViewAcc)).setText( floatFormat.format(settings.getToleranceAcceleration()));
		((TextView) findViewById(R.id.toleranceTextViewBloxFrom)).setText(floatFormat.format(settings.getToleranceBloxMin()));
		((TextView) findViewById(R.id.toleranceTextViewBloxTo)).setText(floatFormat.format(settings.getToleranceBloxMax()));
		((TextView) findViewById(R.id.toleranceTextViewTempFrom)).setText(floatFormat.format(settings.getToleranceTempMin()));
		((TextView) findViewById(R.id.toleranceTextViewTempTo)).setText(floatFormat.format(settings.getToleranceTempMax()));
		((TextView) findViewById(R.id.toleranceTextViewPulseFrom)).setText(intFormat.format(settings.getTolerancePulseMin() ));
		((TextView) findViewById(R.id.toleranceTextViewPulseTo)).setText(intFormat.format(settings.getTolerancePulseMax() ));
	
	}
	
	private void saveSettings() {
		
		// Save general tab only
		settings.setUsername( ((EditText) findViewById(R.id.editTextUsername)).getText().toString() );
		settings.setPassword( ((EditText) findViewById(R.id.editTextPassword)).getText().toString() );
		settings.setUpdateEnabled( ((CheckBox) findViewById(R.id.checkBoxUpdateEnable)).isChecked() );
		settings.setUpdateFrequency( ((Spinner) findViewById(R.id.SpinnerFrequency)).getSelectedItemPosition() );
		settings.setDeviceMac( ((TextView) findViewById(R.id.textViewDeviceMac)).getText().toString() );
		
		// Save settings to sharedProperties
		settings.saveSettings();
	}
	
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(MainActivity.D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                
            	// Update devicemac TextView - Will save only when user hits save button..
                TextView tv = (TextView) findViewById(R.id.textViewDeviceMac);
                tv.setText(address);
            }
            break;
        }
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		
		// Reload UI when settings are changed
		loadUIFromSettings();
		
	}
}
