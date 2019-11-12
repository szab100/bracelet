package hu.symlink.bracelet.soap;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public final class UploadFile extends SoapObject {
    private java.lang.String fileName;

    private java.lang.String file;

    public UploadFile() {
        super("", "");
    }
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }

    public java.lang.String getFileName(java.lang.String fileName) {
        return this.fileName;
    }

    public void setFile(java.lang.String file) {
        this.file = file;
    }

    public java.lang.String getFile() {
        return this.file;
    }

    public int getPropertyCount() {
        return 2;
    }

    public Object getProperty(int __index) {
        switch(__index)  {
        	case 0: return fileName;
        	case 1: return file;
        }
        return null;
    }

    public void setProperty(int __index, Object __obj) {
        switch(__index)  {
        	case 0: fileName = (java.lang.String) __obj; break;
        	case 1: file = (java.lang.String) __obj; break;
        }
    }

    public void getPropertyInfo(int __index, Hashtable __table, PropertyInfo __info) {
        switch(__index)  {
        case 0:
            __info.name = "FileName";
            __info.type = java.lang.String.class; break;
        case 1:
            __info.name = "File";
            __info.type = java.lang.String.class; break;
        }
    }

}
