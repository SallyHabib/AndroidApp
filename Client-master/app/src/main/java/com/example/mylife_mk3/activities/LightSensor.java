package com.example.mylife_mk3.activities;

/**
 * Created by sally on 3/31/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Hp on 10-Feb-17.
 */

public class LightSensor extends AppCompatActivity implements SensorEventListener {
    private SensorManager manager;
    private Sensor mLight;
    static String TAG = "LightSensor" ;
    static boolean isLight ;
    static float VLight;
    Date timeS1;
    Date timeW1;



    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Log.i(TAG, "lightSensor.onCreate : okay!" );

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
//        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
//                Locale.getDefault());
//        Date currentLocalTime = calendar.getTime();
//        DateFormat date = new SimpleDateFormat("ZZ:WW");
//        String localTime = date.format(currentLocalTime);
//        Log.i(TAG, "GMT offset" +localTime );
//        TimeZone tz = TimeZone.getDefault();
//        Calendar cal = GregorianCalendar.getInstance(tz);
//        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());
//
//        String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
//        offset = (offsetInMillis >= 0 ? "+" : "-") + offset;
//        Log.i(TAG, "GMT offset" +offset );
//        SimpleDateFormat timeS = new SimpleDateFormat("HH:mm");
//        String currentTimeS = timeS.format(new Date());
//        Log.i(TAG, "time format" + currentTimeS. );
        SimpleDateFormat AllS = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String CurrentAllS = AllS.format(new Date());
        try {
            timeS1 = AllS.parse(CurrentAllS);
//            Log.i(TAG, "time format" + timeS1 );
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i(TAG, "time format" + "error" );
        }
        SimpleDateFormat AllW = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String CurrentAllW = AllW.format(new Date());
        try {
            timeW1 = AllW.parse("11-04-2017 15:05");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        long timeDiff = timeW1.getTime() - timeS1.getTime();
        long timeDiffSecs = timeDiff/1000;
        String timeDiffString = timeDiffSecs/3600+":"+
                (timeDiffSecs%3600)/60;

        Log.i(TAG, "Difference" + timeDiffString );

    }
    public final void onStart (){
        super.onStart();
        Log.i(TAG, "lightSensor.onStart : okay!" );
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = manager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }
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
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        Log.d(getClass().getName(), String.format("value = %d", accuracy));
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        Log.i(TAG, "lightSensor.onSensorChanged : okay!" );
        VLight = event.values[0];
        if (VLight < 1.0){
            isLight = false ;
        }
        else {
            isLight = true;
        }
        // Do something with this sensor data.
        //manager.unregisterListener(this);

        Intent intent = new Intent(LightSensor.this, MainActivity.class);
        LightSensor.this.startActivity(intent);

    }

//    public static boolean isSleeping (){
//        Log.i(TAG, "lightSensor.isSleeping : okay!" );
//            if (VLight < 1.0){
//                return false ;
//            }
//            else {
//                return true;
//            }
//        }




    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        manager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        manager.unregisterListener(this);

    }

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

