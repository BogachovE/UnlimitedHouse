package com.e.bogachov.unlmitedhouse.RegLogSign;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.MainMenu;
import com.e.bogachov.unlmitedhouse.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class RegLocation extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_location);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        //find View
        Button loginBtn = (Button)findViewById(R.id.nextbtn);
        loginBtn.setOnClickListener(this);

        EditText loc = (EditText)findViewById(R.id.loc_editloc);
        loc.setOnClickListener(this);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        final Place place = PlacePicker.getPlace(data, this);
        Toast.makeText(this, place.getAddress(),
                Toast.LENGTH_LONG)
                .show();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nextbtn:{
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
