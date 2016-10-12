package com.e.bogachov.unlmitedhouse;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.orhanobut.hawk.Hawk;

public class OrderStatus extends Activity {
    LinearLayout linearLayout3;
    LinearLayout linearLayout2;
    LinearLayout linearLayout4;
    TextView order_data;
    TextView process;
    TextView proc_txt;
    TextView textView;
    TextView textView3;
    TextView textView6;
    Firebase mDataRef;
    String orderId;
    String inProgressData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_status);

        ActionBar actionBar = getActionBar();
        actionBar.hide();
        Hawk.init(this).build();
        orderId = Hawk.get("orderid");


        linearLayout2 = (LinearLayout)findViewById(R.id.linearLayout2);
        linearLayout3 = (LinearLayout)findViewById(R.id.linearLayout3);
        linearLayout4 = (LinearLayout)findViewById(R.id.linearLayout4);
        order_data = (TextView)findViewById(R.id.order_data);
        process = (TextView)findViewById(R.id.process);
        proc_txt =(TextView)findViewById(R.id.proc_txt);
        textView = (TextView)findViewById(R.id.textView);
        textView3 = (TextView)findViewById(R.id.textView3);

        mDataRef = new Firebase("https://unlimeted-house.firebaseio.com/orders/"+orderId);
        mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                inProgressData=dataSnapshot.child("data").getValue().toString();
                order_data.setText(inProgressData);
                String status =dataSnapshot.child("status").getValue(String.class);
                switch (status){
                    case "canceld" :{
                        process.setText("canceld");
                        proc_txt.setText("Your order was cancel");
                        inProgressData=dataSnapshot.child("canceldata").getValue().toString();
                        order_data.setText(inProgressData);

                        break;
                    }
                    case "rating" :{

                        linearLayout4 = (LinearLayout)findViewById(R.id.linearLayout4);
                        linearLayout4.setVisibility(View.VISIBLE);
                        inProgressData = dataSnapshot.child("finishingdata").getValue().toString();
                        textView6.setText(inProgressData);


                    }
                    case "finishing" :{
                        linearLayout2 = (LinearLayout)findViewById(R.id.linearLayout2);
                        linearLayout2.setVisibility(View.VISIBLE);
                        inProgressData = dataSnapshot.child("finishingdata").getValue().toString();
                        textView3.setText(inProgressData);



                    }
                    case "confirmation" :{
                        linearLayout3 = (LinearLayout)findViewById(R.id.linearLayout3);
                        linearLayout3.setVisibility(View.VISIBLE);
                        inProgressData=dataSnapshot.child("confirmationdata").getValue().toString();
                        textView.setText(inProgressData);




                    }

                    case "process" :{

                        break;
                    }






                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
