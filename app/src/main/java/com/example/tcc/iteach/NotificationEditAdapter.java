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

public class NotificationEditAdapter extends ArrayAdapter<MessageEdit> {

    Activity activity;
    int resource;
    List<MessageEdit> list;

    public NotificationEditAdapter(Activity activity, int resource, List<MessageEdit> list) {
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

        TextView date = (TextView) view.findViewById(R.id.date);
        TextView content = (TextView) view.findViewById(R.id.content);
        TextView time = (TextView) view.findViewById(R.id.time);


        date.setText( " التاريخ: " +list.get(position).getDate());
        content.setText( " الرسالة: " +list.get(position).getMessage());
        time.setText( " الوقت: " +list.get(position).getTime());





        return view;
    }
}