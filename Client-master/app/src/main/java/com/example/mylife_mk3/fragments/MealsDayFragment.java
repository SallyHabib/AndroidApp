package com.example.mylife_mk3.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylife_mk3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealsDayFragment extends Fragment {


    public MealsDayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meals_day, container, false);
    }

}
