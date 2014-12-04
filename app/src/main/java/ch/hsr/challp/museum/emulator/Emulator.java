package ch.hsr.challp.museum.emulator;

import android.os.Build;

public class Emulator {

    public static boolean isEmulator() {
        // http://stackoverflow.com/questions/2799097/how-can-i-detect-when-an-android-application-is-running-in-the-emulator
        return Build.FINGERPRINT.contains("generic");
    }

}
