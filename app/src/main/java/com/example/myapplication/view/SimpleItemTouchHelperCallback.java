package com.example.myapplication.view;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapters.ItemTouchHelperAdapter;
import com.example.myapplication.database.Repository;
import com.example.myapplication.ui.main.fragments.FragmentList;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;
    private Repository repository = Repository.getInstance();

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
    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
        repository.swap(fromPos, toPos);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d(FragmentList.TAG, "onSwiped");

        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

}
