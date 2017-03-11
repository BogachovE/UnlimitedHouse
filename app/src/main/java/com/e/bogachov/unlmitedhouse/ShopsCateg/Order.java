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
    String inprocessdata;
    String canceldata;
    String processdata;
    String  finishingdata;
    String confirmationdata;
    String visiting;
    String fromcateg;
    String servicetype;
    String shopNotifIp;

    public Order() {

    }


    public Order(String count, String data, String fromshop, String currency, String p, String sum, String name, String descript, String status, String customNum, String id,  String inprocessdata,
            String canceldata,
            String processdata,
            String  finishingdata,
            String confirmationdata, String visiting,String fromcateg,
                 String servicetype,String shopNotifIp) {
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
        this.inprocessdata= inprocessdata;
        this.canceldata = canceldata;
        this.processdata = processdata;
        this.finishingdata = finishingdata;
        this.confirmationdata = confirmationdata;
        this.visiting = visiting;
        this.fromcateg = fromcateg;
        this.servicetype = servicetype;
        this.shopNotifIp = shopNotifIp;

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

    public String getInprocessdata() {
        return inprocessdata;
    }
    public String getCanceldata() {return canceldata;}
    public String getProcessdata() {
        return processdata;
    }
    public String getFinishingdata() {
        return finishingdata;
    }
    public String getConfirmationdata() {
        return confirmationdata;
    }
    public String getVisiting(){return visiting;}
    public String getCustomNum(){return customNum;}
    public String getFromcateg(){return fromcateg;}
    public String getServicetype(){return servicetype;}
    public String getDescript(){return descript;}
    public String getShopNotifIp(){return shopNotifIp;}

}


