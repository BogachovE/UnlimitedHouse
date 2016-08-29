package com.e.bogachov.unlmitedhouse.Slide;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.e.bogachov.unlmitedhouse.R;

public class Orders extends ListActivity {


        String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
                "Костя", "Игорь", "Анна", "Денис", "Андрей" };

        /** Called when the activity is first created. */
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.orders);

            // находим список
            ListView lvMain = (ListView) findViewById(R.id.lvMain);

            // создаем адаптер
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, names);

            // присваиваем адаптер списку
            lvMain.setAdapter(adapter);

        }
}
