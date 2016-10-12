package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.Dialog1;
import com.e.bogachov.unlmitedhouse.Dialog2;
import com.e.bogachov.unlmitedhouse.R;
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
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ServiceTypeMenu extends Activity implements GoogleApiClient.OnConnectionFailedListener {
    String mShops;
    SharedPreferences sPref;
    String isItShop;
    Query mQuery;
    String ownerid;
    String userId;




    public static class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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


        }


        @Override
        public void onClick(View v) {
            DialogFragment dlg1;
            String isItShop = Hawk.get("isitshop");
            userId=Hawk.get("userid");
            ownerid=Hawk.get("ownerid");

            switch (v.getId()) {
                case R.id.rl: {
                    if (isItShop.equals("false")) {
                        Intent goToServiceType = new Intent(v.getContext(), ProductMenu.class);
                        goToServiceType.putExtra("clickid", String.valueOf(getAdapterPosition()+1));
                        Hawk.put("fromShop", shopName.getText().toString());
                        v.getContext().startActivity(goToServiceType);
                        Toast.makeText(v.getContext().getApplicationContext(), "clisk." + (getAdapterPosition()), Toast.LENGTH_SHORT).show();

                    }else if(isItShop.equals("true") & getAdapterPosition() != 0){
                        Intent goToServiceType = new Intent(v.getContext(), ProductMenu.class);
                        goToServiceType.putExtra("clickid", String.valueOf(getAdapterPosition()+1));
                        Hawk.put("fromShop", shopName.getText().toString());
                        v.getContext().startActivity(goToServiceType);
                        Toast.makeText(v.getContext().getApplicationContext(), "clisk." + (getAdapterPosition()), Toast.LENGTH_SHORT).show();

                    }
                    else {  if(isItShop.equals("true") & getAdapterPosition()!= 0& userId.equals(ownerid)){

                        FragmentManager fr = ((Activity) v.getContext()).getFragmentManager();
                        dlg1 = new Dialog1();
                        dlg1.show(fr, "dlg1");
                    }


                    }
                }


            }


        }
    }



    private static final String TAG = "MainActivity";
    private List<Shops> shops;
    private RecyclerView rv;
    private DatabaseReference mData;
    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> mAdapter;
    Firebase ownerRef;

    String mChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_type_menu);




        mData = FirebaseDatabase.getInstance().getReference();


        StaggeredGridLayoutManager gm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(gm);

        rv.setHasFixedSize(true);


        Intent intent = getIntent();
        mShops=intent.getStringExtra("clickid");

        Hawk.init(this).build();
        Hawk.put("shopid",mShops);
        isItShop=Hawk.get("isitshop");
        String categ = Hawk.get("categ");
        mChild="shops/category/"+categ+"/"+mShops.toString()+"/servicetype";

        ownerRef = new Firebase("https://unlimeted-house.firebaseio.com/shops/category/"+categ+"/"+mShops+"/");
        ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
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


                    mAdapter = new FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>(
                            FriendlyMessage.class, R.layout.item, MessageViewHolder.class, mQuery) {


                        @Override
                        protected void populateViewHolder(MessageViewHolder viewHolder, FriendlyMessage model, int position) {
                            viewHolder.shopName.setText(model.getName());
                            Picasso.with(getApplication()).load(model.getphotourl()).into(viewHolder.shopPhoto);
                            Intent shopint = new Intent(getApplicationContext(), ProductMenu.class);
                            shopint.putExtra("shopid", mShops);


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
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        ownerid = Hawk.get("ownerid");
        userId = Hawk.get("userid");

    }





    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();

    }
}