package com.example.myapplication.ui.main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.MyAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;

public class FragmentLibrary extends Fragment {

    String TAG = "lifecycle111";
    DBHelper dbHelper;
    ListView lvList;
    TextView defaultText;
    static boolean isSelectionMode = false;
    int count_of_tracks;
    String[] names;
    int[] temp;
    boolean[] acc;
    int[] count1;
    int[] count2;
    int[] ides;
    private android.view.Menu menu;
    ArrayList<Track> tracks;
    Cursor cursor;
    MyAdapter myAdapter;
    View rootView;
    SQLiteDatabase database = null;
    Toolbar toolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, null);
        lvList = rootView.findViewById(R.id.lvList);
        defaultText = rootView.findViewById(R.id.text_of_empty_list);
        dbHelper = new DBHelper(getContext());
        toolbar = rootView.findViewById(R.id.toolbar);

        tracks = CreateTrackList();


        myAdapter = new MyAdapter(getContext(), tracks);
        lvList.setAdapter(myAdapter);
        lvList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (!isSelectionMode) {
                    Log.d(TAG, "itemClick: position = " + position + ", id = " + id);


//                    intent.putExtra("name", tracks.get(position).name);
//                    intent.putExtra("temp",  tracks.get(position).temp);
//                    intent.putExtra("acc",  tracks.get(position).acc);
//                    intent.putExtra("count1",  tracks.get(position).count1);
//                    intent.putExtra("count2",  tracks.get(position).count2);
//                    startActivity(intent);

                }
            }
        });
        return rootView;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            isSelectionMode = true;
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale);

            for (int i = 0; i < lvList.getChildCount(); i++) {
                View view = ((LinearLayout) lvList.getChildAt(i));
                Log.d(TAG, "i = " + i);
                CheckBox checkBox = view.findViewById(R.id.cbBox);
                LinearLayout.LayoutParams params_for_chBox = (LinearLayout.LayoutParams) checkBox.getLayoutParams();
                ;
                params_for_chBox.width = 51;
                params_for_chBox.leftMargin = 15;
                checkBox.setLayoutParams(params_for_chBox);
                checkBox.setVisibility(View.VISIBLE);
                checkBox.startAnimation(animation);
            }


        } else if (item.getItemId() == 2) {
            isSelectionMode = false;
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale1);

            for (int i = 0; i < lvList.getChildCount(); i++) {
                View view = ((LinearLayout) lvList.getChildAt(i));
                CheckBox checkBox = view.findViewById(R.id.cbBox);
                checkBox.startAnimation(animation);
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                checkBox.setVisibility(View.INVISIBLE);
            }

            SQLiteDatabase database = (dbHelper).getWritableDatabase();
            int count = DeleteSomeItems(rootView.findViewById(R.id.toolbar), database);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "FragLib onResume ");

        cursor = database.query(DBHelper.TABLE_TRACKS, null, null, null, null, null, null);

        tracks = CreateTrackList();
        if (tracks == null) {
            lvList.setVisibility(View.INVISIBLE);
            defaultText.setVisibility(View.VISIBLE);
        } else {
            lvList.setVisibility(View.VISIBLE);
            defaultText.setVisibility(View.INVISIBLE);
        }
        if (tracks != null) {
            myAdapter.updateList(tracks);
        }


    }

    //    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // пункты меню с ID группы = 1 видны, если выбран режим редактирования
//        menu.setGroupVisible(1, isSelectionMode);
//
//        return super.onPrepareOptionsMenu(menu);
//    }
    public int DeleteSomeItems(View v, SQLiteDatabase _database) {
        int result = getCheckedCount(tracks);
        cursor = _database.query(DBHelper.TABLE_TRACKS, null, null, null, null, null, null);

        int[] ides = getCheckedIdes(tracks);
        for (int i = 0; i < result; i++) {
            cursor.moveToPosition(ides[i]);
            _database.delete("tracks", "_id = " + ides[i], null);
            int j = getTrackById(tracks, ides[i]);
            tracks.remove(j);
        }


        if (result == 0) {
            Toast.makeText(getActivity(), "Ни одной записи не было удалено", Toast.LENGTH_LONG).show();

        } else if (result == 1) {
            Toast.makeText(getActivity(), "Была удалена " + result + " запись", Toast.LENGTH_LONG).show();

        } else if (result < 5 & result > 1) {
            Toast.makeText(getActivity(), "Было удалено " + result + " записи", Toast.LENGTH_LONG).show();

        } else if (result > 5) {
            Toast.makeText(getActivity(), "Было удалено " + result + " записей", Toast.LENGTH_LONG).show();

        }
        cursor.close();
        return result;
    }

    private int getTrackById(ArrayList<Track> tracks, int id) {
        int found_id = 0;
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).id == id) found_id = i;
        }
        return found_id;
    }

    public ArrayList<Track> CreateTrackList() {
        ArrayList<Track> tracks = null;

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

        return tracks;
    }

    public int getCheckedCount(ArrayList<Track> t) {
        int count = 0;
        for (int i = 0; i < t.size(); i++) {
            if (t.get(i).acc) count++;
        }
        return count;
    }

    public int[] getCheckedIdes(ArrayList<Track> t) {
        int count_current = 0;
        int[] ides = new int[getCheckedCount(t)];

        for (int i = 0; i < t.size(); i++) {
            if (t.get(i).acc) {
                ides[count_current] = t.get(i).id;
                count_current++;
            }
        }

        return ides;
    }


}



