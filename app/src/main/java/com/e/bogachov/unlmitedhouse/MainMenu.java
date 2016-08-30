package com.e.bogachov.unlmitedhouse;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.e.bogachov.unlmitedhouse.ShopsCateg.ShopsMenu;


public class MainMenu extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        //Find View
        ImageView beauty = (ImageView) findViewById(R.id.beauty);
        ImageView product = (ImageView) findViewById(R.id.product);
        ImageView candy = (ImageView) findViewById(R.id.candy);
        ImageView house = (ImageView) findViewById(R.id.house);
        ImageView other = (ImageView) findViewById(R.id.other);


        beauty.setOnClickListener(this);
        product.setOnClickListener(this);
        candy.setOnClickListener(this);
        house.setOnClickListener(this);
        other.setOnClickListener(this);

       getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getActionBar();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, ShopsMenu.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.drawable.btn_aaact:{
                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                startActivity(goToShopsMenu);

            }

            case R.id.beauty:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                startActivity(goToShopsMenu);

            }

            case R.id.product:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                startActivity(goToShopsMenu);


            }

            case R.id.candy:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                startActivity(goToShopsMenu);

            }

            case R.id.house:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                startActivity(goToShopsMenu);

            }
            case R.id.other:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                startActivity(goToShopsMenu);

            }


        }
    }
}
