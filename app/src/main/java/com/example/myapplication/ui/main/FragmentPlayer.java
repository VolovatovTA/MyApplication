package com.example.myapplication.ui.main;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Saver;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.io.IOException;
import java.util.Objects;

public class FragmentPlayer extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SoundPool.OnLoadCompleteListener, ServiceConnection, SeekBar.OnSeekBarChangeListener {

    String TAG = "Timofey";
    SeekBar seekBar;
    Button btn_1m;
    Button btn_5m;
    Button btn_10m;
    Button btn_1p;
    Button btn_5p;
    Button btn_10p;
    Button tap;
    Button saveInList;


    CompoundButton compoundButton_play;
    CompoundButton compoundButton_accent;

    int soundIdShot;
    SoundPool sp;
    MetronomeService metronomeService;
    long millis;
    int statusOfLoadComplete = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        soundIdShot = sp.load(getContext(), R.raw.wood, 1);

        sp.setOnLoadCompleteListener(this);
        compoundButton_play.setOnCheckedChangeListener(this);
        compoundButton_accent.setOnCheckedChangeListener(this);

        seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
        seekBar.setMax(200);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar.setMin(30);
        }
        seekBar.setOnSeekBarChangeListener(this);

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
                Intent intent = new Intent(getContext(), MetronomeService.class);
                if (isChecked) {

                    intent.setAction(MetronomeService.ACTION_PLAY);
                    getActivity().startService(intent);
                    Log.d(TAG, "playing");
                }
                else {

                    intent.setAction(MetronomeService.ACTION_PAUSE);
                    getActivity().startService(intent);
                }

                break;
            case R.id.accent:

                break;
        }


    }

    private void play() {
//
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(getActivity().getBaseContext(), CHANNEL_ID)
//                        .setSmallIcon(R.drawable.exo_notification_small_icon)
//                        .setContentTitle(getString(R.string.notificationTitle))
//                        .setContentText(getString(R.string.notificationText))
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager =
//                NotificationManagerCompat.from(getActivity().getBaseContext());
//        Notification notification = builder.build();
//        notificationManager.notify(NOTIFY_ID, notification);

        if (statusOfLoadComplete == 1){
//           metronomeService.startForeground(31, notification);
        }
        else {Toast toast = Toast.makeText(getContext(),
                "Секундочку, подгружаем звуки", Toast.LENGTH_SHORT);
        toast.show();}
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        statusOfLoadComplete = 1;

    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int bpm = seekBar.getProgress();
        pushToService(bpm);
    }

    private void pushToService(long bpm) {
        metronomeService.current_bpm = bpm;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        metronomeService.stopSelf();
        Log.d(TAG, "onDestroy FragmentPlayer");

    }
}