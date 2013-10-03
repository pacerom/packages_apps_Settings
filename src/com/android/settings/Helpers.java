package com.android.settings;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;

import android.os.RemoteException;
import android.os.ServiceManager;

import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.lang.Process;

class Helpers {
    private static final String TAG = "SettingsHelpers";

    public static void runSuCommand(String cmd) {
        String[] argv = new String[3];
        argv[0] = "su";
        argv[1] = "-c";
        argv[2] = cmd;

        try {
            Process proc = Runtime.getRuntime().exec(argv);
            proc.waitFor();
        }
        catch (Exception e) {
            Log.w(TAG, "runSuCommand: failed to run " + cmd);
        }
    }

    public static void restartJava() {
        try {
            final IActivityManager am = ActivityManagerNative.asInterface(ServiceManager.checkService("activity"));
            if (am != null) {
                am.restart();
            }
        }
        catch (RemoteException e) {
            Log.e(TAG, "Failed to get activity manager service");
        }
    }
}
