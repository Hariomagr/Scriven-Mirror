package com.example.dellpc.scrivenmirror;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class mytale extends AppCompatActivity {
    TextView data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytale);
        data = (TextView)findViewById(R.id.textView6);
        String dat = getIntent().getStringExtra("data");
        data.setMovementMethod(new ScrollingMovementMethod());
        data.setText(dat);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(mytale.this,sendd.class);
        startActivity(i);
    }
}
