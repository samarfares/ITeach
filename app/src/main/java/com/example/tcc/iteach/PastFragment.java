package com.example.tcc.iteach;

import android.net.ParseException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

public class PastFragment  extends Fragment {

    View view;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ListView listView;
    ArrayList<String> list;
    PastAdapter pastAdapter;
    Lesson lesson;
    String currentDateString;
    int i;
    Person student;
    String stuName ;
    String uID ;

    public PastFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.past_fragment,container,false);

        listView = (ListView) view.findViewById(R.id.listViewPast);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        uID =  firebaseUser.getUid();
        lesson = new Lesson();
        DateFormat format = new SimpleDateFormat("EEE, d MMM yyyy");
        currentDateString = format.format(Calendar.getInstance().getTime());

        databaseReference = FirebaseDatabase.getInstance().getReference("Lessons");
        list = new ArrayList<>();

        pastAdapter = new PastAdapter(getContext(),list);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    if (lesson.getInstructorID().equals(uID)) {
                        if (i >= 0) {
                        if (i == 0) {
                            int t = Integer.parseInt(lesson.getTime().substring(0, lesson.getTime().indexOf(":")));
                            Calendar rightNow = Calendar.getInstance();
                            int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                            if (t < currentHourIn24Format) {

                        /*FirebaseDatabase.getInstance().getReference("Students").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    student = ds.getValue(Person.class);
                                    if (ds.getKey().equals(lesson.getStudentID())){
                                        stuName = student.getFirstName() +" " + student . getLastName();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
*/
                                list.add("التاريخ : " + lesson.getDate() + "\n" + "الوقت : " + lesson.getTime() + "\n" + "المادة : " + lesson.getSubject() + "\n" + "السعر : " + lesson.getPrice() + "\n" + "طريقة الدفع : " + lesson.getPaymentMethod() + "\n" + "مكان الدرس : " + lesson.getLessonPlace() + "\n" + "طريقة التدريس : " + lesson.getTeachingMethod());
                            }
                        } else
                            list.add("التاريخ : " + lesson.getDate() + "\n" + "الوقت : " + lesson.getTime() + "\n" + "المادة : " + lesson.getSubject() + "\n" + "السعر : " + lesson.getPrice() + "\n" + "طريقة الدفع : " + lesson.getPaymentMethod() + "\n" + "مكان الدرس : " + lesson.getLessonPlace() + "\n" + "طريقة التدريس : " + lesson.getTeachingMethod());

                    }
                }
            }
                listView.setAdapter(pastAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;

    }
}
