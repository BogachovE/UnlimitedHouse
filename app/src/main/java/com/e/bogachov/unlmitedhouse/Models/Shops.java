package com.e.bogachov.unlmitedhouse.Models;

/**
 * Created by fructus on 28.08.16.
 */
public class Shops {

    String name;
    String id;
    String category;
    String location;
    String locationlatitude;
    String locationlongitude;
    String shopNotifIp;


    public Shops() {
    }

    public Shops(String name, String id, String category, String location ,String locationlatitude,  String locationlongitude, String shopNotifIp) {
        this.name = name;
        this.id = id;
        this.category = category;
        this.location = location;
        this.locationlatitude = locationlatitude;
        this.locationlongitude = locationlongitude;
        this.shopNotifIp = shopNotifIp;

    }

    public String getName() {
        return name;
    }
    public String getId() {return id;}
    public String getCategory() {return category;}
    public String getLocation() {return location;}
    public String getlatitude(){return locationlatitude;}
    public String getlongitude(){return locationlongitude;}
    public String shopNotifIp(){return shopNotifIp;}

}
