package com.e.bogachov.unlmitedhouse;

import android.widget.TextView;

import java.util.Date;

/**
 * Created by livingroomadmin on 31.08.16.
 */
public class Order {

    String orderNumb;
    String fromShop;
    String orderData;

    public Order(String orderNumb,String fromShop,String orderData){

        this.orderNumb = orderNumb;
        this.fromShop = fromShop;
        this.orderData = orderData;
    }
}
