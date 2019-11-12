package hu.symlink.bracelet.soap;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public final class MobileTolerance extends SoapObject {
    private int pulseMin;

    private int pulseMax;

    private double tempMin;

    private double tempMax;

    private double accelerationMax;

    private double bloodOxygenMin;

    private double bloodOxygenMax;

    public MobileTolerance() {
        super("", "");
    }
    public void setPulseMin(int pulseMin) {
        this.pulseMin = pulseMin;
    }

    public int getPulseMin() {
        return this.pulseMin;
    }

    public void setPulseMax(int pulseMax) {
        this.pulseMax = pulseMax;
    }

    public int getPulseMax() {
        return this.pulseMax;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMin() {
        return this.tempMin;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempMax() {
        return this.tempMax;
    }

    public void setAccelerationMax(double accelerationMax) {
        this.accelerationMax = accelerationMax;
    }

    public double getAccelerationMax() {
        return this.accelerationMax;
    }

    public void setBloodOxygenMin(double bloodOxygenMin) {
        this.bloodOxygenMin = bloodOxygenMin;
    }

    public double getBloodOxygenMin() {
        return this.bloodOxygenMin;
    }

    public void setBloodOxygenMax(double bloodOxygenMax) {
        this.bloodOxygenMax = bloodOxygenMax;
    }

    public double getBloodOxygenMax() {
        return this.bloodOxygenMax;
    }

    public int getPropertyCount() {
        return 7;
    }

    public Object getProperty(int __index) {
        switch(__index)  {
        case 0: return new Integer(pulseMin);
        case 1: return new Integer(pulseMax);
        case 2: return new Double(tempMin);
        case 3: return new Double(tempMax);
        case 4: return new Double(accelerationMax);
        case 5: return new Double(bloodOxygenMin);
        case 6: return new Double(bloodOxygenMax);
        }
        return null;
    }

    public void setProperty(int __index, Object __obj) {
        switch(__index)  {
        case 0: pulseMin = Integer.parseInt(__obj.toString()); break;
        case 1: pulseMax = Integer.parseInt(__obj.toString()); break;
        case 2: tempMin = Double.parseDouble(__obj.toString()); break;
        case 3: tempMax = Double.parseDouble(__obj.toString()); break;
        case 4: accelerationMax = Double.parseDouble(__obj.toString()); break;
        case 5: bloodOxygenMin = Double.parseDouble(__obj.toString()); break;
        case 6: bloodOxygenMax = Double.parseDouble(__obj.toString()); break;
        }
    }

    public void getPropertyInfo(int __index, Hashtable __table, PropertyInfo __info) {
        switch(__index)  {
        case 0:
            __info.name = "PulseMin";
            __info.type = Integer.class; break;
        case 1:
            __info.name = "PulseMax";
            __info.type = Integer.class; break;
        case 2:
            __info.name = "TempMin";
            __info.type = Double.class; break;
        case 3:
            __info.name = "TempMax";
            __info.type = Double.class; break;
        case 4:
            __info.name = "AccelerationMax";
            __info.type = Double.class; break;
        case 5:
            __info.name = "BloodOxygenMin";
            __info.type = Double.class; break;
        case 6:
            __info.name = "BloodOxygenMax";
            __info.type = Double.class; break;
        }
    }

}
