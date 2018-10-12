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

public class NotificationAdapter extends ArrayAdapter<Message> {

    Activity activity;
    int resource;
    List<Message> list;

    public NotificationAdapter(Activity activity, int resource, List<Message> list) {
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

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView content = (TextView) view.findViewById(R.id.content);


        title.setText( "Title: " +list.get(position).getTitle());
        content.setText( "Message: " +list.get(position).getMessage());





        return view;
    }
}