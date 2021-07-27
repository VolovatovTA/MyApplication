package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    LayoutInflater lInflater;
    ArrayList<Track> tracksList = new ArrayList<Track>();
    boolean isSelectionMode;
    Track t_default = new Track("", 90, false, 4, 4, 0);

    public MyAdapter(Context context, ArrayList<Track> tracksList_) {
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        isSelectionMode = false;
        if (tracksList_ == null){
            tracksList.add(t_default);
        }
        else {
            tracksList.remove(t_default);
            tracksList = tracksList_;
        }


    }

    // кол-во элементов
    @Override
    public int getCount() {
        return tracksList.size();
    }

    // элемент по позиции
    @Override
    public Track getItem(int position) {

        return tracksList.get(position);
    }

    // передаём id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // заполняем пункт списка ,чтобы он потом передался куда нужно
    @SuppressLint("SetTextI18n")
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }
        Track t = tracksList.get(position);


        // заполняем View в пункте списка данными из треков: наименование, темп, размер такта

        ((TextView) view.findViewById(R.id.tvName)).setText(t.name);
        ((TextView) view.findViewById(R.id.tvTemp)).setText(t.temp + "");
        ((TextView) view.findViewById(R.id.tvCount1)).setText(t.count1 + "");
        ((TextView) view.findViewById(R.id.tvCount2)).setText(t.count2 + "");

        if (t.acc) ((TextView) view.findViewById(R.id.tvAccent)).setText("Accent: On");
        else ((TextView) view.findViewById(R.id.tvAccent)).setText("Accent: Off");

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cbBox);
        LinearLayout.LayoutParams params_for_chBox = (LinearLayout.LayoutParams) checkBox.getLayoutParams();
        if (isSelectionMode) {

            params_for_chBox.width = 51;
            params_for_chBox.leftMargin = 15;
            checkBox.setLayoutParams(params_for_chBox);
            checkBox.setVisibility(View.VISIBLE);
        }
        else {

            params_for_chBox.width = 1;
            params_for_chBox.leftMargin = 15;
            checkBox.setLayoutParams(params_for_chBox);
            checkBox.setVisibility(View.INVISIBLE);
        }
        // присваиваем чекбоксу обработчик
        checkBox.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        checkBox.setTag(position);
        // отмечаем
        checkBox.setChecked(t.acc);
        return view;
    }


    // обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            //getTrack((Integer) buttonView.getTag()).box = isChecked;
        }
    };
    public void updateList(ArrayList<Track> newList) {
        tracksList = newList;
        this.notifyDataSetChanged();
    }
}
