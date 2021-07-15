package com.example.myapplication.ui.main;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class MetronomeService extends Service {
    Handler h;

    public long current_bpm = 90;
    String TAG = "Timofey";
    SoundPool sp1 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    int soundId1;
    public final static String ACTION_PLAY = "3";
    public final static String ACTION_PAUSE = "4";
    final int CHANGE_COUNT1 = 2;
    final int CHANGE_BPM = 1;
    private static final String CHANNEL_ID = "1";
    private static final int NOTIFY_ID = 1;
    boolean isPlaying = false;
    int iteration = 0;
    private final IBinder binder = new LocalBinder();

    public MetronomeService() {
        Log.d(TAG, "constructor");
    }




    public class LocalBinder extends Binder {
        public MetronomeService getService() {
            return MetronomeService.this;
        }
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");



        h =  new Handler(){
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case CHANGE_BPM:
                        msg.arg1 = (int) current_bpm;
                        Log.d(TAG, "changed bpm");

                        break;
                    case CHANGE_COUNT1:
                        msg.arg2 = 4;
                        break;
                }
            }
        };




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        soundId1 = sp1.load(getBaseContext(), R.raw.click, 1);
        Log.d(TAG, "startCommand");
        String action = intent.getAction();


        Notification notification = createNotification();

        switch (action) {
            case ACTION_PLAY:
                startForeground(1338, notification);
                isPlaying = true;
                play();
                break;
            case ACTION_PAUSE:
                isPlaying = false;
                play();
                break;
        }


        return START_STICKY;
    }

    private Notification createNotification() {
        Intent notificationIntent = new Intent(this, FragmentPlayer.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification.Builder builder = new Notification.Builder(this)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setContentTitle(getText(R.string.notificationTitle))
                .setContentText(getText(R.string.notificationText))
                .setSmallIcon(R.drawable.exo_notification_small_icon)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, getString(R.string.channel_name), NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(getString(R.string.channel_description));
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            builder.setChannelId(CHANNEL_ID);
        }

        return builder.build();


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private Runnable r = new Runnable() {

        public void run() {


            Log.d(TAG, "current_bpm = " + current_bpm);
            if (isPlaying){
                h.postDelayed(r, 60000/current_bpm);
                sp1.play(soundId1, 1, 1, 0, 0, 1);
            }

        }

    };
    Thread t = new Thread(r);
    private void changeBpm (long bpm){
        Message msg = h.obtainMessage(CHANGE_BPM, (int) current_bpm, 4);
        h.sendEmptyMessage(CHANGE_BPM);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");


    }

    public void play() {

        if (isPlaying){
            Log.d(TAG, "play");

            h.post(r);

        }
        else
        {
            Log.d(TAG, "stop");

            h.removeCallbacks(r);
        }

    }


}