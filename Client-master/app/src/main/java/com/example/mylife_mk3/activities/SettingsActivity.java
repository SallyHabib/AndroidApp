package com.example.mylife_mk3.activities;

/**
 * Created by sally on 3/31/2018.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mylife_mk3.fragments.SettingsFragment;

/**
 * Created by Hp on 02-Mar-17.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
