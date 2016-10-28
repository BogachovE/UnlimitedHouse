package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;
import com.firebase.client.android.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class BuyProduct extends Activity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{
    LinearLayout view;
    ImageView custom_order_back;
    TextView tvCount;
    String mShops;
    String mService;
    String mProduct;
    private DatabaseReference mData;
    private static final String TAG = "MainActivity";
    Firebase mRefProductPhoto;
    Firebase mRefProductPrice;
    Firebase mRefProductPricetype;
    Firebase mRefProductDecrip;
    Firebase getmRefProductName;
    Integer p;
    Context context;
    Integer count;
    TextView person_numb_count;
    Boolean visting;
    ImageView visting_req_btn;
    ImageView visting_req_btn2;
    RelativeLayout add_to_basket;
    TextView price_txt;
    TextView price_t;
    String mVisting;
    TextView service_desc;



    final int DIALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
        setContentView(R.layout.buy_product);
        Firebase.setAndroidContext(this);

        Intent intent = getIntent();
        mProduct = Hawk.get("productid");

        Hawk.init(this).build();
        mShops = Hawk.get("shopid");
        mService = Hawk.get("serviceid");

        context = getApplicationContext();

        custom_order_back = (ImageView) findViewById(R.id.custom_order_back);
        custom_order_back.setOnClickListener(this);

        ImageView btn_more = (ImageView) findViewById(R.id.btn_more);
        btn_more.setOnClickListener(this);

        ImageView person_numb_btn_less = (ImageView) findViewById(R.id.person_numb_btn_less);
        person_numb_btn_less.setOnClickListener(this);

        visting_req_btn = (ImageView) findViewById(R.id.visting_req_btn);
        visting_req_btn.setOnClickListener(this);

        visting_req_btn2 = (ImageView) findViewById(R.id.visting_req_btn2);
        visting_req_btn2.setOnClickListener(this);

        add_to_basket = (RelativeLayout) findViewById(R.id.add_to_basket);
        add_to_basket.setOnClickListener(this);

        price_txt = (TextView) findViewById(R.id.price_txt);
        price_t = (TextView) findViewById(R.id.price_t);
        person_numb_count = (TextView) findViewById(R.id.person_numb_count);
        visting_req_btn = (ImageView) findViewById(R.id.visting_req_btn);
        visting_req_btn.setOnClickListener(this);
        visting_req_btn2 = (ImageView) findViewById(R.id.visting_req_btn2);
        visting_req_btn2.setOnClickListener(this);
        mVisting = "no";

        getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        ActionBar actionBar = getActionBar();

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
                Button curtBtn = (Button) findViewById(R.id.curtBtn);
                break;
            }
            case "other": {
                getActionBar().setCustomView(R.layout.abs_other);
                break;
            }
        }

        count = 1;
        categ = Hawk.get("categ");

        mData = FirebaseDatabase.getInstance().getReference();
        mRefProductPhoto = new Firebase("https://unlimeted-house.firebaseio.com/shops/category/" + categ + "/" + mShops + "/servicetype/" + mService.toString() + "/products/" + mProduct + "/photourl");
        mRefProductPhoto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String producturl = dataSnapshot.getValue(String.class);
                ImageView product_image = (ImageView) findViewById(R.id.product_image);
                Picasso.with(getApplication()).load(producturl).into(product_image);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRefProductPrice = new Firebase("https://unlimeted-house.firebaseio.com/shops/category/" + categ + "/" + mShops + "/servicetype/" + mService.toString() + "/products/" + mProduct.toString() + "/price/p");
        mRefProductPrice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String p = dataSnapshot.getValue(String.class);
                TextView price_txt = (TextView) findViewById(R.id.price_txt);
                price_txt.setText(p.toString());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Firebase.setAndroidContext(this);


        mRefProductPricetype = new Firebase("https://unlimeted-house.firebaseio.com/shops/category/" + categ + "/" + mShops + "/servicetype/" + mService + "/products/" + mProduct + "/price/currency");
        mRefProductPricetype.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String t = dataSnapshot.getValue(String.class);
                TextView price_t = (TextView) findViewById(R.id.price_t);
                price_t.setText(" " + t.toString());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRefProductDecrip = new Firebase("https://unlimeted-house.firebaseio.com/shops/category/" + categ + "/" + mShops + "/servicetype/" + mService + "/products/" + mProduct + "/description");
        mRefProductDecrip.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String d = dataSnapshot.getValue(String.class);
                service_desc = (TextView) findViewById(R.id.service_desc);
                service_desc.setText(d);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }





    @Override
    public void onClick(View view) {
        person_numb_count = (TextView)findViewById(R.id.person_numb_count);
        switch (view.getId()){
            case R.id.custom_order_back :{
                showDialog(DIALOG);
                break;
            }
            case R.id.person_numb_btn_less:{
                count=--count;
                person_numb_count.setText(count.toString());
                break;
            }
            case R.id.btn_more:{
                count=++count;
                person_numb_count.setText(count.toString());
                break;
            }

            case R.id.visting_req_btn:{
                visting=true;
                break;
            }

            case R.id.visting_req_btn2:{
                visting=false;
                break;
            }

            case  R.id.add_to_basket:{
                Integer numZakaz= null;
                numZakaz = Hawk.get("numZakaz",numZakaz);
                Date date = new Date();
                if (numZakaz == null){
                    numZakaz = 0;
                    Hawk.put("numZakaz",numZakaz);
                }
                 else  if (numZakaz >= 0){
                    Hawk.put("numZakaz",numZakaz= numZakaz+1);
                }
                String fromShop = Hawk.get("fromShop");
                Integer sum = Integer.parseInt(price_txt.getText().toString()) * Integer.parseInt(person_numb_count.getText().toString());
                String productname = Hawk.get("productname");
                Map<String,String> order = new HashMap<String, String>();
                order.put("p",price_txt.getText().toString());
                order.put("currency",price_t.getText().toString());
                order.put("count",person_numb_count.getText().toString());
                order.put("data", date.toString());
                order.put("visiting",mVisting);
                order.put("fromShop",fromShop);
                order.put("sum",sum.toString());
                order.put("name",productname);
                order.put("descript",service_desc.getText().toString());
                String coment = Hawk.get("coment");
                if (coment != null) {
                order.put("coment",coment);}
                Map<Integer,Map<String,String>> ordervc = new HashMap<Integer,Map<String,String>>();
                if (Hawk.get("order")!= null){ordervc = Hawk.get("order");}
                ordervc.put(numZakaz,order);
                Hawk.put("order",ordervc);
                Toast.makeText(getApplicationContext(),"aded in basket",Toast.LENGTH_SHORT).show();



                break;
            }


        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

//        adb.setTitle("Please write your preference");

        // создаем view из dialog.xml
        view = (LinearLayout) getLayoutInflater()
                .inflate(R.layout.dialog, null);
        // устанавливаем ее, как содержимое тела диалога
        builder.setView(view);
        // находим TexView для отображения кол-ва
        tvCount = (TextView) view.findViewById(R.id.tvCount);
        Button sendbtn = (Button)view.findViewById(R.id.sendbtn);
        final AlertDialog adb =builder.show();
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.sendbtn:{
                        Hawk.put("coment",tvCount.getText().toString());
                        adb.cancel();


                         }
                }
            }
        });


        return null;
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
