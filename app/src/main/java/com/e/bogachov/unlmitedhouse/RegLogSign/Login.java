package com.e.bogachov.unlmitedhouse.RegLogSign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.e.bogachov.unlmitedhouse.MainMenu;
import com.e.bogachov.unlmitedhouse.R;
import com.e.bogachov.unlmitedhouse.ShopMenu;

public class Login extends AppCompatActivity implements View.OnClickListener{
String personType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Find View
        ImageButton loginBtn = (ImageButton)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

        //Dlia proverki!!!
        //personType = "User";
        personType = "Shop";


    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:{
                if(personType == "User"){
                Intent goMainMenu = new Intent(Login.this, MainMenu.class);
                startActivity(goMainMenu);}
                else {
                      if (personType == "Shop"){
                          Intent goShopMenu = new Intent(Login.this, ShopMenu.class);
                          startActivity(goShopMenu);
                          }
                }
                break;
            }
        }
    }
}
