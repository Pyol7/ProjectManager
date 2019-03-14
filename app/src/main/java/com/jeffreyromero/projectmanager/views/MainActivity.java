package com.jeffreyromero.projectmanager.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.gson.Gson;
import com.jeffreyromero.projectmanager.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add item sub type list to Shared Preferences
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("itemSubTypeList", new Gson().toJson(buildItemSubTypeList()));
        editor.commit();
    }

    private List<String> buildItemSubTypeList() {
        List<String> itemTypes = new ArrayList<>();
        itemTypes.add("Dropped Ceiling");
        itemTypes.add("Drywall Ceiling");
        itemTypes.add("Drywall Partition");
        return itemTypes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
