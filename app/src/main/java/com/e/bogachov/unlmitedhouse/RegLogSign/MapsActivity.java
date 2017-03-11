package com.e.bogachov.unlmitedhouse.RegLogSign;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.R;
import com.e.bogachov.unlmitedhouse.Slide.SlideMenu;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    ArrayList<Marker> markers;
    Marker marker;
    private GoogleMap mMap;
    Button chooseBtn;
    String userId;
    List<Address> address;
    HashMap<String, String> newUser;
    Intent getExtra;
    GoogleApiClient mGoogleApiClient;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Hawk.init(this).build();
        userId = Hawk.get("userid");

        chooseBtn = (Button) findViewById(R.id.chooseBtn);
        chooseBtn.setOnClickListener(this);


        newUser = new HashMap<>();
        getExtra = getIntent();
        newUser = (HashMap<String, String>) getExtra.getSerializableExtra("newUser");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addApi(Places.GEO_DATA_API)
                .build();

        PendingResult result =
                Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient,  "grecheskaia",null ,null);


        String s;
        s = "s";
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
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (marker!=null) marker.remove();
        marker = (mMap.addMarker(new MarkerOptions().position(latLng).title("her?")));
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        address = new ArrayList<>();
        try {
            address = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            chooseBtn.setText(address.get(0).getAddressLine(0));
        } catch (IOException e) {
            Toast.makeText(this,"Nexuia "+e,Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chooseBtn: {
                Firebase ref = new Firebase("https://unhouse-143417.firebaseio.com/users/" + userId);
                double lati = address.get(0).getLatitude();
                double longi = address.get(0).getLongitude();

                newUser.put("locationlatitude", Double.toString(lati));
                newUser.put("locationlongitude", Double.toString(longi));

                Location userLoc = new Location("asd");
                userLoc.setLatitude(address.get(0).getLatitude());
                userLoc.setLongitude(address.get(0).getLongitude());
                Hawk.put("userLoc", userLoc);
                Intent goToRegLoc = new Intent(this,RegLocation.class);
                goToRegLoc.putExtra("newUser",newUser);
                startActivity(goToRegLoc);
                break;
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
