package com.example.tcc.iteach;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Reserve extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String currentDateString;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Spot spot;
    Lesson lesson;

    TextView none;
    Button buttonReserve;
    Button buttonDate;

    String date, time = "null", stuID;
    String insID, insName, paymentMethod, lessonPlace, lessonPrice, teachingMethod ,subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        buttonDate = (Button) findViewById(R.id.button_choose_date_schedule_reserve);
        buttonReserve = (Button) findViewById(R.id.button_reserve);
        TextView textView = (TextView) findViewById(R.id.textViewDateReserve);
        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
        textView.setText(currentDateString);

        none = (TextView)findViewById(R.id.noneTextReserve);
        Intent intent = getIntent();

        insID = intent.getStringExtra("insID");

        insName = intent.getStringExtra("insName");
        paymentMethod = intent.getStringExtra("paymentMethod");
        lessonPlace = intent.getStringExtra("place");
        lessonPrice = intent.getStringExtra("price");
        teachingMethod = intent.getStringExtra("teachingMethod");
        subject = intent.getStringExtra("subject");

        if (paymentMethod.equals("فيزا")){
            buttonReserve.setText("استمرار للدفع");
        }

        listView = (ListView) findViewById(R.id.listViewReserve);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        stuID = firebaseUser.getUid();
        String instructor_id = insID;
        spot = new Spot();

        databaseReference = firebaseDatabase.getReference("Instructors").child(instructor_id).child("spots");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.spot_info,R.id.listViewSpotInfoTime,list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    spot = ds.getValue(Spot.class);
                    if (spot.getDate().equals(currentDateString)) {
                        if (spot.isAvailable()) {
                            if (teachingMethod.equals("فردي")) {
                                if (spot.isIndividual()) {
                                    list.add("الوقت : " + spot.getTime().toString());
                                }
                            } else {
                                if (!spot.isIndividual()){
                                    list.add("الوقت : " + spot.getTime().toString());
                                }
                            }
                        }
                    }
                }
                if (!list.isEmpty()){
                    none.setVisibility(View.GONE);
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (time.equals("null")) {
                    Toast.makeText(Reserve.this, "يجب أن تختار وقتاً !!", Toast.LENGTH_LONG).show();
                } else {
                    if (paymentMethod.equals("نقداً")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference("Lessons");
                        lesson = new Lesson(currentDateString, time, insID, stuID, subject, lessonPrice, paymentMethod, lessonPlace, teachingMethod, "لا يوجد");
                        databaseReference.push().setValue(lesson);
                        startActivity(new Intent(Reserve.this, student_main.class));
                        databaseReference = firebaseDatabase.getReference("Instructors").child(insID).child("spots");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    spot = ds.getValue(Spot.class);
                                    if (spot.getDate().equals(currentDateString)) {
                                        if (spot.isAvailable()) {
                                            if (spot.getTime().equals(time)) {
                                                if (teachingMethod.equals("فردي")) {
                                                    if (spot.isIndividual()) {
                                                        ds.getRef().child("available").setValue(false);
                                                    }
                                                } else {
                                                    if (!spot.isIndividual()) {
                                                        ds.getRef().child("numberOfStudent").setValue(spot.getNumberOfStudent() - 1);
                                                        if (spot.getNumberOfStudent() == 1)
                                                            ds.getRef().child("available").setValue(false);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                        FirebaseMessaging.getInstance().subscribeToTopic("notificationsLessons");
                        FirebaseDatabase.getInstance().getReference("messagesLesson").push().setValue(new MessageLesson("لقد قام طالب بحجز درس جديد معك .." ,currentDateString,time,insID));
                        FirebaseMessaging.getInstance().unsubscribeFromTopic( "notificationsLessons");



                        startActivity(new Intent(Reserve.this, student_main.class));

                    } else {

                        Intent i = new Intent(Reserve.this, PaymentActivity.class);
                        i.putExtra("insID", insID);
                        i.putExtra("insName", insName);
                        i.putExtra("paymentMethod", paymentMethod);
                        i.putExtra("lessonPlace", lessonPlace);
                        i.putExtra("lessonPrice", lessonPrice);
                        i.putExtra("teachingMethod", teachingMethod);
                        i.putExtra("subject", subject);
                        startActivity(i);

                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                time = list.get(i).toString().substring(8);
                for (int j = 0; j < listView.getChildCount(); j++) {
                    if(i == j ){
                        listView.getChildAt(j).setBackgroundColor(Color.GRAY);
                    }else{
                        listView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

    }

    //////////////////////////// select date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView textView = (TextView) findViewById(R.id.textViewDateReserve);
        textView.setText(currentDateString);


        listView = (ListView) findViewById(R.id.listViewReserve);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        stuID = firebaseUser.getUid();
        String instructor_id = insID;
        spot = new Spot();

        databaseReference = firebaseDatabase.getReference("Instructors").child(instructor_id).child("spots");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.spot_info,R.id.listViewSpotInfoTime,list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    spot = ds.getValue(Spot.class);
                    if ( spot.getDate().equals(currentDateString)){
                        if (spot.isAvailable()) {
                            if (teachingMethod.equals("فردي")) {
                                if (spot.isIndividual()){
                                    list.add("الوقت : " + spot.getTime().toString() );
                                }
                            } else {
                                if (!spot.isIndividual()){
                                    list.add("الوقت : " + spot.getTime().toString() );
                                }

                            }
                        }
                    }
                }
                if (list.isEmpty()){
                    none.setVisibility(View.VISIBLE);
                }
                else
                    none.setVisibility(View.GONE);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}