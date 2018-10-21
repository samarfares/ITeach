package com.example.tcc.iteach;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UpcomingFragment  extends Fragment {

    View view;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ListView listView;
    ArrayList<String> list;
    ArrayList<String> keyList;
    ArrayList<Lesson> lessons;
    ArrayAdapter<String> adapter;
    Lesson lesson;
    String currentDateString;
    int i;
    int j;
    Person student;
    String stuName ;
    public UpcomingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.upcoming_fragment,container,false);

        listView = (ListView) view.findViewById(R.id.listViewUpcoming);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        String uID = firebaseUser.getUid();
        lesson = new Lesson();
        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());

        databaseReference = FirebaseDatabase.getInstance().getReference("Lessons");
        list = new ArrayList<>();
        keyList = new ArrayList<>();
        lessons = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.spot_info,R.id.listViewSpotInfoTime,list);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    keyList.add(ds.getKey());
                    lesson = ds.getValue(Lesson.class);
                    lessons.add(lesson);
                    try {
                        Date date1 = DateFormat.getDateInstance(DateFormat.FULL).parse(currentDateString);
                        Date date2 = DateFormat.getDateInstance(DateFormat.FULL).parse(lesson.getDate());
                        i = date1.compareTo(date2);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                    if (i <= 0) {
                        if (i==0) {
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
                                list.add("التاريخ : " + lesson.getDate() + "\n" + "الوقت : " + lesson.getTime() + "\n" + "المادة : " + lesson.getSubject() + "\n" + "السعر : " + lesson.getPrice() + "\n" + "طريقة الدفع : " + lesson.getPaymentMethod() + "\n" + "مكان الدرس : " + lesson.getLessonPlace() + "\n" + "طريقة التدريس : " + lesson.getTeachingMethod());
                            }
                        }
                        else{
                            list.add("التاريخ : " + lesson.getDate() + "\n" + "الوقت : " + lesson.getTime() + "\n" + "المادة : " + lesson.getSubject() + "\n" + "السعر : " + lesson.getPrice() + "\n" + "طريقة الدفع : " + lesson.getPaymentMethod() + "\n" + "مكان الدرس : " + lesson.getLessonPlace() + "\n" + "طريقة التدريس : " + lesson.getTeachingMethod());

                        }
                    }
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
                adb.setTitle("إلغاء الدرس");
                adb.setMessage("متأكد من إلغاء الدرس ؟");
                final int positionToRemove = i;
                adb.setNegativeButton("لا", null);
                adb.setPositiveButton("نعم", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("Lessons").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot ds2 : dataSnapshot.getChildren()){
                                    lesson = ds2.getValue(Lesson.class);
                                    if (ds2.getKey().equals(keyList.get(i))){
                                        try {
                                            Date date1 = DateFormat.getDateInstance(DateFormat.FULL).parse(currentDateString);
                                            Date date2 = DateFormat.getDateInstance(DateFormat.FULL).parse(lesson.getDate());
                                            j = date1.compareTo(date2);
                                        } catch (java.text.ParseException e) {
                                            e.printStackTrace();
                                        }
                                        if (j == 0) {
                                            int t = Integer.parseInt(lesson.getTime().substring(0,lesson.getTime().indexOf(":")));
                                            Calendar rightNow = Calendar.getInstance();
                                            int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                            if (t <= currentHourIn24Format + 1){
                                                Toast.makeText(getActivity(), "لا تستطيع إلغاء الدرس اذا كان بعد أقل من ساعة", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                list.remove(positionToRemove);
                                                adapter.notifyDataSetChanged();
                                                FirebaseDatabase.getInstance().getReference("Lessons").child(keyList.get(i)).removeValue();
                                                keyList.remove(i);
                                                lessons.remove(i);
                                                Toast.makeText(getActivity(),"تم إلغاء الدرس بنجاح",Toast.LENGTH_LONG).show();

                                            }

                                        }
                                        else{
                                            list.remove(positionToRemove);
                                            adapter.notifyDataSetChanged();
                                            databaseReference.getRoot().child("Lessons").child(keyList.get(i)).removeValue();
                                            keyList.remove(i);
                                            lessons.remove(i);
                                            Toast.makeText(getActivity(),"تم إلغاء الدرس بنجاح",Toast.LENGTH_LONG).show();

                                        }
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }});
                adb.show();


            }
        });


        return view;
    }
}
