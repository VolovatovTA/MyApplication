package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.DBHelper;
import com.example.myapplication.database.Track;

import java.util.ArrayList;
import java.util.Collections;

public class UserModel_Track {
    public Cursor cursor;
    SQLiteDatabase database = null;
    DBHelper dbHelper;
    public int count_of_tracks;


    public UserModel_Track(){

    }
    ArrayList<Track> tracks = null;
    public ArrayList<Track> getTracks(Context context){
        dbHelper = new DBHelper(context);
        if(database ==null)

    {
        database = (dbHelper).getWritableDatabase();
    }

    cursor = database.query(DBHelper.TABLE_TRACKS,null,null,null,null,null,null);

        if(cursor.getCount() != 0)
    {
        count_of_tracks = cursor.getCount();
        String[] names = new String[count_of_tracks];
        boolean[] acc = new boolean[count_of_tracks];
        int[] count1 = new int[count_of_tracks];
        int[]count2 = new int[count_of_tracks];
        int[]temp = new int[count_of_tracks];
        int[] ides = new int[count_of_tracks];
        int[] positions = new int[count_of_tracks];
        cursor.moveToFirst();
        int j = 0;

        tracks = new ArrayList<>(cursor.getCount());

        do {
            names[j] = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME));
            temp[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEMP));
            acc[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ACCENT)) != 0;
            count1[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COUNT1));
            count2[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COUNT2));
            ides[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID));
            positions[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_POSITION));
            tracks.add(new Track(names[j], temp[j], acc[j], count1[j], count2[j], positions[j], ides[j]));
            j++;
        } while (cursor.moveToNext());

    }
        else {
            count_of_tracks = 0;
        }
        return tracks;
}

    public void deleteItemById(int position) {
        tracks.remove(position);
        database.delete(DBHelper.TABLE_TRACKS, "id = " + position, null);
    }

    public void swap(int fromPos, int toPos) {
        ContentValues cv_from = new ContentValues();
        ContentValues cv_to = new ContentValues();
        cv_from.put(DBHelper.KEY_POSITION, fromPos);
        cv_to.put(DBHelper.KEY_POSITION, toPos);

        Collections.swap(tracks, fromPos, toPos);

        database.update(DBHelper.TABLE_TRACKS, cv_to, " position = ?", new String[]{fromPos + ""});
        database.update(DBHelper.TABLE_TRACKS, cv_from, " position = ?", new String[]{toPos + ""});
    }
    public void close(){
        database.close();

    }

    public void putTrack(Track track) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_NAME, name_of_track);
        contentValues.put(DBHelper.KEY_TEMP, (int) freq);

        contentValues.put(DBHelper.KEY_COUNT1, count1);
        contentValues.put(DBHelper.KEY_COUNT2, count2);
        
    }
}
