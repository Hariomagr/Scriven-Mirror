package com.example.dellpc.scrivenmirror;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Submistatus extends AppCompatActivity {
    ImageButton accept,reject;
    FirebaseAuth mauth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submistatus);
        mauth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Submissions");
        accept = (ImageButton) findViewById(R.id.acceptt);
        reject = (ImageButton) findViewById(R.id.rejectt);
        String name = getIntent().getStringExtra("name");
        final String mail = getIntent().getStringExtra("email");
        final String data = getIntent().getStringExtra("data");
        TextView nam = (TextView)findViewById(R.id.by);
        TextView dat = (TextView)findViewById(R.id.bydata);
        nam.setText("By: "+name);
        dat.setMovementMethod(new ScrollingMovementMethod());
        dat.setText(data);


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Submistatus.this);
                builder1.setMessage("Accept the Submission");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Accept",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseDatabase.getInstance().getReference("Submissions").orderByChild("data").equalTo(data)
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                                    String email=snapshot.child("mail").getValue(String.class);
                                                    String key =snapshot.getRef().getKey();
                                                    DatabaseReference sub = FirebaseDatabase.getInstance().getReference("Submissions");
                                                    if(email.equals(mail)){
                                                        sub.child(key).child("status").setValue("Accepted");
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(Submistatus.this,submissionadmin.class);
                                        startActivity(i);
                                    }
                                },1000);
                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Submistatus.this);
                builder1.setMessage("Reject the Submission");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Reject",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                FirebaseDatabase.getInstance().getReference("Submissions").orderByChild("data").equalTo(data)
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                                    String email=snapshot.child("mail").getValue(String.class);
                                                    String key =snapshot.getRef().getKey();
                                                    DatabaseReference sub = FirebaseDatabase.getInstance().getReference("Submissions");
                                                    if(email.equals(mail)){
                                                        sub.child(key).child("status").setValue("Rejected");
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(Submistatus.this,submissionadmin.class);
                                        startActivity(i);
                                    }
                                },1000);
                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Submistatus.this,submissionadmin.class);
        startActivity(i);
    }
}
