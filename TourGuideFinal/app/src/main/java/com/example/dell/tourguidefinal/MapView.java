package com.example.dell.tourguidefinal;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MapView extends Fragment implements OnMapReadyCallback {

    String DefaultLocation;
    static String UserLocation;
    static String SearachLocation;
    static String type;
    String area;
    static String radius;
    private static GoogleMap mMap;
    ArrayList<PostingSupport> UserArrayList = new ArrayList<>();
    static DatabaseReference mDatabase;
    private static Address hostAddress;
    public static List<PostingSupport> arrayList = new ArrayList<>();

    private static LatLng t_latLng;

    private static Context c;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.show_mapview, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Posts");
        DefaultLocation = "SUST";

        c = getContext();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return view;
    }

    public static void PRE() {
        UserLocation = SearchDialog.getQloaction();
        type = SearchDialog.getQtype();
        radius = SearchDialog.getQradius();

        CircleOptions circleOptions = new CircleOptions();
        MarkerOptions markerOptions = new MarkerOptions();

        PRE2();

        circleOptions.center(t_latLng);
        circleOptions.radius(Integer.valueOf(radius) * 1000);
        circleOptions.strokeColor(Color.CYAN);
        circleOptions.fillColor(0x4D000080);
        mMap.addCircle(circleOptions);


        Log.d("Check  ", UserLocation);
        Log.d("Check  ", type);
        Log.d("Check  ", radius);

        mDatabase.child(type).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PostingSupport gpi = dataSnapshot.getValue(PostingSupport.class);

                assert gpi != null;

                Geocoder geocoder = new Geocoder(c);
                List<Address> addressList = null;
                MarkerOptions markerOptions = new MarkerOptions();
                SearachLocation = gpi.getArea();

                try {
                    addressList = geocoder.getFromLocationName(SearachLocation, 6);
                    if (addressList != null) {
                        for (int i = 0; i < addressList.size(); i++) {
                            Address userAddress = addressList.get(i);

                            LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                            float results[] = new float[100];
                            Location.distanceBetween(userAddress.getLatitude(), userAddress.getLongitude(), hostAddress.getLatitude(), hostAddress.getLongitude(), results);
                            /*
                            markerOptions.position(latLng);
                            markerOptions.title(type);
                            markerOptions.snippet(Integer.toString(fee));

                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                            CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(Search_trial.this);
                            mMap.setInfoWindowAdapter(customInfoWindow);
                            Marker m = mMap.addMarker(markerOptions);
                            m.setTag(rent_add);
                            m.showInfoWindow();

                            */
                            if (results[0] / 1000 <= Float.valueOf(radius)) {
                                arrayList.add(gpi);
                                Log.d("Check","arrayList");
                                for(i=0;i<=arrayList.size();i++) {
                                    System.out.println("array " + arrayList);
                                }
                                markerOptions.position(latLng);
                                markerOptions.title(type);
                                markerOptions.snippet(gpi.getArea());
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                                CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(c);

                                mMap.setInfoWindowAdapter(customInfoWindow);
                                Marker m = mMap.addMarker(markerOptions);
                                m.setTag(gpi);
                                m.showInfoWindow();
                            }

                        }

                    }

                } catch (Exception e) {
                }


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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addressList = null;
        MarkerOptions markerOptions = new MarkerOptions();
        CircleOptions circleOptions = new CircleOptions();
        mMap = googleMap;
        try {
            addressList = geocoder.getFromLocationName("SUST", 10);

            if (addressList != null) {
                for (int i = 0; i < addressList.size(); i++) {
                    Address userAddress = addressList.get(i);
                    hostAddress = userAddress;
                    LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                    markerOptions.position(latLng);
                    markerOptions.title(DefaultLocation);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


                    mMap.addMarker(markerOptions);

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    Log.d("Check ", "Hi");

                }

            }

        } catch (Exception e) {

        }

    }


    /////////////////////////////////////////////
    private static void PRE2() {
        mMap.clear();
        Geocoder geocoder = new Geocoder(c);
        List<Address> addressList = null;
        MarkerOptions markerOptions = new MarkerOptions();
        CircleOptions circleOptions = new CircleOptions();

        try {
            addressList = geocoder.getFromLocationName(UserLocation, 10);
            if (addressList != null) {
                for (int i = 0; i < addressList.size(); i++) {
                    hostAddress = addressList.get(i);

                    t_latLng = new LatLng(hostAddress.getLatitude(), hostAddress.getLongitude());

                    //adding marker to searched place

                    markerOptions.position(t_latLng);
                    markerOptions.title(UserLocation);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                    //uMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(t_latLng, 10));

                }

                //addressList.add(addressList.get(0));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
