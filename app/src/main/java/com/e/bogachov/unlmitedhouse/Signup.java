package com.e.bogachov.unlmitedhouse;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);


        //finf View
        ImageButton loginbtn = (ImageButton)findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(this);

        //font change
        TextView phonetxt = (TextView)findViewById(R.id.phonetxt);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/roboto.ttf");
        phonetxt.setTypeface(type);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginbtn :{
                Intent smsintent = new Intent(Signup.this, Sms_activity.class);
                startActivity(smsintent);

            }
        }

    }
}
