package com.example.tcc.iteach;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterInstructor extends ArrayAdapter<Instructor> {

    Activity activity;
    List<Instructor> instructors;


    public AdapterInstructor (Activity activity , List<Instructor> instructors){
        super(activity,R.layout.data_items,instructors);
        this.activity=activity;
        this.instructors=instructors;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View  view = layoutInflater.inflate(R.layout.data_items,null,true);

        TextView name = (TextView) view.findViewById(R.id.insName);
        TextView gender = (TextView) view.findViewById(R.id.insGender);
        TextView subject = (TextView) view.findViewById(R.id.insSubjects);
        TextView dob = (TextView) view.findViewById(R.id.insDob);
        TextView likes = (TextView) view.findViewById(R.id.insLikes);
        TextView yoe = (TextView) view.findViewById(R.id.insYOE);
        TextView insLessonPlace = (TextView) view.findViewById(R.id.insLessonPlace);
        TextView insLessonPrice = (TextView) view.findViewById(R.id.insLessonPrice);
        TextView insTmethod = (TextView) view.findViewById(R.id.insTmethod);
        TextView insPmethod = (TextView) view.findViewById(R.id.insPmethod);
        TextView insPhone = (TextView) view.findViewById(R.id.insPhone);
        TextView insEmail = (TextView) view.findViewById(R.id.insEmail);


        Instructor instructor = instructors.get(position);

name.setText(instructor.getFirstName().concat(" ").concat(instructor.getLastName()));
gender.setText(instructor.getGender());
dob.setText("Was born on "+instructor.getDob());
likes.setText("Likes "+instructor.getLikes()+"");
        yoe.setText("Years of experience: "+instructor.getYoe()+"");
        insLessonPlace.setText("Prefers teaching at "+instructor.getLessonsPlace());
        insLessonPrice.setText("lesson's price "+instructor.getLessonsPrice()+" SR");
        insTmethod.setText("prefers "+instructor.getTeachingMethod());
        insPmethod.setText("Payment method "+instructor.getPaymentMethod());
        insEmail.setText("Email "+instructor.getEmail());
         insPhone.setText("Mobile "+instructor.getPhoneNum()+"");

        subject.setText("Subjects: "+instructor.subjects.toString());

       //String d = instructor.getPhoneNum()+"";


        return view;
    }





}
