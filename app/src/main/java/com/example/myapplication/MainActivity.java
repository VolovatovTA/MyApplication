package com.example.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


import com.example.myapplication.ui.main.MetronomeService;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    MetronomeService service;
    SectionsPagerAdapter sectionsPagerAdapter;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


    }

    @Override
    protected void onStart() {
//        Intent intent = new Intent(this, MetronomeService.class);
//        startService(intent);
//        bindService(intent, this, Context.BIND_AUTO_CREATE);
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder iBinder) {
        Log.d("startCommand", "Service is Connected");
//        MetronomeService.LocalBinder binder = (MetronomeService.LocalBinder) iBinder;
//        service = binder.getService();
//        service.play();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}