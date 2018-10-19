package com.example.tcc.iteach;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class schedule extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , DatePickerDialog.OnDateSetListener {

    String currentDateString;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    TextView none ;

    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Spot spot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        none = (TextView) findViewById(R.id.noneTextSchedule);
        Button button = (Button) findViewById(R.id.button_choose_date_schedule);
        Button button1 = (Button) findViewById(R.id.button_edit_spots);
        final TextView textView = (TextView) findViewById(R.id.textViewDate);
        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
        textView.setText(currentDateString);


        listView = (ListView) findViewById(R.id.listViewSchedule);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        String instructor_id = firebaseUser.getUid();
        spot = new Spot();


        databaseReference = FirebaseDatabase.getInstance().getReference("Instructors").child(instructor_id).child("spots");
        list = new ArrayList<>();
         adapter = new ArrayAdapter<String>(this,R.layout.spot_info,R.id.listViewSpotInfoTime,list);
        FirebaseDatabase.getInstance().getReference("Instructors").child(instructor_id).child("spots").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    spot = ds.getValue(Spot.class);
                        if (spot.getDate().equals(currentDateString)) {
                            if (spot.isIndividual()) {
                                list.add("Time : " + spot.getTime().toString() + "\nMethod : Individual");
                            } else {
                                list.add("Time : " + spot.getTime().toString() + "\nMethod : Group\nNumber of students : " + spot.getNumberOfStudent());
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



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /////////////////  select date
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(schedule.this , editSpots.class);
                        i.putExtra("date", textView.getText().toString());
                startActivity(i);

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
        getMenuInflater().inflate(R.menu.instructor_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent h= new Intent(schedule.this,instructor_main.class);
            startActivity(h);
        } else if (id == R.id.nav_blackboard) {
            Intent h= new Intent(schedule.this,blackboard.class);
            startActivity(h);
        } else if (id == R.id.nav_notifications) {
            Intent h= new Intent(schedule.this,notifications.class);
            startActivity(h);
        } else if (id == R.id.nav_manage) {
            Intent h= new Intent(schedule.this,settings.class);
            startActivity(h);
        } else if (id == R.id.nav_schedule) {
            Intent h= new Intent(schedule.this,schedule.class);
            startActivity(h);
        } else if (id == R.id.nav_reservations) {
            Intent h= new Intent(schedule.this,reservations.class);
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
    //////////////////////////// select date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView textView = (TextView) findViewById(R.id.textViewDate);
        textView.setText(currentDateString);


        listView = (ListView) findViewById(R.id.listViewSchedule);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        String instructor_id =firebaseUser.getUid();
        spot = new Spot();

        databaseReference = FirebaseDatabase.getInstance().getReference("Instructors").child(instructor_id).child("spots");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.spot_info,R.id.listViewSpotInfoTime,list);
        FirebaseDatabase.getInstance().getReference("Instructors").child(instructor_id).child("spots").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    spot = ds.getValue(Spot.class);
                    if ( spot.getDate().equals(currentDateString)){
                        if ( spot.isIndividual()) {
                            list.add("Time : " + spot.getTime().toString() + "\nMethod : Individual");
                        }
                        else{
                            list.add("Time : " + spot.getTime().toString() + "\nMethod : Group\nNumber of students : "+ spot.getNumberOfStudent());
                        }
                    }
                }
                listView.setAdapter(adapter);
                if (list.isEmpty()){
                    none.setVisibility(View.VISIBLE);
                }
                else
                    none.setVisibility(View.VISIBLE );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
