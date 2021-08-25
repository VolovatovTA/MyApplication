package com.example.myapplication.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.DBHelper;
import com.example.myapplication.database.Track;
import com.example.myapplication.ui.main.MainActivity;
import com.example.myapplication.ui.main.fragments.FragmentList;
import com.example.myapplication.view.RecyclerViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements ItemTouchHelperAdapter {


    private static final String TAG = "Timofey";
    ArrayList<Track> tracks;
    public Cursor cursor;
    SQLiteDatabase database = null;
    DBHelper dbHelper;
    public int count_of_tracks;
    String[] names;
    int[] temp;
    boolean[] acc;
    int[] count1;
    int[] count2;
    int[] ides;
    Thread threadOfDownLoadTracks;
    Runnable downloadTracksFromDataBase;





    @Override
    public void onItemDismiss(int position) {
        Log.d(FragmentList.TAG, "onItemDismiss");

        tracks.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Log.d(FragmentList.TAG, "onItemMove");

        if (fromPosition < toPosition) {

            for (int i = fromPosition; i < toPosition; i++) {

                Collections.swap(tracks, i, i + 1);
            }
        } else {

            for (int i = fromPosition; i > toPosition; i--) {

                Collections.swap(tracks, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public RecyclerListAdapter(Context context) {
//        threadOfDownLoadTracks = new Thread(downloadTracksFromDataBase);
//        threadOfDownLoadTracks.start();
//
//
//        downloadTracksFromDataBase = new Runnable() {
//            @Override
//            public void run() {
//
//        };

        dbHelper = new DBHelper(context);

        if (database == null) {
            database = (dbHelper).getWritableDatabase();
        }
        cursor = database.query(DBHelper.TABLE_TRACKS, null, null, null, null, null, null);

        if (cursor != null) {
                count_of_tracks = cursor.getCount();
                names = new String[count_of_tracks];
                acc = new boolean[count_of_tracks];
                count1 = new int[count_of_tracks];
                count2 = new int[count_of_tracks];
                temp = new int[count_of_tracks];
                ides = new int[count_of_tracks];
            }

                if (cursor.moveToFirst()) {
                int j = 0;
                do {
                    names[j] = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME));
                    temp[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEMP));
                    acc[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ACCENT)) != 0;
                    count1[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COUNT1));
                    count2[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COUNT2));
                    ides[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID));
                    j++;
                } while (cursor.moveToNext());

                tracks = new ArrayList<>(cursor.getCount());

                for (int i = 0; i < cursor.getCount(); i++) {
                    tracks.add(new Track(names[i], temp[i], acc[i], count1[i], count2[i], ides[i]));
                }

            }


    }

    @Override
    public int getItemViewType(final int position) {

        return R.layout.item;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        if (tracks == null) holder.getView().setText("nema");
        else holder.getView().setText(tracks.get(position).name);


    }

    @Override
    public int getItemCount() {
        int size;
        if (tracks == null) size = 1;
        else size = tracks.size();


        return size;
    }


    public void updateTrackList() {


        cursor = database.query(DBHelper.TABLE_TRACKS, null, null, null, null, null, null);
        cursor.moveToLast();
        Log.d(TAG, "Last number" + cursor.getInt(0));

        Track track = new Track(cursor.getString(1), cursor.getInt(2), cursor.getInt(3) == 1, cursor.getInt(4), cursor.getInt(5), cursor.getInt(0));
//        assert false;
        Log.d(TAG, cursor.getString(1));
        tracks.add(track);
        notifyDataSetChanged();
        notifyItemRangeChanged(tracks.size(), getItemCount());


    }
}