package com.example.myapplication.adapters;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

public class Calculator {
    public Calculator(){

    }
    int bpm;
    int i = 0;
    long forBpm;
    public int getBpmRelationTap(){
        forBpm = i == 0 ? System.currentTimeMillis() : System.currentTimeMillis() - forBpm;

     return bpm;
    }
}
