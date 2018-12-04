package com.example.tcc.iteach;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class editProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
TextView editTextEmailInstructor2,editTextPasswordInstructor2,editTextNumberInstructor2,editTextFirstName2,editTextLastName2,
        textViewSpecialty2,lessonsPrice2,yearsExperience2;
Spinner payment2,place2,method2;
Button buttonBrowse22,buttonRegister3;
MultiSelectionSpinner input12;
RadioGroup radioGroupGender22;
RadioButton male3,female3;
DatePicker datePicker2;
    private DatabaseReference databaseReference3,databaseReference33;
    String currentUserId3;
    private FirebaseAuth mAuth3;
    String firstName3,lastName3,birth3,email3,gender3,subject3,lessonPl3,lessonPri3,teachingMeth3,paymentMeth3,phoneNumber3,yoe3;
    Button save2;
    String  instructorEmail, instructorPassword , firstName, lastName  , gender, date , yearsOfExperience , instructorsPhoneNum , chosenString, priceString ,   chosenPaymentMethod,chosenPlace , chosenMethod , insId;
    long longInstructorsPhoneNum;
    int intYearsOfExperience;
    double price;
    List<String> chosen = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth3 = FirebaseAuth.getInstance();
        currentUserId3 = mAuth3.getCurrentUser().getUid();
        databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Instructors").child(currentUserId3);
        databaseReference33 = FirebaseDatabase.getInstance().getReference().child("Instructors").child(currentUserId3);

        editTextFirstName2 = (TextView) findViewById(R.id.editTextFirstName2);
        editTextLastName2 = (TextView) findViewById(R.id.editTextLastName2);
        datePicker2 = (DatePicker) findViewById(R.id.datePicker2);
        editTextEmailInstructor2 = (TextView) findViewById(R.id.editTextEmailInstructor2);
        radioGroupGender22 = (RadioGroup) findViewById(R.id.radioGroupGender22);
        male3 = (RadioButton) findViewById(R.id.male3);
        female3 = (RadioButton) findViewById(R.id.female3);
        input12 = (MultiSelectionSpinner) findViewById(R.id.input12);
        place2 = (Spinner) findViewById(R.id.place2);
        lessonsPrice2 = (TextView) findViewById(R.id.lessonsPrice2);
        method2 = (Spinner) findViewById(R.id.method2);
        payment2 = (Spinner) findViewById(R.id.payment2);
        editTextNumberInstructor2 = (TextView) findViewById(R.id.editTextNumberInstructor2);
        yearsExperience2 = (TextView) findViewById(R.id.yearsExperience2);
        save2 = (Button) findViewById(R.id.buttonRegister3);

        ArrayAdapter<CharSequence> paymentAdapter = ArrayAdapter.createFromResource(this ,R.array.paymentMethod,android.R.layout.simple_spinner_item);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payment2.setAdapter(paymentAdapter);
        //setContentView(R.layout.activity_sign_up_instructor);
        payment2.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> placeAdapter = ArrayAdapter.createFromResource(this,R.array.lessonsPlace,android.R.layout.simple_spinner_item);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place2.setAdapter(placeAdapter);
        place2.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> methodAdapter = ArrayAdapter.createFromResource(this,R.array.method,android.R.layout.simple_spinner_item);
        methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        method2.setAdapter(methodAdapter);
        method2.setOnItemSelectedListener(this);
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
        input12.setItems(list);
       // likes = (TextView) findViewById(R.id.InstructorLikes);
       /* databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    firstName3 = dataSnapshot.child("firstName").getValue().toString();
                    lastName3 = dataSnapshot.child("lastName").getValue().toString();
                    birth3 = dataSnapshot.child("dob").getValue().toString();
                    email3 = dataSnapshot.child("email").getValue().toString();
                    gender3 = dataSnapshot.child("gender").getValue().toString();
                    subject3 = dataSnapshot.child("subjects").getValue().toString();
                    lessonPl3 = dataSnapshot.child("lessonsPlace").getValue().toString();
                    lessonPri3 = dataSnapshot.child("lessonsPrice").getValue().toString();
                    teachingMeth3 = dataSnapshot.child("teachingMethod").getValue().toString();
                    paymentMeth3 = dataSnapshot.child("paymentMethod").getValue().toString();
                    phoneNumber3 = dataSnapshot.child("phoneNum").getValue().toString();
                    yoe3 = dataSnapshot.child("yoe").getValue().toString();
                 //   likes1 = dataSnapshot.child("likes").getValue().toString();
               //     location1 = dataSnapshot.child("location").getValue().toString();
                    editTextFirstName2.setText(firstName3);
                    editTextLastName2.setText(lastName3);
                  //  datePicker2.setText(birth3);
                    editTextEmailInstructor2.setText(email3);
                   // Gender1.setText(gender3);
                    // subjects3.setText(subject3);
                 //   lessonPlace.setText(lessonPl3);
                    lessonsPrice2.setText(lessonPri3);
                 //   teachingMethod.setText(teachingMeth3);
                  //  paymentMethod1.setText(paymentMeth3);
                    editTextNumberInstructor2.setText(phoneNumber3);
                    yearsExperience2.setText(yoe3);
                }
                else {
                    Toast.makeText(editProfile.this, "لقد فشل جلب المعلومات, ", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });  */

save2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        instructorEmail = editTextEmailInstructor2.getText().toString();
        //instructorPassword = editTextPasswordInstructor.getText().toString();

        firstName= editTextFirstName2.getText().toString();
        lastName= editTextLastName2.getText().toString();
        yearsOfExperience = yearsExperience2.getText().toString();
        instructorsPhoneNum = editTextNumberInstructor2.getText().toString();
        date = datePicker2.getDayOfMonth()+"/"+datePicker2.getMonth()+"/"+datePicker2.getYear();
        priceString = lessonsPrice2.getText().toString();
        // To know if user selects male or female
        int radioId= radioGroupGender22.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);
        if(radioButton!=null)
            gender = radioButton.getText().toString();
        chosen=input12.getSelectedStrings();

        // Toast.makeText(SignUpInstructorActivity.this,chosen.get( 0 ), Toast.LENGTH_SHORT).show();


        //chosenString= input12.getSelectedItemsAsString(); // this variable contains the specialties chosen by the instructor as a string EX:Arablic,English
        //instructor = new Instructor(firstName,lastName,date,gender,encryptedLocation,longInstructorsPhoneNum, intYearsOfExperience,price,0 , chosenPaymentMethod, chosenPlace , chosenMethod );


        if(!(TextUtils.isEmpty(instructorEmail))){
            // email is empty!
databaseReference3.child("email").setValue(instructorEmail);
        }

        if(!(TextUtils.isEmpty(instructorsPhoneNum))){
            longInstructorsPhoneNum=Long.parseLong(instructorsPhoneNum);
            databaseReference3.child("phoneNum").setValue(longInstructorsPhoneNum);
        }
        if(instructorsPhoneNum.length()!= 10&& !(instructorsPhoneNum.substring( 0,1 ).equals( "05" ))){
            Toast.makeText(editProfile.this, "رقم الهاتف يجب أن يكون من 10 أرقام ويبدأ ب 05", Toast.LENGTH_LONG).show();
            return ;
        }
        if(!(TextUtils.isEmpty(firstName))){
            databaseReference3.child("firstName").setValue(firstName);
        }

        if(!(TextUtils.isEmpty(lastName))){
            databaseReference3.child("lastName").setValue(lastName);

        }

        if(!(TextUtils.isEmpty(date))){
            databaseReference3.child("dob").setValue(date);

        }

        if(datePicker2.getYear()>2000){
            Toast.makeText(editProfile.this, "عذراً غير مسموح لك التسجيل بهذا العمر", Toast.LENGTH_LONG).show();
return;
        }

        if(!(TextUtils.isEmpty(gender))){
            databaseReference3.child("gender").setValue(gender);

        }

        if(!(TextUtils.isEmpty(yearsOfExperience))){
            intYearsOfExperience=Integer.parseInt(yearsOfExperience);
            databaseReference3.child("yoe").setValue(intYearsOfExperience);

        }

        if(!(TextUtils.isEmpty(priceString))){
            price=Double.parseDouble(priceString);
            databaseReference3.child("lessonsPrice").setValue(price);

        }
        if(!(chosen.equals(""))){
            databaseReference3.child("subjects").setValue(chosen);
        }

            databaseReference3.child("paymentMethod").setValue(chosenPaymentMethod);



            databaseReference3.child("lessonsPlace").setValue(chosenPlace);


            databaseReference3.child("teachingMethod").setValue(chosenMethod);



      /*  if(pdfUri==null){

            return ;
        }  */
// if validations are ok we register user

    }
});
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent==payment2)
            chosenPaymentMethod = parent.getItemAtPosition(position).toString();


        if(parent==place2)
            chosenPlace= parent.getItemAtPosition(position).toString();

        if(parent==method2)
            chosenMethod= parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
