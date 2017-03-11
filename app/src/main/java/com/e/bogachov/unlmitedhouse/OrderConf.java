package com.e.bogachov.unlmitedhouse;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.ShopsCateg.Order;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.orhanobut.hawk.Hawk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Bogachov on 31.10.16.
 */

public class OrderConf extends Activity implements View.OnClickListener {

    TextView order_numb,categ_view,shop_name_view,order_type_view,qus_view,desc_view,data_view,time_view,title,order_numb_t,categ_view_t,details_t,data_t,time_t,text_t,main_btn_t,orders_btn_t;
    Button main_btn,orders_btn;
    String userid;
    String orderid;
    Typeface book;
    Order order;
    Firebase mRef;
    String sDate;
    Date date;
    Chronometer mChronometer;
    private Timer mTimer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_conf);

        //Work with action bar
        ActionBar actionBar = getActionBar();
        actionBar.hide();


        //FindById and Click listners
        order_numb = (TextView)findViewById(R.id.order_numb);
        categ_view = (TextView)findViewById(R.id.categ_view);
        shop_name_view = (TextView)findViewById(R.id.shop_name_view);
        order_type_view = (TextView)findViewById(R.id.order_type_view);
        qus_view = (TextView)findViewById(R.id.qus_view);
        desc_view = (TextView)findViewById(R.id.desc_view);
        data_view = (TextView)findViewById(R.id.data_view);
        time_view = (TextView)findViewById(R.id.time_view);
        title = (TextView)findViewById(R.id.title);
        order_numb_t = (TextView)findViewById(R.id.order_numb_t);
        categ_view_t = (TextView)findViewById(R.id.categ_view_t);
        details_t = (TextView)findViewById(R.id.details_t);
        data_t = (TextView)findViewById(R.id.data_t);
        time_t = (TextView)findViewById(R.id.time_t);
        text_t = (TextView)findViewById(R.id.text_t);
        main_btn_t = (TextView)findViewById(R.id.main_btn_t);
        orders_btn_t = (TextView)findViewById(R.id.orders_btn_t);
        mChronometer = (Chronometer) findViewById(R.id.chronometer);


        main_btn = (Button)findViewById(R.id.main_btn);
        orders_btn = (Button)findViewById(R.id.orders_btn);

        main_btn.setOnClickListener(this);
        orders_btn.setOnClickListener(this);

        //Hawk
        Hawk.init(this).build();
        orderid = Hawk.get("orderid");

        //Firebase ref and contect
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://unhouse-143417.firebaseio.com/orders/"+orderid+"/");


        //Fonts
        book = Typeface.createFromAsset(getAssets(), "fonts/roboto.ttf");
        order_numb.setTypeface(book);
        categ_view.setTypeface(book);
        shop_name_view.setTypeface(book);
        order_type_view.setTypeface(book);
        qus_view.setTypeface(book);
        desc_view.setTypeface(book);
        data_view.setTypeface(book);
        time_view.setTypeface(book);
        title.setTypeface(book);
        categ_view_t.setTypeface(book);
        order_numb_t.setTypeface(book);
        details_t.setTypeface(book);
        data_t.setTypeface(book);
        time_t.setTypeface(book);
        text_t.setTypeface(book);
        main_btn_t.setTypeface(book);
        orders_btn_t.setTypeface(book);


        //Parsing oreder and set Order information
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //getOrder
                order = dataSnapshot.getValue(Order.class);

                //get date on Date
                sDate = order.getData();
                SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                SimpleDateFormat fomaterDate = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat fomaterTime = new SimpleDateFormat("hh:mm");
                SimpleDateFormat fornaterMinutes = new SimpleDateFormat("mm");

                if (sDate != null) {
                    try {
                        date = formatter.parse(sDate);

                    } catch (ParseException e) {
                        date = new Date();
                        e.printStackTrace();
                    }
                }


                order_numb.setText(orderid);
                categ_view.setText(order.getFromcateg());
                shop_name_view.setText(" " + order.getFromshop());
                order_type_view.setText(order.getServicetype());
                qus_view.setText(order.getCount());
                desc_view.setText(order.getDescript());
                data_view.setText(fomaterDate.format(date));
                time_view.setText(fomaterTime.format(date));




                final Timer myTimer = new Timer(); // Создаем таймер
                final Handler uiHandler = new Handler();
                myTimer.schedule(new TimerTask() { // Определяем задачу
                    @Override
                    public void run() {
                        Date newDate = new Date();
                        long diff = newDate.getTime() - date.getTime();
                        //String
                        final long min = diff / 60000;

                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(min>5){
                                    text_t.setText("We are sorry");
                                }
                                switch((int) min){
                                    case 0: {
                                        text_t.setText(R.string.order_conf_txt_5);

                                        break;
                                    }
                                    case 1: {
                                        text_t.setText(R.string.order_conf_txt_4);

                                        break;
                                    }
                                    case 2: {
                                        text_t.setText(R.string.order_conf_txt_3);
                                        String userNotifIp = Hawk.get("userNotifIp");
                                        Notification notif = new Notification();
                                        notif.sendNotif("include_player_ids",userNotifIp,"Your order was accept");

                                        break;
                                    }
                                    case 3: {
                                        text_t.setText(R.string.order_conf_txt_2);

                                        break;
                                    }
                                    case 4: {
                                        text_t.setText(R.string.order_conf_txt_1);

                                        break;
                                    }
                                    case 5: {
                                        text_t.setText(R.string.order_conf_txt_0);
                                        myTimer.cancel();

                                        break;
                                    }





                                }
                            }
                        });
                    };
                }, 0L, 6L * 1000); // интервал - 60000 миллисекунд, 0 миллисекунд до первого запуска.


            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });










    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.main_btn:{

                Intent goToMain = new Intent(this,MainMenu.class);
                startActivity(goToMain);
                break;
            }
            case R.id.orders_btn:{

                Intent goToOrders = new Intent(this,ShopOrders.class);
                startActivity(goToOrders);

                break;
            }



        }

    }

}
