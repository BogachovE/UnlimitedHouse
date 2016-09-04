package com.e.bogachov.unlmitedhouse;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class OrderStatus extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_status);

        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }
}
