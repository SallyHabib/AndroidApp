package com.example.mylife_mk3.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mylife_mk3.R;
import com.example.mylife_mk3.activities.MyLocationsActivity;
import com.example.mylife_mk3.activities.MyMealsActivity;
import com.example.mylife_mk3.activities.MyMoodActivity;
import com.example.mylife_mk3.activities.MyProductivityActivity;
import com.example.mylife_mk3.activities.MySleepActivity;
import com.example.mylife_mk3.activities.MyTasksActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.sql.DriverManager.println;


public class MyStatsFragment extends android.app.Fragment {

//    private Map<String, String> user;
//    private String serverToken;

    public MyStatsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.stats_fragment, container, false);
        final View fitnessView  = inflater.inflate(R.layout.fitness_fragement,container,false);
//        Button button = (Button) rootView.findViewById(R.id.mySleep);
//        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AlexandriaFLF.ttf");
//        button.setTypeface(font);
//        button = (Button) rootView.findViewById(R.id.myLocation);
//        button.setTypeface(font);
//        button = (Button) rootView.findViewById(R.id.myProductivity);
//        button.setTypeface(font);

//        final Bundle arguments = getArguments();
//        setUser((HashMap)arguments.getSerializable("hashmap"));
//        setServerToken(arguments.getString("token"));

        Button sleep = (Button) rootView.findViewById(R.id.mySleep);
        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MySleepActivity.class);
                //intent.putExtras(arguments);
                startActivity(intent);
            }
        });

        Button locations = (Button) rootView.findViewById(R.id.myLocation);
        locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyLocationsActivity.class);
                //intent.putExtras(arguments);
                startActivity(intent);
            }
        });

        Button productivity = (Button) rootView.findViewById(R.id.myProductivity);
        productivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProductivityActivity.class);
                //intent.putExtras(arguments);
                startActivity(intent);
            }
        });

        Button meals = (Button) rootView.findViewById(R.id.myMeals);
        meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyMealsActivity.class);
                //intent.putExtras(arguments);
                startActivity(intent);
            }
        });

        Button tasks = (Button) rootView.findViewById(R.id.myTasks);
        tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyTasksActivity.class);
                startActivity(intent);
            }
        });

        Button mood = (Button) rootView.findViewById(R.id.myMood);
        mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyMoodActivity.class);
                startActivity(intent);
            }
        });

        Button fitness = (Button) rootView.findViewById(R.id.myFitness);
        fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.fitbit.com/oauth2/authorize?response_type=code&client_id=22CMZV&redirect_uri=https://secure-fortress-31275.herokuapp.com/fitbit&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&expires_in=604800"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        Button facebook = (Button) rootView.findViewById(R.id.myFacebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/v2.12/dialog/oauth?client_id=188725925227536&redirect_uri=https://secure-fortress-31275.herokuapp.com/facebook&response_type=code&scope=email,user_birthday,user_posts,user_likes"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        return rootView;
    }


}
