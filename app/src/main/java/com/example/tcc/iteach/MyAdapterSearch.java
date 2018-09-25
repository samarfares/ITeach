package com.example.tcc.iteach;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class MyAdapterSearch extends ArrayAdapter<Instructor> {

    Activity activity;
    int resource;
    List<Instructor> list;

    public MyAdapterSearch(Activity activity, int resource, List<Instructor> list) {
        super(activity, resource,list);
        this.activity = activity;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(resource,null);

        TextView name = (TextView) view.findViewById(R.id.name);

        name.setText(list.get(position).firstName+' '+list.get( position ).lastName);




        return view;
    }
}