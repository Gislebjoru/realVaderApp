package com.example.bjoru.realvaderapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Langtidsvarsel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langtidsvarsel);

        final TextView langtid = (TextView) findViewById(R.id.langtidsvarsel);
        String message = getIntent().getStringExtra(MainActivity.myString);

        langtid.setText(message);



    }

}
