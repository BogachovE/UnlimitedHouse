package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.e.bogachov.unlmitedhouse.R;

import java.util.ArrayList;
import java.util.List;

public class ProductMenu extends Activity {

    private List<Shops> shops;
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_menu);

        rv=(RecyclerView)findViewById(R.id.rv);

        StaggeredGridLayoutManager gm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);


        initializeData();
        initializeAdapter();




    }

    private void initializeAdapter() {
        ProductAdapter adapter = new ProductAdapter(shops);
        rv.setAdapter(adapter);

    }

    private void initializeData() {
        shops = new ArrayList<>();
       /* shops.add(new Shops("Milk 20%",R.drawable.cace));
        shops.add(new Shops("Milk 30%",R.drawable.lasunia));
        shops.add(new Shops("Milk 40%",R.drawable.lasunia));*/

    }




}
