package com.example.tcc.iteach;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class NameSearch extends Fragment {
    View view;

    ListView listView;
    List<Instructor> list,listLocation;
    List<Instructor> namesList;
    List<Instructor> genderList;
    List<Instructor> priceList;
    List<Instructor> subjectList;
    List<Instructor> locations;
    Double distance;
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
    LatLng locationSelectedLat;
    int textlength = 0;
    List<Instructor> instructorsNames;
    List<Instructor> array_sort,names;
    String person;

    Button btn_popup;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public NameSearch() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate( R.layout.fragment_name_search, container, false );
        person=getActivity().getIntent().getStringExtra( "person" );



        editTextName=(EditText)view. findViewById( R.id.editTextName );
        final String name=editTextName.getText().toString();

        textType=(TextView)view. findViewById(R.id.textType);
        radioType=(RadioGroup)view. findViewById(R.id.radioType);
        listView=(ListView) view.findViewById( R.id.list1 );
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
                    myAdapter = new MyAdapterSearch(getActivity(),R.layout.items,list);
                    listView.setAdapter(myAdapter);
                    Utility.setListViewHeightBasedOnChildren(listView);}
                else {

                    Toast.makeText(getActivity(), "لايوجد نتائج مطابقة لبحثك !!", Toast.LENGTH_SHORT).show();

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
                        list.clear();

                        for (int i = 0; i < names.size(); i++) {
                            if (!(names.get(i).firstName .equals( null ))){
                                if (textlength <= names.get(i).firstName.length()) {
                                    if (names.get(i).firstName.toLowerCase().trim().contains(
                                            editTextName.getText().toString().toLowerCase().trim())||names.get(i).lastName.toLowerCase().trim().contains(
                                            editTextName.getText().toString().toLowerCase().trim())) {
                                        list.add(names.get(i));
                                    }
                                }}
                        }
                        if (list.size()!=0){

                            myAdapter = new MyAdapterSearch(getActivity(),R.layout.items,list);


                            listView.setAdapter(myAdapter);
                            Utility.setListViewHeightBasedOnChildren(listView);}






                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });





//****************************

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Intent intent = new Intent(getActivity(),ViewInstructorProfile.class);
                intent.putExtra("name",list.get(position).getFirstName()+" "+list.get(position).getLastName());
                intent.putExtra("email",list.get(position).getEmail());
                intent.putExtra("insId",list.get(position).getUserID());
                intent.putExtra( "person",person );
                Bundle args = new Bundle();
                String test = SignUpInstructorActivity.decryptIt( list.get(position).getLocation());
                Double lat = Double.valueOf( test.substring( test.indexOf( "(" ) + 1, test.indexOf( "," ) ) );
                Double lng = Double.valueOf( test.substring( test.indexOf( "," ) + 1, test.indexOf( ")" ) ) );
                LatLng t = new LatLng( lat, lng );
                args.putParcelable("location",t);
                intent.putExtra("bundle", args);
                startActivity(intent);
            }
        });
        return view;

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
