package com.example.dellpc.scrivenmirror;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth = FirebaseAuth.getInstance();
        final FirebaseUser user = mauth.getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user == null) {
                    Intent i = new Intent(MainActivity.this, Homescreen.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(MainActivity.this, Mainclass.class);
                    startActivity(i);
                }
                finish();
            }
        },3000);






    }

}
