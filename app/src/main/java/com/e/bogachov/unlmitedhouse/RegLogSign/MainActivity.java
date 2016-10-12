package com.e.bogachov.unlmitedhouse.RegLogSign;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.ShopOrders;
import com.e.bogachov.unlmitedhouse.Slide.AddService;
import com.e.bogachov.unlmitedhouse.Slide.ContactUs;
import com.e.bogachov.unlmitedhouse.Slide.Profile;
import com.e.bogachov.unlmitedhouse.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Locale mNewLocale;
    String lang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.hide();


        //SlidingMenu
        SlidingMenu menu = new SlidingMenu(this);
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

        //find view
        ImageView signbtn = (ImageView) findViewById(R.id.signbtn);
        signbtn.setOnClickListener(this);
        Button englang = (Button) findViewById(R.id.englang);
        englang.setOnClickListener(this);
        TextView arabtxt = (TextView)findViewById(R.id.arabtxt);
        arabtxt.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/droid-sans.ttf"));
        ImageView arablang = (ImageView) findViewById(R.id.arablang);
        arablang.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signbtn:{
                Intent signintent = new Intent(MainActivity.this, SiginUp.class);
                startActivity(signintent);
                break;

            }



            case R.id.englang:{


                break;
            }

            case R.id.arablang:{

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



}
