package com.example.myapplication.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.adapters.RecyclerListAdapter;
import com.example.myapplication.database.Repository;
import com.example.myapplication.database.Track;
import com.example.myapplication.database.DBHelper;
import com.example.myapplication.ui.main.fragments.FragmentList;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

public class Saver extends AppCompatActivity implements View.OnClickListener {
    String TAG = "Timofey";
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    EditText name;
    Button save, cancel;
    long freq;
    boolean isAccentOn;
    int count1, count2;

    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saver);
        Log.d(TAG, "Saver onCreate");

        Intent intent = getIntent();

        freq = intent.getLongExtra("temp", 90);
        isAccentOn = intent.getBooleanExtra("accent", false);
        count1 = intent.getIntExtra("number_sounds", 4);
        count2 = intent.getIntExtra("number_share", 4);

        Log.d(TAG, freq + "");

        name = findViewById(R.id.Name);
        save = findViewById(R.id.saveLIST);
        save.setOnClickListener(this);

        cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {



        String name_of_track = name.getText().toString();

        if (view.getId() == R.id.saveLIST){
            Log.d(TAG, name.getText().toString());



            Repository.getInstance().putTrack(new Track(name_of_track, (int) freq, isAccentOn, count1, count2, 0, 0));
            database.insert(DBHelper.TABLE_TRACKS, null, contentValues);

            Cursor cursor = database.query(DBHelper.TABLE_TRACKS, null, null, null, null, null, null);
            cursor.moveToLast();
            Log.d(TAG, "Last number in Saver = " + cursor.getInt(0));
//            Track new_track = new Track(name_of_track,(int) freq, isAccentOn, count1, count2, cursor.getPosition());
//            Observable<Track> observable = Observable.just(new_track);
//            observable.subscribe(observer);

            Intent intent = new Intent();
            intent.putExtra("isAdd", true);
            setResult(RESULT_OK, intent);
            finish();
        }
        else if (view.getId() == R.id.cancel_button) {
//            @SuppressLint("Recycle") Cursor cursor = database.query(DBHelper.TABLE_TRACKS, null,null, null, null, null,null);
//
//            if (cursor.moveToFirst()){
//                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
//                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
//                int tempIndex = cursor.getColumnIndex(DBHelper.KEY_TEMP);
//                do {
//                    Log.d(TAG, "id = " + cursor.getString(idIndex) +
//                            ", name = " + cursor.getString(nameIndex) +
//                            ", temp = " + cursor.getString((tempIndex)));
//                } while (cursor.moveToNext());}
//
//            else {
//                Log.d(TAG, "Ничё нет");
//                dbHelper.close();
//            }
//            cursor.close();
            Intent intent = new Intent();
            intent.putExtra("isAdd", false);
            setResult(RESULT_CANCELED, intent);
            finish();
            this.finish();
        }
    }

}