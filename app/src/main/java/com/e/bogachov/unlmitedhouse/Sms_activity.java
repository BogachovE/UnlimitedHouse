package com.e.bogachov.unlmitedhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Sms_activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_activity);

        //finf View
        Button nextbtn = (Button)findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginbtn :{
                Intent regLocationIntent = new Intent(Sms_activity.this, RegLocation.class);
                startActivity(regLocationIntent);

            }
        }
    }
}
