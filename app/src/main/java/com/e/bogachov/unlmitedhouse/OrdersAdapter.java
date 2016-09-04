package com.e.bogachov.unlmitedhouse;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Created by livingroomadmin on 02.09.16.
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>  {



    public class OrdersViewHolder extends RecyclerView.ViewHolder  {

        CardView cv;
        TextView orderNumb;
        TextView fromShop;
        TextView orderData;
        Button oreder_btnss;
        RelativeLayout rv;
        RelativeLayout rvs;
        Button check_btn;


        Context context = itemView.getContext();

        OrdersViewHolder(View itemView){
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);
            orderNumb = (TextView)itemView.findViewById(R.id.order_numb);
            fromShop = (TextView)itemView.findViewById(R.id.from_shop);
            orderData= (TextView) itemView.findViewById(R.id.order_data);
            oreder_btnss = (Button) itemView.findViewById(R.id.oreder_btnss);
            rv = (RelativeLayout)itemView.findViewById(R.id.rv);
            rvs = (RelativeLayout)itemView.findViewById(R.id.rvs);
            check_btn = (Button)itemView.findViewById(R.id.check_btn);


        }



    }

    List<Order> order;
    OrdersAdapter(List<Order> product){this.order = product;}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item, viewGroup, false);
        OrdersViewHolder pvh = new OrdersViewHolder(v);
        return pvh;

    }

    @Override
    public void onBindViewHolder (final OrdersViewHolder ordersViewHolder, int i) {

        ordersViewHolder.orderNumb.setText(order.get(i).orderNumb);
        ordersViewHolder.fromShop.setText(order.get(i).fromShop);
        ordersViewHolder.orderData.setText(order.get(i).orderData);
        ordersViewHolder.rv.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {

                Animation anim = AnimationUtils.loadAnimation(ordersViewHolder.context,R.anim.myrotate_back);
                ordersViewHolder.oreder_btnss.startAnimation(anim);
                ordersViewHolder.rvs.setVisibility(View.INVISIBLE);
            }
        });
        ordersViewHolder.oreder_btnss.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Animation anim = AnimationUtils.loadAnimation(ordersViewHolder.context,R.anim.myrotate);
                    ordersViewHolder.oreder_btnss.startAnimation(anim);
                    /// button click event
                Animation anim2 = AnimationUtils.loadAnimation(ordersViewHolder.context,R.anim.myscale);
                ordersViewHolder.rvs.setVisibility(View.VISIBLE);
                ordersViewHolder.rvs.startAnimation(anim2);


            }
        });
        ordersViewHolder.check_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent goToOrderStatus = new Intent(ordersViewHolder.context,OrderStatus.class);
                ordersViewHolder.context.startActivity(goToOrderStatus);
            }
        });


    }

    @Override
    public int getItemCount() {

        return order.size();
    }



}
