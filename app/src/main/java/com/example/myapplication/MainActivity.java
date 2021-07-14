package com.example.myapplication;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.example.myapplication.ui.main.FragmentLibrary;
import com.example.myapplication.ui.main.MetronomeService;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.ui.main.SectionsPagerAdapter;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

        SectionsPagerAdapter sectionsPagerAdapter;
    public ViewPager viewPager;
    TabLayout tabs;
    int i = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}