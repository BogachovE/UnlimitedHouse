package com.e.bogachov.unlmitedhouse.Models;

import android.location.Location;

/**
 * Created by Bogachov on 20.10.16.
 */

public class Users {
    private String userid;
    private String phone;
    private String isitshop;
    private String shopcategory;
    private String shopid;
    private String locationlatitude;
    private String locationlongitude;
    private String userNotifIp;

    public  Users(){}



public Users(String userid, String phone, String isitshop, String shopcategory, String shopid,  String locationlatitude, String locationlongitude, String userNotifIp){
    this.userid=userid;
    this.phone=phone;
    this.isitshop=isitshop;
    this.shopcategory=shopcategory;
    this.locationlatitude=locationlatitude;
    this.locationlongitude=locationlongitude;
    this.userNotifIp=userNotifIp;
}

    public String getUserid(){return userid;}
    public String getPhone(){return phone;}
    public String getIsitshop(){return isitshop;}
    public String getShopcategory(){return shopcategory;}
    public String getShopid(){return shopid;}
    public String getLocationlatitude(){return  locationlatitude;}
    public String getLocationlongitude(){return locationlongitude;}
    public String getUserNotifIp(){return  userNotifIp;}


}
