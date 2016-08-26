package com.e.bogachov.unlmitedhouse;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Locale mNewLocale;
    String lang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SlidingMenu
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sidemenu);
        menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);



        //Find View Slide Bar
        TextView profilebtn = (TextView) findViewById(R.id.profilebtn);
        TextView ordersbtn = (TextView) findViewById(R.id.ordersbtn);
        TextView addservicebtn = (TextView) findViewById(R.id.addservicebtn);
        TextView contactbtn = (TextView) findViewById(R.id.contactbtn);
        TextView logoutbtn = (TextView) findViewById(R.id.logoutbtn);
        profilebtn.setOnClickListener(this);
        ordersbtn.setOnClickListener(this);
        addservicebtn.setOnClickListener(this);
        contactbtn.setOnClickListener(this);
        logoutbtn.setOnClickListener(this);


        //find view
        ImageButton signbtn = (ImageButton) findViewById(R.id.signbtn);
        signbtn.setOnClickListener(this);
        ImageButton loginbtn = (ImageButton) findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(this);
        Button englang = (Button) findViewById(R.id.englang);
        englang.setOnClickListener(this);
        Button arablang = (Button) findViewById(R.id.arablang);
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

            case R.id.loginbtn:{
                Intent loginintent = new Intent(MainActivity.this, Login.class);
                startActivity(loginintent);
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
                Intent goToOrders = new Intent(MainActivity.this,Orders.class);
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
                // НУЖНО ЗАКОДИТЬ
                break;
            }
        }
    }



}
