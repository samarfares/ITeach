package com.example.tcc.iteach;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class LocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{


    public static final String TAG = LocationActivity.class.getSimpleName();
    private static final int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient mClient;
    String search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_location );
        mClient = new GoogleApiClient.Builder( this )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .addApi( Places.GEO_DATA_API )
                .enableAutoManage( this, this )
                .build();

        search=getIntent().getStringExtra( "search" );


        try {

            PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
            Intent i=builder.build( this );
            startActivityForResult(i,PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG,String.format( "Google Play Services Not Available" ,e.getMessage()));
        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG,String.format( "Google Play Services Not Available" ,e.getMessage()));
        }
    }

    @Override
    public void onConnected(@Nullable Bundle connectionHint) {
        Log.i(TAG,"API client connection successful!!");
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG,"API client connection suspended!!");
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG,"API client connection Failed!!");

    }


    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        if(requestCode==PLACE_PICKER_REQUEST && resultCode==RESULT_OK){
            Place place=PlacePicker.getPlace( this,data );
            if(place==null){
                Log.i(TAG,"No place selected");
                return;
            }
         LatLngBounds Riyadh = new LatLngBounds(new LatLng(23.63 ,46.51 ),new LatLng(26.4207 ,50.0888));

                LatLng placeId=place.getLatLng();
            if(Riyadh.contains(placeId))

            {
                if( search==null){
                Intent intent=new Intent(this, SignUpInstructorActivity.class);
                    Bundle args = new Bundle();
                    args.putParcelable("location", placeId);
                    intent.putExtra("bundle", args);

                    startActivity(intent);}
                Intent intent=new Intent(this, SearchForInstructorActivity.class);
                Bundle args = new Bundle();
                args.putParcelable("location", placeId);
                intent.putExtra("bundle", args);

                startActivity(intent);

            }
            else
                Toast.makeText(LocationActivity.this, "Your Location must be in Riyadh..sorry !!, ", Toast.LENGTH_LONG).show();

            Intent intent=new Intent(this, SignUpInstructorActivity.class);
            startActivity(intent);
        }


    }


}