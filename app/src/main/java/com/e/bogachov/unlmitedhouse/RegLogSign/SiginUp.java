package com.e.bogachov.unlmitedhouse.RegLogSign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.e.bogachov.unlmitedhouse.R;

public class SiginUp extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        //find View
        Button nextbtn = (Button)findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nextbtn :{
                Intent goTOSms = new Intent(SiginUp.this,Sms_activity.class);
                startActivity(goTOSms);
            }
        }
    }
}
