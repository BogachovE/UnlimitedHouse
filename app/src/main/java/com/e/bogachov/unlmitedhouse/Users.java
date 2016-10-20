package com.e.bogachov.unlmitedhouse;

/**
 * Created by Bogachov on 20.10.16.
 */

public class Users {
    String id;
    String phone;
    String isitshop;

    public  Users(){}



public Users(String id, String phone, String isitshop){
    this.id=id;
    this.phone=phone;
    this.isitshop=isitshop;
}

    public String getId(){return id;}
    public String getPhone(){return phone;}
    public String getIsitshop(){return isitshop;}


}
