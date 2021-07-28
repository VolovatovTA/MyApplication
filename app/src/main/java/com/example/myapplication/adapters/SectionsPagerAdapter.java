package com.example.myapplication.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.R;
import com.example.myapplication.ui.main.fragments.FragmentList;
import com.example.myapplication.ui.main.fragments.FragmentPlayer;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.metronom, R.string.list};

    public SectionsPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0 )
            return new FragmentPlayer();
        else if (position == 1)
            return new FragmentList();
        else return null;        }

    @Override
    public int getCount() {
        return 2;
    }
}