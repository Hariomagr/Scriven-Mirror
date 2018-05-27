package com.example.dellpc.scrivenmirror;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;



public class submissionadmin extends Activity {
    FirebaseAuth mauth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submissions);

        final AVLoadingIndicatorView avLoadingIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.indi);
        avLoadingIndicatorView.bringToFront();
        avLoadingIndicatorView.show();



        mauth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Submissions");
        final FirebaseUser user = mauth.getCurrentUser();



        final ListView listView=(ListView)findViewById(R.id.subsmi);
        final ArrayList<Person> dolist=new ArrayList<>();
        final ArrayList<String> mai=new ArrayList<>();
        final ArrayList<String> nam=new ArrayList<>();
        final ArrayList<String> dat=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Submissions").orderByChild("status").equalTo("Not Checked")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        avLoadingIndicatorView.hide();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            String name=snapshot.child("name").getValue(String.class);
                            String data=snapshot.child("data").getValue(String.class);
                            String email=snapshot.child("mail").getValue(String.class);
                            Person x = new Person("BY: "+name,email);
                            dolist.add(x);
                            mai.add(email);
                            nam.add(name);
                            dat.add(data);
                        }

                        if(mai.size()==0)
                            Toast.makeText(getApplicationContext(),"No more submissions",Toast.LENGTH_SHORT).show();
                        padapterr adapter =new padapterr(submissionadmin.this,R.layout.adapter_view,dolist);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(submissionadmin.this,Submistatus.class);
                i.putExtra("email",mai.get(position).toString());
                i.putExtra("name",nam.get(position).toString());
                i.putExtra("data",dat.get(position).toString());
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(submissionadmin.this,Mainclass.class);
        startActivity(i);
    }
}
