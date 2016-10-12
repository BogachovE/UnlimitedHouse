package com.e.bogachov.unlmitedhouse.Slide;

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

import com.e.bogachov.unlmitedhouse.MainMenu;
import com.e.bogachov.unlmitedhouse.R;
import com.e.bogachov.unlmitedhouse.ShopMenu;
import com.firebase.client.Firebase;
import com.orhanobut.hawk.Hawk;

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


        signbtn = (ImageView)findViewById(R.id.signbtn);
        signbtn.setOnClickListener(this);
        profile_name_edit =(EditText)findViewById(R.id.profile_name_edit);
        profile_email_edit =(EditText)findViewById(R.id.profile_email_edit);
        profile_location_edit =(EditText)findViewById(R.id.profile_location_edit);
        profile_adress_edit =(EditText)findViewById(R.id.profile_adress_edit);

        profile_profeile =(TextView)findViewById(R.id.profile_profeile);
        profile_profeile.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/BebasNeueBook.otf"));
        ref = new Firebase("https://unlimeted-house.firebaseio.com/users/"+userId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signbtn:{
                ref.child("name").setValue(profile_name_edit.getText().toString());
                ref.child("email").setValue(profile_email_edit.getText().toString());
                ref.child("location").setValue(profile_location_edit.getText().toString());
                ref.child("house:").setValue(profile_adress_edit.getText().toString());
                Intent goToMainMenu = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(goToMainMenu);
                break;
            }
        }
    }
}
