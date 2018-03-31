package com.example.mylife_mk3.activities;

/**
 * Created by sally on 3/31/2018.
 */

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylife_mk3.R;
import com.example.mylife_mk3.fragments.SettingsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.gravity;
import static java.security.AccessController.getContext;

/**
 * Created by Hp on 11-Feb-17.
 */

public class AccelerometerSensor extends AppCompatActivity implements SensorEventListener {

    static String TAG = "AccelerometerSensor";
    SensorManager manager;
    Sensor mAccelerometer;
    boolean isLocked;
    int i;
    Map<String, String> params;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Log.i(TAG, "AccelerometerSensor.onCreate : okay!");

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    public final void onStart() {
        super.onStart();
        Log.i(TAG, "AccelerometerSensor : okay!");
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }
//    public AccelerometerSensor{
//
//    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        final String user_id= sharedPreferences.getString("databaseID", "");
        if (SettingsFragment.isCheckedLocked) {
            // locked screen
            KeyguardManager myKM = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);

            if (myKM.inKeyguardRestrictedInputMode()) {
                //it is locked
                isLocked = true;
            } else {
                //it is not locked
                isLocked = false;
            }
//            Log.v("", "" + "isLocked:" + isLocked);
        } else {
            isLocked = true;
        }

        if (isLocked) {
            i++;
        }
        params = new HashMap<>();

        if (i == 2 && isLocked) {
            i = 0;
            params.put("user_id", user_id);
            params.put("token", WelcomeActivity.sharedpreferencesLG.getString("token", "err"));
            params.put("start_time", "1:0:0");
            params.put("end_time", "1:0:0");
            params.put("start_date", "1-1-1");
            params.put("end_date", "1-1-1");
            params.put("offset", "+2:00");
            final String deleteURL = "https://servermarch25-18.herokuapp.com/API/deleteDuplicates";
            RequestQueue queue = Volley.newRequestQueue(this);

            JSONObject JsnObj = new JSONObject(params);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, deleteURL, JsnObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        boolean success = response.getBoolean("success");
                        Log.v("", "" + "successDelete:" + success);
                        if (success) {
                            Log.v(TAG, "YAAAAY!!");
                            params.put("token", WelcomeActivity.sharedpreferencesLG.getString("token", "err"));
                            params.put("user_id", user_id);
                            params.put("start_time", "2:0:0");
                            params.put("duration", "yaraab");
                            params.put("end_time", "2:0:0");
                            params.put("start_date", "2-2-2");
                            params.put("end_date", "2-2-2");
                            params.put("start_day", "ay 7ga");
                            params.put("end_day", "ay 7ga");
                            params.put("offset", "+2:00");
                        } else {
                            String err = response.getString("message");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "LoginPage.OnErrorResponse : okay!");
                    String err2 = null;

                    err2 = error.toString();
                    Log.i(TAG, "LoginPage.OnErrorResponse : " + err2);


                }
            });


            queue.add(request);
        } else

        {
            if(isLocked && i>0)
            {

                params.put("token", WelcomeActivity.sharedpreferencesLG.getString("token", "err"));
                params.put("user_id", user_id);
                params.put("start_time", "1:0:0");
                params.put("end_time", "1:0:0");
                params.put("start_date", "1-1-1");
                params.put("end_date", "1-1-1");
                params.put("duration", "w malo");
                params.put("start_day", "ay 7ga");
                params.put("end_day", "ay 7ga");
                params.put("offset", "+2:00");
            }
        }
        if(isLocked && i>0)
        {
            final String addRecordURL = " https://servermarch25-18.herokuapp.com/API/addSleepTime";

            RequestQueue queue = Volley.newRequestQueue(this);

            final JSONObject JsnObj = new JSONObject(params);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, addRecordURL, JsnObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        boolean success = response.getBoolean("success");

                        Log.v("", "" + "successADD:" + success);
                        if (success) {
                            Log.v("", "" + "addURL:" + "aaaaaddddddeed");
//                                    Log.v("", "" + "duration:" + response.get("duration"));
                            JSONObject record = response.getJSONObject("record");

//                                    Log.v("", "" + "sleep_id:" + sharedpreferences.getString("sleep_id", "err"));

                        } else {
                            String errmsg = response.getString("message");
                            Log.v("", "" + "errmsg:" + errmsg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.v("", "" + "F al catch");
                    }
                }
//                        Log.v("", "" + "hena");

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "MainActivity.OnErrorResponse : okay!");

                    String err2 = error.toString();
                    Log.i(TAG, "LoginPage.OnErrorResponse : " + err2);


                }

            });
            queue.add(request);
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        manager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        manager.unregisterListener(this);

    }
}
