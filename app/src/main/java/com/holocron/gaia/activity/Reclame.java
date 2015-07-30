package com.holocron.gaia.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.holocron.gaia.R;

/**
 * Created by S�vio on 26/07/2015.
 */
public class Reclame extends ActionBarActivity {

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reclame);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

