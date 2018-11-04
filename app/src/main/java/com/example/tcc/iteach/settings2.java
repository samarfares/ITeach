package com.example.tcc.iteach;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class settings2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;
     TextView FN,LN,Dob,Email,Gender,subjects;
     ImageButton edit1,edit2,edit3,edit4,edit5,edit6;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    String currentUserId;
    Person person;
    String firstName,lastName,birth,email,gender,subject;
    MultiSelectionSpinner spinner1;
    private DatePicker datePicker1;
    String date1;
    private RadioGroup radioGroupGender1;

    List<String> chosen1 = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId);
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

        FN = (TextView) findViewById(R.id.IndividualFN);
        LN = (TextView) findViewById(R.id.IndividualLN);
        Dob = (TextView) findViewById(R.id.IndividualDob);
        Email = (TextView) findViewById(R.id.IndividualEma);
        Gender = (TextView) findViewById(R.id.IndividualG);
        subjects = (TextView) findViewById(R.id.IndividualSu);
        edit1 = (ImageButton) findViewById(R.id.edit1);
        edit2 = (ImageButton) findViewById(R.id.edit2);
        edit3 = (ImageButton) findViewById(R.id.edit3);
        edit4 = (ImageButton) findViewById(R.id.edit4);
        edit5 = (ImageButton) findViewById(R.id.edit5);
        edit6 = (ImageButton) findViewById(R.id.edit6);
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
                    FN.setText(firstName);
                    LN.setText(lastName);
                    Dob.setText(birth);
                    Email.setText(email);
                    Gender.setText(gender);
                    subjects.setText(subject);
                }
                else {
                    Toast.makeText(settings2.this, "لقد فشل جلب المعلومات, ", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings2.this);
                builder.setTitle("اكتب الاسم الأول المعدل");
                final EditText inputField = new EditText(settings2.this);
                inputField.setText(firstName);
                builder.setView(inputField);
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("firstName").setValue(inputField.getText().toString());
                        Toast.makeText(settings2.this, "تم تعديل الاسم", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                Dialog dialog2 = builder.create();
                dialog2.show();
            }
        });

        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings2.this);
                builder.setTitle("اكتب الاسم الثاني المعدل");
                final EditText inputField = new EditText(settings2.this);
                inputField.setText(lastName);
                builder.setView(inputField);
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("lastName").setValue(inputField.getText().toString());
                        Toast.makeText(settings2.this, "تم تعديل الاسم", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                Dialog dialog2 = builder.create();
                dialog2.show();
            }
        });

        edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings2.this);
                builder.setTitle("اكتب الجنس المعدل");
                final EditText inputField = new EditText(settings2.this);
                radioGroupGender1 = new RadioGroup(settings2.this);
                inputField.setText(gender);
                builder.setView(radioGroupGender1);
                int radioId= radioGroupGender1.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioId);
                if(radioButton!=null)
                    gender = radioButton.getText().toString();
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("gender").setValue(gender);
                        Toast.makeText(settings2.this, "تم تعديل الجنس", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                Dialog dialog2 = builder.create();
                dialog2.show();
            }
        });

        edit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings2.this);
                builder.setTitle("اختر تاريخ الميلاد المعدل");
                final EditText inputField = new EditText(settings2.this);
                datePicker1 = new DatePicker(settings2.this);
                inputField.setText(birth);
                builder.setView(datePicker1);

             date1 = datePicker1.getDayOfMonth()+"/"+datePicker1.getMonth()+"/"+datePicker1.getYear();
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("dob").setValue(date1);
                        Toast.makeText(settings2.this, "تم تعديل تاريخ الميلاد", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                Dialog dialog2 = builder.create();
                dialog2.show();
            }
        });

        edit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings2.this);
                builder.setTitle("اكتب الايميل المعدل");
                final EditText inputField = new EditText(settings2.this);
                inputField.setText(email);
                builder.setView(inputField);
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("email").setValue(inputField.getText().toString());
                        Toast.makeText(settings2.this, "تم تعديل الايميل", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                Dialog dialog2 = builder.create();
                dialog2.show();
            }
        });
        edit6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings2.this);
                builder.setTitle("اكتب المواد المعدلة");
                final EditText inputField = new EditText(settings2.this);
                spinner1= new MultiSelectionSpinner(settings2.this);
                List<String> list = new ArrayList<String>();

                list.add("العربية");
                list.add("الانكليزية");
                list.add("الرياضيات");
                list.add("الكيمياء");
                list.add("الفيزياء");
                list.add("الموسيقى");
                list.add("الرقص");
                list.add("الرسم");
                list.add("الطبخ");
                spinner1.setItems(list);
        //        inputField.setText(person.getSubjects());
                builder.setView(spinner1);
                chosen1=spinner1.getSelectedStrings();
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("subjects").setValue(chosen1);
                        Toast.makeText(settings2.this, "تم تعديل المواد", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                Dialog dialog2 = builder.create();
                dialog2.show();
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
            Intent h= new Intent(settings2.this,student_main.class);
            startActivity(h);
        } else if (id == R.id.nav_blackboard) {
            Intent h= new Intent(settings2.this,blackboard2.class);
            startActivity(h);
        } else if (id == R.id.nav_notifications) {
            Intent h= new Intent(settings2.this,notifications2.class);
            startActivity(h);
        } else if (id == R.id.nav_manage) {
            Intent h= new Intent(settings2.this,settings2.class);
            startActivity(h);
        } else if (id == R.id.nav_reservations) {
            Intent h= new Intent(settings2.this,reservations2.class);
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
}
