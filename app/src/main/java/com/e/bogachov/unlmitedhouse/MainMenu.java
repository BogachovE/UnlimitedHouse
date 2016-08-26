package com.e.bogachov.unlmitedhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        //Find View
        ImageView beauty = (ImageView)findViewById(R.id.beauty);
        ImageView product = (ImageView)findViewById(R.id.product);
        ImageView candy = (ImageView)findViewById(R.id.candy);
        ImageView house = (ImageView)findViewById(R.id.house);
        ImageView other = (ImageView)findViewById(R.id.other);

        beauty.setOnClickListener(this);
        product.setOnClickListener(this);
        candy.setOnClickListener(this);
        house.setOnClickListener(this);
        other.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.beauty:{

                Intent goToBeauty = new Intent(MainMenu.this,BeautyCateg.class);
                startActivity(goToBeauty);

            }

            case R.id.product:{

                Intent goToProduct = new Intent(MainMenu.this,ProductCateg.class);
                startActivity(goToProduct);


            }

            case R.id.candy:{

                Intent goToCandy = new Intent(MainMenu.this,CandyCateg.class);
                startActivity(goToCandy);

            }

            case R.id.house:{

                Intent goToHouse = new Intent(MainMenu.this,HouseCateg.class);
                startActivity(goToHouse);

            }
            case R.id.other:{

                Intent goToOther = new Intent(MainMenu.this,OtherCateg.class);
                startActivity(goToOther);

            }

        }
    }
}
