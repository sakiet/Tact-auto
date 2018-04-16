package Utils;

import io.appium.java_client.remote.MobilePlatform;

public class MobileAttribute {
    final String osType;

    final  String appPath;
    final String device;
    final String deviceType;
    static final  String appPathStr = "/Users/sakie/workspace/testingEnv/CucumberBasics/Applications/TactApplication-dev-debug.apk";
    static final String deviceStr = "android:8.1";
    static final String deviceTypeStr = "Android Emulator";

    public MobileAttribute() {
        osType =MobilePlatform.IOS;

        appPath = "/Users/sakie/workspace/testingEnv/CucumberBasics/Applications/TactAuth.app";
        device = "iphone:11.2";
        deviceType = "iPhone Simulator";
    }

    public MobileAttribute(String mobile) {
        osType = MobilePlatform.ANDROID;

        appPath = "/Users/sakie/workspace/testingEnv/CucumberBasics/Applications/TactApplication-dev-debug.apk";
        device = "android:8.1";
        deviceType = "Android Emulator";
    }
}