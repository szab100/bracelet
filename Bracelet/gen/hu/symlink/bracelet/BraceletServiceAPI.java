/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\szab100\\workspace\\Bracelet\\aidl\\hu\\symlink\\bracelet\\BraceletServiceAPI.aidl
 */
package hu.symlink.bracelet;
public interface BraceletServiceAPI extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements hu.symlink.bracelet.BraceletServiceAPI
{
private static final java.lang.String DESCRIPTOR = "hu.symlink.bracelet.BraceletServiceAPI";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an hu.symlink.bracelet.BraceletServiceAPI interface,
 * generating a proxy if needed.
 */
public static hu.symlink.bracelet.BraceletServiceAPI asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof hu.symlink.bracelet.BraceletServiceAPI))) {
return ((hu.symlink.bracelet.BraceletServiceAPI)iin);
}
return new hu.symlink.bracelet.BraceletServiceAPI.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_connectBluetoothDevice:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.connectBluetoothDevice(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getLatestMeasure:
{
data.enforceInterface(DESCRIPTOR);
hu.symlink.bracelet.Measure _result = this.getLatestMeasure();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_getConnectionStatus:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getConnectionStatus();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_addListener:
{
data.enforceInterface(DESCRIPTOR);
hu.symlink.bracelet.BraceletListener _arg0;
_arg0 = hu.symlink.bracelet.BraceletListener.Stub.asInterface(data.readStrongBinder());
this.addListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeListener:
{
data.enforceInterface(DESCRIPTOR);
hu.symlink.bracelet.BraceletListener _arg0;
_arg0 = hu.symlink.bracelet.BraceletListener.Stub.asInterface(data.readStrongBinder());
this.removeListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_restartService:
{
data.enforceInterface(DESCRIPTOR);
this.restartService();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements hu.symlink.bracelet.BraceletServiceAPI
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
// Connection

public void connectBluetoothDevice(java.lang.String address) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
mRemote.transact(Stub.TRANSACTION_connectBluetoothDevice, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
// Measure

public hu.symlink.bracelet.Measure getLatestMeasure() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
hu.symlink.bracelet.Measure _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLatestMeasure, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = hu.symlink.bracelet.Measure.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getConnectionStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getConnectionStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void addListener(hu.symlink.bracelet.BraceletListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void removeListener(hu.symlink.bracelet.BraceletListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
// Settings

public void restartService() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_restartService, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_connectBluetoothDevice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getLatestMeasure = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getConnectionStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_addListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_removeListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_restartService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
// Connection

public void connectBluetoothDevice(java.lang.String address) throws android.os.RemoteException;
// Measure

public hu.symlink.bracelet.Measure getLatestMeasure() throws android.os.RemoteException;
public int getConnectionStatus() throws android.os.RemoteException;
public void addListener(hu.symlink.bracelet.BraceletListener listener) throws android.os.RemoteException;
public void removeListener(hu.symlink.bracelet.BraceletListener listener) throws android.os.RemoteException;
// Settings

public void restartService() throws android.os.RemoteException;
}
