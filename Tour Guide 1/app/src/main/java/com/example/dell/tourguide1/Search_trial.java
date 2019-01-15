package com.example.dell.tourguide1;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Search_trial extends FragmentActivity implements OnMapReadyCallback {

    private String user_location,search_location,type;
    private GoogleMap mMap;
    ArrayList<normal_post> user_arrayList = new ArrayList<>();
    DatabaseReference mDatabase1,mDatabase2,mDatabase3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trial);
        //map_button = (Button)findViewById(R.id.map_button);

        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Posts");
        user_location = getIntent().getStringExtra("place").toString().trim();
        type = getIntent().getStringExtra("type").toString().trim();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Log.d("Search_Tag",type);
        Log.d("Search_Tag",user_location);
        Log.d("Search_Tag",type);
        Log.d("Search_Tag",type);

        if(type.equals("Food"))
        {
            mDatabase1.child(type).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    normal_post gpi = dataSnapshot.getValue(normal_post.class);
                    Geocoder geocoder = new Geocoder(Search_trial.this);
                    List<Address> addressList = null;
                    MarkerOptions markerOptions = new MarkerOptions();
                    search_location = gpi.getArea();

                    try {
                        addressList = geocoder.getFromLocationName(search_location,6);
                        if(addressList!=null)
                        {
                            for (int i=0;i<addressList.size();i++)
                            {
                                Address userAddress = addressList.get(i);

                                LatLng latLng = new LatLng(userAddress.getLatitude(),userAddress.getLongitude());

                                markerOptions.position(latLng);
                                markerOptions.title(search_location);
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));


                                mMap.addMarker(markerOptions);

                            }

                        }

                    }catch (Exception e){}


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(type.equals("Hotel"))
        {
            mDatabase1.child(type).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    normal_post gpi = dataSnapshot.getValue(normal_post.class);
                    Geocoder geocoder = new Geocoder(Search_trial.this);
                    List<Address> addressList = null;
                    MarkerOptions markerOptions = new MarkerOptions();
                    search_location = gpi.getArea();

                    try {
                        addressList = geocoder.getFromLocationName(search_location,6);
                        if(addressList!=null)
                        {
                            for (int i=0;i<addressList.size();i++)
                            {
                                Address userAddress = addressList.get(i);

                                LatLng latLng = new LatLng(userAddress.getLatitude(),userAddress.getLongitude());

                                markerOptions.position(latLng);
                                markerOptions.title(search_location);
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));


                                mMap.addMarker(markerOptions);

                            }

                        }

                    }catch (Exception e){}


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(type.equals("Place"))
        {
            mDatabase1.child(type).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    normal_post gpi = dataSnapshot.getValue(normal_post.class);
                    Geocoder geocoder = new Geocoder(Search_trial.this);
                    List<Address> addressList = null;
                    MarkerOptions markerOptions = new MarkerOptions();
                    search_location = gpi.getArea();

                    try {
                        addressList = geocoder.getFromLocationName(search_location,6);
                        if(addressList!=null)
                        {
                            for (int i=0;i<addressList.size();i++)
                            {
                                Address userAddress = addressList.get(i);

                                LatLng latLng = new LatLng(userAddress.getLatitude(),userAddress.getLongitude());

                                markerOptions.position(latLng);
                                markerOptions.title(search_location);
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));


                                mMap.addMarker(markerOptions);

                            }

                        }

                    }catch (Exception e){}


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
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

        Geocoder geocoder = new Geocoder(Search_trial.this);
        List<Address> addressList = null;
        MarkerOptions markerOptions = new MarkerOptions();
        CircleOptions circleOptions = new CircleOptions();
        mMap = googleMap;
        try{
            addressList = geocoder.getFromLocationName(user_location,10);

            if(addressList!=null)
            {
                for (int i=0;i<addressList.size();i++)
                {
                    Address userAddress = addressList.get(i);

                    LatLng latLng = new LatLng(userAddress.getLatitude(),userAddress.getLongitude());

                    markerOptions.position(latLng);
                    markerOptions.title(user_location);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


                    circleOptions.center(latLng);
                    circleOptions.radius(10000);
                    circleOptions.strokeColor(Color.CYAN);
                    circleOptions.fillColor(0x4D000080);

                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                    mMap.addCircle(circleOptions);


                }

            }

        }catch (Exception e){

        }

    }
}
