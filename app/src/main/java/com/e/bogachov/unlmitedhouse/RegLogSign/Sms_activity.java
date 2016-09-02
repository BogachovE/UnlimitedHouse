package com.e.bogachov.unlmitedhouse.RegLogSign;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.e.bogachov.unlmitedhouse.R;

public class Sms_activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_activity);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        //finf View
        Button nextbtn = (Button)findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nextbtn :{
                Intent regLocationIntent = new Intent(Sms_activity.this, RegLocation.class);
                startActivity(regLocationIntent);

            }
        }
    }
}
