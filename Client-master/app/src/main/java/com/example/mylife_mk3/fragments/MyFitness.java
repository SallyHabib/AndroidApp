package com.example.mylife_mk3.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
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

import static java.sql.DriverManager.println;

/**
 * Created by sally on 3/6/2018.
 */

public class MyFitness extends android.app.Fragment {
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");
    String name="";
    String weightString="";
    String heightString="";
    String gender="";
    String birthday="";
    Toast toast;
    JSONObject body;
    int daily_steps=0;
    public  MyFitness(){

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fitness_fragement, container, false);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url("http://10.0.2.2:8080/response")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                // No Internet connection.
                println("Failed");
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response){

                int statusCode = response.code();

                try {
                    String responseBody = response.body().string();
                    body = new JSONObject(responseBody);
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
                        daily_steps=user.getInt("averageDailySteps");

                        getActivity().runOnUiThread(new Runnable() {
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
                                daily_stepsText.setText(daily_steps+"");


                            }
                        });

                    }
                } catch (Exception e) {
                    Log.d("exception",e.getMessage());
                }
                getActivity().runOnUiThread(new Runnable() {

                    public void run() {
                        if (!(body.has("key"))) {

                            toast = Toast.makeText(getActivity().getApplicationContext(), "Please Login to your fitness", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.BOTTOM, 0, 70);
                            toast.show();
                            Log.d("zz", "d5l hna");

                        }
                    }
                });


            }
        });

        return rootView;
    }
}
