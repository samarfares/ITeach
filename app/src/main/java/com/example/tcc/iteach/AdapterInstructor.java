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
dob.setText("ولد في : "+instructor.getDob());
likes.setText(" الإعجابات "+instructor.getLikes()+"");
        yoe.setText("سنين الخبرة : "+instructor.getYoe()+"");
        if (!(instructor.getLessonsPlace().equals( "كلاهما" )))

            insLessonPlace.setText("يفضل أن يكون الدرس : "+instructor.getLessonsPlace());
        else
            insLessonPlace.setText("يفضل أن يكون الدرس : "+"[عند الأستاذ,عند الطالب]");


        insLessonPrice.setText("سعر الدرس : "+instructor.getLessonsPrice()+" SR");
       if (!(instructor.getTeachingMethod().equals( "كلاهما" )))
        insTmethod.setText("يفضل أن يدرس : "+instructor.getTeachingMethod());
       else
           insTmethod.setText("يفضل أن يدرس : "+"[فردي,جماعي]");

        if (!(instructor.getPaymentMethod().equals( "كلاهما" )))
            insPmethod.setText("طريقة الدفع : "+instructor.getPaymentMethod());
        else
            insPmethod.setText("طريقة الدفع : "+"[نقداً,فيزا]");

        insEmail.setText("البريد الالكتروني : "+instructor.getEmail());
         insPhone.setText("رقم الجوال : "+instructor.getPhoneNum()+"");

        subject.setText("المواد : "+instructor.subjects.toString());

       //String d = instructor.getPhoneNum()+"";


        return view;
    }





}
