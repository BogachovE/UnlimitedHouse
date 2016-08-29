package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.e.bogachov.unlmitedhouse.R;

import java.util.ArrayList;
import java.util.List;

public class ServiceTypeMenu extends AppCompatActivity {

    private List<Shops> shops;
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beauty_categ);

        rv = (RecyclerView) findViewById(R.id.rv);

        StaggeredGridLayoutManager gm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);


        initializeData();
        initializeAdapter();


    }

    private void initializeAdapter() {
        ServiceTypeAdapter adapter = new ServiceTypeAdapter(shops);
        rv.setAdapter(adapter);

    }

    private void initializeData() {
        shops = new ArrayList<>();
        shops.add(new Shops("Milk", R.drawable.cace));
        shops.add(new Shops("Yogurt", R.drawable.lasunia));
        shops.add(new Shops("Kefir", R.drawable.lasunia));

    }
}