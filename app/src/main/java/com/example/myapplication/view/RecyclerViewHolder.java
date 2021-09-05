package com.example.myapplication.view;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView count1;
    private TextView count2;
    private TextView acc;
    private TextView temp;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.tvName);
        Log.d("Timofey", "R.id.tvName: " + R.id.tvName + "; name = " + name);
        count1 = itemView.findViewById(R.id.tvCount1);
        Log.d("Timofey", "R.id.tvCount1: " + R.id.tvCount1 + "; count1 = " + count1);
        count2 = itemView.findViewById(R.id.tvCount2);
        temp = itemView.findViewById(R.id.tvTemp);
        acc = itemView.findViewById(R.id.tvAccent);
    }

    public TextView getName(){return name;}
    public TextView getCount1(){
        return count1;
    }
    public TextView getCount2(){return count2;}
    public TextView getTemp(){return temp;}
    public TextView getAcc(){return acc;}

}
