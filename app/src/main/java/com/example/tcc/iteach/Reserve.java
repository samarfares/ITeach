package com.example.tcc.iteach;

import android.app.DatePickerDialog;
import android.content.Intent;
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

    Button buttonReserve;
    Button buttonDate;

    String date, time, stuID;
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


        Intent intent = getIntent();

        insID = intent.getStringExtra("insID");

        insName = intent.getStringExtra("price");
        paymentMethod = intent.getStringExtra("paymentMethod");
        lessonPlace = intent.getStringExtra("place");
        lessonPrice = intent.getStringExtra("price");
        teachingMethod = intent.getStringExtra("teachingMethod");
        subject = intent.getStringExtra("subject");

        if (paymentMethod.equals("VISA")){
            buttonReserve.setText("Continue To Payment");
        }

        listView = (ListView) findViewById(R.id.listViewReserve);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        stuID = firebaseUser.getUid();
        String instructor_id = insID;
        spot = new Spot();

        buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (teachingMethod.equals("Cash")){
                    databaseReference = firebaseDatabase.getReference("Lessons");
                    lesson = new Lesson( date, time,  insID, stuID, subject, lessonPrice,paymentMethod,lessonPlace, teachingMethod);
                    databaseReference.push().setValue(lesson);
                    startActivity(new Intent(Reserve.this , student_main.class));
                }
                else{

                    Intent i = new Intent(Reserve.this , Reserve.class);
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
        });

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
                            if (teachingMethod.equals("Individual")) {
                                if (spot.isIndividual()) {
                                    list.add("Time : " + spot.getTime().toString());
                                }
                            } else {
                                if (!spot.isIndividual()){
                                    list.add("Time : " + spot.getTime().toString());
                                }
                            }
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
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                time = list.get(i).toString();
                Toast.makeText(Reserve.this, "time : "+time,Toast.LENGTH_LONG).show();
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
                            if (teachingMethod.equals("Individual")) {
                                if (spot.isIndividual()){
                                    list.add("Time : " + spot.getTime().toString() );
                                }
                            } else {
                                if (!spot.isIndividual()){
                                    list.add("Time : " + spot.getTime().toString() );
                                }

                            }
                        }
                    }
                }
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}