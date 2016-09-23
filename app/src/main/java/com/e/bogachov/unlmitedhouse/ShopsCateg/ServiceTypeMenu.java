package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class ServiceTypeMenu extends Activity implements GoogleApiClient.OnConnectionFailedListener {
    String mShops;
    SharedPreferences sPref;




    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView shopName;
        ImageView shopPhoto;
        RelativeLayout rl;
        Context context =itemView.getContext();









        public MessageViewHolder(View v) {
            super(v);



            cv = (CardView)itemView.findViewById(R.id.cv);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent goToProduct = new Intent(context,ProductMenu.class);
                    context.startActivity(goToProduct);
                    goToProduct.putExtra("cardid",String.valueOf(getAdapterPosition()));


                    Toast.makeText(context, "clisk."+(getItemId()+2), Toast.LENGTH_SHORT).show();


                }
            });
            shopName = (TextView)itemView.findViewById(R.id.shop_name);
            shopPhoto = (ImageView)itemView.findViewById(R.id.shop_photo);
            rl =(RelativeLayout)itemView.findViewById(R.id.rl);


        }


    }

    private static final String TAG = "MainActivity";
    private List<Shops> shops;
    private RecyclerView rv;
    private DatabaseReference mData;
    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> mAdapter;

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

        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput("file", MODE_PRIVATE)));
            // пишем данные
            bw.write(mShops);
            // закрываем поток
            bw.close();
            Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }









        mChild="shops/"+mShops+"/servicetype";

        mAdapter = new FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder >(
                FriendlyMessage.class, R.layout.item, MessageViewHolder.class, mData.child(mChild) ){


            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, FriendlyMessage model, int position) {
                viewHolder.shopName.setText(model.getName());
                Picasso.with(getApplication()).load(model.getphotourl()).into(viewHolder.shopPhoto);
                Intent shopint = new Intent(getApplicationContext(),ProductMenu.class);
                shopint.putExtra("shopid",mShops);


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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();

    }
}