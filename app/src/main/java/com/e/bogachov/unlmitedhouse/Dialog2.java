package com.e.bogachov.unlmitedhouse;


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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;

public class Dialog2 extends DialogFragment implements OnClickListener {

    final String LOG_TAG = "myLogs";
    Context context ;
    String mShops;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Add new servicetype");
        View v = inflater.inflate(R.layout.add_shop_diolog,null);
        v.findViewById(R.id.okbtn).setOnClickListener(this);
        v.findViewById(R.id.cancelbtn).setOnClickListener(this);
        EditText shop_name_etxt = (EditText)v.findViewById(R.id.shop_name_etxt);

        Hawk.init(getActivity()).build();
        mShops =Hawk.get("shopid");




        context = getActivity();
        return v;
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.okbtn:
                EditText shop_name_etxt = (EditText)getDialog().findViewById(R.id.shop_name_etxt);
                EditText shop_photo_etxt = (EditText)getDialog().findViewById(R.id.shop_photo_etxt);

                final String name = shop_name_etxt.getText().toString();
                final String photo = shop_photo_etxt.getText().toString();
                String categ = Hawk.get("categ");

                final Firebase ref = new Firebase("https://unlimeted-house.firebaseio.com/shops/category/"+categ+"/"+mShops.toString()+"/servicetype");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long s = dataSnapshot.getChildrenCount();
                        final String ss = s.toString();
                        Map<String,String> addZeroServ = new HashMap<String,String>();
                        Map<String,String> shopName = new HashMap<String, String>();
                        shopName.put("name",name);
                        shopName.put("photourl",photo);

                        addZeroServ.put("name","PLUS");
                        addZeroServ.put("photourl","http://fs156.www.ex.ua/show/697962838068/276256660/276256660.png");
                        ref.child(ss).setValue(shopName);
                        ref.child(ss+"/products/0/").setValue(addZeroServ);

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


}
