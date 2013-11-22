/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.android.settings;


import android.content.ContentQueryMap;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.UserManager;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class PowerSettings extends SettingsPreferenceFragment
        implements OnSharedPreferenceChangeListener {
    private static final String TAG = "PowerSettings";

    private static final String KEY_SHOW_AIRPLANE_TOGGLE = "power_show_airplane_toggle";
    private static final String KEY_SHOW_SCREENSHOT = "power_show_screenshot";
    private static final String KEY_SHOW_TORCH_TOGGLE = "power_show_torch_toggle";

    private CheckBoxPreference mShowAirplaneToggle;
    private CheckBoxPreference mShowScreenshot;
    private CheckBoxPreference mShowTorchToggle;

    private boolean getAutoState(String name) {
        return Settings.System.getBoolean(getContentResolver(), name, false);
    }

    private void initUI() {
        mShowAirplaneToggle = (CheckBoxPreference) findPreference(KEY_SHOW_AIRPLANE_TOGGLE);
        mShowAirplaneToggle.setChecked(getAutoState(Settings.System.POWER_DIALOG_SHOW_AIRPLANE_TOGGLE));
        mShowScreenshot = (CheckBoxPreference) findPreference(KEY_SHOW_SCREENSHOT);
        mShowScreenshot.setChecked(getAutoState(Settings.System.POWER_DIALOG_SHOW_SCREENSHOT));
        mShowTorchToggle = (CheckBoxPreference) findPreference(KEY_SHOW_TORCH_TOGGLE);
        mShowTorchToggle.setChecked(getAutoState(Settings.System.POWER_DIALOG_SHOW_TORCH_TOGGLE));
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.power_settings);
        initUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
        Log.i(TAG, "onSharedPreferenceChanged: " + key);
        if (key.equals(KEY_SHOW_AIRPLANE_TOGGLE)) {
            boolean value = preferences.getBoolean(key, true);
            Settings.System.putBoolean(getContentResolver(), Settings.System.POWER_DIALOG_SHOW_AIRPLANE_TOGGLE, value);
        }
        if (key.equals(KEY_SHOW_SCREENSHOT)) {
            boolean value = preferences.getBoolean(key, true);
            Settings.System.putBoolean(getContentResolver(), Settings.System.POWER_DIALOG_SHOW_SCREENSHOT, value);
        }
        if (key.equals(KEY_SHOW_TORCH_TOGGLE)) {
            boolean value = preferences.getBoolean(key, true);
            Settings.System.putBoolean(getContentResolver(), Settings.System.POWER_DIALOG_SHOW_TORCH_TOGGLE, value);
        }
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        Log.i(TAG, "onPreferenceTreeClick");
        if (preference == mShowAirplaneToggle) {
            boolean value = mShowAirplaneToggle.isChecked();
            Settings.System.putBoolean(getContentResolver(), Settings.System.POWER_DIALOG_SHOW_AIRPLANE_TOGGLE, value);
        }
        if (preference == mShowScreenshot) {
            boolean value = mShowScreenshot.isChecked();
            Settings.System.putBoolean(getContentResolver(), Settings.System.POWER_DIALOG_SHOW_SCREENSHOT, value);
        }
        if (preference == mShowTorchToggle) {
            boolean value = mShowTorchToggle.isChecked();
            Settings.System.putBoolean(getContentResolver(), Settings.System.POWER_DIALOG_SHOW_TORCH_TOGGLE, value);
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
