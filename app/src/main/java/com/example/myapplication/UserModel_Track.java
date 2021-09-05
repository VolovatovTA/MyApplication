package com.example.myapplication;

import static com.google.common.primitives.Ints.max;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.myapplication.database.DBHelper;
import com.example.myapplication.database.Track;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserModel_Track {
    private static final String TAG = "Timofey";
    public Cursor cursor;
    SQLiteDatabase database = null;
    DBHelper dbHelper;
    ArrayList<Track> tracks;

    public UserModel_Track() {
        Log.d(TAG, "UserModel_track constructor");

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Track> getTracks(Context context) {
        dbHelper = new DBHelper(context);
        if (database == null) {
            database = (dbHelper).getWritableDatabase();
        }


        cursor = database.query(DBHelper.TABLE_TRACKS, null, null, null, null, null, null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            tracks = new ArrayList<>();

            do {

                Log.d(TAG, "position = " + cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_POSITION)) + "; id = " + cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID)));
                tracks.add(new Track(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEMP)),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ACCENT)) != 0,
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COUNT1)),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COUNT2)),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_POSITION)),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID))));

            } while (cursor.moveToNext());

            tracks.sort(new Comparator<Track>() {
                @Override
                public int compare(Track track, Track t1) {
                    return Integer.compare(track.position, t1.position);
                }
            });


        } else {
            tracks = new ArrayList<>();
        }

        return tracks;
    }

    public void deleteItemById(int position) {
        int id = tracks.get(position).id;
        tracks.remove(position);
        database.delete(DBHelper.TABLE_TRACKS, "id = " + id, null);
        database.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            for (int i = position; i < tracks.size(); i++) {
                cv.put(DBHelper.KEY_POSITION, i);
                id = tracks.get(i).id;
                database.update(DBHelper.TABLE_TRACKS, cv, "id = ?", new String[]{id + ""});
            }

            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        Log.d(TAG, "Track was delete... Id = " + id);
    }

    public void swap(int fromPos, int toPos) {
        ContentValues cv_from = new ContentValues();
        ContentValues cv_to = new ContentValues();

        cv_from.put(DBHelper.KEY_POSITION, fromPos);
        cv_to.put(DBHelper.KEY_POSITION, toPos);

        int id_fromPos = tracks.get(fromPos).id;
        int id_toPos = tracks.get(toPos).id;
        database.beginTransaction();
        try {
            database.update(DBHelper.TABLE_TRACKS, cv_to, "id = ?", new String[]{id_fromPos + ""});
            database.update(DBHelper.TABLE_TRACKS, cv_from, "id = ?", new String[]{id_toPos + ""});
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        Collections.swap(tracks, fromPos, toPos);
    }

    public void close() {
        database.close();
        cursor.close();
    }

    public void putTrack(Track track) {
        if (track.position == -1 || track.id == -1) {
            if (tracks != null & tracks.size() != 0) {
                // position started from 1
                track.position = tracks.size();
                // ides started from 0
                int[] id = new int[tracks.size()];
                for (int i = 0; i < tracks.size(); i++) {
                    id[i] = tracks.get(i).id;
                }
                track.id = max(id) + 1;
            } else {
                track.position = 0;
                // ides started from 0
                track.id = 0;
            }

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

        Log.d(TAG, "count1 in PutTrack = " + track.count1);

        tracks.add(track);
        database.insert(DBHelper.TABLE_TRACKS, null, contentValues);
    }

}
