package com.e.bogachov.unlmitedhouse.Dialogs;


import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;

public class Dialog3 extends DialogFragment implements OnClickListener {

    final String LOG_TAG = "myLogs";
    Context context ;
    String mShops;
    String mService;
    String name;
    String photo;
    String description;
    String price;
    String currency;
    String categ;
    Firebase productRef;
    Firebase requestRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Add new product");
        View v = inflater.inflate(R.layout.add_shop_diolog3,null);
        v.findViewById(R.id.okbtn).setOnClickListener(this);
        v.findViewById(R.id.cancelbtn).setOnClickListener(this);
        EditText shop_name_etxt = (EditText)v.findViewById(R.id.shop_name_etxt);

        Hawk.init(getActivity()).build();
        mShops =Hawk.get("shopid");
        mService = Hawk.get("serviceid");

        requestRef = new Firebase("https://unhouse-143417.firebaseio.com/productrequest");

        context = getActivity();
        return v;
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.okbtn:
                final EditText shop_name_etxt = (EditText)getDialog().findViewById(R.id.shop_name_etxt);
                EditText shop_photo_etxt = (EditText)getDialog().findViewById(R.id.shop_photo_etxt);
                EditText shop_description_etxt = (EditText)getDialog().findViewById(R.id.shop_description_etxt);
                EditText shop_price_etxt = (EditText)getDialog().findViewById(R.id.shop_price_etxt);
                EditText shop_currency_etxt = (EditText)getDialog().findViewById(R.id.shop_currency_etxt);

                name = shop_name_etxt.getText().toString();
                photo = shop_photo_etxt.getText().toString();
                description = shop_description_etxt.getText().toString();
                price = shop_price_etxt.getText().toString();
                currency = shop_currency_etxt.getText().toString();

                String categ = Hawk.get("categ");

                Firebase.setAndroidContext(getActivity());
                final Firebase ref = new Firebase("https://unhouse-143417.firebaseio.com/shops/category/"+categ+"/"+ mShops +"/servicetype/"+mService.toString()+"/products");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        addProduct(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                break;

            case R.id.cancelbtn:
                dismiss();
                break;
        }
        dismiss();
    }



    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }

    public void addProduct(DataSnapshot dataSnapshot) {
        productRef = new Firebase("https://unhouse-143417.firebaseio.com/shops/category/"+categ+"/"+ mShops +"/servicetype/"+ mService +"/products");
        Long s = dataSnapshot.getChildrenCount();
        String ss = s.toString();
        if(!photo.equals("")){
            Map<String,String> addPrice = new HashMap<String,String>();
            Map<String,String> shopName = new HashMap<String, String>();
            shopName.put("name",name);
            shopName.put("photourl",photo);
            shopName.put("description",description);
            addPrice.put("p",price);
            addPrice.put("currency",currency);
            productRef.child(ss).setValue(shopName);
            productRef.child(ss+"/price").setValue(addPrice);

            Map<String, String> addProductRequest = new HashMap<String, String>();
            addProductRequest.put("shopId",mShops);
            addProductRequest.put("name",name);
            addProductRequest.put("p",price);
            addProductRequest.put("currency",currency);
            requestRef.setValue(addProductRequest);
        }
        else Toast.makeText(context,R.string.nullpic,Toast.LENGTH_SHORT).show();

    }

}
