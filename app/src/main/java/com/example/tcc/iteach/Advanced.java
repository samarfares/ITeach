package com.example.tcc.iteach;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.example.tcc.iteach.NameSearch.CalculationByDistance;


public class Advanced extends Fragment {
    View view;
    ListView listView;
    private DatabaseReference databaseReference;

    private Button location,search;
    String[] title;
    String spinner_item;

    SpinnerAdapter adapter;
    MyAdapterSearch myAdapter;

    String decryptedLocation,gender,subject,price,person;

    public LatLng SearchLocation;
    List<Instructor> list,listLocation;
    List<Instructor> namesList;
    List<Instructor> genderList;
    List<Instructor> priceList;
    List<Instructor> subjectList;
    List<Instructor> locations;
    final ArrayList<String> keyList = new ArrayList<>();
    private TextView textLocation;
    private EditText edittext;
    private RadioGroup radioType;
    private RadioButton radioTypeButton;
    private Spinner subjectSpinner;
    private TextView textType,textSubject,text;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Advanced() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Bundle bundle = data.getParcelableExtra("bundle");
        if (bundle!=null) {
            SearchLocation = bundle.getParcelable( "location" );
            person=data.getStringExtra( "person" );
            Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(SearchLocation.latitude, SearchLocation.longitude, 1);
                if(null!=listAddresses&&listAddresses.size()>0){
                    String test = listAddresses.get(0).getAddressLine(0);

                    textLocation.setText( "اخترت هذا الموقع : "+"\n"+test );
                    location.setVisibility( View.GONE );

                }
            } catch (IOException e) {
                e.printStackTrace();}


        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate( R.layout.fragment_advanced, container, false );
        list = new ArrayList<>();
        namesList = new ArrayList<>();
        genderList = new ArrayList<>();
        priceList = new ArrayList<>();
        subjectList = new ArrayList<>();
        locations = new ArrayList<>();
        listLocation = new ArrayList<>();



        title = getResources().getStringArray(R.array.specialty);
        adapter= new SpinnerAdapter(getActivity().getApplicationContext());

        person=getActivity().getIntent().getStringExtra( "person" );

        listView=(ListView)view. findViewById( R.id.list1 );
        text = (TextView) view.findViewById(R.id.text);

        textType = (TextView) view.findViewById(R.id.textType);
        textSubject = (TextView) view.findViewById(R.id.textSubject);


        subjectSpinner = (Spinner) view.findViewById(R.id.subject);
        edittext = (EditText) view.findViewById(R.id.editTextPrice);
        radioType=(RadioGroup) view.findViewById(R.id.radioType);
        textLocation=(TextView) view.findViewById( R.id.textlocation );
        Button button = (Button) view.findViewById(R.id.button1);
        subjectSpinner.setAdapter(adapter);





        location=(Button) view.findViewById(R.id.detectLocation);
        location.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), LocationActivity.class);
                intent.putExtra( "person",person );

                intent.putExtra( "search","true" );
                startActivityForResult(intent,999);


            }


        });










        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_item = title[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        search=(Button)view. findViewById(R.id.button1);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textLocation.setVisibility( View.GONE );
                subjectSpinner.setVisibility( View.GONE );
                edittext.setVisibility( View.GONE );
                radioType.setVisibility( View.GONE );
                location.setVisibility( View.GONE );
                textType.setVisibility( View.GONE );
                textSubject.setVisibility( View.GONE );
                Button button = (Button)view. findViewById(R.id.button1);
                subjectSpinner.setAdapter(adapter);

                int selectedId = radioType.getCheckedRadioButtonId();

                radioTypeButton = (RadioButton) view.findViewById( selectedId );
                search.setVisibility( View.GONE );
                text.setText( "نتائج التصفية : " );
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

                            if(SearchLocation!=null) {
                                String test = SignUpInstructorActivity.decryptIt(instructor.location);
                                Double lat = Double.valueOf( test.substring( test.indexOf( "(" ) + 1, test.indexOf( "," ) ) );
                                Double lng = Double.valueOf( test.substring( test.indexOf( "," ) + 1, test.indexOf( ")" ) ) );
                                LatLng t = new LatLng( lat, lng );
                                Double distance = CalculationByDistance( t, SearchLocation );
                                if (distance <= 5)
                                    locations.add( instructor );

                            }


                            if(gender!=null){
                                if (instructor.getGender().equals( gender ))
                                    genderList.add( instructor );}
                            if (!(price .matches(""))){

                                double lessonPrice = Double.parseDouble( price );
                                if (instructor.lessonsPrice < lessonPrice)
                                    priceList.add( instructor );}
                            if(subject!=null){

                                if (instructor.subjects.contains( subject ))

                                    subjectList.add( instructor );

                            }


                        }
                        // if he chooses but mine is empty



                        Set<Instructor> common1;
                        common1 = new HashSet<Instructor>(list);
                        if (locations.size()>0&&genderList.size()>0&&subjectList.size()>0&& priceList.size()>0)
                        {
                            Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();

                            common1.retainAll(priceList);
                            common1.retainAll(locations);
                            common1.retainAll(subjectList);
                            common1.retainAll(genderList);}
                        if (locations.size()>0&&genderList.size()<=0&&subjectList.size()>0&& priceList.size()>0)
                        {
                            Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();

                            common1.retainAll(priceList);
                            common1.retainAll(locations);
                            common1.retainAll(subjectList);
                         }
                        if (locations.size()>0&&genderList.size()>0&&subjectList.size()<=0&& priceList.size()>0)
                        {
                            Toast.makeText(getActivity(), "3", Toast.LENGTH_SHORT).show();

                            common1.retainAll(priceList);
                            common1.retainAll(locations);
                            common1.retainAll(genderList);}
                        if (locations.size()>0&&genderList.size()>0&&subjectList.size()>0&& priceList.size()<=0)
                        {
                            Toast.makeText(getActivity(), "4", Toast.LENGTH_SHORT).show();

                            common1.retainAll(locations);
                            common1.retainAll(subjectList);
                            common1.retainAll(genderList);}
                        if (locations.size()<=0&&genderList.size()>0&&subjectList.size()>0&& priceList.size()>0)
                        {
                            Toast.makeText(getActivity(), "5", Toast.LENGTH_SHORT).show();

                            common1.retainAll(priceList);
                            common1.retainAll(subjectList);
                            common1.retainAll(genderList);}
                        if (locations.size()>0&&genderList.size()<=0&&subjectList.size()<=0&& priceList.size()>0)
                        {
                            Toast.makeText(getActivity(), "6", Toast.LENGTH_SHORT).show();

                            common1.retainAll(priceList);
                            common1.retainAll(locations);
                            }
                        if (locations.size()>0&&genderList.size()<=0&&subjectList.size()>0&& priceList.size()<=0)
                        {
                            Toast.makeText(getActivity(), "7", Toast.LENGTH_SHORT).show();

                            common1.retainAll(locations);
                            common1.retainAll(subjectList);
                           }
                        if (locations.size()>0&&genderList.size()>0&&subjectList.size()<=0&& priceList.size()<=0)
                        {
                            Toast.makeText(getActivity(), "8", Toast.LENGTH_SHORT).show();

                            common1.retainAll(locations);
                            common1.retainAll(genderList);}
                        if (locations.size()<=0&&genderList.size()<=0&&subjectList.size()>0&& priceList.size()>0)
                        {
                            Toast.makeText(getActivity(), "9", Toast.LENGTH_SHORT).show();

                            common1.retainAll(priceList);
                            common1.retainAll(subjectList);
                            }
                        if (locations.size()<=0&&genderList.size()>0&&subjectList.size()<=0&& priceList.size()>0)
                        {
                            Toast.makeText(getActivity(), "10", Toast.LENGTH_SHORT).show();

                            common1.retainAll(priceList);
                            common1.retainAll(genderList);}
                        if (locations.size()<=0&&genderList.size()>0&&subjectList.size()>0&& priceList.size()<=0)
                        {
                            Toast.makeText(getActivity(), "11", Toast.LENGTH_SHORT).show();

                            common1.retainAll(subjectList);
                            common1.retainAll(genderList);}

                        if (locations.size()<=0&&genderList.size()<=0&&subjectList.size()<=0&& priceList.size()>0)
                        {
                            Toast.makeText(getActivity(), "12", Toast.LENGTH_SHORT).show();

                            common1.retainAll(priceList);
                           }
                        if (locations.size()<=0&&genderList.size()<=0&&subjectList.size()>0&& priceList.size()<=0)
                        {
                            Toast.makeText(getActivity(), "13", Toast.LENGTH_SHORT).show();

                            common1.retainAll(subjectList);
                           }
                        if (locations.size()<=0&&genderList.size()>0&&subjectList.size()<=0&& priceList.size()<=0)
                        {
                            Toast.makeText(getActivity(), "14", Toast.LENGTH_SHORT).show();

                            common1.retainAll(genderList);}
                        if (locations.size()>0&&genderList.size()<=0&&subjectList.size()<=0&& priceList.size()<=0)
                        {
                            Toast.makeText(getActivity(), "15", Toast.LENGTH_SHORT).show();

                            common1.retainAll(locations);
                        }




                        list = new ArrayList<Instructor>(common1);


                        if (list.size()!=0){

                            myAdapter = new MyAdapterSearch(getActivity(),R.layout.items,list);


                            listView.setAdapter(myAdapter);
                            Utility.setListViewHeightBasedOnChildren(listView);
                        }
                        else {
                            text.setText( "لايوجد نتائج مطابقة لبحثك  " );
                            text.setTextSize( 50 );
                            Toast.makeText(getActivity(), "لايوجد نتائج مطابقة لبحثك !!", Toast.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });


            }


        });

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



}
