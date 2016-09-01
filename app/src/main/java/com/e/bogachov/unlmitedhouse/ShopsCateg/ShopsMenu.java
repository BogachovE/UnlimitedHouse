package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shops_menu);



        rv=(RecyclerView)findViewById(R.id.rv);





        StaggeredGridLayoutManager gm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);



        initializeData();
        initializeAdapter();

        getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        getActionBar().setCustomView(R.layout.abs_layout);

        Button curtBtn = (Button)findViewById(R.id.curtBtn);
        curtBtn.setOnClickListener(this);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void initializeAdapter() {
        ShopsAdapter adapter = new ShopsAdapter(shops);
        rv.setAdapter(adapter);

    }

    private void initializeData() {
        shops = new ArrayList<>();
        shops.add(new Shops("Lasunia",R.drawable.cace));
        shops.add(new Shops("Lasunia",R.drawable.lasunia));


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.curtBtn:{
                SlidingMenu menu = new SlidingMenu(this);
                menu.setMode(SlidingMenu.RIGHT);
                menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                menu.setFadeDegree(0.35f);
                menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
                menu.setMenu(R.layout.sidemenu_right);
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