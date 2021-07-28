package com.example.myapplication.ui.main;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements ItemTouchHelperAdapter {

    private static final String[] STRINGS = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "3", "14", "15", "16", "17", "18", "19", "20"
    };
    private final List<String> mItems = new ArrayList<>();


    @Override
    public void onItemDismiss(int position) {
        Log.d(FragmentList.TAG, "onItemDismiss");

        mItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Log.d(FragmentList.TAG, "onItemMove");

        if (fromPosition < toPosition) {
//            Log.d(FragmentList.TAG, "in if");

            for (int i = fromPosition; i < toPosition; i++) {
//                Log.d(FragmentList.TAG, "in for");

                Collections.swap(mItems, i, i + 1);
            }
        } else {
//            Log.d(FragmentList.TAG, "in else");

            for (int i = fromPosition; i > toPosition; i--) {
//                Log.d(FragmentList.TAG, "in for2");

                Collections.swap(mItems, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public RecyclerListAdapter() {
//        Log.d(FragmentList.TAG, "RecyclerListAdapter constructor");

        mItems.addAll(Arrays.asList(STRINGS));
    }


    @Override
    public int getItemViewType(final int position) {
//        Log.d(FragmentList.TAG, "getItemViewType");

        return R.layout.item;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d(FragmentList.TAG, "onCreateViewHolder");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
//        Log.d(FragmentList.TAG, "onBindViewHolder");

        holder.getView().setText(mItems.get(position));
    }

    @Override
    public int getItemCount() {
//        Log.d(FragmentList.TAG, "getItemCount");

        return mItems.size();
    }

}