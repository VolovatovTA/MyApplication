package com.example.myapplication.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Saver;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.io.IOException;
import java.util.Objects;

public class FragmentPlayer extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    String TAG = "Tim";
    SeekBar seekBar;
    Button btn_1m;
    Button btn_5m;
    Button btn_10m;
    Button btn_1p;
    Button btn_5p;
    Button btn_10p;
    Button tap;
    Button saveInList;
    int soundIdShot;
    SoundPool sp;

    CompoundButton compoundButton_play;
    CompoundButton compoundButton_accent;
//    SimpleExoPlayer player;

    MetronomeService metronomeService;
    long millis;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        soundIdShot = sp.load(getActivity(), R.raw.wood, 1);


        sp.play(soundIdShot, 1, 1, 0, 0, 1);

        metronomeService = new MetronomeService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View rootView = inflater.inflate(R.layout.fragment_player, null);
        btn_1m = (Button) rootView.findViewById(R.id.bn1m);
        btn_5m = (Button) rootView.findViewById(R.id.bn5m);
        btn_10m = (Button) rootView.findViewById(R.id.bn10m);
        btn_1p = (Button) rootView.findViewById(R.id.bn1p);
        btn_5p = (Button) rootView.findViewById(R.id.bn5p);
        btn_10p = (Button) rootView.findViewById(R.id.bn10p);
        tap = (Button) rootView.findViewById(R.id.tap);
        saveInList = (Button) rootView.findViewById(R.id.saveList);



        compoundButton_play = (CompoundButton) rootView.findViewById((R.id.play));
        compoundButton_accent = (CompoundButton) rootView.findViewById((R.id.accent));

        compoundButton_play.setOnCheckedChangeListener(this);
        compoundButton_accent.setOnCheckedChangeListener(this);

        seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
        seekBar.setMax(200);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar.setMin(30);
        }

        btn_1m.setOnClickListener(this);
        btn_5m.setOnClickListener(this);
        btn_10m.setOnClickListener(this);
        btn_1p.setOnClickListener(this);
        btn_5p.setOnClickListener(this);
        btn_10p.setOnClickListener(this);
        tap.setOnClickListener(this);
        saveInList.setOnClickListener(this);
        return rootView;
    }



    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bn1m){
            seekBar.setProgress(seekBar.getProgress() - 1);
        }
        else if(v.getId() == R.id.bn5m){
            seekBar.setProgress(seekBar.getProgress() - 5);
        }
        else if(v.getId() == R.id.bn10m){
            seekBar.setProgress(seekBar.getProgress() - 10);
        }
        else if(v.getId() == R.id.bn1p){
            seekBar.setProgress(seekBar.getProgress() + 1);
        }
        else if(v.getId() == R.id.bn5p){
            seekBar.setProgress(seekBar.getProgress() + 5);
        }
        else if(v.getId() == R.id.bn10p){
            seekBar.setProgress(seekBar.getProgress() + 10);
        }
        else if(v.getId() == R.id.tap){
            millis = System.currentTimeMillis();
        }
        else if(v.getId() == R.id.saveList){
            Intent intent1 = new Intent(getActivity(), Saver.class);
            intent1.putExtra("temp", seekBar.getProgress());
            intent1.putExtra("accent", compoundButton_accent.isChecked());
            intent1.putExtra("number_share", 4);
            intent1.putExtra("number_sounds", 4);
            startActivity(intent1);        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){
            case R.id.play:
                if (isChecked) {
                    play();
                    Log.d(TAG, "playing");
                }
                else metronomeService.stopSelf();




                break;
            case R.id.accent:

                break;
        }


    }

    private void play() {

//        sp1.play(soundId1, 1, 1, 0, 10,  1);


    }

}