package com.example.myapplication.ui.main;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        Log.d(FragmentList.TAG, "onCreateView");
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        Log.d(FragmentList.TAG, "isLongPressDragEnabled");

        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        Log.d(FragmentList.TAG, "isItemViewSwipeEnabled");

        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.d(FragmentList.TAG, "getMovementFlags");

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START ;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.d(FragmentList.TAG, "onMove");

        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }



    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d(FragmentList.TAG, "onSwiped");

        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

}
