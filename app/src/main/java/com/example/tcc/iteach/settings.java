package com.example.tcc.iteach;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import com.google.firebase.auth.FirebaseAuth;

public class settings extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener , AdapterView.OnItemSelectedListener  {
    Instructor instructor1;
    FirebaseAuth firebaseAuth;
    TextView FN1,LN1,Dob1,Email1,Gender1,subjects1,lessonPlace,lessonPrice,teachingMethod,paymentMethod1,phoneNum,yoe,likes;
    ImageButton edit21,edit22,edit23,edit24,edit25,edit26,edit28,edit29,edit30,edit31,edit32,edit33;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference,databaseReference2;
    String currentUserId;
    Person person;
    long longInstructorsPhoneNum1;
    int intYearsOfExperience1;
    double price1;
    private Spinner specialtySpinner1 , paymentSpinner1, placeSpinner1 , method1;

    String firstName,lastName,birth,email,gender,subject,lessonPl,lessonPri,teachingMeth,paymentMeth,phoneNumber,yoe1,likes1;
    Double newPrice;
    String  instructorEmail2, instructorPassword2 , firstName2, lastName2  , gender2, date2 , yearsOfExperience2 , instructorsPhoneNum2 , chosenString2, priceString2 ,   chosenPaymentMethod2,chosenPlace2 , chosenMethod2;

    long longInstructorsPhoneNum;
    int intYearsOfExperience;
    MultiSelectionSpinner spinner2;
    private DatePicker datePicker2;
    String date1;
    private RadioGroup radioGroupGender2;
    String location1;

    List<String> chosen2 = new ArrayList<String>();
    Button edit,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Instructors").child(currentUserId);
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Instructors").child(currentUserId);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/


        edit = (Button) findViewById(R.id.editProfile);
        delete = (Button) findViewById(R.id.deleteProfile);

        FN1 = (TextView) findViewById(R.id.InstructorFN);
        LN1 = (TextView) findViewById(R.id.InstructorLN);
        Dob1 = (TextView) findViewById(R.id.InstructorDob);
        Email1 = (TextView) findViewById(R.id.InstructorEma);
        Gender1 = (TextView) findViewById(R.id.InstructorG);
        subjects1 = (TextView) findViewById(R.id.InstructorSu);
        lessonPlace = (TextView) findViewById(R.id.InstructorPlace);
        lessonPrice = (TextView) findViewById(R.id.InstructorPrice);;
        teachingMethod = (TextView) findViewById(R.id.InstructorTMethod);
        paymentMethod1 = (TextView) findViewById(R.id.InstructorPayment);
        phoneNum = (TextView) findViewById(R.id.InstructorPhone);
        yoe = (TextView) findViewById(R.id.InstructorYoe);
        likes = (TextView) findViewById(R.id.InstructorLikes);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    firstName = dataSnapshot.child("firstName").getValue().toString();
                    lastName = dataSnapshot.child("lastName").getValue().toString();
                    birth = dataSnapshot.child("dob").getValue().toString();
                    email = dataSnapshot.child("email").getValue().toString();
                    gender = dataSnapshot.child("gender").getValue().toString();
                    subject = dataSnapshot.child("subjects").getValue().toString();
                    lessonPl = dataSnapshot.child("lessonsPlace").getValue().toString();
                    lessonPri = dataSnapshot.child("lessonsPrice").getValue().toString();
                    teachingMeth = dataSnapshot.child("teachingMethod").getValue().toString();
                    paymentMeth = dataSnapshot.child("paymentMethod").getValue().toString();
                    phoneNumber = dataSnapshot.child("phoneNum").getValue().toString();
                    yoe1 = dataSnapshot.child("yoe").getValue().toString();
                    likes1 = dataSnapshot.child("likes").getValue().toString();
                    location1 = dataSnapshot.child("location").getValue().toString();
                    FN1.setText(firstName);
                    LN1.setText(lastName);
                    Dob1.setText(birth);
                    Email1.setText(email);
                    Gender1.setText(gender);
                    subjects1.setText(subject);
                    lessonPlace.setText(lessonPl);
                    lessonPrice.setText(lessonPri);
                    teachingMethod.setText(teachingMeth);
                    paymentMethod1.setText(paymentMeth);
                    phoneNum.setText(phoneNumber);
                    yoe.setText(yoe1);
                    likes.setText(likes1);
                }
                else {
                    Toast.makeText(settings.this, "لقد فشل جلب المعلومات, ", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(settings.this, editProfile.class));
    }
});

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.student_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent h= new Intent(settings.this,student_main.class);
            startActivity(h);
        } else if (id == R.id.nav_blackboard) {
            Intent h= new Intent(settings.this,blackboard2.class);
            startActivity(h);
        } else if (id == R.id.nav_notifications) {
            Intent h= new Intent(settings.this,notifications2.class);
            startActivity(h);
        } else if (id == R.id.nav_manage) {
            Intent h= new Intent(settings.this,settings2.class);
            startActivity(h);
        } else if (id == R.id.nav_reservations) {
            Intent h= new Intent(settings.this,reservations2.class);
            startActivity(h);
        }
        else if (id==R.id.nav_signOut){
            firebaseAuth.signOut();
            startActivity(new Intent(this, MainActivity.class));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
