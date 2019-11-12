package hu.symlink.bracelet.soap;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public final class Credentials extends SoapObject {
    private java.lang.String userName;

    private java.lang.String password;

    public Credentials() {
        super("", "");
    }
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    public java.lang.String getUserName(java.lang.String userName) {
        return this.userName;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    public java.lang.String getPassword(java.lang.String password) {
        return this.password;
    }

    public int getPropertyCount() {
        return 2;
    }

    public Object getProperty(int __index) {
        switch(__index)  {
        case 0: return userName;
        case 1: return password;
        }
        return null;
    }

    public void setProperty(int __index, Object __obj) {
        switch(__index)  {
        case 0: userName = (java.lang.String) __obj; break;
        case 1: password = (java.lang.String) __obj; break;
        }
    }

    public void getPropertyInfo(int __index, Hashtable __table, PropertyInfo __info) {
        switch(__index)  {
        case 0:
            __info.name = "UserName";
            __info.type = java.lang.String.class; break;
        case 1:
            __info.name = "Password";
            __info.type = java.lang.String.class; break;
        }
    }

}
