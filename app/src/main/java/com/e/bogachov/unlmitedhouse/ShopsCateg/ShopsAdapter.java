package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.R;

import java.util.List;

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ShopsViewHolder> {

    ColorDrawable  cn = new ColorDrawable(Color.parseColor("#FAFAFA"));
    ColorDrawable cdn = new ColorDrawable(Color.parseColor("#FFFFFF"));
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
            shopPhoto = (ImageView)itemView.findViewById(R.id.shop_photo);
            rl =(RelativeLayout)itemView.findViewById(R.id.rl);
            rl.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
           Integer last = shops.size()-1;
            if (getAdapterPosition()==last){
               Intent dialog = new Intent(context,ShopsMenu.class);
                dialog.putExtra("click","true");

            }

            Intent intent=null ;
            switch (getAdapterPosition()){
                case 0:{
                    intent = new Intent(context,ServiceTypeMenu.class);
                    context.startActivity(intent);
                    break;



                }




            }



        }
    }

    List<Shops> shops;

    ShopsAdapter(List<Shops> shops){this.shops = shops;}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ShopsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        ShopsViewHolder pvh = new ShopsViewHolder(v);
        return pvh;

    }

    @Override
    public void onBindViewHolder(ShopsViewHolder shopsViewHolder, int i) {

        shopsViewHolder.shopName.setText(shops.get(i).name);
        shopsViewHolder.shopPhoto.setImageResource(shops.get(i).photoId);
        if (i%2!=0){
        shopsViewHolder.cv.setBackground(cn);
        }
        else {shopsViewHolder.cv.setBackground(cdn);

        }

    }

    @Override
    public int getItemCount() {

        return shops.size();
    }


}



