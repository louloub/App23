package com.example.app23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        // getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    // ----

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }
}
