package com.example.tcc.iteach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ReserveInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;

    TextView textView;

    Spinner subjectSpinner, paymentSpinner, placeSpinner, methodSpinner;
    Button continueButton;

    List<String> subjects;

    String insID, insName, insGender, insPaymentMethod, insLessonsPlace, insLessonsPrice, insTeachingMethod ;

    String chosenSubject, chosenPayment, chosenPlace, chosenMethod ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_info);


        Intent intent = getIntent();

        subjects = new ArrayList<>(getIntent().getStringArrayListExtra("subjects"));
        insID = intent.getStringExtra("insID");
        insName = intent.getStringExtra("insName");
        insGender = intent.getStringExtra("insGender");
        insPaymentMethod = intent.getStringExtra("insPaymentMethod");
        insLessonsPlace = intent.getStringExtra("insLessonsPlace");
        insLessonsPrice = intent.getStringExtra("insLessonsPrice");
        insTeachingMethod = intent.getStringExtra("insTeachingMethod");


        textView=(TextView)findViewById(R.id.text_view_reserve);
        textView.setText(insName);

        subjectSpinner = (Spinner) findViewById(R.id.spinner_reserve1);
        paymentSpinner = (Spinner) findViewById(R.id.spinner_reserve2);
        placeSpinner = (Spinner) findViewById(R.id.spinner_reserve3);
        methodSpinner = (Spinner) findViewById(R.id.spinner_reserve4);
        continueButton = (Button) findViewById(R.id.button_continue_reserve);

        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, subjects);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(subjectAdapter);
        subjectSpinner.setOnItemSelectedListener(this);

        if (insPaymentMethod.equals("Both")) {
            String[] items = {"Cash", "VISA"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            paymentSpinner.setAdapter(adapter);
        }
        else {
            String[] items = {insPaymentMethod};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            paymentSpinner.setAdapter(adapter);
        }
        paymentSpinner.setOnItemSelectedListener(this);
        /////////////////
        if (insLessonsPlace.equals("Both")) {
            String[] items = {"Instructor's place", "Student's place"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            placeSpinner.setAdapter(adapter);
        }
        else {
            String[] items = {insLessonsPlace};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            placeSpinner.setAdapter(adapter);
        }
        placeSpinner.setOnItemSelectedListener(this);


        if (insTeachingMethod.equals("Both")) {
            String[] items = {"Teaching individual", "Teaching group"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            methodSpinner.setAdapter(adapter);
        }
        else {
            String[] items = {insTeachingMethod};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            methodSpinner.setAdapter(adapter);
        }
        methodSpinner.setOnItemSelectedListener(this);

        continueButton.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(adapterView==subjectSpinner)
            chosenSubject = adapterView.getItemAtPosition(i).toString();

        if(adapterView==paymentSpinner)
            chosenPayment = adapterView.getItemAtPosition(i).toString();


        if(adapterView==placeSpinner)
            chosenPlace= adapterView.getItemAtPosition(i).toString();

        if(adapterView==methodSpinner)
            chosenMethod= adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (view == continueButton){
            Intent i = new Intent(ReserveInfo.this , Reserve.class);
            i.putExtra("insID", insID);
            i.putExtra("insName", insName);
            i.putExtra("price", insLessonsPrice);
            i.putExtra("teachingMethod", chosenMethod);
            i.putExtra("paymentMethod", chosenPayment);
            i.putExtra("place", chosenPlace);
            i.putExtra("subject", chosenSubject);

            startActivity(i);
        }
    }
}
