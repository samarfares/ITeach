package com.example.tcc.iteach;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UpcomingFragment2 extends Fragment {
    View view;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ListView listView;
    public static ArrayList<String> list;
    ArrayList<String> keyList;
    ArrayList<Lesson> lessons;

    UpcomingAdapter2 upcomingAdapter;
    Lesson lesson;
    String currentDateString;
    int i;
    int j;
    Person student;
    String stuName ;

    public UpcomingFragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.upcoming_fragment,container,false);

        listView = (ListView) view.findViewById(R.id.listViewUpcoming);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        final String uID = firebaseUser.getUid();
        lesson = new Lesson();
        DateFormat format = new SimpleDateFormat("EEE, d MMM yyyy");
        currentDateString = format.format(Calendar.getInstance().getTime());
        databaseReference = FirebaseDatabase.getInstance().getReference("Lessons");
        list = new ArrayList<String>();
        keyList = new ArrayList<String>();
        lessons = new ArrayList<Lesson>();

        upcomingAdapter = new UpcomingAdapter2(getContext(),list);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    lesson = ds.getValue(Lesson.class);
                    try {
                        DateFormat format = new SimpleDateFormat("EEE, d MMM yyyy");
                        Date date1 = format.parse(currentDateString);
                        Date date2 = format.parse(lesson.getDate());
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
                        /*FirebaseDatabase.getInstance().getReference("Students").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    student = ds.getValue(Person.class);
                                    if (ds.getKey().equals(lesson.getStudentID())){
                                        stuName = student.getFirstName() +" " + student.getLastName();
                                        list.add("Date : " + lesson.getDate() + "\nTime : " + lesson.getTime() + "\nStudent : " + stuName + "\nSubject : " + lesson.getSubject() + "\nPrice : " + lesson.getPrice() + "\nPayment by : " + lesson.getPaymentMethod() + "\nPlace : " + lesson.getLessonPlace() + "\nTeaching method : " + lesson.getTeachingMethod());
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
*/
                        keyList.add(ds.getKey());
                        lessons.add(lesson);
                        list.add("التاريخ : " + lesson.getDate() + "\n" + "الوقت : " + lesson.getTime() + "\n" + "المادة : " + lesson.getSubject() + "\n" + "السعر : " + lesson.getPrice() + "\n" + "طريقة الدفع : " + lesson.getPaymentMethod() + "\n" + "مكان الدرس : " + lesson.getLessonPlace() + "\n" + "طريقة التدريس : " + lesson.getTeachingMethod());
                                }
                            } else {
                                list.add("التاريخ : " + lesson.getDate() + "\n" + "الوقت : " + lesson.getTime() + "\n" + "المادة : " + lesson.getSubject() + "\n" + "السعر : " + lesson.getPrice() + "\n" + "طريقة الدفع : " + lesson.getPaymentMethod() + "\n" + "مكان الدرس : " + lesson.getLessonPlace() + "\n" + "طريقة التدريس : " + lesson.getTeachingMethod());
                            }
                        }
                    }
                }
                listView.setAdapter(upcomingAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return view;
    }
}
