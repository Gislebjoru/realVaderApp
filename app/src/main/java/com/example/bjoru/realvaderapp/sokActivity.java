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

        final Context context = getApplicationContext();
        final LinearLayout myLL = (LinearLayout) findViewById(R.id.linearSok);

        final Intent goBack = new Intent(this, MainActivity.class);

        final Button tilbakeKnapp = (Button) findViewById(R.id.tilbakeKnapp1);
        tilbakeKnapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(goBack);
            }
        });

        final Button sokeKnapp = (Button) findViewById(R.id.sokeKnapp);
        sokeKnapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InputStream fil = context.getResources().openRawResource(R.raw.noreg);
                    myLL.removeAllViews();
                    String text = "";
                    try {
                        byte[] buffer = new byte[fil.available()];
                        fil.read(buffer);
                        fil.close();
                        text = new String(buffer);
                } catch (IOException ex) {
                    System.out.println("IOException"+" "+ex);
                }
                EditText mySok = (EditText) findViewById(R.id.sokeFelt);
                String myString = mySok.getText().toString();
                String[] linjer = text.split("\n");
                String mySearch = "";
                for (String l: linjer) {
                    String[] rader = l.split("\t");
                    if(rader[1].contains(myString)) {
                        TextView myTv = new TextView(context);
                        myTv.setMovementMethod(new ScrollingMovementMethod());
                        mySearch = rader[1].toString()+"\n";
                        myTv.setTextColor(Color.parseColor("#000000"));
                        myTv.setGravity(Gravity.CENTER);
                        myTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        myTv.setText(mySearch);
                        myTv.setTag(rader[12]);
                        myLL.addView(myTv);
                        myTv.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
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
