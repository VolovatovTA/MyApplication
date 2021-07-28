package com.example.myapplication.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.Track;
import com.example.myapplication.ui.main.fragments.FragmentPlayer;

import java.util.Observer;

import rx.Observable;


public class MetronomeService extends Service implements SoundPool.OnLoadCompleteListener {
    public Handler h;

    public long current_bpm = 90;
    static String TAG = "Timofey";
    SoundPool sp;
    int soundId1;
    private static final String CHANNEL_ID = "1";
    //    boolean isPlaying = false;
    public Thread t;
    MyBinder binder = new MyBinder();


    public MetronomeService() {
        Log.d(TAG, " MetronomeService constructor");
    }


    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        Toast t = Toast.makeText(getBaseContext(), R.string.textLoadComplete, Toast.LENGTH_SHORT);
        t.show();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        h = new Handler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                sp = new SoundPool.Builder()
                        .setMaxStreams(100)
                        .setAudioAttributes(new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .setFlags(AudioAttributes.FLAG_LOW_LATENCY)
                                .build())
                        .build();
            }
        } else sp = new SoundPool(100, AudioManager.STREAM_MUSIC, 0);
        soundId1 = sp.load(getBaseContext(), R.raw.wood, 0);
        sp.setOnLoadCompleteListener(this);


    }

    

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        Observable<Integer> observable = Observable.range(2, 4);
        rx.Observer<Integer> observer = new rx.Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError" + e);
            }

            @Override
            public void onNext(Integer i) {
                Log.d(TAG, "onNext " + i);
                sp.play(soundId1, 1, 1, 1, 0, 1);

            }
        };
        observable.subscribe(observer);




        Log.d(TAG, "startCommand");


        Notification notification = createNotification();


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

    public IBinder onBind(Intent arg0) {
        Log.d(TAG, "MyService onBind");
        return binder;
    }

    public class MyBinder extends Binder {
        public MetronomeService getService() {
            return MetronomeService.this;
        }
    }

    public void play() {

    }



}