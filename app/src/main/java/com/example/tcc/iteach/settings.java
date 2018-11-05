package com.example.tcc.iteach;

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

public class settings extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener , AdapterView.OnItemSelectedListener {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        edit21 = (ImageButton) findViewById(R.id.edit21);
        edit22 = (ImageButton) findViewById(R.id.edit22);
        edit23 = (ImageButton) findViewById(R.id.edit23);
        edit24 = (ImageButton) findViewById(R.id.edit24);
        edit25 = (ImageButton) findViewById(R.id.edit25);
        edit26 = (ImageButton) findViewById(R.id.edit26);

        edit28 = (ImageButton) findViewById(R.id.edit28);
        edit29 = (ImageButton) findViewById(R.id.edit29);
        edit30 = (ImageButton) findViewById(R.id.edit30);
        edit31 = (ImageButton) findViewById(R.id.edit31);
        edit32 = (ImageButton) findViewById(R.id.edit32);
        edit33 = (ImageButton) findViewById(R.id.edit33);

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
        edit21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
                builder.setTitle("اكتب الاسم المعدل");
                final EditText inputField = new EditText(settings.this);
                inputField.setText(firstName);
                builder.setView(inputField);
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("firstName").setValue(inputField.getText().toString());
                        Toast.makeText(settings.this, "تم تعديل الاسم الأول", Toast.LENGTH_SHORT).show();
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

        edit22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
                builder.setTitle("اكتب الاسم المعدل");
                final EditText inputField = new EditText(settings.this);
                inputField.setText(lastName);
                builder.setView(inputField);
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("lastName").setValue(inputField.getText().toString());
                        Toast.makeText(settings.this, "تم تعديل الاسم الأخير", Toast.LENGTH_SHORT).show();
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

        edit24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
                builder.setTitle("اختر تاريخ الميلاد المعدل");
                final EditText inputField = new EditText(settings.this);
                datePicker2 = new DatePicker(settings.this);
                inputField.setText(birth);
                builder.setView(datePicker2);

                date1 = datePicker2.getDayOfMonth()+"/"+datePicker2.getMonth()+"/"+datePicker2.getYear();
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("dob").setValue(date1);
                        Toast.makeText(settings.this, "تم تعديل تاريخ الميلاد", Toast.LENGTH_SHORT).show();
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

        edit25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
                builder.setTitle("اكتب النص المعدل");
                final EditText inputField = new EditText(settings.this);
                inputField.setText(email);
                builder.setView(inputField);
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("email").setValue(inputField.getText().toString());
                        Toast.makeText(settings.this, "تم تعديل السؤال", Toast.LENGTH_SHORT).show();
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

        edit23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
                builder.setTitle("اختر الجنس المعدل");
                final EditText inputField = new EditText(settings.this);
                //inputField.setText(gender);
                radioGroupGender2 = new RadioGroup(settings.this);
                RadioButton fe = new RadioButton(settings.this);
                RadioButton Ma = new RadioButton(settings.this);
                fe.setText("أنثى");
                Ma.setText("ذكر");
radioGroupGender2.addView(fe,0);
                radioGroupGender2.addView(Ma,1);
                builder.setView(radioGroupGender2);
                int radioId= radioGroupGender2.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioId);
                if(radioButton!=null)
                    gender = radioButton.getText().toString();
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("gender").setValue(gender);
                        Toast.makeText(settings.this, "تم تعديل الجنس", Toast.LENGTH_SHORT).show();
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
        edit26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
                builder.setTitle("اختر الموادالمعدلة");
                spinner2= new MultiSelectionSpinner(settings.this);
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
                spinner2.setItems(list);
                //        inputField.setText(person.getSubjects());
                builder.setView(spinner2);
                chosen2=spinner2.getSelectedStrings();
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // databaseReference.child("subjects").setValue(chosen2);
                        newPrice=Double.parseDouble(lessonPl);
                    //    instructor1 = new Instructor (firstName,lastName,birth,gender,location1,longInstructorsPhoneNum, intYearsOfExperience,newPrice,0,0,0 ,paymentMeth, lessonPl , teachingMeth , email, chosen2 , currentUserId );
                     //   databaseReference2.setValue(instructor1);
                        databaseReference.child("subjects").setValue(chosen2);
                        Toast.makeText(settings.this, "تم تعديل المواد", Toast.LENGTH_SHORT).show();
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
        edit28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
                builder.setTitle("اخترالمكان المعدل");
                final EditText inputField = new EditText(settings.this);
                //        inputField.setText(person.getSubjects());
                placeSpinner1 = new Spinner(settings.this);
                ArrayAdapter<CharSequence> placeAdapter = ArrayAdapter.createFromResource(getBaseContext(),R.array.lessonsPlace,android.R.layout.simple_spinner_item);
                placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                placeSpinner1.setAdapter(placeAdapter);
                builder.setView(placeSpinner1);
                placeSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        chosenPlace2= parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("lessonsPlace").setValue(chosenPlace2);
                        Toast.makeText(settings.this, "تم تعديل المكان", Toast.LENGTH_SHORT).show();
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

        edit29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
                builder.setTitle("اكتب الرقم المعدل");
                final EditText inputField = new EditText(settings.this);
                //        inputField.setText(person.getSubjects());
                builder.setView(inputField);
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        longInstructorsPhoneNum1=Long.parseLong(inputField.getText().toString());
                        databaseReference.child("phoneNum").setValue(longInstructorsPhoneNum1);
                        Toast.makeText(settings.this, "تم تعديل الرقم", Toast.LENGTH_SHORT).show();
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

        edit30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
                builder.setTitle("اختر طريقة التدريس المعدلة");
                final EditText inputField = new EditText(settings.this);
                //        inputField.setText(person.getSubjects());
                method1 = new Spinner(settings.this);
                ArrayAdapter<CharSequence> methodAdapter = ArrayAdapter.createFromResource(settings.this,R.array.method,android.R.layout.simple_spinner_item);
                methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                method1.setAdapter(methodAdapter);
                builder.setView(method1);
                method1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        chosenMethod2= parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("teachingMethod").setValue(chosenMethod2);
                        Toast.makeText(settings.this, "تم تعديل طريقة التدريس", Toast.LENGTH_SHORT).show();
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

        edit31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(settings.this);
                builder1.setTitle("اختر طريقة الدفع المعدلة");
                final EditText inputField = new EditText(settings.this);
                paymentSpinner1 = new Spinner(settings.this);
                ArrayAdapter<CharSequence> paymentAdapter = ArrayAdapter.createFromResource(settings.this,R.array.paymentMethod,android.R.layout.simple_spinner_item);
                paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                paymentSpinner1.setAdapter(paymentAdapter);
                builder1.setView(paymentSpinner1);
                paymentSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        chosenPaymentMethod2 = parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //        inputField.setText(person.getSubjects());

                builder1.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("paymentMethod").setValue(chosenPaymentMethod2);
                        Toast.makeText(settings.this, "تم تعديل طريقة الدفع", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                Dialog dialog3 = builder1.create();
                dialog3.show();
            }
        });

        edit32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
                builder.setTitle("اكتب السعر المعدل");
                final EditText inputField = new EditText(settings.this);
                //        inputField.setText(person.getSubjects());
                builder.setView(inputField);

                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        price1=Double.parseDouble(inputField.getText().toString());
                        databaseReference.child("lessonsPrice").setValue(price1);
                        Toast.makeText(settings.this, "تم تعديل السعر", Toast.LENGTH_SHORT).show();
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

        edit33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
                builder.setTitle("اكتب سنوات الخبرة المعدل");
                final EditText inputField = new EditText(settings.this);
                //        inputField.setText(person.getSubjects());
                builder.setView(inputField);
                builder.setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intYearsOfExperience1=Integer.parseInt(inputField.getText().toString());
                        databaseReference.child("yoe").setValue(intYearsOfExperience1);
                        Toast.makeText(settings.this, "تم تعديل سنوات الخبرة", Toast.LENGTH_SHORT).show();
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
        if(parent==paymentSpinner1)
            chosenPaymentMethod2 = parent.getItemAtPosition(position).toString();


        if(parent==placeSpinner1)
            chosenPlace2= parent.getItemAtPosition(position).toString();

        if(parent==method1)
            chosenMethod2= parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
