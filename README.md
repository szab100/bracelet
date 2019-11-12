# Bracelet
Open-sourcing my Android + ASP.Net web project from 2011, written for a small,
experimental bluetooth medical bracelet device, primarily made for monitoring elder
people.

The bracelet is an atmel-based, tiny medical bracelet device we developed for elder people,
with integrated bluetooth, accelerometer, temperature sensors and an optical heart-rate and blood-oxygen level monitoring module. The device also has a 'panic' button, which triggers a panic (emergency) event, described below.
  
![screenshot_app_main](https://raw.githubusercontent.com/szab100/bracelet/master/screenshots/app-main.png "App Main")
![screenshot_app_measurements](https://raw.githubusercontent.com/szab100/bracelet/master/screenshots/app-measurements.png "App Measurements")

The Android application has a simple UI to connect and pair with the bluetooth device, as well as to view 
current and previous heart rate measurements, configure web portal credentials and change tolerance settings. The app has a 24/7 background service, which remains connected to the bracelet through bluetooth, continuously
records and synchronizes its measurements and settings with the web portal and monitors the accelorometer's data.

In case of an emergency, when either a sudden drop is detected by the application (using accelerometer data), the
panic button is pressed or the heart rate / blood oxygen levels exceed tolerance levels, the application immediately
pushes an emergency message to the web portal and starts voice recording at the same time.

![screenshot_web_events](https://raw.githubusercontent.com/szab100/bracelet/master/screenshots/web-events.png "Web Events")

An AJAX popup notification, along with the phone's GPS location and the voice recording is immediately
displayed to the web poral's operators, who can send help to the user. The operators can change / override
user tolerance levels settings and can also request on-demand measurements from the bracelet, anytime.

For further information, check out the [documentation](https://github.com/szab100/bracelet/raw/master/Bracelet-Documentation-HU.pdf) (in Hungarian language only!).


Requirements:
- Android app: Eclipse ADT / Android Studio (needs project migration), JDK 1.7+, ksoap2-android (provided in lib/ folder)
- Web Portal: VS 2010+, ASP.Net 4.0+, IIS 7+, MySQL

The source code is provided "as-is", without any support, warranties, conditions or responsibilities of any kind.

License: Apache 2.0
