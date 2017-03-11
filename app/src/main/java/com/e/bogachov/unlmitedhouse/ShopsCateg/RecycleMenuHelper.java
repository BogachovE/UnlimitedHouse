package com.e.bogachov.unlmitedhouse.ShopsCateg;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.e.bogachov.unlmitedhouse.R;
import com.google.android.gms.location.ActivityRecognition;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bogachov on 30.11.16.
 */

public class  RecycleMenuHelper extends Activity {
    String categ;
    List<Order> orders;

    public RecycleMenuHelper() {}


    public static List<Order> initializeData(){
        List<Order> orders;
        String customNum;
        orders = new ArrayList<>();

        Integer numZakaz= null;
        numZakaz = Hawk.get("numZakaz");
        customNum= Hawk.get("userphone").toString();
        Map<Integer,Map<String,String>> ordervc = new HashMap<Integer,Map<String,String>>();
        if (Hawk.get("order")!= null & numZakaz!=null){
            ordervc = Hawk.get("order");
            Map<String,String> order = new HashMap<String, String>();
            for (int i = 0; i<= numZakaz ;i++ ){
                order=ordervc.get(i);
                String pName =Hawk.get("productname");
                orders.add(new Order(order.get("count"),order.get("data"),order.get("fromShop"),order.get("currency"),order.get("p"),order.get("sum"),pName,order.get("descript"),"process",customNum,null,null,null,null,null,null,order.get("visiting"),order.get("fromcateg"),order.get("servicetype"),order.get("shopNotifIp")));
            }
        }
        ordervc.clear();

        Hawk.put("orders",orders);
        return orders;
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void setActionBar(Activity a){
        categ = Hawk.get("categ");

        a.getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        a.getActionBar().setDisplayHomeAsUpEnabled(true);
        a.getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        a.getActionBar().setHomeAsUpIndicator(R.drawable.btn_aaact);
        ActionBar actionBar = a.getActionBar();

        switch (categ) {
            case "beauty": {
                a.getActionBar().setCustomView(R.layout.abs_layout);
                Button basket_btn = (Button)a.findViewById(R.id.basket_btn);
                if ((orders!=null)&&(orders.size()!=0)) {
                    basket_btn.setBackgroundResource(R.drawable.basket_full);
                }
                break;
            }

            case "product": {
                a.getActionBar().setCustomView(R.layout.abs_product);
                Button basket_btn = (Button)a.findViewById(R.id.basket_btn);
                if ((orders!=null)&&(orders.size()!=0)) {
                    basket_btn.setBackgroundResource(R.drawable.basket_full_white);
                }


                break;
            }

            case "candy": {
                a.getActionBar().setCustomView(R.layout.abs_candy);
                Button basket_btn = (Button)a.findViewById(R.id.basket_btn);
                if ((orders!=null)&&(orders.size()!=0)) {
                    basket_btn.setBackgroundResource(R.drawable.basket_full_white);
                }


                break;
            }

            case "house": {
                a.getActionBar().setCustomView(R.layout.abs_house);
                Button basket_btn = (Button)a.findViewById(R.id.basket_btn);
                if ((orders!=null)&&(orders.size()!=0)) {
                    basket_btn.setBackgroundResource(R.drawable.basket_full);
                }


                break;
            }
            case "other": {
                a.getActionBar().setCustomView(R.layout.abs_other);
                Button basket_btn = (Button)a.findViewById(R.id.basket_btn);
                if ((orders!=null)&&(orders.size()!=0)) {
                    basket_btn.setBackgroundResource(R.drawable.basket_full);
                }


                break;
            }
        }

    }

}
