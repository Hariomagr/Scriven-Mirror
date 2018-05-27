package com.example.dellpc.scrivenmirror;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class sendd extends AppCompatActivity {
    ImageButton sendd;
    FirebaseAuth mauth;
    DatabaseReference databaseReference;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendd);
        final AVLoadingIndicatorView avLoadingIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.indii);
        avLoadingIndicatorView.bringToFront();
        avLoadingIndicatorView.show();
        sendd = (ImageButton)findViewById(R.id.add);
        mAdView = (AdView) findViewById(R.id.adView);
        MobileAds.initialize(this, "ca-app-pub-3574223201894561~3262933725");
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);




        sendd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(sendd.this);
                builder1.setMessage("Select Language");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "English",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(sendd.this,submit.class);
                                        i.putExtra("lang","English");
                                        startActivity(i);
                                    }
                                },300);
                            }
                        });

                builder1.setNegativeButton(
                        "Hindi",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(sendd.this,submit.class);
                                        i.putExtra("lang","Hindi");
                                        startActivity(i);
                                    }
                                },300);
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });








        mauth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Submissions");
        final FirebaseUser user = mauth.getCurrentUser();
        final String email = user.getEmail();
        final String name = user.getDisplayName();

        final ListView listView=(ListView)findViewById(R.id.sendstatus);
        final ArrayList<Person> dolist=new ArrayList<>();
        final ArrayList<String> mai=new ArrayList<>();
        final ArrayList<String> nam=new ArrayList<>();
        final ArrayList<String> dat=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Submissions").orderByChild("mail").equalTo(email)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        avLoadingIndicatorView.hide();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            String subs = "Submission ";
                            String name=snapshot.child("name").getValue(String.class);
                            String status = snapshot.child("status").getValue(String.class);
                            String data=snapshot.child("data").getValue(String.class);
                            String email=snapshot.child("mail").getValue(String.class);
                            Person x = new Person(subs,status);
                            dolist.add(x);
                            mai.add(email);
                            nam.add(name);
                            dat.add(data);
                        }

                        if(mai.size()==0)
                            Toast.makeText(getApplicationContext(),"No submissions",Toast.LENGTH_SHORT).show();
                        padapterrr adapter =new padapterrr(sendd.this,R.layout.adapter_view1,dolist);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(sendd.this,mytale.class);
                i.putExtra("email",mai.get(position).toString());
                i.putExtra("name",nam.get(position).toString());
                i.putExtra("data",dat.get(position).toString());
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(sendd.this,Mainclass.class);
        startActivity(i);
    }
}
