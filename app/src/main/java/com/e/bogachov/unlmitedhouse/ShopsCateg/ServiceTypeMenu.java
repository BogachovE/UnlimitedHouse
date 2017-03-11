package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.Dialogs.Dialog2;
import com.e.bogachov.unlmitedhouse.MySchedule;
import com.e.bogachov.unlmitedhouse.R;
import com.e.bogachov.unlmitedhouse.SlideMenuHelper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServiceTypeMenu extends Activity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{
    String mShops;
    String isItShop;
    Query mQuery;
    String ownerid;
    String userId;
    private List<Order> orders;
    final int DIALOG_EXIT = 2;
    TextView textView7;
    private Animation mFadeInAnimation, mFadeOutAnimation;
    ImageView shadow;
    ColorDrawable cn;
    ColorDrawable cdn;







    public static  class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        TextView shopName;
        ImageView shopPhoto;
        RelativeLayout rl;
        Context context = itemView.getContext();
        String userId;
        String ownerid;



        public MessageViewHolder(View v) {
            super(v);


            shopName = (TextView) itemView.findViewById(R.id.shop_name);
            shopPhoto = (ImageView) itemView.findViewById(R.id.plus);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            rl.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv);



        }


        @Override
        public void onClick(View v) {
            DialogFragment dlg2;
            String isItShop = Hawk.get("isitshop");
            userId=Hawk.get("userid");
            ownerid=Hawk.get("ownerid");

            switch (v.getId()) {
                case R.id.rl: {
                    if (isItShop.equals("false")) {
                        Intent goToServiceType = new Intent(v.getContext(), ProductMenu.class);
                        goToServiceType.putExtra("clickid", String.valueOf(getAdapterPosition()+1));
                        Hawk.put("servicetype", shopName.getText().toString());
                        Hawk.put("serviceid", String.valueOf(getAdapterPosition()+1));
                        v.getContext().startActivity(goToServiceType);
                        Toast.makeText(v.getContext().getApplicationContext(), "clisk." + (getAdapterPosition()), Toast.LENGTH_SHORT).show();

                    }else if(isItShop.equals("true") & getAdapterPosition() != 0){
                        Intent goToServiceType = new Intent(v.getContext(), ProductMenu.class);
                        Hawk.put("serviceid", String.valueOf(getAdapterPosition()));
                        v.getContext().startActivity(goToServiceType);
                        Toast.makeText(v.getContext().getApplicationContext(), "clisk." + (getAdapterPosition()), Toast.LENGTH_SHORT).show();

                    }
                    else {  if(isItShop.equals("true") & getAdapterPosition()== 0& userId.equals(ownerid)){

                        FragmentManager fr = ((Activity) v.getContext()).getFragmentManager();
                        dlg2 = new Dialog2();
                        dlg2.show(fr, "dlg1");
                    }


                    }
                }


            }


        }



    }



    private static final String TAG = "MainActivity";
    private RecyclerView rv;
    private DatabaseReference mData;
    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> mAdapter;
    Firebase ownerRef;

    String mChild;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Hawk.init(this).build();
        String categ = Hawk.get("categ");

        switch (categ) {
            case "beauty": {
                super.setTheme(R.style.Beauty);

                break;
            }

            case "product": {
                super.setTheme(R.style.Candy);

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_type_menu);

        RecycleMenuHelper recycleMenuHelper;
        StaggeredGridLayoutManager gm;

        recycleMenuHelper = new RecycleMenuHelper();
        gm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        rv = (RecyclerView) findViewById(R.id.rv);

        mData = FirebaseDatabase.getInstance().getReference();


        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);



        mShops=Hawk.get("shopid");
        userId=Hawk.get("userid");


        isItShop=Hawk.get("isitshop");
        categ = Hawk.get("categ");
        mChild="shops/category/"+categ+"/"+ mShops +"/servicetype";
        Firebase.setAndroidContext(this);

        ownerRef = new Firebase("https://unhouse-143417.firebaseio.com/shops/category/"+categ+"/"+mShops+"/");
        ownerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ownerid = dataSnapshot.child("ownerid").getValue(String.class);
                Hawk.put("ownerid", ownerid);
                if (ownerid != null) {
                    if (isItShop.equals("false")) {
                        mQuery = mData.child(mChild).orderByKey().startAt("1");
                    } else if (userId.equals(ownerid)) {
                        mQuery = mData.child(mChild);
                    } else mQuery = mData.child(mChild).orderByKey().startAt("1");
                }else mQuery = mData.child(mChild).orderByKey().startAt("1");




                    mAdapter = new FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>(
                            FriendlyMessage.class, R.layout.item, MessageViewHolder.class, mQuery) {


                        @Override
                        protected void populateViewHolder(MessageViewHolder viewHolder, FriendlyMessage model,  int position) {
                            viewHolder.shopName.setText(model.getName());
                            Picasso.with(getApplication()).load(model.getphotourl()).into(viewHolder.shopPhoto);
                            cn = new ColorDrawable(Color.parseColor("#FAFAFA"));
                            cdn = new ColorDrawable(Color.parseColor("#FFFFFF"));


                            if (((position + 3) % 4 != 0) && ((position + 2) % 4 != 0)) {
                                viewHolder.cv.setBackground(cdn);
                            } else {
                                viewHolder.cv.setBackground(cn);

                            }

                            Intent shopint = new Intent(getApplicationContext(), ProductMenu.class);
                            shopint.putExtra("shopid", mShops);
                            final Integer pos = position;

                            if(isItShop.equals("true") & position != 0& userId.equals(ownerid)) {

                                viewHolder.rl.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View v) {
                                        Hawk.put("longservice", pos.toString());
                                        showDialog(DIALOG_EXIT);
                                        return false;
                                    }
                                });
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


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        ownerid = Hawk.get("ownerid");
        userId = Hawk.get("userid");




        RecycleMenuHelper.initializeData();
        recycleMenuHelper.setActionBar(this);

    }





    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onClick(View view) {
        SlideMenuHelper slideMenuHelper = new SlideMenuHelper();

        switch (view.getId()) {
            case R.id.basket_btn:{
                slideMenuHelper.setRightSlideMenu(this, orders);
                view.setClickable(false);
                break;
            }

            case R.id.curtBtn:{
                slideMenuHelper.setLeftSlideMenu(this);
                break;

            }

            case R.id.okbtn:{
                Toast.makeText(getApplicationContext(),"Clsik ok",Toast.LENGTH_LONG).show();
                break;
            }

            case R.id.textView7:{
                Intent goToSchedle = new Intent(view.getContext(), MySchedule.class);
                view.getContext().startActivity(goToSchedle);
                Hawk.put("orders",orders);
                mAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (id == DIALOG_EXIT) {

        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==DIALOG_EXIT){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_select_service, (ViewGroup) findViewById(R.id.root));
            String categ = Hawk.get("categ");
            String mShops=Hawk.get("shopid");
            String mService= Hawk.get("longservice");
            final Firebase selectRef = new Firebase("https://unlimeted-house.firebaseio.com/shops/category/"+categ+"/"+mShops+"/servicetype/"+mService+"/");


            final EditText rename_edit = (EditText) view.findViewById(R.id.rename_edit);
            final EditText change_pic_edit = (EditText) view.findViewById(R.id.change_pic_edit);


            Button rename_btn = (Button) view.findViewById(R.id.rename_btn);
            rename_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectRef.child("name").setValue(rename_edit.getText().toString());
                    finish();
                }
            });


            Button dell_btn = (Button) view.findViewById(R.id.dell_btn);
            dell_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectRef.removeValue();
                    finish();
                }
            });


            Button change_pic_btn = (Button) view.findViewById(R.id.change_pic_btn);
            change_pic_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(change_pic_edit.getText()!=null){
                    selectRef.child("photourl").setValue(change_pic_edit.getText().toString());
                    }
                    else Toast.makeText(getApplicationContext(),R.string.nullpic,Toast.LENGTH_SHORT).show();
                    finish();


                }
            });



            alert.setView(view);
            alert.show();

        }
        return super.onCreateDialog(id);

    }



    @Override
    public void onBackPressed() {
        if(orders.size()!=0){
            Toast.makeText(this,R.string.notnullshop,Toast.LENGTH_SHORT).show();
        }
        else finish();
    }

    @Override
    public void onStart(){
        RecycleMenuHelper recycleMenuHelper = new RecycleMenuHelper();
        recycleMenuHelper.initializeData();
        super.onStart();

        orders=Hawk.get("orders");
        String categ = Hawk.get("categ");
        switch (categ) {
            case "beauty": {
                getActionBar().setCustomView(R.layout.abs_layout);
                Button curtBtn = (Button)findViewById(R.id.curtBtn);
                curtBtn.setOnClickListener(this);
                Button basket_btn = (Button)findViewById(R.id.basket_btn);
                basket_btn.setOnClickListener(this);
                if (orders.size()!=0){
                    basket_btn.setBackgroundResource(R.drawable.basket_full);
                }


                break;
            }

            case "product": {
                getActionBar().setCustomView(R.layout.abs_product);
                Button curtBtn = (Button)findViewById(R.id.curtBtn);
                curtBtn.setOnClickListener(this);
                Button basket_btn = (Button)findViewById(R.id.basket_btn);
                basket_btn.setOnClickListener(this);
                if (orders.size()!=0){
                    basket_btn.setBackgroundResource(R.drawable.basket_full_white);
                }


                break;
            }

            case "candy": {
                getActionBar().setCustomView(R.layout.abs_candy);
                Button curtBtn = (Button)findViewById(R.id.curtBtn);
                curtBtn.setOnClickListener(this);
                Button basket_btn = (Button)findViewById(R.id.basket_btn);
                basket_btn.setOnClickListener(this);
                if (orders.size()!=0){
                    basket_btn.setBackgroundResource(R.drawable.basket_full_white);
                }


                break;
            }

            case "house": {
                getActionBar().setCustomView(R.layout.abs_house);
                Button curtBtn = (Button)findViewById(R.id.curtBtn);
                curtBtn.setOnClickListener(this);
                Button basket_btn = (Button)findViewById(R.id.basket_btn);
                basket_btn.setOnClickListener(this);
                if (orders.size()!=0){
                    basket_btn.setBackgroundResource(R.drawable.basket_full);
                }


                break;
            }
            case "other": {
                getActionBar().setCustomView(R.layout.abs_other);
                Button curtBtn = (Button)findViewById(R.id.curtBtn);
                curtBtn.setOnClickListener(this);
                Button basket_btn = (Button)findViewById(R.id.basket_btn);
                basket_btn.setOnClickListener(this);
                if (orders.size()!=0){
                    basket_btn.setBackgroundResource(R.drawable.basket_full);
                }


                break;
            }
        }


    }
    @Override
    public void onResume(){
        super.onResume();
        RecycleMenuHelper recycleMenuHelper = new RecycleMenuHelper();
        recycleMenuHelper.initializeData();

        orders=Hawk.get("orders");
        String categ = Hawk.get("categ");
        switch (categ) {
            case "beauty": {
                getActionBar().setCustomView(R.layout.abs_layout);
                Button curtBtn = (Button)findViewById(R.id.curtBtn);
                curtBtn.setOnClickListener(this);
                Button basket_btn = (Button)findViewById(R.id.basket_btn);
                basket_btn.setOnClickListener(this);
                if (orders.size()!=0){
                    basket_btn.setBackgroundResource(R.drawable.basket_full);
                }


                break;
            }

            case "product": {
                getActionBar().setCustomView(R.layout.abs_product);
                Button curtBtn = (Button)findViewById(R.id.curtBtn);
                curtBtn.setOnClickListener(this);
                Button basket_btn = (Button)findViewById(R.id.basket_btn);
                basket_btn.setOnClickListener(this);
                if (orders.size()!=0){
                    basket_btn.setBackgroundResource(R.drawable.basket_full_white);
                }


                break;
            }

            case "candy": {
                getActionBar().setCustomView(R.layout.abs_candy);
                Button curtBtn = (Button)findViewById(R.id.curtBtn);
                curtBtn.setOnClickListener(this);
                Button basket_btn = (Button)findViewById(R.id.basket_btn);
                basket_btn.setOnClickListener(this);
                if (orders.size()!=0){
                    basket_btn.setBackgroundResource(R.drawable.basket_full_white);
                }


                break;
            }

            case "house": {
                getActionBar().setCustomView(R.layout.abs_house);
                Button curtBtn = (Button)findViewById(R.id.curtBtn);
                curtBtn.setOnClickListener(this);
                Button basket_btn = (Button)findViewById(R.id.basket_btn);
                basket_btn.setOnClickListener(this);
                if (orders.size()!=0){
                    basket_btn.setBackgroundResource(R.drawable.basket_full);
                }


                break;
            }
            case "other": {
                getActionBar().setCustomView(R.layout.abs_other);
                Button curtBtn = (Button)findViewById(R.id.curtBtn);
                curtBtn.setOnClickListener(this);
                Button basket_btn = (Button)findViewById(R.id.basket_btn);
                basket_btn.setOnClickListener(this);
                if (orders.size()!=0){
                    basket_btn.setBackgroundResource(R.drawable.basket_full);
                }


                break;
            }
        }


    }



}