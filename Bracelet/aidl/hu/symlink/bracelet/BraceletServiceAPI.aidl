package hu.symlink.bracelet;
 
import android.bluetooth.BluetoothDevice;
import hu.symlink.bracelet.Measure;
import hu.symlink.bracelet.BraceletListener;
 
interface BraceletServiceAPI {
 
  // Connection
  void connectBluetoothDevice(String address);
 
  // Measure
  Measure getLatestMeasure();
  int getConnectionStatus();
  void addListener(BraceletListener listener);
  void removeListener(BraceletListener listener);
  
  // Settings
  void restartService();
}