package com.example.myapplication.database;

import android.content.Context;

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

    public Track getItemById(int id, Context context){
        Track track = getFromDataBase(context).get(id);
        return track;
    }
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
        model.putTrack(track);
    }
}
