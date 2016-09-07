package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.Product;
import com.e.bogachov.unlmitedhouse.R;
import com.e.bogachov.unlmitedhouse.RightAdapter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class ShopsMenu extends Activity implements View.OnClickListener{

    private List<Shops> shops;
    private RecyclerView rv;
    private RecyclerView rv2;
    private List<Product> product;
    SlidingMenu menu2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();


        String categ = intent.getStringExtra("categ");
        switch (categ){
            case "beauty":{
                super.setTheme(R.style.Beauty);

                break;
            }

            case "product":{
                super.setTheme(R.style.Product);

                break;
            }

            case "candy":{
                super.setTheme(R.style.Candy);

                break;
            }

            case "house":{
                super.setTheme(R.style.House);

                break;
            }
            case "other":{
                super.setTheme(R.style.Other);

                break;
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shops_menu);





        SlidingMenu menu2 = new SlidingMenu(this);
        menu2.setMode(SlidingMenu.RIGHT);
        menu2.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu2.setFadeDegree(0.35f);
        menu2.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu2.setMenu(R.layout.sidemenu_right);
        menu2.setBehindWidthRes(R.dimen.slidingmenu_behind_width);


        rv=(RecyclerView)findViewById(R.id.rv);


        rv2 = (RecyclerView)findViewById(R.id.rv2);






        StaggeredGridLayoutManager gm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
       StaggeredGridLayoutManager gm2 = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);

         rv.setLayoutManager(gm);
         rv.setHasFixedSize(true);

        rv2.setLayoutManager(gm2);
        rv2.setHasFixedSize(true);



        initializeData();
        initializeAdapter();
        initializeData2();
        initializeAdapter2();


        getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);

        switch (categ){
            case "beauty":{
                getActionBar().setCustomView(R.layout.abs_layout);

                break;
            }

            case "product":{
                getActionBar().setCustomView(R.layout.abs_product);

                break;
            }

            case "candy":{
                getActionBar().setCustomView(R.layout.abs_candy);

                break;
            }

            case "house":{
                getActionBar().setCustomView(R.layout.abs_house);

                break;
            }
            case "other":{
                getActionBar().setCustomView(R.layout.abs_other);

                break;
            }
        }











    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item4:{





                menu2.toggle(true);





            }


            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeAdapter() {
        ShopsAdapter adapter = new ShopsAdapter(shops);
        rv.setAdapter(adapter);
        RightAdapter adapter2 = new RightAdapter(product);
        rv2.setAdapter(adapter2);


    }
    private void initializeAdapter2() {
        RightAdapter adapter2 = new RightAdapter(product);
        rv2.setAdapter(adapter2);

    }

    private void initializeData2() {
        product = new ArrayList<>();
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));


    }

    private void initializeData() {
        shops = new ArrayList<>();
        shops.add(new Shops("Lasunia",R.drawable.cace));
        shops.add(new Shops("Lasunia",R.drawable.lasunia));
        product = new ArrayList<>();
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));
        product.add(new Product("sdasdas","sdasdasd"));


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.curtBtn:{
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
                menu.toggle(true);

            }


        }
    }
}