package hu.symlink.bracelet.soap;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public final class MobileMeasure extends SoapObject {
    private int pulse;

    private double temperature;

    private double acceleration;

    private double bloodOxygen;

    private java.util.Date measured;
    
    private java.lang.String sensorid;

    public MobileMeasure() {
        super("", "");
    }
    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getPulse(int pulse) {
        return this.pulse;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature(double temperature) {
        return this.temperature;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getAcceleration(double acceleration) {
        return this.acceleration;
    }

    public void setBloodOxygen(double bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
    }

    public double getBloodOxygen(double bloodOxygen) {
        return this.bloodOxygen;
    }

    public void setMeasured(java.util.Date measured) {
        this.measured = measured;
    }

    public java.util.Date getMeasured(java.util.Date measured) {
        return this.measured;
    }
    
    public void setSensorID(java.lang.String sensorid) {
    	this.sensorid = sensorid;
    }
    
    public java.lang.String getSensorID(java.lang.String sensorid) {
    	return this.sensorid;
    }

    public int getPropertyCount() {
        return 6;
    }

    public Object getProperty(int __index) {
        switch(__index)  {
        case 0: return new Integer(pulse);
        case 1: return new Double(temperature);
        case 2: return new Double(acceleration);
        case 3: return new Double(bloodOxygen);
        case 4: return measured;
        case 5: return sensorid;
        }
        return null;
    }

    public void setProperty(int __index, Object __obj) {
        switch(__index)  {
        case 0: pulse = Integer.parseInt(__obj.toString()); break;
        case 1: temperature = Double.parseDouble(__obj.toString()); break;
        case 2: acceleration = Double.parseDouble(__obj.toString()); break;
        case 3: bloodOxygen = Double.parseDouble(__obj.toString()); break;
        case 4: measured = (java.util.Date) __obj; break;
        case 5: sensorid = (java.lang.String) __obj; break;
        }
    }

    public void getPropertyInfo(int __index, Hashtable __table, PropertyInfo __info) {
        switch(__index)  {
        case 0:
            __info.name = "Pulse";
            __info.type = Integer.class; break;
        case 1:
            __info.name = "Temperature";
            __info.type = Double.class; break;
        case 2:
            __info.name = "Acceleration";
            __info.type = Double.class; break;
        case 3:
            __info.name = "BloodOxygen";
            __info.type = Double.class; break;
        case 4:
            __info.name = "Measured";
            __info.type = java.util.Date.class; break;
        case 5:
            __info.name = "SensorID";
            __info.type = java.lang.String.class; break;
        }
    }

}
