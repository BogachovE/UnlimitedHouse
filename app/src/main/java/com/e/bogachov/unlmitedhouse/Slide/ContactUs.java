package com.e.bogachov.unlmitedhouse.Slide;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.e.bogachov.unlmitedhouse.R;

public class ContactUs extends Activity  implements View.OnClickListener{
    TextView profile_profeile;
    TextView contact_us_text;
    TextView contact_us_name_text;
    ImageButton email_btn;
    EditText mail_text,contact_us_name_edit;

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

        email_btn = (ImageButton)findViewById(R.id.email_btn);
        email_btn.setOnClickListener(this);

        mail_text = (EditText)findViewById(R.id.mail_text);
        mail_text.setOnClickListener(this);
        contact_us_name_edit = (EditText)findViewById(R.id.contact_us_name_edit);
        contact_us_name_edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_btn: {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                emailIntent.setType("plain/text");
                // Кому
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[]{"support@unlimited-house.com"});
                // Зачем
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        contact_us_name_edit.getText().toString());
                // О чём
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        mail_text.getText().toString());

                // Поехали!
                this.startActivity(Intent.createChooser(emailIntent,
                        "Massege sending.."));
                break;
            }
            case R.id.mail_text: {
                mail_text.setText("");
                break;
            }
            case R.id.contact_us_name_edit: {
                contact_us_name_edit.setText("");
            }

        }
    }
}
