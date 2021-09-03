package com.example.myapplication.database;

public class Track {
    public String name;
    public int temp;
    public boolean acc;
    public int count1;
    public int count2;
    public int id;
    public int position;

    public Track(String _name, int _temp, boolean _acc, int _count1, int _count2, int _position,  int _id) {
        name = _name;
        temp = _temp;
        acc = _acc;
        count1 = _count1;
        count2 = _count2;
        position = _position;
        id = _id;
    }

}
