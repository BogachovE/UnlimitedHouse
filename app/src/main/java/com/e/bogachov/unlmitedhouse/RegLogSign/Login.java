package com.e.bogachov.unlmitedhouse.RegLogSign;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.e.bogachov.unlmitedhouse.MainMenu;
import com.e.bogachov.unlmitedhouse.R;
import com.e.bogachov.unlmitedhouse.ShopMenu;

public class Login extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Find View
        ImageButton loginBtn = (ImageButton)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

        //Dlia proverki!!!
        //personType = "User";



    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:{
                Intent goShopMenu = new Intent(Login.this, ShopMenu.class);
                startActivity(goShopMenu);

            }
        }
    }
}
