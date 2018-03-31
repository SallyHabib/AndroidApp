package com.example.mylife_mk3.activities;

/**
 * Created by sally on 3/31/2018.
 */

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.media.VolumeProvider;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Intent.ACTION_POWER_CONNECTED;
import static android.content.Intent.ACTION_POWER_DISCONNECTED;

/**
 * Created by Hp on 12-Feb-17.
 */

public class TestLight extends AppCompatActivity {
    static String TAG = "TestLight";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Map<String, String> params = new HashMap<>();
        params.put("token", WelcomeActivity.sharedpreferencesLG.getString("token", "err"));
        params.put("user_id", "58f682d89a78ec42445321f5");
        params.put("start_time", "1:53");
        params.put("end_time", "4:00");
        params.put("start_date", "12-05-2017");
        params.put("end_date", "12-05-2017");
        params.put("offset" , "+02:00");
        String loginURL =  "https://servermarch25-18.herokuapp.com/API/deleteDuplicates";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject JsnObj = new JSONObject(params);
        JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(Request.Method.POST, loginURL, JsnObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean success = response.getBoolean("success");
                    Log.v("", "" + "success:" + success);
                    if (success) {
                        Log.v(TAG , "YAAAAY!!");

                    } else {
                        String err = response.getString("message");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "LoginPage.OnErrorResponse : okay!");
                String err2 = null;

                err2 = error.toString();
                Log.i(TAG, "LoginPage.OnErrorResponse : " + err2);


            }
        });

        queue.add(JsonObjectRequest);
    }





//    private SensorManager manager;
//    private Sensor mLight;
//   static String TAG = "TestLight" ;
//    boolean isPlugged;
//    static boolean isLight ;
//    static float VLight;


//    public final void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.activity_main);
//        Log.i(TAG, "TestLight.onCreate : okay!");
//
//
////        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
////        mLight = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
//
//    }
//    public final void onStart (){
//        super.onStart();
//        Log.i(TAG, "TestLight.onStart : okay!" );
//        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        mLight = manager.getDefaultSensor(Sensor.TYPE_LIGHT);



//       class RecorderTask extends TimerTask {
//            private MediaRecorder recorder;
//
//            public RecorderTask(MediaRecorder recorder) {
//                this.recorder = recorder;
//            }
//
//            public void run() {
//                Log.v("MicInfoService", "amplitude: " + recorder.getMaxAmplitude());
//            }
//        }
//        MediaRecorder recorder = new MediaRecorder();
//        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new RecorderTask(recorder), 0, 1000);
//
//    }
    //    public LightSensor(){
//
//    }
//    public LightSensor() {
//        Log.i(TAG, "lightSensor.constructor : okay!" );
//        //super.onCreate(savedInstanceState);
//       // setContentView(R.layout.activity_main);
//        Log.i(TAG, "lightSensor.onCreate : okay!" );
//
//        // Get an instance of the sensor service, and use that to get an instance of
//        // a particular sensor.
////        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
////        mLight = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
//        //setInstance(this);
//        //onCreate(activity.savedInstanceState);
//        //onStart();
//        //isLight = isSleeping(mLight);
//    }
//    @Override
//    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
//        // Do something here if sensor accuracy changes.
//        Log.d(getClass().getName(), String.format("value = %d", accuracy));
//    }

//    @Override
//    public final void onSensorChanged(SensorEvent event) {
//        Log.i(TAG, "TestLight.onSensorChanged : okay!" );
//        VLight = event.values[0];
//        if (VLight < 1.0){
//            isLight = false ;
//        }
//        else {
//            isLight = true;
//        }
//        Log.v("", "" + isLight);
    // Do something with this sensor data.
    //manager.unregisterListener(this);

//        Intent intent = new Intent(LightSensor.this, MainActivity.class);
//        LightSensor.this.startActivity(intent);



//    public static boolean isSleeping (){
//        Log.i(TAG, "lightSensor.isSleeping : okay!" );
//            if (VLight < 1.0){
//                return false ;
//            }
//            else {
//                return true;
//            }
//        }




//    @Override
//    protected void onResume() {
//        // Register a listener for the sensor.
//        super.onResume();
//        manager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
//
//    }
//
//    @Override
//    protected void onPause() {
//        // Be sure to unregister the sensor when the activity pauses.
//        super.onPause();
//        manager.unregisterListener(this);
//
//    }

//    public MainActivity getInstance() {
//        if(instance==null){
//            setInstance(this);
//        }
//        return instance;
//    }
//
//    public static void setInstance(LightSensor instance) {
//        LightSensor.instance = instance;
//    }






}

