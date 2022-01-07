package com.bkacad.nnt.cityapi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<City> list;

    public MyAdapter(Context context, ArrayList<City> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city, parent, false);
        }
        TextView tvCity = convertView.findViewById(R.id.item_city_name);
        City item = list.get(position);
        tvCity.setText(String.format("%d. %s", getItemId(position), item.getName()));
        return convertView;
    }
}
