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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.R;
import com.e.bogachov.unlmitedhouse.ShopsCateg.ShopsMenu;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Dialog1 extends DialogFragment implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    Context context ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Add new shop");
        View v = inflater.inflate(R.layout.add_shop_diolog,null);
        v.findViewById(R.id.okbtn).setOnClickListener(this);
        v.findViewById(R.id.cancelbtn).setOnClickListener(this);
        EditText shop_name_etxt = (EditText)v.findViewById(R.id.shop_name_etxt);

        Hawk.init(getActivity()).build();




        context = getActivity();
        return v;
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.okbtn:
                final EditText shop_name_etxt = (EditText)getDialog().findViewById(R.id.shop_name_etxt);
                EditText shop_photo_etxt = (EditText)getDialog().findViewById(R.id.shop_photo_etxt);

                final String name = shop_name_etxt.getText().toString();
                final String photo = shop_photo_etxt.getText().toString();
                String categ = Hawk.get("categ");

                final Firebase ref = new Firebase("https://unhouse-143417.firebaseio.com/shops/category/"+categ);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long s = dataSnapshot.getChildrenCount();
                        final String ss = s.toString();

                        if(!photo.equals("")){
                            Map<String, String> addZeroServ = new HashMap<String, String>();
                            Map<String, String> shopName = new HashMap<String, String>();
                            shopName.put("name", name);
                            shopName.put("photourl", photo);
                            shopName.put("category", Hawk.get("categ").toString());
                            addZeroServ.put("name", "PLUS");
                            addZeroServ.put("photourl", "https://firebasestorage.googleapis.com/v0/b/unlimeted-house.appspot.com/o/PLUS.png?alt=media&token=470dc5a1-fca3-4bda-b9d0-a8590e73783a");
                            ref.child(ss).setValue(shopName);
                            ref.child(ss + "/servicetype/0/").setValue(addZeroServ);
                        }
                        else Toast.makeText(context,R.string.nullpic,Toast.LENGTH_SHORT).show();

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
