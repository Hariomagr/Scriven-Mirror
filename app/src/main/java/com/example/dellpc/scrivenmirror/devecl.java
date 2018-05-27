package com.example.dellpc.scrivenmirror;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class devecl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devecl);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(devecl.this,Mainclass.class);
        startActivity(i);
    }
}
