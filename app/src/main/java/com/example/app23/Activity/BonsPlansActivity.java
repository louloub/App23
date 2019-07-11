package com.example.app23.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.app23.R;

public class BonsPlansActivity extends OptionMenuActivity {

    private static final String NAME_FOR_ACTIONBAR = "Bons Plans";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bons_plans);

        getSupportActionBar().setTitle(NAME_FOR_ACTIONBAR);

    }
}
