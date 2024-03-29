package com.example.mylife_mk3.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mylife_mk3.R;
import com.example.mylife_mk3.adapters.ViewPagerAdapter;
import com.example.mylife_mk3.fragments.AddSleepRecordFragment;
import com.example.mylife_mk3.fragments.AddTaskRecordFragment;
import com.example.mylife_mk3.fragments.SleepDayFragment;
import com.example.mylife_mk3.fragments.SleepMonthFragment;
import com.example.mylife_mk3.fragments.TasksDayFragment;

public class MyTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.tasksPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tasksTabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddTaskRecordFragment(), "Add");
        adapter.addFragment(new TasksDayFragment(), "View");
        viewPager.setAdapter(adapter);
    }

}
