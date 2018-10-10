package com.example.tcc.iteach;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

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

    Button buttonReserve;
    Button buttonDate;

    String date, time, stuID;
    String insID, insName, insGender, insPaymentMethod, insLessonsPlace, insLessonsPrice, insTeachingMethod ;


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
        insName = intent.getStringExtra("insName");
        insGender = intent.getStringExtra("insGender");
        insPaymentMethod = intent.getStringExtra("insPaymentMethod");
        insLessonsPlace = intent.getStringExtra("insLessonsPlace");
        insLessonsPrice = intent.getStringExtra("insLessonsPrice");
        insTeachingMethod = intent.getStringExtra("insTeachingMethod");

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
                            if (insTeachingMethod.equals("Individual")) {
                                if (spot.isIndividual())
                                    list.add("Time : " + spot.getTime().toString());
                            } else {
                                if (!spot.isIndividual())
                                    list.add("Time : " + spot.getTime().toString());
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
                            if (insTeachingMethod.equals("Individual")) {
                                if (spot.isIndividual())
                                    list.add("Time : " + spot.getTime().toString() );
                            } else {
                                if (!spot.isIndividual())
                                    list.add("Time : " + spot.getTime().toString() );

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