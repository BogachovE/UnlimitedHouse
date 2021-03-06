package com.e.bogachov.unlmitedhouse;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.RegLogSign.MainActivity;
import com.e.bogachov.unlmitedhouse.ShopsCateg.ShopsMenu;
import com.e.bogachov.unlmitedhouse.Slide.AddService;
import com.e.bogachov.unlmitedhouse.Slide.ContactUs;
import com.e.bogachov.unlmitedhouse.Slide.Profile;
import com.firebase.client.Firebase;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.orhanobut.hawk.Hawk;
//TODO добавить добавление в продукты

public class MainMenu extends Activity implements View.OnClickListener {
    String isItShop;
    String userId;
    ImageView shadow;
    SlidingMenu menu;
    private Animation mFadeInAnimation, mFadeOutAnimation;

    SharedPreferences sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        Firebase.setAndroidContext(this);

        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sidemenu);
        menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                shadow = (ImageView) findViewById(R.id.shadow) ;
                shadow.setVisibility(View.VISIBLE);
                shadow.startAnimation(mFadeInAnimation);

            }
        });
        menu.setOnCloseListener(new SlidingMenu.OnCloseListener() {
            @Override
            public void onClose() {
                shadow.startAnimation(mFadeOutAnimation);
                shadow.setVisibility(View.INVISIBLE);
            }
        });



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



        //Find View
        ImageView hbtn = (ImageView)findViewById(R.id.hbtn);
        ImageView beauty = (ImageView) findViewById(R.id.beauty);
        ImageView product = (ImageView) findViewById(R.id.product);
        ImageView candy = (ImageView) findViewById(R.id.candy);
        ImageView other = (ImageView) findViewById(R.id.other);
        mFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mFadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fadeout);






        hbtn.setOnClickListener(this);
        beauty.setOnClickListener(this);
        product.setOnClickListener(this);
        candy.setOnClickListener(this);
        other.setOnClickListener(this);


        getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(R.layout.abs_main);
        TextView title=(TextView)findViewById(getResources().getIdentifier("hello", "id", getPackageName()));
        title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto.ttf"));
        title.setText(R.string.main_menu_title);









        Button curtBtn = (Button)findViewById(R.id.curtBtn);
        curtBtn.setOnClickListener(this);



        Hawk.init(this).build();

        Hawk.delete("numZakaz");




        userId=Hawk.get("userid");
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("userid", userId);
        ed.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.curtBtn:{

            }


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }







    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.hbtn:{
                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                goToShopsMenu.putExtra("categ","house");
                Hawk.put("categ","house");

                startActivity(goToShopsMenu);
                break;
            }

            case R.id.curtBtn:{
                menu.toggle(true);

                break;

            }


            case R.id.beauty:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                goToShopsMenu.putExtra("categ","beauty");
                Hawk.put("categ","beauty");
                goToShopsMenu.putExtra("isItShop","false");
                startActivity(goToShopsMenu);
                break;

            }

            case R.id.product:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                goToShopsMenu.putExtra("categ","product");
                Hawk.put("categ","product");
                goToShopsMenu.putExtra("isItShop","false");
                startActivity(goToShopsMenu);
                break;


            }


            case R.id.candy:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                goToShopsMenu.putExtra("categ","candy");
                Hawk.put("categ","candy");
                goToShopsMenu.putExtra("isItShop","false");
                startActivity(goToShopsMenu);
                break;

            }



            case R.id.other:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                goToShopsMenu.putExtra("categ","other");
                Hawk.put("categ","other");
                goToShopsMenu.putExtra("isItShop","false");
                startActivity(goToShopsMenu);
                break;

            }
            //Slide Menu Buttons
            case R.id.profilebtn:{
                Intent goToProfile = new Intent(this,Profile.class);
                startActivity(goToProfile);
                break;
            }
            case R.id.ordersbtn:{
                Intent goToOrders = new Intent(this,ShopOrders.class);
                startActivity(goToOrders);
                break;
            }
            case R.id.addservicebtn:{
                Intent goToAddservice = new Intent(this,AddService.class);
                startActivity(goToAddservice);
                break;
            }
            case R.id.contactbtn:{
                Intent goToContactUs = new Intent(this,ContactUs.class);
                startActivity(goToContactUs);
                break;
            }
            case R.id.logoutbtn:{
                Intent goToStart = new Intent(getApplicationContext(),MainActivity.class);
                Hawk.deleteAll();
                SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ed = sPref.edit();
                ed.clear();
                ed.apply();
                startActivity(goToStart);


                break;
            }


        }
    }
}
