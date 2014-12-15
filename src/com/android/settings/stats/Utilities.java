/*
 * Copyright (C) 2012 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.stats;

import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.math.BigInteger;
import java.net.NetworkInterface;
import java.security.MessageDigest;

import android.util.Log;

public class Utilities {
    public static String getDeviceTargetName() {
        return SystemProperties.get("ro.product.device");
    }

    public static String getDeviceModelName() {
        return SystemProperties.get("ro.product.model");
    }

    public static String getAndroidVersion() {
        return SystemProperties.get("ro.build.version.release");
    }

    public static String getRomName() {
        return SystemProperties.get("ro.rom.name");
    }

    public static String getRomVersion() {
        return SystemProperties.get("ro.rom.version");
    }

    public static String getCarrierName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String carrier = tm.getNetworkOperatorName();
        if (TextUtils.isEmpty(carrier)) {
            carrier = "Unknown";
        }
        return carrier;
    }

    public static String getCarrierId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierId = tm.getNetworkOperator();
        if (TextUtils.isEmpty(carrierId)) {
            carrierId = "0";
        }
        return carrierId;
    }

    public static String getCountryCode(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCode = tm.getNetworkCountryIso();
        if (TextUtils.isEmpty(countryCode)) {
            countryCode = "xx";
        }
        return countryCode;
    }

    public static String getDeviceId(Context ctx) {
        String device_id = null;
        String wifiInterface = SystemProperties.get("wifi.interface");
        try {
            String wifiMac = new String(NetworkInterface.getByName(
                    wifiInterface).getHardwareAddress());
            device_id = digest(wifiMac);
        } catch (Exception e) {
        }
        return device_id;
    }

    public static String digest(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return new BigInteger(1, md.digest(input.getBytes())).toString(16);
        } catch (Exception e) {
            return null;
        }
    }
}
