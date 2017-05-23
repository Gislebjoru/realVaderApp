package com.example.bjoru.realvaderapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class sokActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sok);

        //definerer variabler og intents
        final Context context = getApplicationContext();
        final LinearLayout myLL = (LinearLayout) findViewById(R.id.linearSok);

        final Intent goBack = new Intent(this, MainActivity.class);

        //definerer knapp og setter onclicklistener
        final Button tilbakeKnapp = (Button) findViewById(R.id.tilbakeKnapp1);
        tilbakeKnapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(goBack);
            }
        });

        //definerer knapp og setter onclicklistener
        final Button sokeKnapp = (Button) findViewById(R.id.sokeKnapp);
        sokeKnapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //leser inn fil
                InputStream fil = context.getResources().openRawResource(R.raw.noreg);
                    //fjerner alle "current" søkeresultater når knapp trykkes på nytt
                    myLL.removeAllViews();
                    //definerer variablen text og starter en try and catch
                    String text = "";
                    try {
                        byte[] buffer = new byte[fil.available()];
                        fil.read(buffer);
                        fil.close();
                        text = new String(buffer);
                } catch (IOException ex) {
                    System.out.println("IOException"+" "+ex);
                }
                //finner og navngir variabler, samt splitter på \n og \t
                EditText mySok = (EditText) findViewById(R.id.sokeFelt);
                String myString = mySok.getText().toString();
                String[] linjer = text.split("\n");
                String mySearch = "";
                for (String l: linjer) {
                    String[] rader = l.split("\t");
                    //om rader[1] samsvarer med det som skrives inn
                    if(rader[1].toLowerCase().contains(myString.toLowerCase())) {
                        //setter nytt textview og definerer verdier
                        TextView myTv = new TextView(context);
                        myTv.setMovementMethod(new ScrollingMovementMethod());
                        mySearch = rader[1].toString()+"\n";
                        myTv.setTextColor(Color.parseColor("#000000"));
                        myTv.setGravity(Gravity.CENTER);
                        myTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        myTv.setText(mySearch);
                        myTv.setTag(rader[12]);
                        myLL.addView(myTv);
                        //setter onclicklistener på textview som gjør at vi kan trykke på søkeresults
                        myTv.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                //onclick definer id og send den til mainactivity med intenten goBack
                                String id = view.getTag().toString();
                                goBack.putExtra("url", id);
                                startActivity(goBack);
                            }
                        });
                    }


                }

            }
        });
    }
}
