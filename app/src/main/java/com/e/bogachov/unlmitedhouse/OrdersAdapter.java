package com.e.bogachov.unlmitedhouse;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by livingroomadmin on 02.09.16.
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {

    public class OrdersViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView orderNumb;
        TextView fromShop;
        TextView orderData;

        Context context = itemView.getContext();

        OrdersViewHolder(View itemView){
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);
            orderNumb = (TextView)itemView.findViewById(R.id.order_numb);
            fromShop = (TextView)itemView.findViewById(R.id.from_shop);
            orderData= (TextView) itemView.findViewById(R.id.order_data);
        }


    }

    List<Product> product;
    OrdersAdapter(List<Product> product){this.product = product;}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        OrdersViewHolder pvh = new OrdersViewHolder(v);
        return pvh;

    }

    @Override
    public void onBindViewHolder(OrdersViewHolder ordersViewHolder, int i) {

        ordersViewHolder.orderNumb.setText(product.get(i).orderNumb);
        ordersViewHolder.fromShop.setText(product.get(i).fromShop);
        ordersViewHolder.orderData = (product.get(i).orderData);

    }

    @Override
    public int getItemCount() {

        return product.size();
    }

}
