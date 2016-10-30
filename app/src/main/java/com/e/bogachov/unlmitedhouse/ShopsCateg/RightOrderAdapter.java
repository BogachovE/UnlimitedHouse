package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.R;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RightOrderAdapter extends RecyclerView.Adapter<RightOrderAdapter.RightOrderViewHolder> {



    public void delete(int position) { //removes the row
        orders.remove(position);
        notifyItemRemoved(position);
    }




    RightOrderAdapter adapter = this;
    ColorDrawable  cn = new ColorDrawable(Color.parseColor("#FAFAFA"));
    ColorDrawable cdn = new ColorDrawable(Color.parseColor("#FFFFFF"));
    public class RightOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



        CardView cv2;
        TextView productName;
        TextView productCount;
        Button btn;


        Context context = itemView.getContext();



        RightOrderViewHolder(final View itemView){
            super(itemView);

            cv2 = (CardView)itemView.findViewById(R.id.cv2);
            productName = (TextView)itemView.findViewById(R.id.productName);
            productCount = (TextView) itemView.findViewById(R.id.productCount);
            btn =(Button)itemView.findViewById(R.id.btn);


            productName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf"));
            productCount.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf"));



        }




        @Override
        public void onClick(View v) {
           /*Integer last = shops.size()-1;
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



*/        }
    }





    List<Order> orders;
    RightOrderAdapter(List<Order> orders){
        this.orders = orders;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RightOrderViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_right, viewGroup, false);
        RightOrderViewHolder pvh = new RightOrderViewHolder(v);
        return pvh;

    }

    @Override
    public void onBindViewHolder(RightOrderViewHolder rightOrderViewHolder, final int i) {


        rightOrderViewHolder.productName.setText(orders.get(i).name + orders.get(i).count);
        rightOrderViewHolder.productCount.setText(orders.get(i).descript);
        rightOrderViewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete(i);
                orders.remove(i);



            }
        });






    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


}



