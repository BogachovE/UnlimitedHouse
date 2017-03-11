package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.Dialogs.Dialog3;
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
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductMenu extends Activity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {

    private static final String TAG = "MainActivity";

    private RecyclerView rv;
    private DatabaseReference mData;
    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> mAdapter;
    String mChild;
    String mService;
    String mShops;
    Intent toProduct;
    SharedPreferences sPref;
    String isItShop;
    Query mQuery;
    private List<Order> orders;
    String customNum;
    final int DIALOG_EXIT = 2;
    ColorDrawable cn;
    ColorDrawable cdn;




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();

    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        TextView shopName;
        ImageView shopPhoto;
        RelativeLayout rl;
        Context context = itemView.getContext();




        public MessageViewHolder(View v) {
            super(v);
            shopName = (TextView)itemView.findViewById(R.id.shop_name);
            shopPhoto = (ImageView)itemView.findViewById(R.id.plus);
            rl =(RelativeLayout)itemView.findViewById(R.id.rl);
            rl.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv);


        }

        @Override
        public void onClick(View v) {
            DialogFragment dlg3;
            String isItShop = Hawk.get("isitshop");

            switch (v.getId()){
                case R.id.rl:{

                    if (isItShop.equals("false")) {
                        Intent goToServiceType = new Intent(v.getContext(), BuyProduct.class);
                        Hawk.put("productid", String.valueOf(getAdapterPosition()+1));
                        Hawk.put("productname", shopName.getText().toString());
                        goToServiceType.putExtra("clickid", String.valueOf(getAdapterPosition()+1));
                        v.getContext().startActivity(goToServiceType);
                        Toast.makeText(v.getContext().getApplicationContext(), "clisk." + (getAdapterPosition()), Toast.LENGTH_SHORT).show();

                    }else if(isItShop.equals("true") & getAdapterPosition() != 0){
                        Intent goToServiceType = new Intent(v.getContext(), BuyProduct.class);
                        Hawk.put("productid", String.valueOf(getAdapterPosition()));
                        Hawk.put("productname", shopName.getText().toString());
                        v.getContext().startActivity(goToServiceType);
                        Toast.makeText(v.getContext().getApplicationContext(), "clisk." + (getAdapterPosition()), Toast.LENGTH_SHORT).show();

                    }
                    else {
                        FragmentManager fr = ((Activity) v.getContext()).getFragmentManager();
                        dlg3 = new Dialog3();
                        dlg3.show(fr, "dlg1");


                    }
                }

            }
        }
    }

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
        setContentView(R.layout.product_menu);

        cn = new ColorDrawable(Color.parseColor("#FAFAFA"));
        cdn = new ColorDrawable(Color.parseColor("#FFFFFF"));

        mService =Hawk.get("serviceid");
        isItShop = Hawk.get("isitshop");
        mShops=Hawk.get("shopid");

        mData = FirebaseDatabase.getInstance().getReference();

        StaggeredGridLayoutManager gm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);

       categ = Hawk.get("categ");

        mChild="shops/"+"category/"+categ+"/"+ mShops +"/servicetype/"+ mService +"/products";


            if (isItShop.equals("false")) {
                mQuery = mData.child(mChild).orderByKey().startAt("1");
            }else  mQuery = mData.child(mChild);


        mAdapter = new FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder >(
                FriendlyMessage.class, R.layout.item, MessageViewHolder.class, mQuery ){

            String userId = Hawk.get("userid");
            String ownerid = Hawk.get("ownerid");


            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, FriendlyMessage model, int position) {
                viewHolder.shopName.setText(model.getName());
                Picasso.with(getApplication()).load(model.getphotourl()).into(viewHolder.shopPhoto);

                if (((position + 3) % 4 != 0) && ((position + 2) % 4 != 0)) {
                    viewHolder.cv.setBackground(cdn);
                } else {
                    viewHolder.cv.setBackground(cn);

                }

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


        RecycleMenuHelper recycleMenuHelper = new RecycleMenuHelper();
        RecycleMenuHelper.initializeData();
        recycleMenuHelper.setActionBar(this);



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
            View view = inflater.inflate(R.layout.dialog_select_product, (ViewGroup) findViewById(R.id.root));
            String categ = Hawk.get("categ");
            String mShops=Hawk.get("shopid");
            String mService= Hawk.get("serviceid");
            String mProduct = Hawk.get("longservice");
            final Firebase selectRef = new Firebase("https://unhouse-143417.firebaseio.com/shops/category/"+categ+"/"+mShops+"/servicetype/"+mService+"/products/"+mProduct+"/");


            final EditText rename_edit = (EditText) view.findViewById(R.id.rename_edit);
            final EditText change_pic_edit = (EditText) view.findViewById(R.id.change_pic_edit);
            final EditText change_dec_edit = (EditText) view.findViewById(R.id.change_dec_edit);
            final EditText change_price_edit = (EditText) view.findViewById(R.id.change_price_edit);


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

            Button change_dec_btn = (Button) view.findViewById(R.id.change_dec_btn);
            change_dec_btn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectRef.child("description").setValue(change_dec_edit.getText().toString());
                    return false;
                }
            });

            Button change_price_btn = (Button) view.findViewById(R.id.change_price_btn);
            change_price_btn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectRef.child("price/p").setValue(change_price_edit.getText().toString());
                    return false;
                }
            });






            alert.setView(view);
            alert.show();

        }
        return super.onCreateDialog(id);

    }



}

