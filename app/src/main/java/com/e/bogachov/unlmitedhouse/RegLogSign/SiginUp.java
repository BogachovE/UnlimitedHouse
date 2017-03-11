package com.e.bogachov.unlmitedhouse.RegLogSign;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.MainMenu;
import com.e.bogachov.unlmitedhouse.R;

import com.e.bogachov.unlmitedhouse.ShopMenu;
import com.e.bogachov.unlmitedhouse.Models.Users;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.nexmo.sdk.NexmoClient;
import com.nexmo.sdk.core.client.ClientBuilderException;
import com.nexmo.sdk.verify.client.VerifyClient;
import com.nexmo.sdk.verify.event.UserObject;
import com.nexmo.sdk.verify.event.VerifyClientListener;
import com.onesignal.OneSignal;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.e.bogachov.unlmitedhouse.verifysample.Config.NexmoAppId;
import static com.e.bogachov.unlmitedhouse.verifysample.Config.NexmoSharedSecretKey;

public class SiginUp extends Activity implements View.OnClickListener {
    EditText phonetxt;
    String mPhone;
    DatabaseReference mData;
    String userPhone;
    String isItShop;
    SharedPreferences sPref;
    Users users;
    String notifID;
    public static final String TAG = SiginUp.class.getSimpleName();
    VerifyClient  verifyClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);


        phonetxt = (EditText) findViewById(R.id.phonetxt);
        Button nextbtn = (Button) findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(this);

        OneSignal.startInit(this).init();

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);
                notifID=registrationId;
                notifID=userId;


            }
        });


        ActionBar actionBar = getActionBar();
        if (actionBar!= null) {
            actionBar.hide();
        }
        Firebase.setAndroidContext(this);
        Hawk.init(this).build();
        acquireVerifyClient();

        mData = FirebaseDatabase.getInstance().getReference();




        EditText phonetxt = (EditText)findViewById(R.id.phonetxt);
        phonetxt.setOnClickListener(this);
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

        Firebase countRef = new Firebase("https://unhouse-143417.firebaseio.com/users");
        countRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Hawk.put("count",dataSnapshot.getChildrenCount()+1);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

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
                phonetxt.setText(R.string.sigin_up_pin);
                Button nextbtn = (Button) findViewById(R.id.nextbtn);
                nextbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        verifyClient.checkPinCode(phonetxt.getText().toString());
                    }
                });
            }

            @Override
            public void onUserVerified(final VerifyClient verifyClient, final UserObject user) {
                Log.d(TAG, "onUserVerified for number: " + user.getPhoneNumber());
                Toast.makeText(getApplicationContext(), "You are verificated", Toast.LENGTH_SHORT).show();

                Hawk.put("userphone", mPhone);

                 mData = FirebaseDatabase.getInstance().getReference();
                Query queryRef = mData.child("users").orderByChild("phone").equalTo(userPhone);

                queryRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            setExistUser(dataSnapshot);
                        }
                        else if (!dataSnapshot.exists()) {
                            addNewUser(dataSnapshot);
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


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextbtn: {
                mPhone = phonetxt.getText().toString();
                verifyClient.getVerifiedUser("SA",phonetxt.getText().toString());
                userPhone = mPhone;
            }
            case R.id.phonetxt:{phonetxt.setText("");}
        }


    }

    void addNewUser(DataSnapshot dataSnapshot) {
        Long lcount = Hawk.get("count");
        final String count = lcount.toString();
        Intent goToLocation;
        final HashMap<String, String> newUser = new HashMap<String, String>();

        newUser.put("phone", userPhone);
        newUser.put("isitshop", "false");
        newUser.put("userid", count);

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);
                newUser.put("userNotifIp", notifID);
            }
        });
        Hawk.put("userNotifIp",newUser.get("userNotifIp"));
        Hawk.put("isitshop", "false");
        Hawk.put("userid",count);

        goToLocation = new Intent(getApplicationContext(), RegLocation.class);
        goToLocation.putExtra("newUser",newUser);
        startActivity(goToLocation);
    }

    void setExistUser(DataSnapshot dataSnapshot) {
        String latitude;
        String longitude;
        Intent goToShopMenu;
        Intent goToMain;
        Location shopLoc;

        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            users = postSnapshot.getValue(Users.class);

            latitude = users.getLocationlatitude();
            longitude = users.getLocationlongitude();

            shopLoc = new Location("asd");
            shopLoc.setLatitude(Double.parseDouble(latitude));
            shopLoc.setLongitude(Double.parseDouble(longitude));

            isItShop = users.getIsitshop();

            if (isItShop.equals("true")) {
                Hawk.put("categ", users.getShopcategory());
                Hawk.put("shopid", users.getShopid());
            }

            Hawk.put("userNumber", mPhone);
            Hawk.put("isitshop", users.getIsitshop());
            Hawk.put("userLoc", shopLoc);
            Hawk.put("userid", users.getUserid());
            Hawk.put("userNotifIp", users.getUserNotifIp());
            Hawk.put("userid", users.getUserid());

            if (isItShop.equals("true")) {
                goToShopMenu = new Intent(getApplicationContext(), ShopMenu.class);
                startActivity(goToShopMenu);
            } else {
                goToMain = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(goToMain);
            }
        }
    }
}

