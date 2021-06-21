package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class DataController {
    private static final DataController INSTANCE = new DataController();

    public static DataController getInstance() {
            return INSTANCE;
    }

//    private MutableLiveData<Boolean> liveData = new MutableLiveData<>();
//    public LiveData<Boolean> getData() {
//        liveData.setValue(true);
//        return liveData;
//    }
}
