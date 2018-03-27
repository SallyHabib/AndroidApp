package com.example.mylife_mk3.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.example.mylife_mk3.R;

import java.io.IOException;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.Toast;

import okhttp3.*;

import static android.content.Context.MODE_PRIVATE;
import static java.sql.DriverManager.println;

/**
 * Created by sally on 3/6/2018.
 */

public class MyFitness extends android.app.Fragment {
    protected FragmentActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (FragmentActivity) activity;
    }
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");
    String name="";
    String weightString="";
    String heightString="";
    String user_id="empty";
    String gender="";
    String birthday="";
    Toast toast;
    JSONObject body;
    int daily_steps=0;
    double strideLengthWalking=0.0;
    private SharedPreferences sharedPreferences;
    public  MyFitness(){

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fitness_fragement, container, false);
        sharedPreferences = getContext().getSharedPreferences(getContext().getString(R.string.preference_file_key), getContext().MODE_PRIVATE);
         String user_id= sharedPreferences.getString("databaseID", "");
         Log.d("off","offf");
         println("ooo");
        Log.d("zozo",user_id);
        if(!(user_id.equals(""))) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .get()
                    .url("https://secure-fortress-31275.herokuapp.com/response?database_id=" + user_id)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    try {


                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toast = Toast.makeText(mActivity.getApplicationContext(), "internal server error", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.BOTTOM, 0, 70);
                                toast.show();
                                Log.d("zz", "d5l hna");
                            }
                        });
                    } catch (Exception zz) {
                        Log.d("message", zz.getMessage());

                    }
                }

                @Override
                public void onResponse(Call call, final Response response) {

                    int statusCode = response.code();

                    try {
                        if(response.body()!=null) {
                            String responseBody = response.body().string();
                            body = new JSONObject(responseBody);
                            if (body != null) {
                                if (body.has("key")) {
                                    JSONObject user = body.getJSONObject("key");
                                    Log.d("zozo", user.toString());
                                    int weight = user.getInt("weight");
                                    int height = user.getInt("height");
                                    Log.d("zozo", weight + "");
                                    name = user.getString("fullName");
                                    weightString = weight + "";
                                    heightString = height + "";
                                    gender = user.getString("gender");
                                    birthday = user.getString("dateOfBirth");
                                    daily_steps = user.getInt("averageDailySteps");
                                    strideLengthWalking = user.getDouble("strideLengthWalking");

                                    mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            TextView fullName = (TextView) rootView.findViewById(R.id.fullName);
                                            fullName.setText(name);

                                            LinearLayout weightLayout = (LinearLayout) rootView.findViewById(R.id.weight_layout);
                                            TextView weigttText = (TextView) weightLayout.findViewById(R.id.weight);
                                            weigttText.setText(weightString);

                                            TextView heightText = (TextView) rootView.findViewById(R.id.height);
                                            heightText.setText(heightString);

                                            TextView genderText = (TextView) rootView.findViewById(R.id.gender);
                                            genderText.setText(gender);

                                            TextView birthdatText = (TextView) rootView.findViewById(R.id.user_birthday_text);
                                            birthdatText.setText(birthday);

                                            TextView daily_stepsText = (TextView) rootView.findViewById(R.id.steps);
                                            daily_stepsText.setText(daily_steps + "");

                                            TextView strideLengthWalkingTe = (TextView) rootView.findViewById(R.id.strideLengthWalkingText);
                                            strideLengthWalkingTe.setText(strideLengthWalking + "");

                                        }
                                    });

                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                    }
                    try {


                        mActivity.runOnUiThread(new Runnable() {

                            public void run() {
                                if(response.body()!=null) {
                                    if (body != null) {
                                        if (!(body.has("key"))) {

                                            toast = Toast.makeText(getActivity().getApplicationContext(), "Please Login to your fitness or allow the app to access your data", Toast.LENGTH_LONG);
                                            toast.setGravity(Gravity.BOTTOM, 0, 70);
                                            toast.show();
                                            Log.d("zz", "d5l hna");

                                        }
                                    }
                                }
                            }
                        });
                    } catch (Exception z) {
                        Log.d("message", z.getMessage());
                    }

                }
            });
        }
        return rootView;
    }
}
