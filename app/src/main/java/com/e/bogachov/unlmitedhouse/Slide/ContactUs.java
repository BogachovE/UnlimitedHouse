package com.e.bogachov.unlmitedhouse.Slide;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.R;

public class ContactUs extends Activity {
    TextView profile_profeile;
    TextView contact_us_text;
    TextView contact_us_name_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        profile_profeile = (TextView)findViewById(R.id.profile_profeile);
        profile_profeile.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BebasNeueBook.otf"));

        contact_us_text = (TextView)findViewById(R.id.contact_us_text);
        contact_us_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BebasNeueBook.otf"));

        contact_us_name_text = (TextView)findViewById(R.id.contact_us_name_text);
        contact_us_name_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BebasNeueBook.otf"));
    }
}
