package com.e.bogachov.unlmitedhouse;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.RectF;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.e.bogachov.unlmitedhouse.Models.Products;
import com.e.bogachov.unlmitedhouse.Models.Shops;
import com.e.bogachov.unlmitedhouse.ShopsCateg.Order;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.hawk.Hawk;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.TimeZone;

import static android.R.color.holo_red_light;

public class MySchedule extends Activity implements WeekView.EventClickListener,  WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener,WeekView.EmptyViewClickListener, MonthLoader.MonthChangeListener {

    LinearLayout view;
    TextView tvCount;
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
    private ArrayList<WeekViewEvent> mNewEvents =new ArrayList<WeekViewEvent>();
    private ArrayList<WeekViewEvent> mNewEEvents = new ArrayList<WeekViewEvent>();

    String isItShop;
    final int DIALOG = 1;
    final Random random = new Random();
    Date mDate;
    List<Products> mProducts;
    String myShopName;
    String dataString;
    String mycustomNum;
    Order order;
    Date bDate;
    List<Date> lDate;
    List<Date>lBusy;
    String busyTime;
    String mLong;
    List<String> lEventName;
    List<String>lOrderId;
    List<String> lOrderStatus;
    Query mQuery;
    private List<Order> orders;
    final int DIALOG_EXIT = 2;
    String mID;
    String categ,shopid;
    Shops mOneProsuct;
    String test;
    ArrayList<Location> allShopsLocation;
    ArrayList<String> allShopsName;
    ArrayList<Float> allDistance;
    ArrayList<String> allShopsNotifIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_schedule);
        Hawk.init(this).build();
        myShopName = Hawk.get("fromshop");
        mycustomNum = Hawk.get("phone");
        categ=Hawk.get("categ");
        shopid=Hawk.get("shopid");
        Firebase.setAndroidContext(this);

        allShopsName = new ArrayList<>();
        allShopsLocation = new ArrayList<>();
        allDistance = new ArrayList<>();
        allShopsNotifIp= new ArrayList<>();






        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);
     // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);
// The week view has infinite scrolling horizontally. We have to provide the events of a
// month every time the month changes on the week vie
        mWeekView.setMonthChangeListener(this);
// Set long press listener for events.
        mWeekView.setEventLongPressListener(this);
        mWeekView.setEmptyViewClickListener(this);
        //ActionBar actionBar = getActionBar();
        //actionBar.hide();

        setupDateTimeInterpreter(false);


        isItShop = Hawk.get("isitshop");


        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        if(isItShop.equals("true")){
            mQuery = mRef.child("orders").orderByChild("fromshop").equalTo(myShopName);}
        else if(isItShop.equals("false")){
            mQuery = mRef.child("orders").orderByChild("customNum").equalTo(mycustomNum);
        }
        Firebase busyRef = new Firebase("https://unhouse-143417.firebaseio.com/shops/category/"+categ+"/"+shopid+"/");
        busyRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                busyTime=dataSnapshot.child("busy/time").getValue(String.class);
                mLong=dataSnapshot.child("busy/long").getValue(String.class);

                SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                if(busyTime!=null) {
                    try {
                        bDate = formatter.parse(busyTime);

                    } catch (ParseException e) {
                        bDate = new Date();
                        e.printStackTrace();
                    }
                }




            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                 lDate = new ArrayList<Date>();
                 lEventName = new ArrayList<String>();
                 lOrderId = new ArrayList<String>();
                 lOrderStatus = new ArrayList<String>();



                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Getting the data from snapshot
                    order = postSnapshot.getValue(Order.class);
                    //dataString = dataSnapshot.child("data").getValue(Order.class);

                    try {
                        dataString = order.getData();
                        mDate = formatter.parse(dataString);

                    } catch (ParseException e) {
                        mDate = new Date();
                        e.printStackTrace();
                    }

                    lEventName.add(order.getName());
                    lDate.add(mDate);
                    lOrderId.add(order.getId());
                    lOrderStatus.add(order.getStatus());
                }
                mWeekView.notifyDatasetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });







    }
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sch_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

        if(isItShop.equals("true")&event.getName().equals("Busy")){
            mNewEvents.remove(event);
            lBusy=null;
            bDate=null;
            final Firebase ref = new Firebase("https://unhouse-143417.firebaseio.com/shops/category/"+categ+"/"+shopid+"/");

            com.firebase.client.Query refQ = ref.child("busy").orderByChild("time").equalTo(event.getStartTime().getTime().toString());
            refQ.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    ref.child(dataSnapshot.getKey()).removeValue();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


            mWeekView.notifyDatasetChanged();
        }
        if(isItShop.equals("true")&event.getColor()==-14186012){
            Toast.makeText(this, "Clicked not busy", Toast.LENGTH_SHORT).show();
            String mmID = event.getName();
            mID =mmID.substring(mmID.indexOf(" ")+1,mmID.indexOf(" ",4));
            showDialog(DIALOG_EXIT);





        }
        if(isItShop.equals("false")&!event.getName().equals("Busy")){
            String mmID = event.getName();
            mID =mmID.substring(mmID.indexOf(" ")+1,mmID.indexOf(" ",4));
            Hawk.put("orderid",mID);
            Intent goToOrderStatus = new Intent(getApplicationContext(),OrderStatus.class);
            startActivity(goToOrderStatus);

        }


    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }









    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, final int newMonth) {
        // Populate the week view with some events.



        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();



        if(lDate!=null) {
            for (int i = 0; i < lDate.size(); i++) {


                Calendar startTime = Calendar.getInstance();
                startTime.setTime(lDate.get(i));
                startTime.set(Calendar.MONTH, newMonth - 1);
                Calendar endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, lDate.get(i).getHours() + 1);
                endTime.set(Calendar.MINUTE, 30);
                endTime.set(Calendar.MONTH, newMonth - 1);
                WeekViewEvent event = new WeekViewEvent(random.nextInt(),"ID "+ lOrderId.get(i)+" "+lEventName.get(i), startTime, endTime);
                switch (lOrderStatus.get(i)){
                    case "process":{
                        event.setColor(getResources().getColor(R.color.accent));
                        break;
                    }
                    case "confirmation":{
                        event.setColor(getResources().getColor(R.color.event_color_03));
                    }
                    case "canceld":{
                        event.setColor(getResources().getColor(R.color.event_color_02));
                    }
                }

                events.add(event);
            }



        }
        if(bDate!=null){

                Calendar startTime = Calendar.getInstance();
                startTime.setTime(bDate);
                startTime.set(Calendar.MONTH, newMonth - 1);
                Calendar endTime = (Calendar) startTime.clone();
                endTime.add(Calendar.HOUR, Integer.parseInt(mLong));
            WeekViewEvent busyevent = new WeekViewEvent(random.nextInt(),"Busy",startTime,endTime);
            busyevent.setColor(getResources().getColor(R.color.colorAccent));
            events.add(busyevent);
            }

        ArrayList<WeekViewEvent> newEvents = getNewEvents(newYear, newMonth);
        events.addAll(newEvents);


        return events;
    }

    @Override
    public void onEmptyViewClicked(final Calendar time) {
        if(isItShop.equals("true")) {
            Hawk.put("ttime",time);
            showDialog(DIALOG);

        }
        else if(isItShop.equals("false")&!categ.equals("house")){
            orders=Hawk.get("orders");
            final Firebase mRef = new Firebase("https://unhouse-143417.firebaseio.com/orders");
            mRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    Integer numZakaz= null;
                    Date d = time.getTime();
                    numZakaz = Hawk.get("numZakaz");
                    Long s = dataSnapshot.getChildrenCount()+1;
                    if (Hawk.get("order")!= null) {
                        for (int i = 0; i <= numZakaz; i++) {
                            s=s+1;
                            String ss = s.toString();
                            mRef.child(ss).setValue(orders.get(i));
                            mRef.child(ss+"/id").setValue(ss);
                            mRef.child(ss+"/data").setValue(d.toString());
                            mRef.child(ss+"/customNum").setValue(orders.get(i).getCustomNum());

                            String userNotifIp = Hawk.get("userNotifIp");
                            String shopNotifIp = Hawk.get("shopNotifIp");
                            Notification notif = new Notification();
                            notif.sendNotif("include_player_ids",shopNotifIp,"shop yoba boba");
                            notif.sendNotif("include_player_ids",userNotifIp,"user yoba boba");


                        }
                    }
                    Hawk.delete("order");
                    Hawk.delete("numZakaz");
                    mWeekView.notifyDatasetChanged();
                    onMonthChange(2016, new Date().getMonth());
                    Toast.makeText(getApplicationContext(), "Order added" , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }else if(isItShop.equals("false")&categ.equals("house")){   //If hose category, Find nearly shop

            String productname = Hawk.get("productname");

            DatabaseReference nearlyRef = FirebaseDatabase.getInstance().getReference();
            Query nearlyQuery = nearlyRef.child("products").orderByChild("name").equalTo("Papirony");




            nearlyQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i=0;
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        dataSnapshot = postSnapshot.child("shops");
                        for (DataSnapshot postSnapshot1 : dataSnapshot.getChildren()) {
                            mOneProsuct = postSnapshot1.getValue(Shops.class);

                            String latitude = postSnapshot1.child("locationlatitude").getValue(String.class);
                            String longitude = postSnapshot1.child("locationlongitude").getValue(String.class);

                            Location shopLoc = new Location("asd");
                            shopLoc.setLatitude(Double.parseDouble(latitude));
                            shopLoc.setLongitude(Double.parseDouble(longitude));
                            allShopsLocation.add(i,shopLoc);
                            allShopsName.add(i,postSnapshot1.child("name").getValue(String.class));
                            allShopsNotifIp.add(i,postSnapshot1.child("shopNotifIp").getValue(String.class));
                            i++;
                        }

                        Location userLoc = Hawk.get("userLoc");
                        for(int j=0;j<allShopsLocation.size();j++){
                            allDistance.add(j,userLoc.distanceTo(allShopsLocation.get(j)));
                        }
                        int n=0;
                        for (int  k = 0; k < allDistance.size(); k++) {
                            if (allDistance.get(n)<allDistance.get(k)) n=k;
                        }

                         orders=Hawk.get("orders");
                        final Firebase mRef = new Firebase("https://unhouse-143417.firebaseio.com/");
                        final int finalN = n;
                        mRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
                            @Override
                            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                Integer numZakaz= null;
                                Date d = time.getTime();
                                numZakaz = Hawk.get("numZakaz");
                                Long s = dataSnapshot.getChildrenCount()+1;
                                if (Hawk.get("order")!= null) {
                                    for (int i = 0; i <= numZakaz; i++) {
                                        s=s+1;
                                        String ss = s.toString();
                                        mRef.child(ss).setValue(orders.get(i));
                                        mRef.child("/orders/"+ss+"/id").setValue(ss);
                                        mRef.child("/orders/"+ss+"/data").setValue(d.toString());
                                        mRef.child("/orders/"+ss+"/customNum").setValue(orders.get(i).getCustomNum());
                                        mRef.child("/orders/"+ss+"/fromshop").setValue(allShopsName.get(finalN));
                                        Hawk.put("shopNotifIp",allShopsNotifIp.get(finalN));

                                        String userNotifIp = Hawk.get("userNotifIp");
                                        String shopNotifIp = Hawk.get("shopNotifIp");
                                        Notification notif = new Notification();
                                        notif.sendNotif("include_player_ids",shopNotifIp,"shop yoba boba");
                                        notif.sendNotif("include_player_ids",userNotifIp,"user yoba boba");

                                    }
                                }
                                Hawk.delete("order");
                                Hawk.delete("numZakaz");
                                mWeekView.notifyDatasetChanged();
                                onMonthChange(2016, new Date().getMonth());
                                Toast.makeText(getApplicationContext(), "Order added" , Toast.LENGTH_SHORT).show();



                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }

                        });

                    }






                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });

        }


    }

    private ArrayList<WeekViewEvent> getNewEvents(int year, int month) {

        // Get the starting point and ending point of the given month. We need this to find the
        // events of the given month.
        Calendar startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.YEAR, year);
        startOfMonth.set(Calendar.MONTH, month - 1);
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        startOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        startOfMonth.set(Calendar.MINUTE, 0);
        startOfMonth.set(Calendar.SECOND, 0);
        startOfMonth.set(Calendar.MILLISECOND, 0);
        Calendar endOfMonth = (Calendar) startOfMonth.clone();
        endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getMaximum(Calendar.DAY_OF_MONTH));
        endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
        endOfMonth.set(Calendar.MINUTE, 59);
        endOfMonth.set(Calendar.SECOND, 59);

        // Find the events that were added by tapping on empty view and that occurs in the given
        // time frame.
        ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : mNewEvents) {
            if (event.getEndTime().getTimeInMillis() > startOfMonth.getTimeInMillis() &&
                    event.getStartTime().getTimeInMillis() < endOfMonth.getTimeInMillis()) {
                events.add(event);
            }
        }
        return events;
    }



    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (id == DIALOG) {

        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
       if(id==DIALOG) {
           final AlertDialog.Builder builder = new AlertDialog.Builder(this);
           builder.setTitle("How long you will busy");
           // создаем view из dialog.xml
           view = (LinearLayout) getLayoutInflater()
                   .inflate(R.layout.dialog, null);
           // устанавливаем ее, как содержимое тела диалога
           builder.setView(view);
           // находим TexView для отображения кол-ва
           tvCount = (TextView) view.findViewById(R.id.tvCount);
           Button sendbtn = (Button) view.findViewById(R.id.sendbtn);
           final AlertDialog adb =builder.show();
           sendbtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   switch (v.getId()) {
                       case R.id.sendbtn: {
                           final Calendar ttime;
                           ttime = Hawk.get("ttime");
                           if (isItShop.equals("true")) {
                               Calendar endTime = (Calendar) ttime.clone();
                               endTime.add(Calendar.HOUR, Integer.parseInt(tvCount.getText().toString()));


                               Firebase ref = new Firebase("https://unhouse-143417.firebaseio.com/shops/category/"+categ+"/"+shopid+"/");
                               ref.child("busy/time").setValue(ttime.getTime().toString());
                               ref.child("busy/long").setValue(tvCount.getText().toString());
                               // Create a new event.
                               WeekViewEvent event = new WeekViewEvent(random.nextInt(), "Busy", ttime, endTime);
                               event.setColor(getResources().getColor(R.color.colorAccent));
                               mNewEvents.add(event);

                               // Refresh the week view. onMonthChange will be called again.
                               mWeekView.notifyDatasetChanged();
                               adb.cancel();
                           }


                       }
                   }
               }
           });
           return null;
       }
        if(id==DIALOG_EXIT){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            // заголовок
         //   adb.setTitle(R.string.exit);
            // сообщение
            //adb.setMessage(R.string.save_data);
            // иконка
           // adb.setIcon(android.R.drawable.ic_dialog_info);
            // кнопка положительного ответа
            adb.setPositiveButton("Accept Order", myClickListener);
            // кнопка отрицательного ответа
            adb.setNegativeButton("Reject Order", myClickListener);
            // кнопка нейтрального ответа
            //adb.setNeutralButton(R.string.cancel, myClickListener);
            // создаем диалог
            return adb.create();
        }
        return super.onCreateDialog(id);
    }
    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                // положительная кнопка
                case Dialog.BUTTON_POSITIVE:
                    Firebase acceptRef = new Firebase("https://unhouse-143417.firebaseio.com/orders/"+mID);
                    acceptRef.child("status").setValue("confirmation");
                    acceptRef.child("confirmationdata").setValue(new Date().toString());
                    Toast.makeText(getApplicationContext(), mID, Toast.LENGTH_SHORT).show();

                   // finish();
                    break;
                // негативная кнопка
                case Dialog.BUTTON_NEGATIVE:
                     acceptRef = new Firebase("https://unhouse-143417.firebaseio.com/orders/"+mID);
                    acceptRef.child("status").setValue("canceld");
                    acceptRef.child("canceldata").setValue(new Date().toString());
                    Toast.makeText(getApplicationContext(), mID, Toast.LENGTH_SHORT).show();
                    //finish();
                    break;
                // нейтральная кнопка
               // case Dialog.BUTTON_NEUTRAL:
                 //   break;
            }



        }
    };
}
