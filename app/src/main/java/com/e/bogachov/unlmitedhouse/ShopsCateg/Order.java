package com.e.bogachov.unlmitedhouse.ShopsCateg;

/**
 * Created by Bogachov on 01.10.16.
 */

public class Order {

    String count;
    String data;
    String fromshop;
    String currency;
    String p;
    String sum;
    String name;
    String descript;
    String status;
    String customNum;
    String id;

    public Order() {

    }


    public Order(String count, String data, String fromshop, String currency, String p, String sum, String name, String descript, String status, String customNum, String id) {
        this.count = count;
        this.name = name;
        this.data = data;
        this.fromshop = fromshop;
        this.currency = currency;
        this.p = p;
        this.sum = sum;
        this.descript = descript;
        this.status = status;
        this.customNum = customNum;
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public String getFromshop() {
        return fromshop;
    }

    public String getCurrency() {
        return currency;
    }

    public String getP() {
        return p;
    }

    public String getSum() {
        return sum;
    }
    public String getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }

}


