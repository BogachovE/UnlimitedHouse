package com.e.bogachov.unlmitedhouse.RegLogSign;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.MainMenu;
import com.e.bogachov.unlmitedhouse.R;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;

public class RegLocation extends Activity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    EditText loc_house;
    String userId;
    Boolean plaseExists;
    Intent getExtra;
    HashMap<String, String> newUser;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_location);

        plaseExists = false;

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        newUser = new HashMap<String, String>();

        Firebase.setAndroidContext(this);
        //find View
        Button loginBtn = (Button) findViewById(R.id.nextbtn);
        loginBtn.setOnClickListener(this);

        loc_house = (EditText) findViewById(R.id.loc_house);
        loc_house.setOnClickListener(this);

        EditText loc = (EditText) findViewById(R.id.loc_editloc);
        loc.setOnClickListener(this);
        Hawk.init(this).build();
        Hawk.get("userphone");
        userId = Hawk.get("userid");

        getExtra = getIntent();
        newUser = (HashMap<String, String>) getExtra.getSerializableExtra("newUser");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            final Place place = PlacePicker.getPlace(data, this);

            Toast.makeText(this, place.getAddress(),
                    Toast.LENGTH_LONG)
                    .show();
            Firebase ref = new Firebase("https://unhouse-143417.firebaseio.com/users/" + userId);
            Map<String, Object> setLocation = new HashMap<String, Object>();
            double lati = place.getLatLng().latitude;
            double longi = place.getLatLng().longitude;


            newUser.put("locationlatitude", Double.toString(lati));
            newUser.put("locationlongitude", Double.toString(longi));
            ref.setValue(newUser);

            Location userLoc = new Location("asd");
            userLoc.setLatitude(place.getLatLng().latitude);
            userLoc.setLongitude(place.getLatLng().longitude);
            Hawk.put("userLoc", userLoc);
            plaseExists = true;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextbtn: {
                if ((!loc_house.getText().toString().equals("  ENTER HOUSE NUMBER"))) {
                    Firebase ref = new Firebase("https://unhouse-143417.firebaseio.com/users/" + userId);
                    newUser.put("house", loc_house.getText().toString());
                    ref.setValue(newUser);
                    Intent goMainMenu = new Intent(RegLocation.this, MainMenu.class);
                    startActivity(goMainMenu);
                } else {
                    Toast.makeText(this, "Please enter your location", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case R.id.loc_editloc: {
                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(this);
                    // Start the Intent by requesting a result, identified by a request code.
                    startActivityForResult(intent, 1);


                    // Hide the pick option in the UI to prevent users from starting the picker
                    // multiple times.


                } catch (GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil
                            .getErrorDialog(e.getConnectionStatusCode(), this, 0);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(this, "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
            case R.id.loc_house: {
                loc_house.setText("");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("APIII","conected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("APIII","Suspendedconected");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("APIII","Faileconected");
    }
}
