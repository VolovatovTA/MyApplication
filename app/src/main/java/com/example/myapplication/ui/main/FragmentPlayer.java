package com.example.myapplication.ui.main;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Saver;

public class FragmentPlayer extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SoundPool.OnLoadCompleteListener, ServiceConnection, SeekBar.OnSeekBarChangeListener, TextView.OnEditorActionListener {

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
    Intent intent;


    CompoundButton compoundButton_play;
    CompoundButton compoundButton_accent;

//    MetronomeService metronomeService = new MetronomeService();
    long millis;
    int statusOfLoadComplete = 0;
    public EditText bpm_EditText;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View rootView = inflater.inflate(R.layout.fragment_player, null);
        btn_1m = rootView.findViewById(R.id.bn1m);
        btn_5m = rootView.findViewById(R.id.bn5m);
        btn_10m = rootView.findViewById(R.id.bn10m);
        btn_1p = rootView.findViewById(R.id.bn1p);
        btn_5p = rootView.findViewById(R.id.bn5p);
        btn_10p = rootView.findViewById(R.id.bn10p);
        tap = rootView.findViewById(R.id.tap);
        saveInList = rootView.findViewById(R.id.saveList);
        bpm_EditText = rootView.findViewById(R.id.editTextNumber);
        seekBar = rootView.findViewById(R.id.seekBar);

        compoundButton_play = rootView.findViewById((R.id.play));
        compoundButton_accent = rootView.findViewById((R.id.accent));


        compoundButton_play.setOnCheckedChangeListener(this);
        compoundButton_accent.setOnCheckedChangeListener(this);

        seekBar.setMax(200);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar.setMin(30);
        }
        seekBar.setProgress(90);
        bpm_EditText.setText(String.valueOf(seekBar.getProgress()));
        bpm_EditText.setOnEditorActionListener(this);
        seekBar.setOnSeekBarChangeListener(this);

        btn_1m.setOnClickListener(this);
        btn_5m.setOnClickListener(this);
        btn_10m.setOnClickListener(this);
        btn_1p.setOnClickListener(this);
        btn_5p.setOnClickListener(this);
        btn_10p.setOnClickListener(this);
        tap.setOnClickListener(this);
        saveInList.setOnClickListener(this);
        MetronomeService.current_bpm = 90;
        return rootView;
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bn1m) {
            seekBar.setProgress(seekBar.getProgress() - 1);
        } else if (v.getId() == R.id.bn5m) {
            seekBar.setProgress(seekBar.getProgress() - 5);
        } else if (v.getId() == R.id.bn10m) {
            seekBar.setProgress(seekBar.getProgress() - 10);
        } else if (v.getId() == R.id.bn1p) {
            seekBar.setProgress(seekBar.getProgress() + 1);
        } else if (v.getId() == R.id.bn5p) {
            seekBar.setProgress(seekBar.getProgress() + 5);
        } else if (v.getId() == R.id.bn10p) {
            seekBar.setProgress(seekBar.getProgress() + 10);
        } else if (v.getId() == R.id.tap) {
            millis = System.currentTimeMillis();
        } else if (v.getId() == R.id.saveList) {
            Intent intent1 = new Intent(getActivity(), Saver.class);
            intent1.putExtra("temp", seekBar.getProgress());
            intent1.putExtra("accent", compoundButton_accent.isChecked());
            intent1.putExtra("number_share", 4);
            intent1.putExtra("number_sounds", 4);
            startActivity(intent1);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        intent = new Intent(getContext(), MetronomeService.class);

        switch (buttonView.getId()) {
            case R.id.play:
                if (isChecked) {

                    intent.setAction(MetronomeService.ACTION_PLAY);

                    Log.d(TAG, "playing");
                } else {

                    intent.setAction(MetronomeService.ACTION_PAUSE);
                }

                break;
            case R.id.accent:

                break;
        }
        getActivity().startService(intent);
//        MetronomeService metronomeService = getActivity().getSer

    }

    private void play() {
        Toast toast;
        String message;
        if (statusOfLoadComplete == 1) message = "Загрузили";
        else message = "Секундочку, подгружаем звуки";
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();

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
        bpm_EditText.setText(String.valueOf(seekBar.getProgress()));

        MetronomeService.current_bpm = bpm;

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
//        metronomeService.stopSelf();
        Log.d(TAG, "onDestroy FragmentPlayer");

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.d(TAG, v.getText().toString());
        seekBar.setProgress(Integer.parseInt(v.getText().toString()));

        return false;
    }
}