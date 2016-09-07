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
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.ShopsCateg.ShopsMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


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
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        getActionBar().setCustomView(R.layout.abs_main);
        ActionBar actionBar = getActionBar();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:{
                //SlidingMenu
                SlidingMenu menu = new SlidingMenu(this);
                menu.setMode(SlidingMenu.LEFT);
                menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                menu.setFadeDegree(0.35f);
                menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
                menu.setMenu(R.layout.sidemenu);
                menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);



                //Find View Slide Bar
                TextView profilebtn = (TextView) findViewById(R.id.profilebtn);
                TextView ordersbtn = (TextView) findViewById(R.id.ordersbtn);
                TextView addservicebtn = (TextView) findViewById(R.id.addservicebtn);
                TextView contactbtn = (TextView) findViewById(R.id.contactbtn);
                TextView logoutbtn = (TextView) findViewById(R.id.logoutbtn);
                profilebtn.setOnClickListener(this);
                ordersbtn.setOnClickListener(this);
                addservicebtn.setOnClickListener(this);
                contactbtn.setOnClickListener(this);
                logoutbtn.setOnClickListener(this);
                menu.toggle(true);

            }


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
                break;

            }

            case R.id.beauty:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                goToShopsMenu.putExtra("categ","beauty");
                startActivity(goToShopsMenu);
                break;

            }

            case R.id.product:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                goToShopsMenu.putExtra("categ","product");
                startActivity(goToShopsMenu);
                break;


            }

            case R.id.candy:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                goToShopsMenu.putExtra("categ","candy");
                startActivity(goToShopsMenu);
                break;

            }

            case R.id.house:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                goToShopsMenu.putExtra("categ","house");
                startActivity(goToShopsMenu);
                break;

            }
            case R.id.other:{

                Intent goToShopsMenu = new Intent(MainMenu.this,ShopsMenu.class);
                goToShopsMenu.putExtra("categ","other");
                startActivity(goToShopsMenu);
                break;

            }


        }
    }
}
