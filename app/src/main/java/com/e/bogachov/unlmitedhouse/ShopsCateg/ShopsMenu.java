package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShopsMenu extends Activity implements  GoogleApiClient.OnConnectionFailedListener{

    ColorDrawable cn = new ColorDrawable(Color.parseColor("#FAFAFA"));
    ColorDrawable cdn = new ColorDrawable(Color.parseColor("#FFFFFF"));
    Long l;

    public static class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        TextView shopName;
        ImageView shopPhoto;
        RelativeLayout rl;
        Context context;
        Long l;


        public MessageViewHolder(View v) {
            super(v);
            cv = (CardView)itemView.findViewById(R.id.cv);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToServiceType = new Intent(v.getContext(),ServiceTypeMenu.class);
                    goToServiceType.putExtra("clickid",String.valueOf(getAdapterPosition()));
                    v.getContext().startActivity(goToServiceType);
                    Toast.makeText(v.getContext().getApplicationContext(), "clisk."+(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    l =getItemId();



                }
            });
            shopName = (TextView)itemView.findViewById(R.id.shop_name);
            shopPhoto = (ImageView)itemView.findViewById(R.id.shop_photo);
            rl =(RelativeLayout)itemView.findViewById(R.id.rl);


        }

        @Override
        public void onClick(View v) {

        }
    }


    private RecyclerView rv;
    private List<Product> product;
    SlidingMenu menu2;
    String isItShop;
    final int DIALOG = 1;
    private static final String TAG = "MainActivity";
    private DatabaseReference mData;
    LinearLayoutManager mLiner;
    SharedPreferences mSharedPreferences;


    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();


        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mData = FirebaseDatabase.getInstance().getReference();



        isItShop = intent.getStringExtra("isItShop");
        String categ = intent.getStringExtra("categ");
        if (isItShop.equals("false")) {
            switch (categ) {
                case "beauty": {
                    super.setTheme(R.style.Beauty);

                    break;
                }

                case "product": {
                    super.setTheme(R.style.Product);

                    break;
                }

                case "candy": {
                    super.setTheme(R.style.Candy);

                    break;
                }

                case "house": {
                    super.setTheme(R.style.House);

                    break;
                }
                case "other": {
                    super.setTheme(R.style.Other);

                    break;
                }
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











        StaggeredGridLayoutManager gm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

         rv = (RecyclerView) findViewById(R.id.rv);

        rv.setLayoutManager(gm);



        mAdapter = new FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder >(
              FriendlyMessage.class, R.layout.item, MessageViewHolder.class, mData.child("shops") ){


            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, FriendlyMessage model, int position) {
                viewHolder.shopName.setText(model.getName());
                Picasso.with(getApplication()).load(model.getphotourl()).into(viewHolder.shopPhoto);
                if (position%2!=0){
                    viewHolder.cv.setBackground(cn);
                }
                else {viewHolder.cv.setBackground(cdn);

                }

            }
        };

        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mAdapter.getItemCount();

            }
        });


            rv.setAdapter(mAdapter);



     //   initializeData();
     //   initializeAdapter();


 //       String click;
 //       click=intent.getStringExtra("click");





        getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);


        categ = intent.getStringExtra("categ");

      if (isItShop.equals("false") ) {

            switch (categ) {
                case "beauty": {
                    getActionBar().setCustomView(R.layout.abs_layout);

                    break;
                }

                case "product": {
                    getActionBar().setCustomView(R.layout.abs_product);

                    break;
                }

                case "candy": {
                    getActionBar().setCustomView(R.layout.abs_candy);

                    break;
                }

                case "house": {
                    getActionBar().setCustomView(R.layout.abs_house);

                    break;
                }
                case "other": {
                    getActionBar().setCustomView(R.layout.abs_other);

                    break;
                }
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
       /* ShopsAdapter adapter = new ShopsAdapter(shops);
        rv.setAdapter(adapter);*/



    }




/*
        if (isItShop.equals("true")){ shops.add(new Shops("Service type",R.drawable.plus));}
*/






    /*@Override
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
    }*/

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
//        adb.setTitle("Please write your preference");
        // создаем view из dialog.xml
       LinearLayout view = (LinearLayout) getLayoutInflater()
                .inflate(R.layout.dialog, null);
        // устанавливаем ее, как содержимое тела диалога
        adb.setView(view);
        // находим TexView для отображения кол-ва
       TextView tvCount = (TextView) view.findViewById(R.id.tvCount);
        return adb.create();
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (id == DIALOG) {

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();

    }


}