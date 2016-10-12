package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.R;

/**
 * Created by livingroomadmin on 08.09.16.
 */
public  class ShopsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    CardView cv;
    TextView shopName;
    ImageView shopPhoto;
    RelativeLayout rl;


    Context context = itemView.getContext();



    ShopsViewHolder(View itemView){
        super(itemView);

        cv = (CardView)itemView.findViewById(R.id.cv);
        shopName = (TextView)itemView.findViewById(R.id.shop_name);
        shopPhoto = (ImageView)itemView.findViewById(R.id.plus);
        rl =(RelativeLayout)itemView.findViewById(R.id.rl);
        rl.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {










    }
}