package com.e.bogachov.unlmitedhouse;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShopInNumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_in_number);

        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }
}
