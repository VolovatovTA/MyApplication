package com.example.myapplication.ui.main;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class MetronomeService extends Service implements SoundPool.OnLoadCompleteListener {
    public static final String ACTION_NONE = "5";
    Handler h;

    public static long current_bpm;
    static String TAG = "Timofey";
    SoundPool sp;
    int soundId1;
    public final static String ACTION_PLAY = "3";
    public final static String ACTION_PAUSE = "4";
    private static final String CHANNEL_ID = "1";
    boolean isPlaying = false;

    public MetronomeService() {
        Log.d(TAG, "constructor");
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        Toast t = Toast.makeText(getBaseContext(), R.string.textLoadComplete, Toast.LENGTH_SHORT);
        t.show();
        sp.play(soundId1,1,1,1,10,1);
    }




    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        h = new Handler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sp = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build())
                    .build();
        } else sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sp.load(getBaseContext(), R.raw.wood, 0);
        sp.setOnLoadCompleteListener(this);


    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {




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



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void play() {
//        if (isPlaying){
//            Log.d(TAG, "play");
//
//            h.post(this);
//        }
//        else
//        {
//            Log.d(TAG, "stop");
//            h.removeCallbacks(this);
//        }
        h.post(r);
//
//        Log.d(TAG, "get_bpm1 = " + getBpm());
//        setBpm(95);
//        Log.d(TAG, "get_bpm2 = " + getBpm());
//        int b  = 100;
//        setBpm(b);
//        Log.d(TAG, "get_bpm2 = " + getBpm());
    }


    Runnable r = new Runnable() {
        @Override
        public void run() {
        if (isPlaying){
            h.postDelayed(this, 60000/current_bpm);
            sp.play(soundId1, 1, 1, 0, 0, 1);
        }
        }
    };
}