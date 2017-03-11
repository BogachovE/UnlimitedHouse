package com.e.bogachov.unlmitedhouse.Slide;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.MainMenu;
import com.e.bogachov.unlmitedhouse.R;
import com.e.bogachov.unlmitedhouse.ShopMenu;
import com.firebase.client.Firebase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;

public class Profile extends Activity implements View.OnClickListener{
    TextView profile_profeile;
    ImageView signbtn;
    String userId;
    EditText profile_name_edit,profile_email_edit,profile_location_edit,profile_adress_edit;

    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        userId= Hawk.get("userid");
        Firebase.setAndroidContext(this);
        ActionBar actionBar = getActionBar();
        actionBar.hide();


        signbtn = (ImageView)findViewById(R.id.signbtn);
        signbtn.setOnClickListener(this);
        profile_name_edit =(EditText)findViewById(R.id.profile_name_edit);
        profile_name_edit.setOnClickListener(this);
        profile_email_edit =(EditText)findViewById(R.id.profile_email_edit);
        profile_email_edit.setOnClickListener(this);
        profile_location_edit =(EditText)findViewById(R.id.profile_location_edit);
        profile_adress_edit =(EditText)findViewById(R.id.profile_adress_edit);
        profile_adress_edit.setOnClickListener(this);
        profile_location_edit.setOnClickListener(this);

        profile_profeile =(TextView)findViewById(R.id.profile_profeile);
        profile_profeile.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/BebasNeueBook.otf"));
        ref = new Firebase("https://unlimeted-house.firebaseio.com/users/"+userId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        final Place place = PlacePicker.getPlace(data, this);
        Toast.makeText(this, place.getAddress(),
                Toast.LENGTH_LONG)
                .show();
        profile_location_edit.setText(place.getAddress());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signbtn:{
                ref.child("name").setValue(profile_name_edit.getText().toString());
                ref.child("email").setValue(profile_email_edit.getText().toString());
                ref.child("location").setValue(profile_location_edit.getText().toString());
                ref.child("house").setValue(profile_adress_edit.getText().toString());
                Intent goToMainMenu = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(goToMainMenu);
                break;
            }
            case R.id.profile_location_edit:{
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

                break;
            }
            case R.id.profile_name_edit:{
                profile_name_edit.setText("");
                break;
            }
            case R.id.profile_email_edit:{
                profile_email_edit.setText("");
                break;
            }
            case R.id.profile_adress_edit:{
                profile_adress_edit.setText("");
                break;
            }
        }
    }
}
