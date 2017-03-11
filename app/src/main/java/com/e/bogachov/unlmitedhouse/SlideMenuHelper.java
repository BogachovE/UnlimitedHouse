package com.e.bogachov.unlmitedhouse;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.bogachov.unlmitedhouse.ShopsCateg.Order;
import com.e.bogachov.unlmitedhouse.ShopsCateg.RecycleMenuHelper;
import com.e.bogachov.unlmitedhouse.ShopsCateg.RightOrderAdapter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

/**
 * Created by Bogachov on 08.12.16.
 */

public class SlideMenuHelper {
    private Animation mFadeInAnimation;
    private Animation mFadeOutAnimation;
    private ImageView shadow;

    public SlideMenuHelper() {}

    public void setRightSlideMenu(final Activity activity,List<Order> orders) {
        TextView textView7;
        RecyclerView rv2;
        final Button basket_btn;
        LinearLayoutManager llm;
        RightOrderAdapter adapter;
        final SlidingMenu menu2;

        mFadeInAnimation = AnimationUtils.loadAnimation(activity, R.anim.fadein);
        mFadeOutAnimation = AnimationUtils.loadAnimation(activity, R.anim.fadeout);

        menu2 = new SlidingMenu(activity);
        llm = new LinearLayoutManager(activity);
        adapter = new RightOrderAdapter(orders);


        menu2.setMode(SlidingMenu.RIGHT);
        menu2.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu2.setFadeDegree(0.35f);
        menu2.attachToActivity(activity, SlidingMenu.SLIDING_CONTENT);
        menu2.setMenu(R.layout.sidemenu_right);
        menu2.setBehindWidthRes(R.dimen.slidingmenu_behind_width);
        menu2.toggle(true);

        rv2 = (RecyclerView)activity.findViewById(R.id.rv2);
        textView7 = (TextView)activity.findViewById(R.id.textView7);
        textView7.setOnClickListener((View.OnClickListener) activity);
        basket_btn = (Button) activity.findViewById(R.id.basket_btn);
        shadow = (ImageView) activity.findViewById(R.id.shadow) ;

        shadow.setVisibility(View.VISIBLE);
        shadow.startAnimation(mFadeInAnimation);

        menu2.setOnCloseListener(new SlidingMenu.OnCloseListener() {
            @Override
            public void onClose() {
                mFadeInAnimation = AnimationUtils.loadAnimation(activity, R.anim.fadein);
                mFadeOutAnimation = AnimationUtils.loadAnimation(activity, R.anim.fadeout);
                shadow = (ImageView)activity.findViewById(R.id.shadow) ;
                shadow.startAnimation(mFadeOutAnimation);
                shadow.setVisibility(View.INVISIBLE);
                basket_btn.setClickable(true);

            }
        });

        RecycleMenuHelper.initializeData();
        rv2.setLayoutManager(llm);
        rv2.setAdapter(adapter);
    }

    public void setLeftSlideMenu(final Activity activity) {
        SlidingMenu menu = new SlidingMenu(activity);

        mFadeInAnimation = AnimationUtils.loadAnimation(activity, R.anim.fadein);
        mFadeOutAnimation = AnimationUtils.loadAnimation(activity, R.anim.fadeout);

        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(activity, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sidemenu);
        menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);
        menu.toggle(true);

        //Find View Slide Bar
        TextView profilebtn = ((TextView) activity.findViewById(R.id.profilebtn));
        profilebtn.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/BebasNeueBook.otf"));
        TextView ordersbtn = (TextView) activity.findViewById(R.id.ordersbtn);
        ordersbtn.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/BebasNeueBook.otf"));
        TextView addservicebtn = (TextView) activity.findViewById(R.id.addservicebtn);
        addservicebtn.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/BebasNeueBook.otf"));
        TextView contactbtn = (TextView) activity.findViewById(R.id.contactbtn);
        contactbtn.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/BebasNeueBook.otf"));
        TextView logoutbtn = (TextView) activity.findViewById(R.id.logoutbtn);
        logoutbtn.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/BebasNeueBook.otf"));
        profilebtn.setOnClickListener((View.OnClickListener) activity);
        ordersbtn.setOnClickListener((View.OnClickListener) activity);
        addservicebtn.setOnClickListener((View.OnClickListener) activity);
        contactbtn.setOnClickListener((View.OnClickListener) activity);
        logoutbtn.setOnClickListener((View.OnClickListener) activity);

        menu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                shadow = (ImageView) activity.findViewById(R.id.shadow);
                shadow.setVisibility(View.VISIBLE);
                shadow.startAnimation(mFadeInAnimation);

            }
        });

        menu.setOnCloseListener(new SlidingMenu.OnCloseListener() {
            @Override
            public void onClose() {
                shadow = (ImageView) activity.findViewById(R.id.shadow);
                shadow.startAnimation(mFadeOutAnimation);
                shadow.setVisibility(View.INVISIBLE);
            }
        });
    }
}
