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

public class EditLesson extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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
    Button buttonEdit;
    Button buttonDate;

    String date, time = "null", stuID;
    String insID, insName, paymentMethod, lessonPlace, lessonPrice, teachingMethod ,subject, lessonID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lesson);

        buttonDate = (Button) findViewById(R.id.button_choose_date_schedule_edit);
        buttonEdit = (Button) findViewById(R.id.button_edit);
        TextView textView = (TextView) findViewById(R.id.textViewDateEdit);
        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
        textView.setText(currentDateString);

        none = (TextView)findViewById(R.id.noneTextEdit);
        Intent intent = getIntent();

        lessonID = intent.getStringExtra("lessonID");
        insID = intent.getStringExtra("insID");
        teachingMethod = intent.getStringExtra("teachingMethod");

        Toast.makeText(this, lessonID+" : " + insID, Toast.LENGTH_SHORT).show();

        listView = (ListView) findViewById(R.id.listViewEdit);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
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

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (time.equals("null")) {
                    Toast.makeText(EditLesson.this, "يجب أن تختار وقتاً !!", Toast.LENGTH_LONG).show();
                } else {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Lessons");
                    databaseReference.child(lessonID).child("date").setValue(currentDateString);
                    databaseReference.child(lessonID).child("time").setValue(time);
                    startActivity(new Intent(EditLesson.this, reservations.class));
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
        TextView textView = (TextView) findViewById(R.id.textViewDateEdit);
        textView.setText(currentDateString);
        listView = (ListView) findViewById(R.id.listViewEdit);
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