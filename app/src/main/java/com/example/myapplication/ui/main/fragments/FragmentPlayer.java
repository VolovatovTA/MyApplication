package com.example.myapplication.ui.main.fragments;

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
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.database.Repository;
import com.example.myapplication.services.MetronomeService;
import com.example.myapplication.ui.main.Saver;
import com.example.myapplication.view.VerticalSeekBar;
import com.google.android.material.slider.Slider;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


public class FragmentPlayer extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SoundPool.OnLoadCompleteListener, ServiceConnection, SeekBar.OnSeekBarChangeListener, TextView.OnEditorActionListener {

    String TAG = "Timofey";
    VerticalSeekBar verticalSeekBar;
    Button btn_1m;
    Button btn_5m;
    Button btn_10m;
    Button btn_1p;
    Button btn_5p;
    Button btn_10p;
    Spinner count1;
    Spinner count2;
    Button tap;
    Button saveInList;
    Intent intent;



    CompoundButton compoundButton_play;
    CompoundButton compoundButton_accent;

//    MetronomeService metronomeService = new MetronomeService();
    long millis;
    int statusOfLoadComplete = 0;
    public EditText bpm_EditText;
    boolean bound;
    ServiceConnection sConn;
    public MetronomeService metronomeService;

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
        count1 = rootView.findViewById(R.id.count1);
        count2 = rootView.findViewById(R.id.count2);
        tap = rootView.findViewById(R.id.tap);
        saveInList = rootView.findViewById(R.id.saveList);
        bpm_EditText = rootView.findViewById(R.id.editTextNumber);
        verticalSeekBar = rootView.findViewById(R.id.seekBar1);

        compoundButton_play = rootView.findViewById((R.id.play));
        compoundButton_accent = rootView.findViewById((R.id.accent));


        compoundButton_play.setOnCheckedChangeListener(this);
        compoundButton_accent.setOnCheckedChangeListener(this);

        verticalSeekBar.setMax(200);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            verticalSeekBar.setMin(30);
        }
        verticalSeekBar.setProgress(90);
        bpm_EditText.setText(String.valueOf(verticalSeekBar.getProgress()));
        bpm_EditText.setOnEditorActionListener(this);
        verticalSeekBar.setOnSeekBarChangeListener(this);

        btn_1m.setOnClickListener(this);
        btn_5m.setOnClickListener(this);
        btn_10m.setOnClickListener(this);
        btn_1p.setOnClickListener(this);
        btn_5p.setOnClickListener(this);
        btn_10p.setOnClickListener(this);
        tap.setOnClickListener(this);
//        count1.setOnClickListener(this);
//        count2.setOnClickListener(this);
        saveInList.setOnClickListener(this);
        intent = new Intent(getContext(), MetronomeService.class);
        String[] chose_of_count1 = {"2", "4", "6", "8", "12", "16"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, chose_of_count1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        count1.setAdapter(adapter);
        count2.setAdapter(adapter);
//        count1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d(TAG, "number = " + count1.getItemAtPosition(i).toString() + " number beats");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        getActivity().startService(intent);

        sConn = new ServiceConnection() {

            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d("Timofey", "MainActivity onServiceConnected");
                bound = true;
                metronomeService = ((MetronomeService.MyBinder) binder).getService();
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d("Timofey", "MainActivity onServiceDisconnected");
                bound = false;
            }
        };

        return rootView;

    }


    long ms = 0;
    long[] intervals = new long[5];
    int i = 1;
    int interval;
    int times = 0;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bn1m) {verticalSeekBar.setProgress(verticalSeekBar.getProgress() - 1);}
        else if (v.getId() == R.id.bn5m) {verticalSeekBar.setProgress(verticalSeekBar.getProgress() - 5);}
        else if (v.getId() == R.id.bn10m) {verticalSeekBar.setProgress(verticalSeekBar.getProgress() - 10);}
        else if (v.getId() == R.id.bn1p) {verticalSeekBar.setProgress(verticalSeekBar.getProgress() + 1);}
        else if (v.getId() == R.id.bn5p) {verticalSeekBar.setProgress(verticalSeekBar.getProgress() + 5);}
        else if (v.getId() == R.id.bn10p) {verticalSeekBar.setProgress(verticalSeekBar.getProgress() + 10);}
        else if (v.getId() == R.id.tap) {
            if (times != 0) {intervals[i - 1] = System.currentTimeMillis() - ms;
            interval = (int) LongStream.of(intervals).sum()/(times);}
            Log.d(TAG, "i = " + i + "; interval = " + interval);
            if (ms != 0) verticalSeekBar.setProgress(60000/interval);
            ms = System.currentTimeMillis();
            i = i < 4 ? i + 1: 1;
            times = times < 4 ? times + 1: 4;

        }
        else if (v.getId() == R.id.saveList) {
            Intent intent = new Intent(getActivity(), Saver.class);
            intent.putExtra("temp", verticalSeekBar.getProgress());
            intent.putExtra("accent", compoundButton_accent.isChecked());
            intent.putExtra("number_sounds", Integer.parseInt(count1.getSelectedItem().toString()));
            intent.putExtra("number_share",  Integer.parseInt(count2.getSelectedItem().toString()));
            startActivity(intent);
        }
    }




    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


        switch (buttonView.getId()) {
            case R.id.play:
                if (isChecked){
                    metronomeService.play();
                }
                else {
                    metronomeService.stop();
                }
                break;
            case R.id.accent:
                break;
        }

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
//        int bpm = seekBar.getProgress();
        bpm_EditText.setText(String.valueOf(seekBar.getProgress()));
        metronomeService.current_bpm = seekBar.getProgress();
        Log.d(TAG, "current bpm = " + metronomeService.current_bpm);



    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().bindService(intent, sConn, 0);

    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unbindService(sConn);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        metronomeService.stopSelf();
        Repository.getInstance().close();
        Log.d(TAG, "onDestroy FragmentPlayer");

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.d(TAG, v.getText().toString());
        verticalSeekBar.setProgress(Integer.parseInt(v.getText().toString()));

        return false;
    }
}