package hu.symlink.bracelet.soap;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public final class MobileEvent extends SoapObject {
    private int eventCode;

    private int level;

    private java.lang.String description;

    private double longitude;
    
    private double latitude;
    
    public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLattitude() {
		return latitude;
	}
	public void setLattitude(double lattitude) {
		this.latitude = lattitude;
	}
	public MobileEvent() {
        super("", "");
    }
    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public int getEventCode(int eventCode) {
        return this.eventCode;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel(int level) {
        return this.level;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getDescription(java.lang.String description) {
        return this.description;
    }

    public int getPropertyCount() {
        return 5;
    }

    public Object getProperty(int __index) {
        switch(__index)  {
        case 0: return new Integer(eventCode);
        case 1: return new Integer(level);
        case 2: return description;
        case 3: return longitude;
        case 4: return latitude;
        }
        return null;
    }

    public void setProperty(int __index, Object __obj) {
        switch(__index)  {
        case 0: eventCode = Integer.parseInt(__obj.toString()); break;
        case 1: level = Integer.parseInt(__obj.toString()); break;
        case 2: description = (java.lang.String) __obj; break;
        case 3: longitude = Double.parseDouble(__obj.toString()); break;
        case 4: latitude = Double.parseDouble(__obj.toString()); break;
        }
    }

    public void getPropertyInfo(int __index, Hashtable __table, PropertyInfo __info) {
        switch(__index)  {
        case 0:
            __info.name = "EventCode";
            __info.type = Integer.class; break;
        case 1:
            __info.name = "Level";
            __info.type = Integer.class; break;
        case 2:
            __info.name = "Description";
            __info.type = java.lang.String.class; break;
        case 3:
            __info.name = "Longitude";
            __info.type = Double.class; break;
        case 4:
            __info.name = "Latitude";
            __info.type = Double.class; break;
        }
    }

}
