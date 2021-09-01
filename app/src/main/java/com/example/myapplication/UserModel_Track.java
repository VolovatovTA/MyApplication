package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.adapters.Notifyer;
import com.example.myapplication.database.DBHelper;
import com.example.myapplication.database.Track;

import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.rxjava3.core.Observable;

public class UserModel_Track {
    private static final String TAG = "Timofey";
    public Cursor cursor;
    SQLiteDatabase database = null;
    DBHelper dbHelper;


    public UserModel_Track() {
        Log.d(TAG, "UserModel_track constructor");

    }

    ArrayList<Track> tracks;

    public ArrayList<Track> getTracks(Context context) {
        dbHelper = new DBHelper(context);
        if (database == null) {
            database = (dbHelper).getWritableDatabase();
        }

        cursor = database.query(DBHelper.TABLE_TRACKS, null, null, null, null, null, null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            tracks = new ArrayList<>(cursor.getCount());

            do {

                tracks.add(new Track(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEMP)),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ACCENT)) != 0,
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COUNT1)),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COUNT2)),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_POSITION)),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID))));
            } while (cursor.moveToNext());

        }
        else{
            tracks = new ArrayList<>();
        }

        return tracks;
    }

    public void deleteItemById(int position) {
        int id = tracks.get(position).id;
        tracks.remove(position);
        database.delete(DBHelper.TABLE_TRACKS, "id = " + id, null);
        Log.d(TAG, "Track was delete... Id = " + id);
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

    public void close() {
        database.close();

    }

    public void putTrack(Track track) {
        if (track.position == -1 || track.id == -1) {
            Log.d(TAG, "tracks.size() = " + tracks.size());
            Log.d(TAG, "tracks = " + tracks);

            if (tracks != null & tracks.size() != 0) {
                // position started from 1
                track.position = tracks.size() + 1;
                // ides started from 0
                track.id = tracks.get(tracks.size() - 1).id + 1;
            }
            else {
                track.position = 1;
                // ides started from 0
                track.id = 0;
            }

        }
        for (int i = 0; i < tracks.size(); i++) {
            Log.d(TAG, "id = " + tracks.get(i).id + "position = " + tracks.get(i).position);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_NAME, track.name);
        contentValues.put(DBHelper.KEY_TEMP, track.temp);
        int acc;
        if (!track.acc) {
            acc = 0;
        } else {
            acc = 1;
        }
        contentValues.put(DBHelper.KEY_ACCENT, acc);
        contentValues.put(DBHelper.KEY_COUNT1, track.count1);
        contentValues.put(DBHelper.KEY_COUNT2, track.count2);
        contentValues.put(DBHelper.KEY_ID, track.id);
        contentValues.put(DBHelper.KEY_POSITION, track.position);

        Log.d(TAG, "Track is put. Id = " + track.id);
        tracks.add(track);
        database.insert(DBHelper.TABLE_TRACKS, null, contentValues);
    }

}
