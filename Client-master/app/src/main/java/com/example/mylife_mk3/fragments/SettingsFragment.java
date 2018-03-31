package com.example.mylife_mk3.fragments;

/**
 * Created by sally on 3/31/2018.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.mylife_mk3.R;


/**
 * Created by Hp on 01-Mar-17.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    CheckBoxPreference Dark;
    CheckBoxPreference Calm;
    CheckBoxPreference charging;
    CheckBoxPreference flightmode;
    CheckBoxPreference locked;
    CheckBoxPreference movement;
    final static String TAG = "SettingsFragment";
    public static boolean isCheckedDark;
    public static boolean isCheckedCalm;
    public static boolean isCheckedCharging;
    public static boolean isCheckedFM;
    public static boolean isCheckedLocked;
    public static boolean isCheckedMov;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        Log.i(TAG, "SettingsFragment.onCreate : okay!");
        addPreferencesFromResource(R.xml.preferences);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);


        Dark = (CheckBoxPreference) findPreference("Dark");
        Calm = (CheckBoxPreference) findPreference("calm");
        flightmode = (CheckBoxPreference) findPreference("flight_mode");
        locked = (CheckBoxPreference) findPreference("locked_screen");
        charging = (CheckBoxPreference) findPreference("charge_the_mobile");
        movement = (CheckBoxPreference) findPreference("the_mobile_movement");

        isCheckedCharging = charging.isChecked();
        isCheckedLocked = locked.isChecked();
        isCheckedMov = movement.isChecked();
        isCheckedCalm = Calm.isChecked();
        isCheckedDark = Dark.isChecked();
        isCheckedFM = flightmode.isChecked();


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i(TAG, "SettingsFragment.onSharedPreferenceChanged : okay!");
        if (key.equals("calm")) {
            Calm = (CheckBoxPreference) findPreference("calm");
            if (Calm.isChecked()) {
                isCheckedCalm = true;
            } else {
                isCheckedCalm = false;
            }
            Log.v("", "" + "SettingsFragment.isCheckedCalm:" + isCheckedCalm);
        }
        if (Dark.isChecked()) {
            Dark = (CheckBoxPreference) findPreference("Dark");
            if (sharedPreferences.getBoolean("Dark", false)) {
                isCheckedDark = true;
            } else {
                isCheckedDark = false;
            }
            Log.v("", "" + "SettingsFragment.isCheckedDark:" + isCheckedDark);
        }

        if (key.equals("flight_mode")) {
            flightmode = (CheckBoxPreference) findPreference("flight_mode");
            if (flightmode.isChecked()) {
                isCheckedFM = true;
            } else {
                isCheckedFM = false;
            }
            Log.v("", "" + "SettingsFragment.isCheckedFM:" + isCheckedFM);
        }
        if (key.equals("locked_screen")) {
            locked = (CheckBoxPreference) findPreference("locked_screen");
            if (locked.isChecked()) {
                isCheckedLocked = true;
            } else {
                isCheckedLocked = false;
            }
            Log.v("", "" + "SettingsFragment.isCheckedLocked:" + isCheckedLocked);
        }
        if (key.equals("charge_the_mobile")) {
            charging = (CheckBoxPreference) findPreference("charge_the_mobile");
            if (charging.isChecked()) {
                isCheckedCharging = true;
            } else {
                isCheckedCharging = false;
            }
            Log.v("", "" + "SettingsFragment.isCheckedCharging:" + isCheckedCharging);
        }
        if (key.equals("the_mobile_movement")) {
            movement = (CheckBoxPreference) findPreference("the_mobile_movement");
            if (movement.isChecked()) {
                isCheckedMov = true;
            } else {
                isCheckedMov = false;
            }
            Log.v("", "" + "SettingsFragment.isCheckedMov:" + isCheckedMov);
        }


    }

    @Override
    public void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        isCheckedCharging = charging.isChecked();
        isCheckedLocked = locked.isChecked();
        isCheckedMov = movement.isChecked();
        isCheckedCalm = Calm.isChecked();
        isCheckedDark = Dark.isChecked();
        isCheckedFM = flightmode.isChecked();
    }

    @Override
    public void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        isCheckedCharging = charging.isChecked();
        isCheckedLocked = locked.isChecked();
        isCheckedMov = movement.isChecked();
        isCheckedCalm = Calm.isChecked();
        isCheckedDark = Dark.isChecked();
        isCheckedFM = flightmode.isChecked();
    }

    public void onStop() {
        super.onStop();
        isCheckedCharging = charging.isChecked();
        isCheckedLocked = locked.isChecked();
        isCheckedMov = movement.isChecked();
        isCheckedCalm = Calm.isChecked();
        isCheckedDark = Dark.isChecked();
        isCheckedFM = flightmode.isChecked();
    }
}
