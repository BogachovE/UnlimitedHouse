package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.app.ActionBar;
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
import android.os.Bundle;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;
import com.e.bogachov.unlmitedhouse.MySchedule;
import com.e.bogachov.unlmitedhouse.R;
import com.e.bogachov.unlmitedhouse.SlideMenuHelper;
import com.firebase.client.Firebase;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopsMenu extends Activity implements  GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    ColorDrawable cn;
    ColorDrawable cdn;
    String isItShop;
    private static final String TAG = "MainActivity";
    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> mAdapter;
    List<Order> orders;
    Query mQuery;
    final int DIALOG_EXIT = 2;
    String categ;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView rv;
        DatabaseReference mData;

        cn = new ColorDrawable(Color.parseColor("#FAFAFA"));
        cdn = new ColorDrawable(Color.parseColor("#FFFFFF"));

        //Hawk init and get
        Hawk.init(this).build();
        categ = Hawk.get("categ");

        //Set Theme
        RecycleMenuHelper recycleMenuHelper = new RecycleMenuHelper();

       /* map = {
       *         'beauty' : 'Beauty'
       * }
       *
       *
       * super.setTheme(R.style.);
       */

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
        setContentView(R.layout.shops_menu);




        //Shared pref
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed = sPref.edit();
        isItShop = sPref.getString("isitshop", "");


        //FireBase referenceses and query
        mData = FirebaseDatabase.getInstance().getReference();
        if(isItShop.equals("false")) {
            mQuery = mData.child("shops/category/" + categ).orderByKey().startAt("1");
        }
        else mQuery = mData.child("shops/category/" + categ);





        StaggeredGridLayoutManager gm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(gm);



        mAdapter = new FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder >(
                FriendlyMessage.class, R.layout.item, MessageViewHolder.class, mQuery ){


            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, FriendlyMessage model, int position) {
                    viewHolder.shopName.setText(model.getName());
                    Picasso.with(getApplication()).load(model.getphotourl()).into(viewHolder.shopPhoto);
                if (((position + 3) % 4 != 0) && ((position + 2) % 4 != 0)) {
                    viewHolder.cv.setBackground(cdn);
                    } else {
                        viewHolder.cv.setBackground(cn);
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





        RecycleMenuHelper.initializeData();
        recycleMenuHelper.setActionBar(this);

        Button curtBtn = (Button)findViewById(R.id.curtBtn);
        curtBtn.setOnClickListener(this);
        Button basket_btn = (Button)findViewById(R.id.basket_btn);
        basket_btn.setOnClickListener(this);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.curtBtn:{

            }
            case R.id.basket_btn:{

            }



            return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView shopName;
        CardView cv;
        ImageView shopPhoto;
        RelativeLayout rl;

        public MessageViewHolder(View v) {
            super(v);

            cv = (CardView) itemView.findViewById(R.id.cv);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /* */
                    Toast.makeText(v.getContext().getApplicationContext(), getAdapterPosition(), Toast.LENGTH_LONG).show();


                }
            });
            shopName = (TextView) itemView.findViewById(R.id.shop_name);
            shopPhoto = (ImageView) itemView.findViewById(R.id.plus);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            rl.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            DialogFragment dlg1;
            String isItShop=Hawk.get("isitshop");

            switch (v.getId()){
                case R.id.rl: {


                    Intent goToServiceType = new Intent(v.getContext(), ServiceTypeMenu.class);
                    goToServiceType.putExtra("clickid", String.valueOf(getAdapterPosition()));
                    Hawk.put("shopid", String.valueOf(getAdapterPosition()));
                    Hawk.put("fromShop", shopName.getText().toString());
                    v.getContext().startActivity(goToServiceType);
                    Toast.makeText(v.getContext().getApplicationContext(), "clisk." + (getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    break;
                }

            }


        }


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

    /*@Override
     *protected void onPrepareDialog(int id, Dialog dialog) {
     *   super.onPrepareDialog(id, dialog);
     *   if (id == DIALOG_EXIT) {
     *
     *    }
     *}
     */

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==DIALOG_EXIT){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_select_service, (ViewGroup) findViewById(R.id.root));
            String categ = Hawk.get("categ");
            String mShops=Hawk.get("longservice");

            final Firebase selectRef = new Firebase("https://unhouse-143417.firebaseio.com/shops/category/"+categ+"/"+mShops+"/");


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
                    selectRef.child("photourl").setValue(change_pic_edit.getText().toString());
                    finish();


                }
            });

            rename_btn.setText(R.string.shops_menu_ren);
            dell_btn.setText(R.string.shops_menu_dell);
            change_pic_btn.setText(R.string.shops_menu_pic);




            alert.setView(view);
            alert.show();

        }
        return super.onCreateDialog(id);

    }






    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();

    }


}