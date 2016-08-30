package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.os.Bundle;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.e.bogachov.unlmitedhouse.R;

import java.util.ArrayList;
import java.util.List;

public class ShopsMenu extends Activity {

    private List<Shops> shops;
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shops_menu);

        rv=(RecyclerView)findViewById(R.id.rv);

        StaggeredGridLayoutManager gm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);


        initializeData();
        initializeAdapter();




    }

    private void initializeAdapter() {
        ShopsAdapter adapter = new ShopsAdapter(shops);
        rv.setAdapter(adapter);

    }

    private void initializeData() {
        shops = new ArrayList<>();
        shops.add(new Shops("Lasunia",R.drawable.cace));
        shops.add(new Shops("Lasunia",R.drawable.lasunia));
        shops.add(new Shops("Lasunia",R.drawable.lasunia));

    }




}