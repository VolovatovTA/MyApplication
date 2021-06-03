package com.example.myapplication.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class FragmentPlayer extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player, null);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.play){
            Log.d("Tim", "play");
        }
        else if(v.getId() == R.id.bn1m){
            Log.d("Tim", "not play");

        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.play:             Log.d("Tim", "play"); break;
            case R.id.bn1m:             Log.d("Tim", "b1m"); break;



        }


    }
}