package com.e.bogachov.unlmitedhouse;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.ShopsCateg.Order;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.hawk.Hawk;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopInNumber extends Activity implements View.OnClickListener{
    ImageView back_btn;
    TextView num_ord,com_ord,canc_order,most_order,most_time;
    String fromshop;
    DatabaseReference orderRef;
    com.google.firebase.database.Query orderCountQ, compOrderQ, canc_orderQ,most_orderQ;
    Order order;
    Date startData,finishData;
    Integer cancel=0,complite= 0;
    List<String> orders;
    List<Integer> orderstime;
    Integer i;
    Integer j;
    double m;
    double n;
    Integer p;
    Integer q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_in_number);

        Hawk.init(this).build();
        fromshop = Hawk.get("fromshop");

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        back_btn = (ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);
        num_ord = (TextView)findViewById(R.id.num_ord);
        com_ord = (TextView)findViewById(R.id.com_ord);
        canc_order = (TextView)findViewById(R.id.canc_order);
        most_order = (TextView)findViewById(R.id.most_order);
        most_time = (TextView)findViewById(R.id.most_time);

        Firebase.setAndroidContext(this);

        q=0;


        orderRef = FirebaseDatabase.getInstance().getReference();
        orderCountQ = orderRef.child("orders").orderByChild("fromshop").equalTo(fromshop);
        orderCountQ.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                       @Override
                                                       public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                           Long count = dataSnapshot.getChildrenCount();
                                                           num_ord.setText(count.toString());
                                                           orders= new ArrayList<>();
                                                           orderstime= new ArrayList<Integer>();
                                                           String finishDataString,confDataString;

                                                           for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                                                               //Getting the data from snapshot
                                                               order = postSnapshot.getValue(Order.class);
                                                               orders.add(order.getName());
                                                               //dataString = dataSnapshot.child("data").getValue(Order.class);

                                                               if (order.getStatus().equals("finishing")) {
                                                                   complite = complite + 1;

                                                                   SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");

                                                                   try {
                                                                       finishDataString = order.getFinishingdata();
                                                                       confDataString = order.getConfirmationdata();
                                                                       finishData = formatter.parse(finishDataString);
                                                                       startData = formatter.parse(confDataString);

                                                                   } catch (ParseException e) {
                                                                       finishData = new Date();
                                                                       startData = new Date();
                                                                       e.printStackTrace();
                                                                   }
                                                                   Integer fm = finishData.getMinutes() + finishData.getHours()*60;
                                                                   Integer sm = startData.getMinutes() + startData.getHours()*60;
                                                                   orderstime.add(fm-sm);


                                                               }
                                                               if (order.getStatus().equals("canceld")){
                                                                   cancel = cancel + 1;
                                                               }

                                                           }

                                                           m=1;
                                                           p=1;

                                                           if(orders!=null){
                                                               for(i=0;i<=orders.size()-1;i++){
                                                                   n=0;
                                                                   for(j=0;j<=orders.size()-1;j++){
                                                                       if(orders.get(i).equals(orders.get(j))){
                                                                           n=n+1;
                                                                       }
                                                                   }
                                                                   if (n>m){
                                                                       m=n;
                                                                       p=i;
                                                                   }
                                                               }
                                                               most_order.setText(orders.get(p));


                                                           }

                                                           m=0.0;
                                                           p=1;
                                                           n=0.0;

                                                           if(orderstime!=null){
                                                               for(i=0;i<=orderstime.size()-1;i++){
                                                                   for(j=0;j<=orderstime.size()-1;j++){
                                                                       if(orderstime.get(i)>=(orderstime.get(j))){
                                                                           n=orderstime.get(i);
                                                                       }
                                                                   }
                                                                   if (n>m){
                                                                       m=n;

                                                                   }
                                                               }
                                                               Double res =m/60.0;
                                                               most_time.setText(res.toString());


                                                           }


                                                           if (complite > 0) {
                                                               com_ord.setText(complite.toString());
                                                           } else com_ord.setText("0");

                                                           if (cancel > 0) {
                                                               canc_order.setText(cancel.toString());
                                                           } else canc_order.setText("0");



                                                       }

                                                       @Override
                                                       public void onCancelled(DatabaseError databaseError) {

                                                       }
                                                   });


        /*compOrderQ = orderCountQ.startAt("finishing").endAt("finishing");
        compOrderQ.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long count = dataSnapshot.getChildrenCount();
                com_ord.setText(count.toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/
        /*canc_orderQ = orderCountQ.orderByChild("status").equalTo("canceld");
        canc_orderQ.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long count = dataSnapshot.getChildrenCount();
                canc_order.setText(count.toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/







    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:{
                finish();
                break;
            }
        }

    }
}
