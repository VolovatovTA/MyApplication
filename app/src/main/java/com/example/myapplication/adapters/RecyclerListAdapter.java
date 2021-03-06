package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Repository;
import com.example.myapplication.database.Track;
import com.example.myapplication.ui.main.fragments.FragmentList;
import com.example.myapplication.view.RecyclerViewHolder;

import java.util.ArrayList;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements ItemTouchHelperAdapter {


    private static final String TAG = "Timofey";
    Repository repository = Repository.getInstance();
    Context context;
    ArrayList<Track> tracks;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public RecyclerListAdapter(Context context) {
        this.context = context;
        tracks = repository.getFromDataBase(context);
    }

    @Override
    public void onItemDismiss(int position) {
        Log.d(FragmentList.TAG, "onItemDismiss");
        repository.deleteItemById(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Log.d(FragmentList.TAG, "onItemMove");
        Log.d(TAG, "fromPos = " + fromPosition + "; toPos = " + toPosition);

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                repository.swap(i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                repository.swap(i, i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
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
        if (tracks == null) {}
        else {
            holder.getName().setText(tracks.get(position).name);

            holder.getTemp().setText(Integer.toString(tracks.get(position).temp));
            holder.getCount1().setText(Integer.toString(tracks.get(position).count1));
            holder.getCount2().setText(Integer.toString(tracks.get(position).count2));
            holder.getAcc().setText(tracks.get(position).acc ? "ON" : "Off");
        }


    }

    @Override
    public int getItemCount() {
        int size;
        if (tracks == null) size = 1;
        else size = tracks.size();


        return size;
    }


    public void updateTrackList() {

        notifyDataSetChanged();
        notifyItemRangeChanged(tracks.size(), getItemCount());


    }
}