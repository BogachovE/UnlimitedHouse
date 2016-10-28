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
import android.os.Bundle;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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

    ColorDrawable cn = new ColorDrawable(Color.parseColor("#FAFAFA"));
    ColorDrawable cdn = new ColorDrawable(Color.parseColor("#FFFFFF"));
    private RecyclerView rv;
    SlidingMenu menu2;
    String isItShop;
    private static final String TAG = "MainActivity";
    private DatabaseReference mData;
    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> mAdapter;
    private List<Order> orders;
    TextView textView7;
    String customNum;
    Query mQuery;
    ImageView shadow;
    private Animation mFadeInAnimation, mFadeOutAnimation;
    SharedPreferences sPref;
    final int DIALOG_EXIT = 2;





    public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView shopName;
        CardView cv;
        ImageView shopPhoto;
        RelativeLayout rl;
        Context context;
        Long l;



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
                    goToServiceType.putExtra("clickid", String.valueOf(getAdapterPosition() + 1));
                    Hawk.put("fromShop", shopName.getText().toString());
                    v.getContext().startActivity(goToServiceType);
                    Toast.makeText(v.getContext().getApplicationContext(), "clisk." + (getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    break;
                }

            }


        }

        @Override
        public boolean onLongClick(View v) {
            String isItShop=Hawk.get("isitshop");



            switch(v.getId()){
                case R.id.rl:{
                    if(isItShop.equals("true") & getAdapterPosition() != 0){
                        showDialog(DIALOG_EXIT);


                    }

                    break;
                }



            }

            return false;
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        mData = FirebaseDatabase.getInstance().getReference();



        Hawk.init(this).build();
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed = sPref.edit();
        isItShop = sPref.getString("isitshop", "");


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
        setContentView(R.layout.shops_menu);
















        StaggeredGridLayoutManager gm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(gm);

         if(isItShop.equals("false")) {
             mQuery = mData.child("shops/category/" + categ).orderByKey().startAt("1");
         }
        else mQuery = mData.child("shops/category/" + categ);

        mAdapter = new FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder >(
                FriendlyMessage.class, R.layout.item, MessageViewHolder.class, mQuery ){


            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, FriendlyMessage model, int position) {
                    viewHolder.shopName.setText(model.getName());
                    Picasso.with(getApplication()).load(model.getphotourl()).into(viewHolder.shopPhoto);
                    if (position % 2 != 0) {
                        viewHolder.cv.setBackground(cn);
                    } else {
                        viewHolder.cv.setBackground(cdn);

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


        if (mAdapter.getItemCount()%2!=0){
      //      rl.setBackground(cn);

        }
        //else {rl.setBackground(cdn);

      //  }


        getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        ActionBar actionBar = getActionBar();


        initializeData();




        categ = Hawk.get("categ");



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




    private List<Order> initializeData(){
        orders = new ArrayList<>();
        Integer numZakaz= null;
        numZakaz = Hawk.get("numZakaz");
        customNum= Hawk.get("userphone").toString();
        Map<Integer,Map<String,String>> ordervc = new HashMap<Integer,Map<String,String>>();
        if (Hawk.get("order")!= null & numZakaz!=null){
            ordervc = Hawk.get("order");
            Map<String,String> order = new HashMap<String, String>();
            for (int i = 0; i<= numZakaz ;i++ ){
              order=ordervc.get(i);
              orders.add(new Order(order.get("count"),order.get("data"),order.get("fromShop"),order.get("currency"),order.get("p"),order.get("sum"),order.get("name"),order.get("descript"),"process",customNum,"sda","da","sda","da","sda","das"));
            }
        }
        ordervc.clear();

        Hawk.put("orders",orders);
        return orders;


    }





    @Override
    public void onClick(View view) {
        mFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mFadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        switch (view.getId()) {
            case R.id.basket_btn:{
                SlidingMenu menu2 = new SlidingMenu(this);
                menu2.setMode(SlidingMenu.RIGHT);
                menu2.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                menu2.setFadeDegree(0.35f);
                menu2.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
                menu2.setMenu(R.layout.sidemenu_right);
                menu2.setBehindWidthRes(R.dimen.slidingmenu_behind_width);



                        mFadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                        mFadeOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
                        shadow = (ImageView) findViewById(R.id.shadow) ;
                        shadow.setVisibility(View.VISIBLE);
                        shadow.startAnimation(mFadeInAnimation);
                        Toast.makeText(getApplicationContext(),"sd",Toast.LENGTH_SHORT);

                menu2.setOnCloseListener(new SlidingMenu.OnCloseListener() {
                    @Override
                    public void onClose() {
                        mFadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                        mFadeOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
                        shadow = (ImageView) findViewById(R.id.shadow) ;
                        shadow.startAnimation(mFadeOutAnimation);
                        shadow.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"11",Toast.LENGTH_SHORT);
                    }
                });


                textView7 = (TextView)findViewById(R.id.textView7);
                textView7.setOnClickListener(this);

                RecyclerView rv2 = (RecyclerView)findViewById(R.id.rv2);


                initializeData();
                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                rv2.setLayoutManager(llm);
                RightOrderAdapter adapter = new RightOrderAdapter(orders);
                rv2.setAdapter(adapter);





                //Find View Slide Bar

                menu2.toggle(true);
                break;

            }

            case R.id.curtBtn:{

                SlidingMenu menu = new SlidingMenu(this);
                menu.setMode(SlidingMenu.LEFT);
                menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                menu.setFadeDegree(0.35f);
                menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
                menu.setMenu(R.layout.sidemenu);
                menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);
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
                        shadow = (ImageView) findViewById(R.id.shadow) ;
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

                menu.toggle(true);
                break;

            }





            case R.id.plus: {
             //   dlg1.show(getFragmentManager(),"dlg1");
                break;
            }

            case R.id.okbtn:{
                Toast.makeText(getApplicationContext(),"Clsik ok",Toast.LENGTH_LONG).show();
                break;
            }

            case R.id.textView7:{
                Intent goToSchedle = new Intent(view.getContext(), MySchedule.class);
                view.getContext().startActivity(goToSchedle);
                Hawk.delete("orders");
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
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            // заголовок
            //   adb.setTitle(R.string.exit);
            // сообщение
            //adb.setMessage(R.string.save_data);
            // иконка
            // adb.setIcon(android.R.drawable.ic_dialog_info);
            // кнопка положительного ответа
            adb.setPositiveButton("Rename shop", myClickListener);
            // кнопка отрицательного ответа
            adb.setNegativeButton("Delete shop", myClickListener);
            // кнопка нейтрального ответа
            adb.setNeutralButton("Change picture", myClickListener);
            // создаем диалог
            return adb.create();
        }
        return super.onCreateDialog(id);
    }
    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                // положительная кнопка
                case Dialog.BUTTON_POSITIVE:
                    Firebase acceptRef = new Firebase("https://unlimeted-house.firebaseio.com/orders/"+mID);
                    acceptRef.child("status").setValue("confirmation");
                    acceptRef.child("confirmationdata").setValue(new Date().toString());
                    Toast.makeText(getApplicationContext(), mID, Toast.LENGTH_SHORT).show();

                    // finish();
                    break;
                // негативная кнопка
                case Dialog.BUTTON_NEGATIVE:
                    acceptRef = new Firebase("https://unlimeted-house.firebaseio.com/orders/"+mID);
                    acceptRef.child("status").setValue("canceld");
                    acceptRef.child("canceldata").setValue(new Date().toString());
                    Toast.makeText(getApplicationContext(), mID, Toast.LENGTH_SHORT).show();
                    //finish();
                    break;
                // нейтральная кнопка
                // case Dialog.BUTTON_NEUTRAL:
                //   break;
            }
        }
    };







    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();

    }


}