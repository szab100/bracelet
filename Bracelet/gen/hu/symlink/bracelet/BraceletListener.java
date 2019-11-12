/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\szab100\\workspace\\Bracelet\\aidl\\hu\\symlink\\bracelet\\BraceletListener.aidl
 */
package hu.symlink.bracelet;
public interface BraceletListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements hu.symlink.bracelet.BraceletListener
{
private static final java.lang.String DESCRIPTOR = "hu.symlink.bracelet.BraceletListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an hu.symlink.bracelet.BraceletListener interface,
 * generating a proxy if needed.
 */
public static hu.symlink.bracelet.BraceletListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof hu.symlink.bracelet.BraceletListener))) {
return ((hu.symlink.bracelet.BraceletListener)iin);
}
return new hu.symlink.bracelet.BraceletListener.Stub.Proxy(obj);
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
case TRANSACTION_latestMeasureUpdated:
{
data.enforceInterface(DESCRIPTOR);
this.latestMeasureUpdated();
reply.writeNoException();
return true;
}
case TRANSACTION_connectionStateChanged:
{
data.enforceInterface(DESCRIPTOR);
this.connectionStateChanged();
reply.writeNoException();
return true;
}
case TRANSACTION_batteryLevelChanged:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.batteryLevelChanged(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_panicOccured:
{
data.enforceInterface(DESCRIPTOR);
this.panicOccured();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements hu.symlink.bracelet.BraceletListener
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
public void latestMeasureUpdated() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_latestMeasureUpdated, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void connectionStateChanged() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_connectionStateChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void batteryLevelChanged(int level) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(level);
mRemote.transact(Stub.TRANSACTION_batteryLevelChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void panicOccured() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_panicOccured, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_latestMeasureUpdated = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_connectionStateChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_batteryLevelChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_panicOccured = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public void latestMeasureUpdated() throws android.os.RemoteException;
public void connectionStateChanged() throws android.os.RemoteException;
public void batteryLevelChanged(int level) throws android.os.RemoteException;
public void panicOccured() throws android.os.RemoteException;
}
