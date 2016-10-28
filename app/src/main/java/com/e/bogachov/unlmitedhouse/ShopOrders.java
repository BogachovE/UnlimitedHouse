package com.e.bogachov.unlmitedhouse;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.ShopsCateg.FriendlyMessage;
import com.e.bogachov.unlmitedhouse.ShopsCateg.Order;
import com.e.bogachov.unlmitedhouse.ShopsCateg.ServiceTypeMenu;
import com.e.bogachov.unlmitedhouse.ShopsCateg.ShopsMenu;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopOrders extends Activity {

    private List<Order> order;
    RecyclerView rv;
    private FirebaseRecyclerAdapter<Order, OrderViewHolder> mAdapter;
    private DatabaseReference mData;
    String isItShop;
    String customNum;
    Query mQuery;
    String shopname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_orders);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        Hawk.init(this).build();
        isItShop = Hawk.get("isitshop");
        customNum = Hawk.get("userphone");
        shopname = Hawk.get("fromshop");





        mData = FirebaseDatabase.getInstance().getReference();

       if (isItShop.equals("false")) {
             mQuery = mData.child("orders").orderByChild("customNum").equalTo(customNum);
       }
       else mQuery = mData.child("orders").orderByChild("fromshop").equalTo(shopname);


        StaggeredGridLayoutManager gm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(gm);

        rv.setHasFixedSize(true);


        mAdapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder >(
                Order.class, R.layout.order_item, OrderViewHolder.class, mQuery ){


            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, final Order model, int position) {
                Date mDate;
                viewHolder.from_shop.setText(" "+model.getFromshop());
                viewHolder.order_numb.setText(" "+model.getId()+" ");
                viewHolder.order_data.setText(" "+model.getData());



                SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                try {
                    String dataString = model.getData();
                     mDate  = formatter.parse(dataString);

                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(),"blya",Toast.LENGTH_SHORT).show();
                    mDate = new Date();
                    e.printStackTrace();
                }
                Integer day = mDate.getDay();
                Integer month = mDate.getMonth();
                String[] monStr = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
                viewHolder.order_data_two.setText(day.toString());
                viewHolder.order_data_big.setText(monStr[month]);





                viewHolder.check_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Firebase   mDelRef = new Firebase("https://unlimeted-house.firebaseio.com/orders/"+model.getId());
                        if(isItShop.equals("false")){
                        mDelRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                            @Override
                            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                mDelRef.removeValue();

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });}
                        else if (isItShop.equals("true")){
                            mDelRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                                @Override
                                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                    Date d = new Date();
                                    mDelRef.child("status").setValue("confirmation");
                                    mDelRef.child("confirmationdata").setValue(d.toString());
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });

                        }
                    }
                });
                viewHolder.cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isItShop.equals("false")){
                        Hawk.put("orderid",model.getId());
                        Intent goToOrderStat = new Intent(v.getContext(),OrderStatus.class);
                        v.getContext().startActivity(goToOrderStat);}
                        else if (isItShop.equals("true")){
                            final Firebase   mDelRef = new Firebase("https://unlimeted-house.firebaseio.com/orders/"+model.getId());
                            mDelRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                                @Override
                                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                    Date d = new Date();
                                    mDelRef.child("status").setValue("canceld");
                                    mDelRef.child("canceldata").setValue(d.toString());
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });


                        }

                    }
                });




            }

        };

        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int OrderCount = mAdapter.getItemCount();


            }
        });


        rv.setAdapter(mAdapter);


    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView order_numb;
        TextView from_shop;
        Context context;
        Long l;
        Button oreder_btnss;
        RelativeLayout rvs;
        RelativeLayout rv;
        Button cancel_btn;
        Button check_btn;
        TextView order_data;
        String isItShop;
        TextView order_data_two;
        TextView order_data_big;


        public OrderViewHolder(View v) {
            super(v);
            cv = (CardView) itemView.findViewById(R.id.cv);
            order_numb = (TextView) itemView.findViewById(R.id.order_numb);
            from_shop = (TextView) itemView.findViewById(R.id.from_shop);
            oreder_btnss = (Button)itemView.findViewById(R.id.oreder_btnss);
            rvs = (RelativeLayout)itemView.findViewById(R.id.rvs);
            rv = (RelativeLayout)itemView.findViewById(R.id.rv);
            cancel_btn = (Button)itemView.findViewById(R.id.cancel_btn);
            check_btn = (Button)itemView.findViewById(R.id.check_btn);
            rv.setOnClickListener(this);
            oreder_btnss.setOnClickListener(this);
            cancel_btn.setOnClickListener(this);
            check_btn.setOnClickListener(this);
            order_data = (TextView)itemView.findViewById(R.id.order_data);
            isItShop = Hawk.get("isitshop");
            if (isItShop.equals("true")){
                cancel_btn.setBackgroundResource(R.drawable.reject);
                check_btn.setBackgroundResource(R.drawable.accept);

            }
            order_data_two = (TextView)itemView.findViewById(R.id.order_data_two);
            order_data_big = (TextView)itemView.findViewById(R.id.order_data_big);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rv:{
                    Animation anim = null;
                    anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.myrotate_back);
                    oreder_btnss.startAnimation(anim);
                    rvs.setVisibility(View.INVISIBLE);
                    break;
                }
                case R.id.oreder_btnss:{
                    Animation anim = null;
                    Animation anim2 = null;
                    anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.myrotate);
                    anim2 = AnimationUtils.loadAnimation(v.getContext(),R.anim.myscale);
                    oreder_btnss.startAnimation(anim);
                    rvs.setVisibility(View.VISIBLE);
                    rvs.startAnimation(anim2);
                    break;

                }


            }




        }
    }


}





