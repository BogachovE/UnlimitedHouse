package com.e.bogachov.unlmitedhouse;

import android.widget.TextView;

import java.util.Date;

/**
 * Created by livingroomadmin on 31.08.16.
 */
public class Product {
    String name;
    int count;
    String orderNumb;
    String fromShop;
    String orderData;

    public Product(String name, int count,String orderNumb,String fromShop,String orderData){
        this.name = name;
        this.count = count;
        this.orderNumb = orderNumb;
        this.fromShop = fromShop;
        this.orderData = orderData;
    }
}
