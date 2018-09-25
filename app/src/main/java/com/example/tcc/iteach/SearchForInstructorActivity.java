package com.example.tcc.iteach;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchForInstructorActivity extends AppCompatActivity {
    ListView listView;
    List<Instructor> list;
    final ArrayList<String> keyList = new ArrayList<>();
    public static final String DATABASE_PATH = "Instructors";
    private DatabaseReference databaseReference;
    MyAdapterSearch myAdapter;
    private EditText editTextName, editTextPrice;
    private TextView textViewSearch,textType,textSubject;
    private Button location,search,advancedSearch;
    private RadioGroup radioType;
    private RadioButton radioTypeButton;
    private Spinner subjectSpinner;
    String decryptedLocation;
    LatLng locationSelectedLat;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_instructor);

        Bundle bundle = getIntent().getParcelableExtra("bundle");
        if (bundle!=null)
        locationSelectedLat = bundle.getParcelable("location");


        subjectSpinner = (Spinner) findViewById(R.id.subject);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.specialty, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(adapter);
        subjectSpinner.setVisibility( View.GONE );
        editTextName=(EditText) findViewById( R.id.editTextName );
        editTextName.setVisibility( View.VISIBLE );
        textSubject=(TextView) findViewById(R.id.textSubject);
        textSubject.setVisibility( View.GONE );
        textType=(TextView) findViewById(R.id.textType);
        textType.setVisibility( View.GONE );
        textViewSearch=(TextView) findViewById(R.id.TextViewSearch);
        editTextPrice= (EditText) findViewById(R.id.editTextPrice);
        editTextPrice.setVisibility( View.GONE );
        radioType=(RadioGroup) findViewById(R.id.radioType);
        radioType.setVisibility( View.GONE );
        advancedSearch=(Button) findViewById(R.id.advancedSearch);
        location=(Button) findViewById(R.id.location);
        location.setVisibility( View.GONE );
        search=(Button) findViewById(R.id.search);
        location.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(SearchForInstructorActivity.this, LocationActivity.class);
                intent.putExtra( "search","true" );
                startActivity(intent);

            }


        });
        advancedSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 subjectSpinner.setVisibility( View.VISIBLE );
                 textSubject.setVisibility( View.VISIBLE );
                 textType.setVisibility( View.VISIBLE );
                 editTextPrice.setVisibility( View.VISIBLE );
                 radioType.setVisibility( View.VISIBLE );
                 location.setVisibility( View.VISIBLE );




                }


        });
        final String name=editTextName.getText().toString();
        final String price=editTextPrice.getText().toString();
        final String subject=subjectSpinner.getSelectedItem().toString();
        int selectedId = radioType.getCheckedRadioButtonId();

        radioTypeButton = (RadioButton) findViewById( selectedId );

        final String gender = radioTypeButton.getText().toString();
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                list = new ArrayList<>();
                databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list.clear();

                        for(DataSnapshot snap : dataSnapshot.getChildren()){
                            keyList.add(snap.getKey());

                            Instructor instructor = snap.getValue(Instructor.class);
                            if (name!=null) {
                                if (price != null) {
                                    if(gender!=null)
                                    {
                                        if(subject!=null){
                                            double lessonPrice = Double.parseDouble( price );

                                            if(instructor.firstName.matches(".*"+name+".*")||instructor.lastName.matches(".*"+name+".*"))
                                              if (instructor.lessonsPrice <= lessonPrice)
                                                  if (instructor.getGender().equals( gender ))
                                                      // location and subject!!!!!!!!!!!
                                                      list.add( instructor );


                                        }
                                        double lessonPrice = Double.parseDouble( price );

                                        if(instructor.firstName.matches(".*"+name+".*")||instructor.lastName.matches(".*"+name+".*"))
                                            if (instructor.lessonsPrice <= lessonPrice)
                                                if (instructor.getGender().equals( gender ))
                                                    list.add( instructor );
                                    }
                                    double lessonPrice = Double.parseDouble( price );

                                    if(instructor.firstName.matches(".*"+name+".*")||instructor.lastName.matches(".*"+name+".*"))
                                        if (instructor.lessonsPrice <= lessonPrice)
                                            list.add( instructor );

                                }
                                if(instructor.firstName.matches(".*"+name+".*")||instructor.lastName.matches(".*"+name+".*"))
                                    list.add( instructor );


                            }
                                }
                            }





                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





            }



        });
        myAdapter = new MyAdapterSearch( this,R.layout.items,list);
        listView.setAdapter(myAdapter);


    }

}
