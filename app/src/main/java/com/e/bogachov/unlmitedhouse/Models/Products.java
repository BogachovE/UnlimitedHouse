package com.e.bogachov.unlmitedhouse.Models;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bogachov on 10.11.16.
 */

public class Products {

    String name;
    Map<Integer,Map<String,String>> shops;

    public Products(){}

    public Products(String name,Map<Integer,Map<String,String>> shops){
        this.name = name;
        this.shops = shops;
    }

    public String getName(){return name;}
    public Map<Integer,Map<String,String>> getShops(){return shops;}

}
