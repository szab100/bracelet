package hu.symlink.bracelet.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.content.Context;
import android.content.ContextWrapper;
import android.media.MediaRecorder;
import android.util.Log;


public class AudioRecorder
{
    private static final String LOG_TAG = "AudioRecorder";
    private static String mFileName = null;
    private MediaRecorder mRecorder = null;

    public void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
    
    public File getRecordedFile() {
    	return new File(mFileName);
    }

    public AudioRecorder(Context context) {
        // Create application directory
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("recordings", Context.MODE_PRIVATE);
        
        mFileName = directory.getAbsolutePath();
        SimpleDateFormat stdFormat = new SimpleDateFormat("yyyy-MM-dd_-_kk-mm-ss");
        mFileName += "/" + stdFormat.format(getLocalDate()) + ".mp4";
    }
    
    private Date getLocalDate() {
    	// Offset from UTC in seconds
		int utcOffsetInSecs = (int)Math.round( ((float)TimeZone.getDefault().getRawOffset() / 1000.0) );
		
		// Add daylight savings if available
		if(TimeZone.getDefault().useDaylightTime()) utcOffsetInSecs+=3600;
		
		// Set measured date to current (Local / Daylight) date
		Calendar localCal = GregorianCalendar.getInstance();
		
		// TODO: check
		//localCal.add(Calendar.SECOND, utcOffsetInSecs);
		
		return localCal.getTime();
    }
}