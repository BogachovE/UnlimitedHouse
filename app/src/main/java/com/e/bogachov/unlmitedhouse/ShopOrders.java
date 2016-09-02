package com.e.bogachov.unlmitedhouse;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.ShopsCateg.Shops;
import com.e.bogachov.unlmitedhouse.ShopsCateg.ShopsAdapter;
import com.e.bogachov.unlmitedhouse.Slide.Orders;

import java.util.ArrayList;
import java.util.List;

public class ShopOrders extends Activity {

    private List<Order> order;
    RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_orders);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        rv = (RecyclerView) findViewById(R.id.rv);
        StaggeredGridLayoutManager gm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();



    }

    private void initializeAdapter() {
        OrdersAdapter adapter = new OrdersAdapter(order);
        rv.setAdapter(adapter);

    }

    private void initializeData() {
        order = new ArrayList<>();
        order.add(new Order("1", "Lasunia", "2/10/2016"));
        order.add(new Order("5", "Lasunia", "5/10/2016"));


    }
}