package com.example.fitfactory.TrainerActivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fitfactory.R;

import java.util.ArrayList;

public class HoursAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Integer> freeHours;
    public HoursAdapter(Context context, ArrayList<Integer> showFreeHours) {
        this.context = context;
        this.freeHours = showFreeHours;
    }

    @Override
    public int getCount() {
        return freeHours != null ? freeHours.size():0;
    }

    @Override
    public Object getItem(int position) {
        return freeHours.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_hour, viewGroup, false);
        TextView itemHour_TXT_hour = rootView.findViewById(R.id.itemHour_TXT_hour);
        itemHour_TXT_hour.setText(""+freeHours.get(i)+":00");

        return rootView;
    }
}
