package com.e.bogachov.unlmitedhouse;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShopOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_orders);

        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }
}
