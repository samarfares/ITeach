package com.example.tcc.iteach;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UpcomingAdapter2 extends ArrayAdapter<String> {


        private Context context;

        DatabaseReference databaseReference;
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;

        ArrayList<String> list;
        ArrayList<String> keyList;
        ArrayList<Lesson> lessons;


        Lesson lesson;
        String currentDateString;
        int i;
        int j;


        public UpcomingAdapter2 (Context context, ArrayList<String> records) {
            super(context, 0, records);

        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            String item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.upcoming_content2, parent, false);
            }

            databaseReference = FirebaseDatabase.getInstance().getReference();
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            final String uID = firebaseUser.getUid();
            lesson = new Lesson();
            currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
            databaseReference = FirebaseDatabase.getInstance().getReference("Lessons");
            list = new ArrayList<String>();
            keyList = new ArrayList<String>();
            lessons = new ArrayList<Lesson>();


            ///////////////////////////////////


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        lesson = ds.getValue(Lesson.class);
                        try {
                            Date date1 = DateFormat.getDateInstance(DateFormat.FULL).parse(currentDateString);
                            Date date2 = DateFormat.getDateInstance(DateFormat.FULL).parse(lesson.getDate());
                            i = date1.compareTo(date2);
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }
                        if (lesson.getStudentID().equals(uID)) {
                            if (i <= 0) {
                                if (i == 0) {
                                    int t = Integer.parseInt(lesson.getTime().substring(0, lesson.getTime().indexOf(":")));
                                    Calendar rightNow = Calendar.getInstance();
                                    int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                    if (t >= currentHourIn24Format) {

                                        keyList.add(ds.getKey());
                                        lessons.add(lesson);
                                        list.add("التاريخ : " + lesson.getDate() + "\n" + "الوقت : " + lesson.getTime() + "\n" + "المادة : " + lesson.getSubject() + "\n" + "السعر : " + lesson.getPrice() + "\n" + "طريقة الدفع : " + lesson.getPaymentMethod() + "\n" + "مكان الدرس : " + lesson.getLessonPlace() + "\n" + "طريقة التدريس : " + lesson.getTeachingMethod());
                                    }
                                } else {
                                    keyList.add(ds.getKey());
                                    lessons.add(lesson);
                                    list.add("التاريخ : " + lesson.getDate() + "\n" + "الوقت : " + lesson.getTime() + "\n" + "المادة : " + lesson.getSubject() + "\n" + "السعر : " + lesson.getPrice() + "\n" + "طريقة الدفع : " + lesson.getPaymentMethod() + "\n" + "مكان الدرس : " + lesson.getLessonPlace() + "\n" + "طريقة التدريس : " + lesson.getTeachingMethod());

                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            ////////////////////////////////////
            TextView lessonTextView = (TextView) convertView.findViewById(R.id.lesson2);
            Button edit = (Button) convertView.findViewById(R.id.editLesson2);
            edit.setTag(position);
            Button cancel = (Button) convertView.findViewById(R.id.cancelLesson2);
            cancel.setTag(position);
            lessonTextView.setText(item);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), EditLesson.class);
                    intent.putExtra("lessonID", keyList.get(position));
                    intent.putExtra("insID", lessons.get(position).getInstructorID());
                    intent.putExtra("teachingMethod", lessons.get(position).getTeachingMethod());
                    view.getContext().startActivity(intent);
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                    adb.setTitle("إلغاء الدرس");
                    adb.setMessage("متأكد من إلغاء الدرس ؟");
                    final int positionToRemove = position;
                    adb.setNegativeButton("لا", null);
                    adb.setPositiveButton("نعم", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            int pos = (int) view.getTag();
                            FirebaseDatabase.getInstance().getReference("Lessons").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                                        lesson = ds2.getValue(Lesson.class);
                                        if (ds2.getKey().equals(keyList.get(position))) {
                                            try {
                                                Date date1 = DateFormat.getDateInstance(DateFormat.FULL).parse(currentDateString);
                                                Date date2 = DateFormat.getDateInstance(DateFormat.FULL).parse(lesson.getDate());
                                                j = date1.compareTo(date2);
                                            } catch (java.text.ParseException e) {
                                                e.printStackTrace();
                                            }
                                            if (j == 0) {
                                                int t = Integer.parseInt(lesson.getTime().substring(0, lesson.getTime().indexOf(":")));
                                                Calendar rightNow = Calendar.getInstance();
                                                int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                if (t <= currentHourIn24Format + 1) {
                                                    Toast.makeText(getContext(), "لا تستطيع إلغاء الدرس اذا كان بعد أقل من ساعة", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    list.remove(positionToRemove);
                                                    UpcomingFragment.list.remove(positionToRemove);
                                                    notifyDataSetChanged();
                                                    FirebaseDatabase.getInstance().getReference("Lessons").child(keyList.get(position)).removeValue();
                                                    keyList.remove(position);
                                                    lessons.remove(position);
                                                    Toast.makeText(getContext(), "تم إلغاء الدرس بنجاح", Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                list.remove(positionToRemove);
                                                UpcomingFragment.list.remove(positionToRemove);
                                                notifyDataSetChanged();
                                                databaseReference.getRoot().child("Lessons").child(keyList.get(position)).removeValue();
                                                keyList.remove(position);
                                                lessons.remove(position);
                                                Toast.makeText(getContext(), "تم إلغاء الدرس بنجاح", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    });
                    adb.show();

                }
            });

            return convertView;

        }

    }
