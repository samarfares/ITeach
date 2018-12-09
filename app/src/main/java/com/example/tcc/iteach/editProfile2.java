package com.example.tcc.iteach;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
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

public class editProfile2 extends AppCompatActivity {
    TextView editTextEmailStudent2,editTextPasswordStudent2,editTextNumberStudent2,editTextFirstNameStudent2,editTextLastNameStudent2,
            textViewSpecialty2,lessonsPrice2,yearsExperience2;
    Spinner payment2,place2,method2;
    Button buttonBrowse22,buttonRegister3;
    MultiSelectionSpinner input22;
    RadioGroup radioGroupGender22;
    RadioButton male22,female22;
    DatePicker datePickerStudent2;
    private DatabaseReference databaseReference4,databaseReference44;
    String currentUserId4;
    private FirebaseAuth mAuth4;
    String firstName3,lastName3,birth3,email3,gender3,subject3,lessonPl3,lessonPri3,teachingMeth3,paymentMeth3,phoneNumber3,yoe3;
    List<String> subjects = new ArrayList<String>();
    String studentEmail, studentPassword, studentFName , studentLName, studentGender, studentDOB;
    private RadioGroup radioGroupGender2;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile2);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth4 = FirebaseAuth.getInstance();
        currentUserId4 = mAuth4.getCurrentUser().getUid();
        databaseReference4 = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId4);
        databaseReference44 = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserId4);
        datePickerStudent2 = (DatePicker) findViewById(R.id.datePickerStudent2);
        editTextFirstNameStudent2 = (TextView) findViewById(R.id.editTextFirstNameStudent2);
        editTextLastNameStudent2 = (TextView) findViewById(R.id.editTextLastNameStudent2);

        editTextEmailStudent2 = (TextView) findViewById(R.id.editTextEmailStudent2);
        radioGroupGender22 = (RadioGroup) findViewById(R.id.radioGroupGender22);
        male22 = (RadioButton) findViewById(R.id.male22);
        female22 = (RadioButton) findViewById(R.id.female22);
        input22 = (MultiSelectionSpinner) findViewById(R.id.input22);
        save = (Button) findViewById(R.id.buttonRegister22);
        List<String> list = new ArrayList<String>();
        list.add("");
        list.add("العربية");
        list.add("الانكليزية");
        list.add("الرياضيات");
        list.add("الكيمياء");
        list.add("الفيزياء");
        list.add("الموسيقى");
        list.add("الرقص");
        list.add("الرسم");
        list.add("الطبخ");
        input22.setItems(list);

        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    firstName3 = dataSnapshot.child("firstName").getValue().toString();
                    lastName3 = dataSnapshot.child("lastName").getValue().toString();
                    birth3 = dataSnapshot.child("dob").getValue().toString();
                    String day = birth3.substring(0,2);
                    String month = birth3.substring(3,5);
                    String year = birth3.substring(6,10);
                    int mday=Integer.valueOf(day);
                    int mmonth=Integer.valueOf(month);
                    int myear=Integer.valueOf(year);
                    datePickerStudent2.updateDate(myear,mmonth,mday);
                    email3 = dataSnapshot.child("email").getValue().toString();
                    gender3 = dataSnapshot.child("gender").getValue().toString();
                    subject3 = dataSnapshot.child("subjects").getValue().toString();
                    editTextFirstNameStudent2.setText(firstName3);
                    editTextLastNameStudent2.setText(lastName3);
                    //  datePicker2.setText(birth3);
                    editTextEmailStudent2.setText(email3);
                    // Gender1.setText(gender3);
                    // subjects3.setText(subject3);
                    //   lessonPlace.setText(lessonPl3);
                }
                else {
                 //   Toast.makeText(editProfile2.this, "لقد فشل جلب المعلومات, ", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       // place2 = (Spinner) findViewById(R.id.place2);
      //  lessonsPrice2 = (TextView) findViewById(R.id.lessonsPrice2);
     //   method2 = (Spinner) findViewById(R.id.method2);
     //   payment2 = (Spinner) findViewById(R.id.payment2);
        //editTextNumberStudent2 = (TextView) findViewById(R.id.editTextNumberStudent2);
       // yearsExperience2 = (TextView) findViewById(R.id.yearsExperience2);
        // likes = (TextView) findViewById(R.id.InstructorLikes);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        studentEmail = editTextEmailStudent2.getText().toString();
      //  studentPassword = editTextPasswordStudent.getText().toString();

        studentFName= editTextFirstNameStudent2.getText().toString();
        studentLName= editTextLastNameStudent2.getText().toString();
        studentDOB = datePickerStudent2.getDayOfMonth()+"/"+datePickerStudent2.getMonth()+"/"+datePickerStudent2.getYear();
                subjects=input22.getSelectedStrings();
        // To know if user selects male or female
        int radioId= radioGroupGender22.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);
        if(radioButton!=null)
            studentGender = radioButton.getText().toString();

                if(!(TextUtils.isEmpty(studentEmail))){

                    databaseReference4.child("email").setValue(studentEmail);
                }


                if(!(TextUtils.isEmpty(studentFName))){
                    databaseReference4.child("firstName").setValue(studentFName);}

                if(!(TextUtils.isEmpty(studentLName))){
                    databaseReference4.child("lastName").setValue(studentLName);
                }

                if(!(TextUtils.isEmpty(studentGender))){
                    databaseReference4.child("gender").setValue(studentGender);
                }

                if(!(TextUtils.isEmpty(studentDOB))){
                    databaseReference4.child("dob").setValue(studentDOB);
                }
                if(datePickerStudent2.getYear()>2003){
                    Toast.makeText(editProfile2.this, "عذراً غير مسموح لك التسجيل بهذا العمر", Toast.LENGTH_LONG).show();return;
                    }
                    if(!(subjects.equals(""))){
                        databaseReference4.child("subjects").setValue(subjects);
                    }
                Toast.makeText(editProfile2.this, "تم التعديل بنجاح", Toast.LENGTH_LONG).show();

                Intent h= new Intent(editProfile2.this,settings2.class);
                startActivity(h);

            }
        });

    }
}
