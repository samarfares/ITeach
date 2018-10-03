package com.example.tcc.iteach;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchForInstructorActivity extends AppCompatActivity {
    ListView listView;
    List<Instructor> list,listLocation;
    List<Instructor> namesList;
    List<Instructor> genderList;
    List<Instructor> priceList;
    List<Instructor> subjectList;
    List<Instructor> locations;

    MyAdapterSearch myAdapter;

    public LatLng SearchLocation;


    final ArrayList<String> keyList = new ArrayList<>();
    public static final String DATABASE_PATH = "Instructors";
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private EditText editTextName;
    private TextView textViewSearch,textType,textSubject;
    private Button location,search,advancedSearch,locationSearch;
    private RadioGroup radioType;
    private RadioButton radioTypeButton;
    private Spinner subjectSpinner;
    String decryptedLocation,gender,subject,price;
    LatLng locationSelectedLat;
    int textlength = 0;
    List<Instructor> instructorsNames;
    List<Instructor> array_sort,names;


    ImageButton btn_popup;

    String[] title;
    String spinner_item;

    SpinnerAdapter adapter;
/*
  @Override
    protected void onStart() {

mAuth =FirebaseAuth.getInstance();
        super.onStart();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser==null)
        {
            Intent intent=new Intent(SearchForInstructorActivity.this,MainActivity.class);
            startActivity(intent);
        }

    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_instructor);


        Bundle bundle = getIntent().getParcelableExtra("bundle");
        if (bundle!=null) {
            SearchLocation = bundle.getParcelable( "location" );

        }


        editTextName=(EditText) findViewById( R.id.editTextName );
        final String name=editTextName.getText().toString();

        textType=(TextView) findViewById(R.id.textType);
        radioType=(RadioGroup) findViewById(R.id.radioType);
        listView=(ListView) findViewById( R.id.list1 );
        list = new ArrayList<>();
        array_sort=new ArrayList<>();
        names=new ArrayList<>();
        instructorsNames=new ArrayList<>();
        namesList = new ArrayList<>();
        genderList = new ArrayList<>();
        priceList = new ArrayList<>();
        subjectList = new ArrayList<>();
        locations = new ArrayList<>();
        listLocation = new ArrayList<>();



        location=(Button) findViewById(R.id.detectLocation);
        location.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(SearchForInstructorActivity.this, LocationActivity.class);
                intent.putExtra( "search","true" );
                startActivity(intent);


            }


        });
        locationSearch=(Button) findViewById(R.id.location);
        locationSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(SearchLocation!=null) {
                    databaseReference = FirebaseDatabase.getInstance().getReference( "Instructors" );
                    databaseReference.addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            locations.clear();
                            for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                Instructor instructor = snap.getValue( Instructor.class );
                                locations.add( instructor );

                            }
                            for (int i = 0; i < locations.size(); i++) {

                                String test = SignUpInstructorActivity.decryptIt( locations.get( i ).location );
                                Double lat = Double.valueOf( test.substring( test.indexOf( "(" ) + 1, test.indexOf( "," ) ) );
                                Double lng = Double.valueOf( test.substring( test.indexOf( "," ) + 1, test.indexOf( ")" ) ) );
                                LatLng t = new LatLng( lat, lng );
                                Double distance = CalculationByDistance( t, SearchLocation );
                                if (distance <= 5)
                                    listLocation.add( locations.get( i ) );


                            }
                            if (listLocation.size()>0)
                            {

                                myAdapter = new MyAdapterSearch( SearchForInstructorActivity.this, R.layout.items, listLocation );
                            listView.setAdapter( myAdapter );
                            Utility.setListViewHeightBasedOnChildren( listView );}
                            else
                                Toast.makeText(SearchForInstructorActivity.this, "No Results Found !!", Toast.LENGTH_SHORT).show();


                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    } );

                }
                else
                    Toast.makeText(SearchForInstructorActivity.this, "You need to choose location first", Toast.LENGTH_SHORT).show();



            }


        });






        databaseReference = FirebaseDatabase.getInstance().getReference("Instructors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    keyList.add(snap.getKey());
                    names.add(snap.getValue(Instructor.class));

                    Instructor instructor = snap.getValue(Instructor.class);
                        list.add(instructor);

                }

                if (list.size()!=0){
                    myAdapter = new MyAdapterSearch(SearchForInstructorActivity.this,R.layout.items,list);
                    listView.setAdapter(myAdapter);
                    Utility.setListViewHeightBasedOnChildren(listView);}
                else {

                    Toast.makeText(SearchForInstructorActivity.this, "No Results Found !!", Toast.LENGTH_SHORT).show();

                }
                for (int i = 0; i < names.size(); i++) {

                    Instructor Names =names.get(i);
                    // Binds all strings into an array
                    array_sort.add(Names);
                }

                editTextName.addTextChangedListener(new TextWatcher() {


                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        textlength = editTextName.getText().length();
                        array_sort.clear();

                        for (int i = 0; i < names.size(); i++) {
                            if (!(names.get(i).firstName .equals( null ))){
                            if (textlength <= names.get(i).firstName.length()) {
                                if (names.get(i).firstName.toLowerCase().trim().contains(
                                        editTextName.getText().toString().toLowerCase().trim())||names.get(i).lastName.toLowerCase().trim().contains(
                                        editTextName.getText().toString().toLowerCase().trim())) {
                                    array_sort.add(names.get(i));
                                }
                            }}
                        }
                        if (array_sort.size()!=0){

                            myAdapter = new MyAdapterSearch(SearchForInstructorActivity.this,R.layout.items,array_sort);


                            listView.setAdapter(myAdapter);
                            Utility.setListViewHeightBasedOnChildren(listView);}






                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });






        title = getResources().getStringArray(R.array.specialty);

        btn_popup = (ImageButton) findViewById(R.id.button1);

        adapter=new SpinnerAdapter(getApplicationContext());

        btn_popup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Dialog dialog = new Dialog(SearchForInstructorActivity.this);
                dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.row_spinner);
                dialog.setCancelable(true);




                // set the custom dialog components - text, image and button
                final Spinner spinner = (Spinner) dialog.findViewById(R.id.subject);
                final EditText edittext = (EditText) dialog.findViewById(R.id.editTextPrice);
                final RadioGroup radioType=(RadioGroup) dialog.findViewById(R.id.radioType);

                Button button = (Button) dialog.findViewById(R.id.button1);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spinner_item = title[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        int selectedId = radioType.getCheckedRadioButtonId();

                        final RadioButton radioTypeButton = (RadioButton) dialog.findViewById( selectedId );

                        if (radioTypeButton!=null )gender = radioTypeButton.getText().toString();
                       if(!(edittext.getText().toString().equals( null )))  price=edittext.getText().toString();
                      subject=spinner_item;

                        databaseReference = FirebaseDatabase.getInstance().getReference("Instructors");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                list.clear();
                                genderList.clear();
                                subjectList.clear();
                                priceList.clear();
                                for(DataSnapshot snap : dataSnapshot.getChildren()){
                                    keyList.add(snap.getKey());

                                    Instructor instructor = snap.getValue(Instructor.class);
                                    list.add( instructor );


                                    if(gender!=null){
                                        if (instructor.getGender().equals( gender ))
                                            genderList.add( instructor );}
                                    if (!(price .matches(""))){

                                        double lessonPrice = Double.parseDouble( price );
                                        if (instructor.lessonsPrice <= lessonPrice)
                                            priceList.add( instructor );}
                                    if(subject!=null){

                                      if (instructor.subjects.contains( subject ))
                                          subjectList.add( instructor );
                                    }


                                }



                                    Set<Instructor> common1;
                                common1 = new HashSet<Instructor>(list);
                               if (genderList.size()>0&& priceList.size()<=0 &&subjectList.size()<=0)
                                common1.retainAll(genderList);
                                if(priceList.size ()>0&&genderList.size()<=0&&subjectList.size()<=0)
                                common1.retainAll(priceList);
                                if (subjectList.size()>0&&genderList.size()<=0&&priceList.size()<=0)
                                    common1.retainAll(subjectList);
                                if (genderList.size()>0&& priceList.size()>0 &&subjectList.size()<=0){
                                    common1.retainAll(genderList);
                                    common1.retainAll(priceList);}
                                if (genderList.size()>0&& priceList.size()<=0 &&subjectList.size()>0){
                                    common1.retainAll(genderList);
                                    common1.retainAll(subjectList);}
                                if(priceList.size ()>0&&genderList.size()<=0&&subjectList.size()>0){
                                    common1.retainAll(priceList);
                                    common1.retainAll(subjectList); }
                                if(priceList.size ()>0&&genderList.size()>0&&subjectList.size()>0){
                                    common1.retainAll(priceList);
                                    common1.retainAll(subjectList);
                                    common1.retainAll(genderList);
                                }




                                list = new ArrayList<Instructor>(common1);
                                if(genderList.size()<=0 && priceList.size()<=0 && subjectList.size()<=0)
                                    list.clear();

                                if (list.size()!=0){
                                    Toast.makeText(SearchForInstructorActivity.this, "innnn filter", Toast.LENGTH_SHORT).show();

                                    myAdapter = new MyAdapterSearch(SearchForInstructorActivity.this,R.layout.items,list);


                                    listView.setAdapter(myAdapter);
                                    Utility.setListViewHeightBasedOnChildren(listView);}
                                else {

                                    Toast.makeText(SearchForInstructorActivity.this, "No Results Found !!", Toast.LENGTH_SHORT).show();

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }


                        });


                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });


    }

    public class SpinnerAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater mInflater;

        public SpinnerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                v = mInflater.inflate(R.layout.row_edittext, null);
                holder = new ListContent();
                holder.text = (TextView) v.findViewById(R.id.textView1);

                v.setTag(holder);
            } else {
                holder = (ListContent) v.getTag();
            }

            holder.text.setText(title[position]);

            return v;
        }
    }

    static class ListContent {
        TextView text;
    }
    public static double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius=6371;//radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec =  Integer.valueOf(newFormat.format(km));
        double meter=valueResult%1000;
        int  meterInDec= Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value",""+valueResult+"   KM  "+kmInDec+" Meter   "+meterInDec);

        return Radius * c;
    }
}
    /*
    ListView listView;
    List<Instructor> list;
    final ArrayList<String> keyList = new ArrayList<>();
    public static final String DATABASE_PATH = "Instructors";
    private DatabaseReference databaseReference;
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
        listView=(ListView) findViewById( R.id.list1 );
        list = new ArrayList<>();


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
                databaseReference = FirebaseDatabase.getInstance().getReference("Instructors");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list.clear();

                        for(DataSnapshot snap : dataSnapshot.getChildren()){
                            keyList.add(snap.getKey());

                            Instructor instructor = snap.getValue(Instructor.class);

                            if (!name.equals(null)) {

                                if (gender!=null) {

                                    if(!gender.equals(null))
                                    {
                                        if(!subject.equals(null)){
                                            if (location !=null){
                                            double lessonPrice = Double.parseDouble( price );

                                            if(instructor.firstName.matches(".*"+name+".*")||instructor.lastName.matches(".*"+name+".*"))
                                              if (instructor.lessonsPrice <= lessonPrice)
                                                  if (instructor.getGender().equals( gender ))
                                                    // SignUpInstructorActivity.decryptIt(String.valueOf(instructor.location.latitude));
                                                      if (instructor.subjects.contains( subject ))
                                                      list.add( instructor );


                                        }

                                            double lessonPrice = Double.parseDouble( price );

                                            if(instructor.firstName.matches(".*"+name+".*")||instructor.lastName.matches(".*"+name+".*"))
                                                if (instructor.lessonsPrice <= lessonPrice)
                                                    if (instructor.getGender().equals( gender ))
                                                        if (instructor.subjects.contains( subject ))
                                                            list.add( instructor );



                                        }
                                        double lessonPrice = Double.parseDouble( price );

                                        if(instructor.firstName.matches(".*"+name+".*")||instructor.lastName.matches(".*"+name+".*"))
                                            if (instructor.lessonsPrice <= lessonPrice)
                                                if (instructor.getGender().equals( gender ))
                                                    list.add( instructor );



                                }    }

                                if(instructor.firstName.matches(".*"+name+".*")||instructor.lastName.matches(".*"+name+".*"))

                                    list.add( instructor );}



                            }
                                }



                        if (list!=null){
                            Toast.makeText(SearchForInstructorActivity.this,list.get(0).firstName+' '+list.get( 0 ).lastName, Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(SearchForInstructorActivity.this,SearchResultsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("data", (Parcelable) list );
                            intent.putExtras(bundle);
                            startActivity(intent);


                        }

                        else
                            Toast.makeText(SearchForInstructorActivity.this, "No Results Found !!", Toast.LENGTH_SHORT).show();

                    }






                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });






            }



        });


    }
*/

