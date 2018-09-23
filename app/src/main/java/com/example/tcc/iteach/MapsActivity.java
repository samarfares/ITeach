package com.example.tcc.iteach;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
private Instructor instructor;
String studentFName , studentLName , SGender , studentDOB , instructorsFName ,instructorsLName,DOB,gender ;
long phoneNum;
int yearsOfExperience;
double price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        instructorsFName = getIntent().getExtras().getString("instructorsFName");
        instructorsLName = getIntent().getExtras().getString("instructorsLName");
DOB = getIntent().getExtras().getString("DOB");
gender = getIntent().getExtras().getString("gender");
phoneNum = getIntent().getExtras().getLong("phoneNum");
yearsOfExperience =getIntent().getExtras().getInt("yearsOfExperience");
 price = getIntent().getExtras().getDouble("price");

 studentFName =  getIntent().getExtras().getString("studentFName");
 studentLName =  getIntent().getExtras().getString("studentLName");
 SGender =  getIntent().getExtras().getString("SGender");
 studentDOB=getIntent().getExtras().getString("studentDOB");

 // to know who called this activity student or instructor
        if (!(instructorsFName.equals(null))){


 }

 else if (!(studentFName.equals(null))){



        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
