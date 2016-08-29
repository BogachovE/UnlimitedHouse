package com.e.bogachov.unlmitedhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.e.bogachov.unlmitedhouse.ShopsCateg.ShopsMenu;


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
