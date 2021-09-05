package com.example.myapplication.database;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.myapplication.UserModel_Track;

import java.util.ArrayList;

public final class Repository {
    String TAG = "Timofey";


    UserModel_Track model;

    private static final Repository instance = new Repository();
    private Repository(){
        model = new UserModel_Track();
    }

    public static Repository getInstance(){
        return instance;
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public Track getItemById(int id, Context context){
//        Track track = getFromDataBase(context).get(id);
//        return track;
//    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Track> getFromDataBase(Context context) {

        return model.getTracks(context);
    }
    

    
    public ArrayList<Track> getFromRemoteDataBase(){
        ArrayList<Track> tracks = null;
        return null;
    }


    public void deleteItemById(int position) {
        model.deleteItemById(position);
    }

    public void swap(int fromPos, int toPos) {
        model.swap(fromPos, toPos);
    }

    public void putTrack(Track track) {
        Log.d(TAG, "count1 = " + track.count1);

        model.putTrack(track);
    }

    public void close() {model.close();}
}
