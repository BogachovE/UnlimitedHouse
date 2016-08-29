package com.e.bogachov.unlmitedhouse.RegLogSign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.e.bogachov.unlmitedhouse.MainMenu;
import com.e.bogachov.unlmitedhouse.R;

public class RegLocation extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_location);

        //find View
        Button loginBtn = (Button)findViewById(R.id.nextbtn);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nextbtn:{
                Intent goMainMenu = new Intent(RegLocation.this, MainMenu.class);
                startActivity(goMainMenu);
            }
        }
    }
}
