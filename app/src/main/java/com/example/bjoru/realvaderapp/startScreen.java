package com.example.bjoru.realvaderapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class startScreen extends AppCompatActivity {

    //denne må defineres med en int for at permission request skal funke
    final int LOCATION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        //definererintents og knapper
        final Intent heyGuys = new Intent(this, MainActivity.class);
        final Intent guysHey = new Intent(this, sokActivity.class);

        final Button findMe = (Button) findViewById(R.id.findMe);
        final Button sokMe = (Button) findViewById(R.id.goToSok);

        //onclicklistener på begge knappen som starter forskjellige intents
        findMe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(heyGuys);
            }
        });

        sokMe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(guysHey);
            }
        });

        //sjekker permission og ber om permission til å bruke din location om det ikke er gitt alt
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("Request location access");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            System.out.println("Granted access to my location");
        }
    }
}
