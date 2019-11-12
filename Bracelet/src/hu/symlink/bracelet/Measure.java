package hu.symlink.bracelet;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.os.Parcel;
import android.os.Parcelable;

public class Measure implements Parcelable {

    private int pulse = 0;
    private float temperature = 0;
    private float acceleration = 0;
    private float bloodoxygen = 0;
    private int power = 0;
    private Boolean panic = null;
    private String sensorid = "00:00:00:00:00";

	private Date measured;

    public int describeContents() {
        return 0;
    }
    
    
    // Default constructor
    public Measure()
    {
    	// Initialize measured to current date/time
    	Calendar cal = GregorianCalendar.getInstance();
    	measured = cal.getTime();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(pulse);
        out.writeFloat(temperature);
        out.writeFloat(acceleration);
        out.writeFloat(bloodoxygen);
        out.writeString(measured.toGMTString());
    }

    public static final Parcelable.Creator<Measure> CREATOR
            = new Parcelable.Creator<Measure>() {
        public Measure createFromParcel(Parcel in) {
            return new Measure(in);
        }

        public Measure[] newArray(int size) {
            return new Measure[size];
        }
    };
    
    private Measure(Parcel in) {
        pulse = in.readInt();
        temperature = in.readFloat();
        acceleration = in.readFloat();
        bloodoxygen = in.readFloat();
        measured = new Date( Date.parse(in.readString()) );
    }

	public int getPulse() {
		return pulse;
	}

	public void setPulse(int pulse) {
		this.pulse = pulse;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getBloodoxygen() {
		return bloodoxygen;
	}

	public void setBloodoxygen(float bloodoxygen) {
		this.bloodoxygen = bloodoxygen;
	}

	public Date getMeasured() {
		return measured;
	}

	public void setMeasured(Date measured) {
		this.measured = measured;
	}
	
	public float getAcceleration() {
		// Return vectorial value
		return acceleration;
	}
	
	public void setAcceleration(float val) {
		acceleration = val;
	}
	
    public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
	
	public Boolean isPanic() {
		return panic;
	}

	public void setPanic(Boolean panic) {
		this.panic = panic;
	}
	
	public String getSensorid() {
		return sensorid;
	}

	public void setSensorid(String sensorid) {
		this.sensorid = sensorid;
	}



}
