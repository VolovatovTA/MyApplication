package com.example.myapplication;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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

public class MainActivity extends AppCompatActivity implements ServiceConnection {


    SectionsPagerAdapter sectionsPagerAdapter;
    public ViewPager viewPager;
    TabLayout tabs;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                Fragment frag1 = getFragmentManager().findFragmentByTag(tabs.getTag(0));
//
//                ListView listView = frag1.getView().findViewById(R.id.lvList);
//                MyAdapter myAdapter = (MyAdapter) listView.getAdapter();
//                myAdapter.notifyDataSetChanged();

//                if (position == 1) ; getSupportFragmentManager().s

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
        Log.d("startCommand","Service is Connected");
//        MetronomeService.LocalBinder binder = (MetronomeService.LocalBinder) iBinder;
//        service = binder.getService();
//        service.play();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}