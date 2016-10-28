package com.e.bogachov.unlmitedhouse;

/**
 * Created by Bogachov on 20.10.16.
 */

public class Users {
    String id;
    String phone;
    String isitshop;
    String shopcateg;
    String shopid;

    public  Users(){}



public Users(String id, String phone, String isitshop, String shopcateg, String shopid){
    this.id=id;
    this.phone=phone;
    this.isitshop=isitshop;
    this.shopcateg=shopcateg;
    this.shopid=shopid;
}

    public String getId(){return id;}
    public String getPhone(){return phone;}
    public String getIsitshop(){return isitshop;}
    public String getShopcateg(){return shopcateg;}
    public String getShopid(){return shopid;}


}
