package com.e.bogachov.unlmitedhouse;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.ShopsCateg.ShopsMenu;

public class ShopMenu extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_menu);


        ActionBar actionBar = getActionBar();
        actionBar.hide();

        FrameLayout my_shop = (FrameLayout)findViewById(R.id.my_shop);
        my_shop.setOnClickListener(this);
        FrameLayout my_schedule = (FrameLayout)findViewById(R.id.my_schedule);
        my_schedule.setOnClickListener(this);
        ImageView orders = (ImageView)findViewById(R.id.orders_btn);
        orders.setOnClickListener(this);
        FrameLayout shop_in_number = (FrameLayout)findViewById(R.id.shop_in_number);
        shop_in_number.setOnClickListener(this);
        FrameLayout rating = (FrameLayout)findViewById(R.id.rating);
        rating.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.my_shop:{
                Intent goToShops = new Intent(this, MainMenu.class);
                startActivity(goToShops);
                break;
            }

            case R.id.my_schedule:{
                Intent goToMySchedule = new Intent(this,MySchedule.class);
            }

            case R.id.orders_btn:{
                Intent goToShopOrders = new Intent(this,ShopOrders.class);
                startActivity(goToShopOrders);
                break;
            }
            case R.id.shop_in_number:{
                Intent goToShopInNumber= new Intent(this,ShopInNumber.class);
            }
            case R.id.rating:{
                Intent goToRating = new Intent(this,Rating.class);
            }
        }

    }
}
