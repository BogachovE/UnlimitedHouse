package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    Integer p;
    Context context;
    Integer count;
    TextView person_numb_count;
    Boolean visting;
    ImageView visting_req_btn;
    ImageView visting_req_btn2;




    final int DIALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_product);
        Firebase.setAndroidContext(this);

        Intent intent = getIntent();
        mProduct=Hawk.get("productid");

        Hawk.init(this).build();
        mShops=Hawk.get("shopid");
        mService=Hawk.get("serviceid");

        context = getApplicationContext();

        custom_order_back =(ImageView) findViewById(R.id.custom_order_back);
        custom_order_back.setOnClickListener(this);

        Button btn_more =(Button)findViewById(R.id.btn_more);
        btn_more.setOnClickListener(this);

        Button person_numb_btn_less =(Button)findViewById(R.id.person_numb_btn_less);
        person_numb_btn_less.setOnClickListener(this);

        visting_req_btn = (ImageView)findViewById(R.id.visting_req_btn);
        visting_req_btn.setOnClickListener(this);

        visting_req_btn2 = (ImageView)findViewById(R.id.visting_req_btn2);
        visting_req_btn2.setOnClickListener(this);


        count=1;

        mData = FirebaseDatabase.getInstance().getReference();
        mRefProductPhoto = new Firebase("https://unlimeted-house.firebaseio.com/shops/"+mShops+"/servicetype/"+mService+"/products/"+mProduct+"/photourl");
        mRefProductPhoto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String producturl = dataSnapshot.getValue(String.class);
                ImageView product_image = (ImageView)findViewById(R.id.product_image);
                Picasso.with(getApplication()).load(producturl).into(product_image);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRefProductPrice = new Firebase("https://unlimeted-house.firebaseio.com/shops/"+mShops+"/servicetype/"+mService+"/products/"+mProduct+"/price/p");
        mRefProductPrice.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer p = dataSnapshot.getValue(Integer.class);
                TextView price_txt =(TextView)findViewById(R.id.price_txt);
                price_txt.setText(p.toString());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Firebase.setAndroidContext(this);




        mRefProductPricetype = new Firebase("https://unlimeted-house.firebaseio.com/shops/"+mShops+"/servicetype/"+mService+"/products/"+mProduct+"/price/currency");
        mRefProductPricetype.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String t = dataSnapshot.getValue(String.class);
                TextView price_t =(TextView)findViewById(R.id.price_t);
                price_t.setText(" "+t.toString());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRefProductDecrip = new Firebase("https://unlimeted-house.firebaseio.com/shops/"+mShops+"/servicetype/"+mService+"/products/"+mProduct+"/decrip");
        mRefProductDecrip.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String d = dataSnapshot.getValue(String.class);
                TextView service_desc = (TextView)findViewById(R.id.service_desc);
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

        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
//        adb.setTitle("Please write your preference");
        // создаем view из dialog.xml
        view = (LinearLayout) getLayoutInflater()
                .inflate(R.layout.dialog, null);
        // устанавливаем ее, как содержимое тела диалога
        adb.setView(view);
        // находим TexView для отображения кол-ва
        tvCount = (TextView) view.findViewById(R.id.tvCount);
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
