package com.e.bogachov.unlmitedhouse.RegLogSign;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.MainMenu;
import com.e.bogachov.unlmitedhouse.ShopMenu;
import com.e.bogachov.unlmitedhouse.ShopOrders;
import com.e.bogachov.unlmitedhouse.Slide.AddService;
import com.e.bogachov.unlmitedhouse.Slide.ContactUs;
import com.e.bogachov.unlmitedhouse.Slide.Profile;
import com.e.bogachov.unlmitedhouse.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.FirebaseApp;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String userId;
    String isitshop;
    String shopId;
    String shopcateg;
    String mPhone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Firebase.setAndroidContext(this);
        Hawk.init(this).build();

        if (Hawk.contains("userid")) {
          loadUser();
        }

        //find view
        ImageView signbtn = (ImageView) findViewById(R.id.signbtn);
        signbtn.setOnClickListener(this);
        Button englang = (Button) findViewById(R.id.englang);
        englang.setOnClickListener(this);
        TextView arabtxt = (TextView)findViewById(R.id.arabtxt);
        arabtxt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/droid-sans.ttf"));
        ImageView arablang = (ImageView) findViewById(R.id.arablang);
        arablang.setOnClickListener(this);

        setSlideMenu();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signbtn: {


                if (isitshop != null) {
                    if (!userId.equals("") & (isitshop.equals("false"))) {
                        Hawk.put("userid", userId);
                        Intent goToMain = new Intent(MainActivity.this, MainMenu.class);
                        startActivity(goToMain);

                    } else if (!userId.equals("") & (isitshop.equals("true"))) {
                        Hawk.put("userid", userId);
                        Intent goToShop = new Intent(MainActivity.this, ShopMenu.class);
                        startActivity(goToShop);
                    } else {
                        Intent signintent = new Intent(MainActivity.this, SiginUp.class);
                        startActivity(signintent);
                    }
                    break;
                }
                else{
                    Intent signintent = new Intent(MainActivity.this, SiginUp.class);
                    startActivity(signintent);
                }
            }



            case R.id.englang:{
                //TODO will do
                break;
            }

            case R.id.arablang:{
                //TODO will do
                break;
            }
    //Slide Menu Buttons
            case R.id.profilebtn:{
                Intent goToProfile = new Intent(MainActivity.this,Profile.class);
                startActivity(goToProfile);
                break;
            }
            case R.id.ordersbtn:{
                Intent goToOrders = new Intent(MainActivity.this,ShopOrders.class);
                startActivity(goToOrders);
                break;
            }
            case R.id.addservicebtn:{
                Intent goToAddservice = new Intent(MainActivity.this,AddService.class);
                startActivity(goToAddservice);
                break;
            }
            case R.id.contactbtn:{
                Intent goToContactUs = new Intent(MainActivity.this,ContactUs.class);
                startActivity(goToContactUs);
                break;
            }
            case R.id.logoutbtn:{
                Intent goToStart = new Intent(getApplicationContext(),MainActivity.class);
                Hawk.deleteAll();
                startActivity(goToStart);
                break;
            }
        }
    }

    public void setSlideMenu(){
        SlidingMenu menu;

        menu= new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sidemenu);
        menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);

        //Find View Slide Bar
        TextView profilebtn = ((TextView) findViewById(R.id.profilebtn));
        profilebtn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BebasNeueBook.otf"));
        TextView ordersbtn = (TextView) findViewById(R.id.ordersbtn);
        ordersbtn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BebasNeueBook.otf"));
        TextView addservicebtn = (TextView) findViewById(R.id.addservicebtn);
        addservicebtn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BebasNeueBook.otf"));
        TextView contactbtn = (TextView) findViewById(R.id.contactbtn);
        contactbtn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BebasNeueBook.otf"));
        TextView logoutbtn = (TextView) findViewById(R.id.logoutbtn);
        logoutbtn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BebasNeueBook.otf"));
        profilebtn.setOnClickListener(this);
        ordersbtn.setOnClickListener(this);
        addservicebtn.setOnClickListener(this);
        contactbtn.setOnClickListener(this);
        logoutbtn.setOnClickListener(this);
    }

    public void loadUser() {
        userId = Hawk.get("userid");

        Firebase shopRef = new Firebase("https://unhouse-143417.firebaseio.com/users/"+userId+"/");
        shopRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    isitshop = dataSnapshot.child("isitshop").getValue(String.class);
                    Hawk.put("isitshop", isitshop);
                    if (isitshop.equals("true")) {
                        shopId = dataSnapshot.child("shopid").getValue(String.class);
                        Hawk.put("shopid", shopId);
                        shopcateg = dataSnapshot.child("shopcategory").getValue(String.class);
                        Hawk.put("categ", shopcateg);
                    }
                    mPhone = dataSnapshot.child("phone").getValue(String.class);
                    Hawk.put("phone", mPhone);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
