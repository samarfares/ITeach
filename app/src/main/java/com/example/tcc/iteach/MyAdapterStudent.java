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

public class MyAdapterStudent extends ArrayAdapter<Person> {

    Activity activity;
    int resource;
    List<Person> list;

    public MyAdapterStudent(Activity activity, int resource, List<Person> list) {
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
        TextView gender = (TextView) view.findViewById(R.id.gender);
        TextView email = (TextView) view.findViewById(R.id.Email);

        name.setText( " الاسم: " +list.get(position).firstName+' '+list.get( position ).lastName);
        gender.setText( "الجنس:  " +list.get(position).getGender());
        email.setText( "الايميل: " + list.get(position).getEmail());






        return view;
    }
}