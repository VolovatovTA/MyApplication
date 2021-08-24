package com.example.myapplication.ui.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.RecyclerListAdapter;
import com.example.myapplication.view.SimpleItemTouchHelperCallback;


public class FragmentList extends Fragment  {
    private RecyclerView recyclerView;
    public static String TAG = "Timofey";
    RecyclerListAdapter myAdapter;
    ViewGroup viewGroup;
    View view;


    public FragmentList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView FragmentList");
        viewGroup = container;
        view = inflater.inflate(R.layout.recycle_view_trying, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        myAdapter = new RecyclerListAdapter(getContext());
        recyclerView.setAdapter(myAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(myAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        return view;
    }



    @Override
    public void onResume() {




        Log.d(TAG, "onResume FragmentList");



        super.onResume();



    }
}