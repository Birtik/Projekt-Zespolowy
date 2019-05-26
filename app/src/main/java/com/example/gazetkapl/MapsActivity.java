package com.example.gazetkapl;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //public static final String KEY_VERTICAL_ACCURACY ="";
    private GoogleMap mMap;
    private double N,E;
    private static final String TAG = "MapsActivity";
    private static final int MY_REQUEST_INT = 177;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION},MY_REQUEST_INT);
            }
            return;
        }
        else
        {

            mMap.setMyLocationEnabled(true);
            fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);
            final Task location = fusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    currentLocation = (Location) task.getResult();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(),
                            currentLocation.getLongitude()),15F));
                N = currentLocation.getLatitude();
                E = currentLocation.getLongitude();
                searchShops(N,E);
                }
            });
        }
        //String napis = "Biedronka szczecin";




        /*LatLng sydney = new LatLng(53.453705, 14.536539);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Jesteś tutaj!"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15F));

        LatLng sydney2 = new LatLng(53.453290, 14.534887);
        mMap.addMarker(new MarkerOptions().position(sydney2).title("Jesteś tutaj!"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney2,15F));*/
    }
    void searchShops(double N2, double E2)
    {
        String napis = "Lidl chopina";
        Geocoder geocenter = new Geocoder(this);
        List<Address> lista = null;

        try
        {
            lista = geocenter.getFromLocationName(napis,2, (N2- 0.022), (E2-0.018 ),  (N2+ 0.022),(E2+0.018));
        }catch(IOException e){
            e.printStackTrace();
        }

        Address sklep;
        LatLng s;

        for(int i=0;i<lista.size();i++)
        {
            sklep = lista.get(i);
            s = new LatLng(sklep.getLatitude(),sklep.getLongitude());
            mMap.addMarker(new MarkerOptions().position(s).title("Lidl!"));
        }
    }

}