package com.e.bogachov.unlmitedhouse;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Locale mNewLocale;
    String lang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find view
        ImageButton signbtn = (ImageButton) findViewById(R.id.signbtn);
        signbtn.setOnClickListener(this);
        ImageButton loginbtn = (ImageButton) findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(this);
        Button englang = (Button) findViewById(R.id.englang);
        englang.setOnClickListener(this);
        Button arablang = (Button) findViewById(R.id.arablang);
        arablang.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signbtn:{
                Intent signintent = new Intent(MainActivity.this, Signup.class);
                startActivity(signintent);
                break;

            }

            case R.id.loginbtn:{
                Intent loginintent = new Intent(MainActivity.this, Login.class);
                startActivity(loginintent);
                break;

            }

            case R.id.englang:{

                break;
            }

            case R.id.arablang:{

                break;
            }
        }
    }

}
