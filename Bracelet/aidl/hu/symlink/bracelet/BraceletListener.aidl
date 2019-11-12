package hu.symlink.bracelet;

interface BraceletListener {

  void latestMeasureUpdated();
  void connectionStateChanged();
  void batteryLevelChanged(int level);
  void panicOccured();
}