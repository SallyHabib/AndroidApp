package com.example.mylife_mk3.activities;

import android.Manifest;
import android.app.Fragment;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylife_mk3.fragments.MyDayFragment;
import com.example.mylife_mk3.fragments.MyFitness;
import com.example.mylife_mk3.fragments.MyProfileFragment;
import com.example.mylife_mk3.fragments.MyStatsFragment;
import com.example.mylife_mk3.R;
import com.example.mylife_mk3.fragments.SettingsFragment;

import okhttp3.*;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import android.provider.Settings;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final MediaType CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    static String TAG = "MainActivity";
    SensorManager manager;
    List<Sensor> sensors;
    boolean isSleeping;
    boolean isMoving;
    boolean isLight;
    private SharedPreferences sharedPreferences;

    int x;
    int y;
    int z;
    int found = 0;

    Sensor sLight;
    Sensor sAccelerometer;

    TextView textViewSleepFrom;
    TextView textViewSleepTo;
    TextView err;
    LinearLayout myLayout;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    String keyBoolean = "isSleeping";
    boolean isCharging;
    boolean isLocked;
    boolean isNoise;
    boolean isFM;
    String SleepTo;
    String SleepFrom;
    String wrng;
    String wrng2;

    MediaRecorder mRecorder;

    SettingsFragment SF;

    String result1;
    String result2;
    String result;
    String offset;

    String[] days = new String[]{"saturday", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday"};
    String currentTimeS;
    String oldTimeS;
    String CurrentAllS;
    String oldALLS;
    String currentDateS;
    String oldDateS;
    String dayS;
    String olddayS;
    Date timeS1;
    Date timeSO;
    Date timeW1;
    Date timeW0;
    String dayW;
    String oldDayW;
    String currentDateW;
    String oldDateW;
    String CurrentAllW;
    String oldALLW;
    String currentTimeW;
    String oldTimeW;

    Map<String, String> params;

    int sc = 0;
    boolean merge;

    String timeDiffString;

    public void setting() {
        Log.e("yaaaaay", "activityy");
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    //    private HashMap<String, String> user = new HashMap<String, String>();
//    private String serverToken = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View vi = inflater.inflate(R.layout.sleep_layout, null);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        myLayout = (LinearLayout) findViewById(R.id.sleep);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensors = manager.getSensorList(Sensor.TYPE_ALL);
        sLight = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sAccelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        manager.registerListener(this, sLight, SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(this, sAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);


        textViewSleepFrom = (TextView)vi.findViewById(R.id.SleepFrom);
        Log.d("sleepFrom",textViewSleepFrom.getText().toString());
        textViewSleepTo = (TextView)vi.findViewById(R.id.SleepTo);
        err = (TextView)vi.findViewById(R.id.error);

        SF = new SettingsFragment();
        getFragmentManager().beginTransaction().add(SF, "SF").commit();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    0);

        }
        else{
            if (mRecorder == null) {
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setOutputFile("/dev/null");
                try {
                    mRecorder.prepare();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // mRecorder.start();
            }
        }

        final Fragment temp = new MyStatsFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, temp)
                .commit();

        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnv.getMenu().getItem(1).setChecked(true);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment frag = temp;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_profile:
                        MyProfileFragment profile = new MyProfileFragment();
                        getFragmentManager().beginTransaction().remove(frag)
                                .add(R.id.fragmentContainer, profile)
                                .commit();
                        frag = profile;
                        return true;
                    case R.id.action_stats:
                        MyStatsFragment stats = new MyStatsFragment();
                        getFragmentManager().beginTransaction().remove(frag)
                                .add(R.id.fragmentContainer, stats)
                                .commit();
                        frag = stats;
                        return true;
                    case R.id.action_day:
                        MyDayFragment day = new MyDayFragment();
                        getFragmentManager().beginTransaction().remove(frag)
                                .add(R.id.fragmentContainer, day)
                                .commit();
                        frag = day;
                        return true;
                    case R.id.action_fitness:
                        MyFitness fitness = new MyFitness();
                        getFragmentManager().beginTransaction().remove(frag)
                                .add(R.id.fragmentContainer, fitness)
                                .commit();
                        frag = fitness;
                        return true;
                }
                return true;
            }
        });
    }

    public void onStart() {
        super.onStart();
        //Log.i(TAG, "MainActivity.onStart : okay!");

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensors = manager.getSensorList(Sensor.TYPE_ALL);
        sLight = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sAccelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        manager.registerListener(this, sLight, SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(this, sAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        SleepTo = textViewSleepTo.getText().toString();
        SleepFrom = textViewSleepFrom.getText().toString();
        wrng2 = err.getText().toString();

        editor.putString("SleepTo", SleepTo);
        editor.putString("SleepFrom", SleepFrom);
        editor.putString("err", wrng2);

//        Log.v("","" + "test:" + LoginPage.sharedpreferencesLG.getString("token" , ""));

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {


        switch (requestCode) {

            case 0:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mRecorder == null) {
                        Log.d("HEIH", "GOT PERMISSION! MIC Stuff");
                        mRecorder = new MediaRecorder();
                        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        mRecorder.setOutputFile("/dev/null");
                        try {
                            mRecorder.prepare();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        // mRecorder.start();
                    }
                } else {
                    Toast.makeText(this, "Audio Permission denied", Toast.LENGTH_LONG).show();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    protected void onDestroy() {
        super.onDestroy();
    }


    public double getAmplitude() {
        if (mRecorder != null)
            //return  (mRecorder.getMaxAmplitude()/2700.0);
            return 20 * Math.log10(mRecorder.getMaxAmplitude() / 2700.0);
        else
            return 0;

    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onStop() {
        super.onStop();
        getFragmentManager().beginTransaction().remove(SF);
        getFragmentManager().beginTransaction().add(SF, "SF");
        textViewSleepFrom.setText(sharedpreferences.getString("SleepFrom", "error"));
        textViewSleepTo.setText(sharedpreferences.getString("SleepTo", "error"));
        err.setText(sharedpreferences.getString("err", "err"));
    }

    public void onSensorChanged(SensorEvent event) {
        Log.e("Helloo", "runninng sensotrs");
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        final String user_id= sharedPreferences.getString("databaseID", "");

        editor.putBoolean(keyBoolean, isSleeping);

        editor.putInt("X", x);
        editor.putInt("Y", y);
        editor.putInt("Z", z);
        editor.commit();

        Sensor mySensor = event.sensor;
//        Log.i(TAG, "MainActivity.onSensorChanged : okay!");
        // accelerometer
//        Log.v("", "" + "isCheckedDark:" + SettingsFragment.isCheckedDark);
        //light sensor
        if (mySensor.getType() == Sensor.TYPE_LIGHT) {
            found++;
            if (SettingsFragment.isCheckedDark) {
                if (event.values[0] < 1.0) {
                    isLight = false;
                } else {
                    isLight = true;
                }
            } else {
                isLight = false;
            }

//                Log.v("", "" + "isLight:" + isLight);
        }
//        Log.v("", "" + "isCheckedCalm:" + SettingsFragment.isCheckedCalm);
        if (SettingsFragment.isCheckedCalm) {
            //noise
            double amp = getAmplitude();
//            Log.d(TAG, "AMPLITUDE:" + amp);
            if ((amp > 2.0)) {
                isNoise = true;
            } else {
                isNoise = false;
            }
//            Log.v("", "" + "isNoise:" + isNoise);
            try {

            } catch (RuntimeException stopException) {
                //handle cleanup here
            }
        } else {
            isNoise = false;
        }
//        Log.v("", "" + "isCheckedMov:" + SettingsFragment.isCheckedMov);
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            found++;
            if (SettingsFragment.isCheckedMov) {
                x = (int) event.values[0];
                y = (int) event.values[1];
                z = (int) event.values[2];

                if (sharedpreferences.getInt("X", x) == x && sharedpreferences.getInt("Y", y) == y && sharedpreferences.getInt("Z", z) == z) {
                    isMoving = false;
                } else {
                    isMoving = true;
                }
//
//                Log.v("", "" + "isMoving:" + isMoving);
            }
        } else {
            isMoving = false;
        }
//        Log.v("", "" + "isCheckedCharging:" + SettingsFragment.isCheckedCharging);
        if (SettingsFragment.isCheckedCharging) {
            // battery status
            IntentFilter iFilter = new IntentFilter(
                    Intent.ACTION_BATTERY_CHANGED);

            Intent batteryStatus = this.registerReceiver(null, iFilter);

            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

//            Log.v("", "" + "isCharing:" + isCharging);
        } else {
            isCharging = true;
        }
//        Log.v("", "" + "isCheckedLocked:" + SettingsFragment.isCheckedLocked);
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
        if (SettingsFragment.isCheckedFM) {
            // flight mood
            isFM = Settings.System.getInt(this.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
        } else {
            //it is not locked
            isFM = true;
        }
//        Log.v("", "" + "isCheckedFM:" + SettingsFragment.isCheckedFM);
        // sleeping
        if (found == 2)

        {
            found = 0;
            Log.v("", "" + "isMoving:" + isMoving);
            Log.v("", "" + "isLight:" + isLight);
            Log.v("", "" + "isCharging:" + isCharging);
            Log.v("", "" + "isLocked:" + isLocked);
            Log.v("", "" + "isNoise:" + isNoise);
            Log.v("", "" + "isFM:" + isFM);
            if (!isMoving && !isLight && isCharging && isLocked && !isNoise && isFM) {
                isSleeping = true;
//            Log.v("", ""+ "run.isSleeping:" + isSleeping);
//            Log.v("", ""+ "run.sharedprefernces:" + sharedpreferences.getBoolean(keyBoolean,isSleeping));

                if (!sharedpreferences.getBoolean(keyBoolean, false)) {
                    final Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isSleeping) {
//                                 Log.i(TAG, "MainActivity.run : okay!");
                                Calendar calendar = Calendar.getInstance();
                                editor.putString("dayS", dayS);
                                if (calendar.get(Calendar.DAY_OF_WEEK) < 7) {
                                    int i = calendar.get(Calendar.DAY_OF_WEEK);
                                    dayS = days[i];
//                                    Log.v("Day", days[i]);
                                } else {
                                    int i = calendar.get(Calendar.DAY_OF_WEEK) % 7;
                                    dayS = days[i];
//                                    Log.v("Day", days[i]);
                                }
                                editor.putString("CurrentAllS", CurrentAllS);
                                editor.putString("currentDateS", currentDateS);
                                editor.putString("currentTimeS", currentTimeS);
                                editor.commit();
                                SimpleDateFormat dateS = new SimpleDateFormat("dd-MM-yyyy");
                                currentDateS = dateS.format(new Date());
                                SimpleDateFormat timeS = new SimpleDateFormat("HH:mm");
                                currentTimeS = timeS.format(new Date());
                                SimpleDateFormat AllS = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                                CurrentAllS = AllS.format(new Date());
                                oldALLS = sharedpreferences.getString("CurrentAllS", "error");
                                oldDateS = sharedpreferences.getString("currentDateS", "error");
                                olddayS = sharedpreferences.getString("dayS", "error");
                                oldTimeS = sharedpreferences.getString("currentTimeS", "error");
//                                Log.v("CurrentAllS", CurrentAllS);
//                                Log.v("oldALLS", oldALLS);
                                try {
                                    timeS1 = AllS.parse(CurrentAllS);
//                                    Log.i("timeS1" , timeS1 + "");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    timeSO = AllS.parse(oldALLS);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                result1 = "You slept at " + currentTimeS + " ";
                                textViewSleepFrom.setText(result1);
                                sc = 1;

                            }


                        }
                    }, 6000);
                }

            } else {
                isSleeping = false;
            }
            if (sharedpreferences.getBoolean(keyBoolean, isSleeping) && !isSleeping) {
//                Log.i(TAG , "se7y");
                Calendar calendar = Calendar.getInstance();
                editor.putString("dayW", dayW);
                if (calendar.get(Calendar.DAY_OF_WEEK) < 7) {
                    int i = calendar.get(Calendar.DAY_OF_WEEK);
                    dayW = days[i];
//                    Log.v("Day" ,  days[i]);
                } else {
                    int i = calendar.get(Calendar.DAY_OF_WEEK) % 7;
                    dayW = days[i];

                }
                editor.putString("CurrentAllW", CurrentAllW);
                editor.putString("currentDateW", currentDateW);
                editor.putString("currentTimeW", currentTimeW);
                editor.commit();
                SimpleDateFormat dateW = new SimpleDateFormat("dd-MM-yyyy");
                currentDateW = dateW.format(new Date());
                SimpleDateFormat timeW = new SimpleDateFormat("HH:mm");
                currentTimeW = timeW.format(new Date());
                SimpleDateFormat AllW = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                CurrentAllW = AllW.format(new Date());
                oldALLW = sharedpreferences.getString("CurrentAllW", "error");
                oldDateW = sharedpreferences.getString("currentDateW", "error");
                oldDayW = sharedpreferences.getString("dayW", "error");
                oldTimeW = sharedpreferences.getString("currentTimeW", "error");
//                Log.v("CurrentAllW", CurrentAllW);
//                Log.v("oldALLW", oldALLW);
                sc = 2;
                try {
                    timeW1 = AllW.parse(CurrentAllW);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    timeW0 = AllW.parse(oldALLW);
//                    Log.i("timeW0" , timeW0 + "");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        if (timeW1 != null && timeS1 != null) {

            SimpleDateFormat format = new SimpleDateFormat("hh:mm");
            long timeDiff = timeW1.getTime() - timeS1.getTime();
            long timeDiffSecs = timeDiff / 1000;
            timeDiffString = timeDiffSecs / 3600 + ":" +
                    (timeDiffSecs % 3600) / 60;
            if (timeDiffSecs / 3600 == 0) timeDiffString = "00:" +
                    (timeDiffSecs % 3600) / 60;
            if (timeDiffSecs / 3600 < 10) timeDiffString = "0" + timeDiffSecs / 3600 + ":" +
                    (timeDiffSecs % 3600) / 60;
            if ((timeDiffSecs % 3600) / 60 < 10) timeDiffString = timeDiffSecs / 3600 + ":" + "0" +
                    (timeDiffSecs % 3600) / 60 + ":00";
            if ((timeDiffSecs % 3600) / 60 == 0)
                timeDiffString = timeDiffSecs / 3600 + ":00" + ":00";
            if (!(timeW0 == null) && !(timeS1 == null)) {



                long timeDiff2 = timeW0.getTime() - timeS1.getTime();

                long timeDiffSecs2 = timeDiff2 / 1000;
                String timeDiffString2 = timeDiffSecs2 / 3600 + ":" +
                        (timeDiffSecs2 % 3600) / 60;
                if (timeDiffSecs2 / 3600 == 0) timeDiffString2 = "00:" +
                        (timeDiffSecs2 % 3600) / 60;
                if (timeDiffSecs2 / 3600 < 10) timeDiffString2 = "0" + timeDiffSecs2 / 3600 + ":" +
                        (timeDiffSecs2 % 3600) / 60;
                if ((timeDiffSecs2 % 3600) / 60 < 10)
                    timeDiffString2 = timeDiffSecs2 / 3600 + ":" + "0" +
                            (timeDiffSecs2 % 3600) / 60 + ":00";
                if ((timeDiffSecs2 % 3600) / 60 == 0)
                    timeDiffString2 = timeDiffSecs2 / 3600 + ":00" + ":00";
//            Log.i(TAG , ( timeDiffSecs / 3600 == 00) + "test true 2");
//                Log.i("timeDiffSecs2" , timeDiffSecs2 % 3600 / 60 + "" );
                if (((timeDiffSecs2 % 3600) / 60 < 5) && (timeDiffSecs2 / 3600 == 00)) {
                    merge = true;
                } else {
                    merge = false;

                }
            }

//            Log.i(TAG , ((timeDiffSecs % 3600) / 60 > 1) + "test true 1");
            if (((timeDiffSecs % 3600) / 60 < 1) && (timeDiffSecs / 3600 == 00)) {
//                Log.i(TAG , "ml72tesh tnam");
                err.setText("ml72tesh tnam");
            } else {
//                Log.i(TAG , "sc:"+ sc);
                if (sc == 2) {
                    sc = 0;
                    TimeZone tz = TimeZone.getDefault();
                    Calendar cal = GregorianCalendar.getInstance(tz);
                    int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

                    offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
                    offset = (offsetInMillis >= 0 ? "+" : "-") + offset;

                    result2 = "To " + currentTimeW;
                    result = result1 + result2;
                    textViewSleepTo.setText(result2);
                    wrng = "isLight F " + isLight + " isNoise F " + isNoise + " isLocked T " + isLocked + " isMoving F " + isMoving + " isFM T " + isFM + " isCharging T " + isCharging;
                    err.setText(wrng);
                    SleepTo = textViewSleepTo.getText().toString();
                    SleepFrom = textViewSleepFrom.getText().toString();
                    wrng2 = err.getText().toString();

                    editor.putString("SleepFrom", SleepFrom);
                    editor.putString("SleepTo", SleepTo);
                    editor.putString("err", wrng2);


                    params = new HashMap<>();

                    params.put("token", WelcomeActivity.sharedpreferencesLG.getString("token", "err"));
                    params.put("user_id", user_id);
                    params.put("start_time", currentTimeS);
//                        Log.v(TAG,"abl al add" + currentTimeS);
                    params.put("end_time", currentTimeW);
                    params.put("start_date", currentDateS);
                    params.put("end_date", currentDateW);
                    params.put("duration", timeDiffString);
//                        Log.i("duration merge false" , timeDiffString );
                    params.put("start_day", dayS);
                    Log.i("start_day" , dayS );
                    params.put("end_day", dayW);
//                      Log.i("end_day" , dayW );
                    params.put("offset", offset);
                    params.put("sleep_id" , sharedpreferences.getString("sleep_id" , "err" ));
                    Log.i("sleep_id" ,  sharedpreferences.getString("sleep_id" , "err" ));

//                    final String addRecordURL = " https://morning-hollows-45802.herokuapp.com/API/addSleepTime";
//                    params.put("duration", timeDiffString);
//                    Log.v("abl al add duration" , timeDiffString + "");

                    final String addRecordURL = "https://servermarch25-18.herokuapp.com/API/addSleepTime";

                    RequestQueue queue = Volley.newRequestQueue(this);

                    final JSONObject JsnObj = new JSONObject(params);
//                    Log.v("jsnObj" , params + "");


                    JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.POST, addRecordURL, JsnObj, new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean success = response.getBoolean("success");

                                Log.v("", "" + "successADD:" + success);
                                if (success) {
                                    Log.v("", "" + "addURL:" + "aaaaaddddddeed");
//                                    Log.v("", "" + "duration:" + response.get("duration"));
                                    JSONObject record = response.getJSONObject("record");
                                    editor.putString("sleep_id", record.getString("_id"));
                                    editor.commit();
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

                    }, new com.android.volley.Response.ErrorListener() {
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
        }
//        Log.v("", "" + "isSleeping:" + isSleeping);


    }

    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.

        super.onPause();

        textViewSleepFrom.setText(sharedpreferences.getString("SleepFrom", "error"));
        textViewSleepTo.setText(sharedpreferences.getString("SleepTo", "error"));
        err.setText(sharedpreferences.getString("err", "err"));

    }

    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        textViewSleepFrom.setText(sharedpreferences.getString("SleepFrom", "error"));
//        textViewSleepFrom = new TextView(this);
//        textViewSleepFrom.setText(sharedpreferences.getString("SleepFrom", "error"));
        textViewSleepTo.setText(sharedpreferences.getString("SleepTo", "error"));
//        listView.invalidateViews();
        err.setText(sharedpreferences.getString("err", "err"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            setting();
            return true;
        }
        if (id == R.id.action_logout) {

            SharedPreferences preferences = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();

            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
