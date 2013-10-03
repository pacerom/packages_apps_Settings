package com.android.settings;

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

    public static void setSystemProp(String prop, String val) {
        runSuCommand("setprop " + prop + " " + val);
    }

    public static void killProcessByName(String name) {
        File procdir = new File("/proc");
        File files[] = procdir.listFiles();
        for (File d: files) {
            if (!d.isDirectory()) {
                continue;
            }
            int pid = 0;
            try {
                pid = Integer.parseInt(d.getName());
            }
            catch (Exception e) {
                // Ignore
            }
            if (pid == 0) {
                continue;
            }
            try {
                FileReader fr = new FileReader(new File(d.getPath() + "/cmdline"));
                char buf[] = new char[256];
                int len = fr.read(buf);
                for (int n = 0; n < len; ++n) {
                    if (buf[n] == '\0') {
                        len = n;
                        break;
                    }
                }
                String cmdline = new String(buf, 0, len);
                if (cmdline.equals(name)) {
                    Log.w(TAG, "killProcessByName: killing pid=" + pid);
                    runSuCommand("kill " + pid);
                }
            }
            catch (Exception e) {
                Log.w(TAG, "killProcessByName: exception reading cmdline");
            }
        }
    }

    public static void restartSystemUI() {
        killProcessByName("com.android.systemui");
    }

    public static void restartJava() {
        killProcessByName("zygote");
    }
}
