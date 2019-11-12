package hu.symlink.bracelet.soap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

@SuppressWarnings("deprecation")
public final class BraceletMobileServiceSoap {
	
	private static Boolean D = true;
	
	// MANIFEST
	private static final String NAMESPACE = "http://soap.bracelet.symlink.hu/";
	final String SOAP_ACTION_BASE = "http://soap.bracelet.symlink.hu/";

    public  boolean login(hu.symlink.bracelet.soap.Credentials credentials) throws Exception {
    	String MethodName = "Login";
    	
    	SoapObject _client = new SoapObject(NAMESPACE, MethodName);
        _client.addProperty("credentials", credentials);
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        
        _envelope.dotNet = true;
        
        // Add double marshaling support
        MarshalDouble mdouble = new MarshalDouble();
        mdouble.register(_envelope);

        // Add date marshaling support
        MarshalDate mdate = new MarshalDate();
        mdate.register(_envelope);
        
        // Need to turn off i:type="..." in response xml data
        _envelope.implicitTypes = (true);

        _envelope.bodyOut = _client;
        AndroidHttpTransport _ht = new AndroidHttpTransport(Configuration.getWsUrl());
        if(D) _ht.debug = true;
        _ht.call(SOAP_ACTION_BASE + MethodName, _envelope);
        return _envelope.getResponse().toString().equals("true");
    }


    public  boolean storeMeasure(hu.symlink.bracelet.soap.MobileMeasure p_measure, hu.symlink.bracelet.soap.Credentials credentials) throws Exception {
    	
    	String MethodName = "StoreMeasure";
    	
        SoapObject _client = new SoapObject(NAMESPACE, MethodName);
        _client.addProperty("p_measure", p_measure);
        _client.addProperty("credentials", credentials);
        
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        
        _envelope.dotNet = true;
        
        // Add double marshaling support
        MarshalDouble mdouble = new MarshalDouble();
        mdouble.register(_envelope);

        // Add date marshaling support
        MarshalDate mdate = new MarshalDate();
        mdate.register(_envelope);
        
        // Need to turn off i:type="..." in response xml data
        _envelope.implicitTypes = (true);
        
        _envelope.bodyOut = _client;
        AndroidHttpTransport _ht = new AndroidHttpTransport(Configuration.getWsUrl());
        if(D) _ht.debug = true;
        _ht.call(SOAP_ACTION_BASE + MethodName, _envelope);
        return _envelope.getResponse().toString().equals("true");
    }


    public  hu.symlink.bracelet.soap.MobileTolerance getTolerance(hu.symlink.bracelet.soap.Credentials credentials) throws Exception {
    	String MethodName = "GetTolerance";
    	
    	SoapObject _client = new SoapObject(NAMESPACE, MethodName);
        _client.addProperty("credentials", credentials);
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        
        _envelope.dotNet = true;
        
        // Add double marshaling support
        MarshalDouble mdouble = new MarshalDouble();
        mdouble.register(_envelope);

        // Add date marshaling support
        MarshalDate mdate = new MarshalDate();
        mdate.register(_envelope);
        
        // Need to turn off i:type="..." in response xml data
        _envelope.implicitTypes = (true);
        
        _envelope.bodyOut = _client;
        AndroidHttpTransport _ht = new AndroidHttpTransport(Configuration.getWsUrl());
        if(D) _ht.debug = true;
        _ht.call(SOAP_ACTION_BASE + MethodName, _envelope);
        SoapObject _ret = (SoapObject) _envelope.getResponse();
        int _len = _ret.getPropertyCount();
        hu.symlink.bracelet.soap.MobileTolerance _returned = new hu.symlink.bracelet.soap.MobileTolerance();
        for (int _i = 0; _i < _len; _i++) {
            _returned.setProperty(_i, _ret.getProperty(_i));        }
        return _returned;
    }


    public  int raiseEvent(hu.symlink.bracelet.soap.MobileEvent mobEvent, hu.symlink.bracelet.soap.Credentials credentials) throws Exception {
    	String MethodName = "RaiseEvent";
    	
    	SoapObject _client = new SoapObject(NAMESPACE, MethodName);
        _client.addProperty("mobEvent", mobEvent);
        _client.addProperty("credentials", credentials);
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        
        _envelope.dotNet = true;
        
        // Add double marshaling support
        MarshalDouble mdouble = new MarshalDouble();
        mdouble.register(_envelope);

        // Add date marshaling support
        MarshalDate mdate = new MarshalDate();
        mdate.register(_envelope);
        
        // Need to turn off i:type="..." in response xml data
        _envelope.implicitTypes = (true);
        
        _envelope.bodyOut = _client;
        AndroidHttpTransport _ht = new AndroidHttpTransport(Configuration.getWsUrl());
        if(D) _ht.debug = true;
        _ht.call(SOAP_ACTION_BASE + MethodName, _envelope);
        int eventID = Integer.parseInt(_envelope.getResponse().toString());
        return eventID;
    }
    
    public  int isActionQueued(hu.symlink.bracelet.soap.Credentials credentials) throws Exception {
    	String MethodName = "IsActionQueued";
    	
    	SoapObject _client = new SoapObject(NAMESPACE, MethodName);
        _client.addProperty("credentials", credentials);
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        
        _envelope.dotNet = true;
        
        // Add double marshaling support
        MarshalDouble mdouble = new MarshalDouble();
        mdouble.register(_envelope);

        // Add date marshaling support
        MarshalDate mdate = new MarshalDate();
        mdate.register(_envelope);
        
        // Need to turn off i:type="..." in response xml data
        _envelope.implicitTypes = (true);
        
        _envelope.bodyOut = _client;
        AndroidHttpTransport _ht = new AndroidHttpTransport(Configuration.getWsUrl());
        if(D) _ht.debug = true;
        _ht.call(SOAP_ACTION_BASE + MethodName, _envelope);
        int result = Integer.parseInt(_envelope.getResponse().toString());
        return result;
    }

    public  boolean uploadRecording(hu.symlink.bracelet.soap.UploadFile file, int eventID, hu.symlink.bracelet.soap.Credentials credentials) throws Exception {
    	String MethodName = "UploadRecording";
    	
    	SoapObject _client = new SoapObject(NAMESPACE, MethodName);
        _client.addProperty("file", file);
        _client.addProperty("eventID", eventID + "");
        _client.addProperty("credentials", credentials);
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        
        _envelope.dotNet = true;
        
        // Add double marshaling support
        MarshalDouble mdouble = new MarshalDouble();
        mdouble.register(_envelope);

        // Add date marshaling support
        MarshalDate mdate = new MarshalDate();
        mdate.register(_envelope);
        
        // Need to turn off i:type="..." in response xml data
        _envelope.implicitTypes = (true);
        
        _envelope.bodyOut = _client;
        AndroidHttpTransport _ht = new AndroidHttpTransport(Configuration.getWsUrl());
        if(D) _ht.debug = true;
        _ht.call(SOAP_ACTION_BASE + MethodName, _envelope);
        return _envelope.getResponse().toString().equals("true");
    }

}
