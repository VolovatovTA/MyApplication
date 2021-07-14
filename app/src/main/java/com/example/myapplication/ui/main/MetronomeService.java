package com.example.myapplication.ui.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;

public class MetronomeService extends Service {
    Handler  h;
    String TAG = "Timofey";
    SoundPool sp1 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    int soundId1;
    final String ACTION_PAUSE = "321";
    boolean isPlaying = false;
    int iteration = 0;
    private final IBinder binder = new LocalBinder();
    public MetronomeService() {
        Log.d(TAG, "constructor");
        h = new Handler();
    }


    public class LocalBinder extends Binder {
        public MetronomeService getService() {
            return MetronomeService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        soundId1 = sp1.load(getBaseContext(), R.raw.click, 1);
        String action = intent.getAction();
        Log.d(TAG, "start");

        if (action == ACTION_PAUSE){
            Log.d(TAG, "stop action");
            stopForeground(true);
            h.removeCallbacks(PlayingSound);
        }



        return super.onStartCommand(intent, flags, startId);
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

    public void play() {
        isPlaying = true;
        Intent intent = new Intent(this, MetronomeService.class);
        intent.setAction(ACTION_PAUSE);
        NotificationCompat.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
                    .createNotificationChannel(new NotificationChannel("metronome", getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT));

            builder = new NotificationCompat.Builder(this, "metronome");
        } else
            builder = new NotificationCompat.Builder(this);

        startForeground(530,
                builder.setContentTitle(getString(R.string.accent_on))
                        .setContentText(getString(R.string.app_name))
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentIntent(PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_ONE_SHOT))
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .build()
        );
        h.post(PlayingSound);
    }

    Runnable PlayingSound = new Runnable() {
        public void run() {
            // планирует сам себя через 1000 мсек
            h.postDelayed(PlayingSound, 100);
           // sp1.play(soundId1,1,1,1,0,1);
            sp1.play(soundId1, 1, 1, 0, 0,  1);

            Log.d(TAG, "run");
        }
    };

}