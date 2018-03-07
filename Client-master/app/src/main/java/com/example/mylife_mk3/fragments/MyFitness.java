package com.example.mylife_mk3.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylife_mk3.R;

/**
 * Created by sally on 3/6/2018.
 */

public class MyFitness extends android.app.Fragment {

    public  MyFitness(){

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fitness_fragement, container, false);

        return rootView;
    }
}
