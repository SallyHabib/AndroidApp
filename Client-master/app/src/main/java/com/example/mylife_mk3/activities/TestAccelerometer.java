package com.example.mylife_mk3.activities;

/**
 * Created by sally on 3/31/2018.
 */

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.FloatMath;
import android.util.Log;


/**
 * Created by Hp on 11-Feb-17.
 */

public class TestAccelerometer extends AppCompatActivity implements SensorEventListener {

    /**
     * Created by Hp on 11-Feb-17.
     */
    private SensorManager manager;
    private Sensor mAccelerometer;
    String TAG = "TestAccelerometer" ;
    //    static double gravity [] ;
//    static double linear_acceleration [];
    boolean isMoving;
    //double gravity[];
    //double linear_acceleration[];
    double x;
    double y ;
    double z;
    double xOld = 0;
    double yOld = 0;
    double zOld = 0;
    private long lastUpdate = 0;
    private static final int SHAKE_THRESHOLD = 600;
//    private float[] mGravity;
//    private float mAccel;
//    private float mAccelCurrent;
//    private float mAccelLast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Log.i(TAG, "TestAccelerometer.onCreate : okay!" );

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    public final void onStart (){
        super.onStart();
        Log.i(TAG, "TestAccelerometer.onStart : okay!" );
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }
//    public AccelerometerSensor{
//
//    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.

//        double alpha = 0.8;
//
//        // Isolate the force of gravity with the low-pass filter.
//        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
//        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
//        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
//
//         //Remove the gravity contribution with the high-pass filter.
//        linear_acceleration[0] = event.values[0] - gravity[0];
//        linear_acceleration[1] = event.values[1] - gravity[1];
//        linear_acceleration[2] = event.values[2] - gravity[2];
////        Log.d("ADebugTag", "X: " + Float.toString(event.values[0]));
////        Log.d("ADebugTag", "Y: " + Float.toString(event.values[1]));
////        Log.d("ADebugTag", "Z: " + Float.toString(event.values[2]));
//        Log.d("tag", "Xnew: " + linear_acceleration[0]);
//        Log.d("tag", "Ynew "  + linear_acceleration[1]);
//        Log.d("tag", "Znew "  + linear_acceleration[2]);
//
//        if(linear_acceleration[0]== 0 && linear_acceleration[1]== 0 && linear_acceleration[2]== 0){
//            isMoving = false;
//        }
//        else{
//            isMoving = true;
//        }
//        Log.v("", "" + isMoving);
        //Log.i(TAG, "TestAccelerometer.onSensorChanged : okay!" );
//            mGravity = event.values.clone();
//            // Shake detection
//            float x = mGravity[0];
//        float y = mGravity[1];
//        float z = mGravity[2];
//        mAccelLast = mAccelCurrent;
//        mAccelCurrent = (float) Math.sqrt(x*x + y*y + z*z);
//        float delta = mAccelCurrent - mAccelLast;
//        mAccel = mAccel * 0.9f + delta;
//        // Make this higher or lower according to how much
//        // motion you want to detect
//        Log.d("ADebugTag", "mAccel: " + Float.toString(mAccel));
//        if(mAccel == 0.0){
//            // do something
//            isMoving = false;
//
//        }
//        else {
//            isMoving = true;
//        }
//        Log.v("", "" + isMoving);
        x = event.values[0];
        x = (int) Math.round(x * 10) / (double) 10;
        y = event.values[1];
        y = (int) Math.round(y * 10) / (double) 10;
        z = (int) Math.round(z * 10) / (double) 10;
        long curTime = System.currentTimeMillis();
        Log.d("ADebugTag", "X: " + x);
        Log.d("ADebugTag", "Y: " + y);
        Log.d("ADebugTag", "Z: " + z);
        Log.d("ADebugTag", "XOld: " + xOld);
        Log.d("ADebugTag", "YOld: " + yOld);
        Log.d("ADebugTag", "ZOld: " + zOld);
//        if ((curTime - lastUpdate) > 100) {
//            long diffTime = (curTime - lastUpdate);
//            lastUpdate = curTime;
//            float speed = (float) (Math.abs(x + y + z - xOld - yOld - zOld) / diffTime * 10000);


        if (xOld == x && yOld == y && zOld == z) {
            isMoving = false;
        } else {
            isMoving = true;
            xOld = x;
            yOld = y;
            zOld = z;
        }
        Log.v("", "" + isMoving);






    }


    //manager.unregisterListener(this);
//            Intent intent = new Intent(com.example.hp.myapplication.AccelerometerSensor.this, MainActivity.class);
//            com.example.hp.myapplication.AccelerometerSensor.this.startActivity(intent);

//    public boolean isMoving(){
//        if(gravity[0] == 0.0 && gravity[1] == 0.0 && gravity[2] == 0.0 ){
//            if(linear_acceleration[0] == 0.0 && linear_acceleration[0] == 0.0 && linear_acceleration[0] == 0.0)
//            {
//                return false;
//            }
//            else {
//                return true;
//            }
//
//        }
//        else {
//            return true;
//        }
//    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(getClass().getName(), String.format("value = %d", accuracy));

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        Log.i(TAG, "TestAccelerometer.onResume : okay!" );
        super.onResume();
        manager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        Log.i(TAG, "TestAccelerometer.Pause : okay!" );
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        manager.unregisterListener(this);

    }
}



