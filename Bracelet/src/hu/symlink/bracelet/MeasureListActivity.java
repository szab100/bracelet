package hu.symlink.bracelet;

import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MeasureListActivity extends Activity {
        
        protected EditText searchText;
        protected ListAdapter adapter;
        protected ListView measureList;
        
        private static String MEASURES_TABLE_NAME = "MEASURES";
        private SQLiteDatabase measuresDB = null;
        
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurelist);
        
        // Get measures database adapter
        //dbAdapter = new MeasuresDBAdapter(this);
        measureList = (ListView) findViewById (R.id.listMeasures);
        
        // Initialize list first
        refreshUIList(null);
    }
    
    public void refreshUIList(View view) {
    	
    	// Get the cursor from MeasuresDBAdapter
        //Cursor cursor = dbAdapter.getLastMonthDataCursor();
    	Cursor cursor = null;
        
    	// Initialize database
    	try
    	{
          measuresDB = openOrCreateDatabase("NAME", Context.MODE_PRIVATE, null);
          
          // Create table if necessarry
          createTable();
          
          // Offset from UTC in seconds
          int utcOffsetInSecs = (int)Math.round( ((float)TimeZone.getDefault().getRawOffset() / 1000.0) );
          
          // Add daylight savings if available
          if(TimeZone.getDefault().useDaylightTime()) utcOffsetInSecs+=3600;
          
          cursor = measuresDB.rawQuery("SELECT rowid AS _id, PULSE, ACC, TEMP, BLOX, datetime(MEASURED,'" + utcOffsetInSecs + " second') AS MEASURED FROM " + MEASURES_TABLE_NAME +
        		  " WHERE datetime(MEASURED)  >= datetime('now', '-30 day') ORDER BY MEASURED DESC"
        		  , null);
    	} //+" WHERE datetime('MEASURED')  >= datetime('now','-30 day') "
    	catch (Exception e)
    	{        	 
    		String str = "";
    	}
    	
        // Create our adapter for listView
         adapter = new SimpleCursorAdapter(
                        this, 
                        R.layout.measurelist_item, 
                        cursor,
                        new String[] {"PULSE", "ACC", "TEMP", "BLOX", "MEASURED"}, 
                        new int[] {R.id.measure_pulse, R.id.measure_acc, R.id.measure_temp, R.id.measure_blox, R.id.measure_date});
        //startManagingCursor(cursor);
        measureList.setAdapter(adapter);
        
        // Refresh outer layout
        
    }
    
    /**
     * Create a table if it doesn't already exist
     */
    private void createTable()
    {
       measuresDB.execSQL("CREATE TABLE IF NOT EXISTS " + MEASURES_TABLE_NAME +
                    "(PULSE INTEGER, " +
                    " TEMP FLOAT, " +
                    " ACC FLOAT," +
                    " BLOX FLOAT, " +
                    " MEASURED VARCHAR);");
       
//       // TODO: remove !!
//       insertTestData();
    }
 
    /**
     * Insert some test data, modify as you see fit
     */
//    public void insertTestData()
//    {
//    	measuresDB.execSQL("DELETE FROM " + MEASURES_TABLE_NAME + ";");
//    	
//        measuresDB.execSQL("INSERT INTO " + MEASURES_TABLE_NAME + " Values ('104','36.3','0.3','95',datetime('now') );");
//        measuresDB.execSQL("INSERT INTO " + MEASURES_TABLE_NAME + " Values ('67','35.7','0.8','98',datetime('now') );");
//        measuresDB.execSQL("INSERT INTO " + MEASURES_TABLE_NAME + " Values ('81','37.2','1.2','90',datetime('now') );");
//        measuresDB.execSQL("INSERT INTO " + MEASURES_TABLE_NAME + " Values ('93','36.5','1.5','99',datetime('now') );");
//        measuresDB.execSQL("INSERT INTO " + MEASURES_TABLE_NAME + " Values ('123','38.5','2.5','93.5',datetime('now') );");
//    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.measurelist_menu, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch(item.getItemId()) {
    	
    		case R.id.measurelist_refresh:
    			// Launch Options activity
    			refreshUIList(null);
    			return true;
    		
    		case R.id.measureslist_back:
    			this.finish();
    			return true;
    			
    		default:
    			return false;
    	}
    }
    
}
