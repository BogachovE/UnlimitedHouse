package com.e.bogachov.unlmitedhouse.Slide;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.R;
import com.e.bogachov.unlmitedhouse.ShopMenu;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;

public class AddService extends Activity implements View.OnClickListener {
    String userId;
    EditText profile_name_edit,profile_email_edit,
            profile_location_edit,
            profile_adress_edit,
            profile_photo_edit;
    ImageView signbtn;

    Firebase ref;
    Firebase categRef;
    TextView profile_profeile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_service);

        signbtn = (ImageView)findViewById(R.id.signbtn);
        signbtn.setOnClickListener(this);
        profile_profeile =(TextView)findViewById(R.id.profile_profeile);
        profile_profeile.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/BebasNeueBook.otf"));

        userId= Hawk.get("userid");
        Firebase.setAndroidContext(this);

        profile_photo_edit= (EditText)findViewById(R.id.profile_photo_edit);
        profile_name_edit =(EditText)findViewById(R.id.profile_name_edit);
        profile_email_edit =(EditText)findViewById(R.id.profile_email_edit);
        profile_location_edit =(EditText)findViewById(R.id.profile_location_edit);
        profile_adress_edit =(EditText)findViewById(R.id.profile_adress_edit);

        ref = new Firebase("https://unlimeted-house.firebaseio.com/users/"+userId);
        categRef = new Firebase("https://unlimeted-house.firebaseio.com/shops/category/"+profile_location_edit.getText().toString()+"/");




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signbtn:{
                ref.child("name").setValue(profile_name_edit.getText().toString());
                ref.child("email").setValue(profile_email_edit.getText().toString());
                categRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long count = dataSnapshot.getChildrenCount();
                        categRef.child(count.toString()).child("name").setValue(profile_name_edit.getText().toString());
                        categRef.child(count.toString()).child("photourl").setValue(profile_photo_edit.getText().toString());
                        Map<String,String> addZeroServ = new HashMap<String,String>();
                        addZeroServ.put("name","PLUS");
                        addZeroServ.put("photourl","http://fs156.www.ex.ua/show/697962838068/276256660/276256660.png");
                        categRef.child(count+"/servicetype/0/").setValue(addZeroServ);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                ref.child("adress").setValue(profile_adress_edit.getText().toString());
                ref.child("isitshop").setValue("true");
                Intent goToShopMenu = new Intent(getApplicationContext(), ShopMenu.class);
                startActivity(goToShopMenu);
                break;
            }
        }
    }
}
