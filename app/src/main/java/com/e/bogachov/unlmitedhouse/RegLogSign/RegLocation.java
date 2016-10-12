package com.e.bogachov.unlmitedhouse.RegLogSign;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.MainMenu;
import com.e.bogachov.unlmitedhouse.R;
import com.firebase.client.Firebase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;

public class RegLocation extends Activity implements View.OnClickListener {

    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_location);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        Firebase.setAndroidContext(this);
        //find View
        Button loginBtn = (Button)findViewById(R.id.nextbtn);
        loginBtn.setOnClickListener(this);

        EditText loc = (EditText)findViewById(R.id.loc_editloc);
        loc.setOnClickListener(this);
        Hawk.init(this).build();
        Hawk.get("userphone");
        userId = Hawk.get("userid");






    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        final Place place = PlacePicker.getPlace(data, this);
        Toast.makeText(this, place.getAddress(),
                Toast.LENGTH_LONG)
                .show();

        Firebase ref = new Firebase("https://unlimeted-house.firebaseio.com/users/"+userId);
        Map<String, Object> setLocation = new HashMap<String, Object>();
        setLocation.put("location", place.getAddress());
        ref.updateChildren(setLocation);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nextbtn:{
                EditText loc_house = (EditText)findViewById(R.id.loc_house);
                Firebase ref = new Firebase("https://unlimeted-house.firebaseio.com/users/"+userId);
                Map<String, Object> setHouse = new HashMap<String, Object>();
                setHouse.put("house", loc_house.getText().toString());
                ref.updateChildren(setHouse);
                Intent goMainMenu = new Intent(RegLocation.this, MainMenu.class);
                startActivity(goMainMenu);
                break;
            }

            case R.id.loc_editloc:{
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
        }
    }



}
