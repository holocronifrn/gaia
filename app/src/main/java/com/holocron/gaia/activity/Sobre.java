package com.holocron.gaia.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.holocron.gaia.R;

/**
 * Created by S�vio on 29/07/2015.
 */
public class Sobre extends ActionBarActivity{
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sobre);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
