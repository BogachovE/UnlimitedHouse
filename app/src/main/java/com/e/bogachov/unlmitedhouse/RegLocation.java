package com.e.bogachov.unlmitedhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class RegLocation extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_location);

        //find View
        ImageButton loginBtn = (ImageButton)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:{
                Intent goMainMenu = new Intent(RegLocation.this, MainMenu.class);
                startActivity(goMainMenu);
            }
        }
    }
}
