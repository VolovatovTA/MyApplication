package com.example.myapplication;

import android.os.Bundle;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.myapplication.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    SectionsPagerAdapter sectionsPagerAdapter;
    public ViewPager viewPager;
    TabLayout tabs;
    int i = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        binding.viewPager.setAdapter(sectionsPagerAdapter);
        binding.viewPager.setOffscreenPageLimit(0);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        binding.tabs.setupWithViewPager(binding.viewPager);

        binding.tabs.getTabAt(0).setText(R.string.metronom);
        binding.tabs.getTabAt(1).setText(R.string.list);

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