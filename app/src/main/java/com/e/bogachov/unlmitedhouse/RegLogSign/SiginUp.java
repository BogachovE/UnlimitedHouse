package com.e.bogachov.unlmitedhouse.RegLogSign;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.R;

import com.e.bogachov.unlmitedhouse.ShopMenu;
import com.e.bogachov.unlmitedhouse.ShopsCateg.Order;
import com.e.bogachov.unlmitedhouse.Users;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nexmo.sdk.NexmoClient;
import com.nexmo.sdk.core.client.ClientBuilderException;
import com.nexmo.sdk.verify.client.VerifyClient;
import com.nexmo.sdk.verify.event.UserObject;
import com.nexmo.sdk.verify.event.VerifyClientListener;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.e.bogachov.unlmitedhouse.R.id.phonetxt;
import static com.e.bogachov.unlmitedhouse.verifysample.Config.NexmoAppId;
import static com.e.bogachov.unlmitedhouse.verifysample.Config.NexmoSharedSecretKey;

public class SiginUp extends Activity implements View.OnClickListener {
    Context context = getApplication();

    String mPrefix;
    EditText phonetxt;
    String mPhone;
    DatabaseReference mData;
    Long count;
    Long newUserId;
    String userPhone;
    String isItShop;
    SharedPreferences sPref;
    Users users;


    public static final String TAG = SiginUp.class.getSimpleName();
    VerifyClient  verifyClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        // getFragmentManager().beginTransaction().add(R.id.container, new MainFragment()).commit();

        phonetxt = (EditText) findViewById(R.id.phonetxt);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        //find View
        Button nextbtn = (Button) findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(this);

        Hawk.init(this).build();
        acquireVerifyClient();
        Firebase.setAndroidContext(this);
        mData = FirebaseDatabase.getInstance().getReference();


        EditText phonetxt = (EditText)findViewById(R.id.phonetxt);
        phonetxt.setOnClickListener(this);

        Firebase countRef = new Firebase("https://unlimeted-house.firebaseio.com/users");
        countRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Hawk.put("count",dataSnapshot.getChildrenCount()+1);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        phonetxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    return true;
                }

                return false;
            }
        });




    }



    public void acquireVerifyClient() {
        Context context = getApplicationContext();
        NexmoClient nexmoClient = null;
        try {
             nexmoClient = new NexmoClient.NexmoClientBuilder()
                    .context(context)
                    .applicationId(NexmoAppId) //your App key
                    .sharedSecretKey(NexmoSharedSecretKey) //your App secret
                    .build();


        } catch (ClientBuilderException e) {
            Toast.makeText(getApplicationContext(),"Ups", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return;
        }

       verifyClient = new VerifyClient(nexmoClient);


        verifyClient.addVerifyListener(new VerifyClientListener() {
            @Override
            public void onVerifyInProgress(final VerifyClient verifyClient, UserObject user) {
                Log.d(TAG, "onVerifyInProgress for number: " + user.getPhoneNumber());
                Toast.makeText(getApplicationContext(),"we send a sms", Toast.LENGTH_SHORT).show();
                phonetxt.setText("Enter PIN code");
                Button nextbtn = (Button) findViewById(R.id.nextbtn);
                nextbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        verifyClient.checkPinCode(phonetxt.getText().toString());
                    }
                });


            }

            @Override
            public void onUserVerified(final VerifyClient verifyClient, UserObject user) {
                Log.d(TAG, "onUserVerified for number: " + user.getPhoneNumber());
                Toast.makeText(getApplicationContext(), "You are verificated", Toast.LENGTH_SHORT).show();



                Hawk.init(getApplicationContext()).build();
                Hawk.put("userphone", mPhone);

                 mData = FirebaseDatabase.getInstance().getReference();




                Query queryRef = mData.child("users").orderByChild("phone").equalTo(userPhone);

                queryRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            //dataSnapshot.getChildren().iterator().next()

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                users = postSnapshot.getValue(Users.class);

                                Hawk.put("userid", users.getId());
                                sPref = getPreferences(MODE_PRIVATE);
                                SharedPreferences.Editor ed = sPref.edit();
                                ed.putString("userid", users.getId());
                                Hawk.put("userid", users.getId());
                                Hawk.put("categ",users.getShopcateg());
                                Hawk.put("shopid",users.getShopid());
                                ed.commit();
                                Hawk.put("isitshop", users.getIsitshop());
                                isItShop = users.getIsitshop();
                                ed.putString("isitshop", "true");
                                ed.commit();

                                //Toast.makeText(getApplicationContext(), isItShop, Toast.LENGTH_SHORT).show();
                                if (isItShop.equals("true")) {
                                    Intent goToShopMenu = new Intent(getApplicationContext(), ShopMenu.class);
                                    startActivity(goToShopMenu);
                                }
                            }

                        }
                        else if (!dataSnapshot.exists()){
                            Long lcount =Hawk.get("count");
                            String count = lcount.toString();
                            mData.child("users/"+count).child("phone").setValue(userPhone);
                            mData.child("users/"+count).child("isitshop").setValue("false");
                            mData.child("users/"+count).child("id").setValue(count);
                            Hawk.put("isitshop","false");
                            isItShop = Hawk.get("isitshop");
                            //Toast.makeText(getApplicationContext(),isItShop, Toast.LENGTH_SHORT).show();


                            Hawk.put("userid",count);
                            sPref = getPreferences(MODE_PRIVATE);
                            SharedPreferences.Editor ed = sPref.edit();
                            ed.putString("userid", count);
                            ed.commit();
                            ed.putString("isitshop", "false");
                            ed.commit();


                        }
                        if(isItShop.equals("false")) {
                            Hawk.put("isitshop","false");
                            sPref = getPreferences(MODE_PRIVATE);
                            SharedPreferences.Editor ed = sPref.edit();
                            ed.putString("isitshop", "false");
                            ed.commit();
                            Intent goToLocation = new Intent(getApplicationContext(), RegLocation.class);
                            startActivity(goToLocation);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), isItShop, Toast.LENGTH_SHORT).show();

                    }
                });






            }

            @Override
            public void onError(final VerifyClient verifyClient, final com.nexmo.sdk.verify.event.VerifyError errorCode, UserObject user) {
                Log.d(TAG, "onError: " + errorCode + " for number: " + user.getPhoneNumber());
            }

            @Override
            public void onException(final IOException exception) {
            }
        });

        //verifyClient.getVerifiedUser(mPrefix, phonetxt);
        //verifyClient.checkPinCode(mPin);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextbtn: {

                mPhone = phonetxt.getText().toString();
                verifyClient.getVerifiedUser("UA",phonetxt.getText().toString());
                userPhone = mPhone;



            }
            case R.id.phonetxt:{phonetxt.setText("");}
        }


    }


}

