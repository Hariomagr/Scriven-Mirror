package com.example.dellpc.scrivenmirror;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class submit extends AppCompatActivity {
    ImageButton submitbutt;
    EditText editText;
    DatabaseReference databaseReference;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        submitbutt = (ImageButton)findViewById(R.id.submitbutt);
        editText = (EditText) findViewById(R.id.submi);
        mauth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Submissions");
        final FirebaseUser user = mauth.getCurrentUser();

              final String email = user.getEmail();
              final String name = user.getDisplayName();

        final String lang = getIntent().getStringExtra("lang");
        submitbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(submit.this);
                builder1.setMessage("Send Your tale");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Confirm",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String x = editText.getText().toString();
                                String idd = databaseReference.push().getKey();
                                Submissions reg = new Submissions();
                                reg.setName(name);
                                reg.setMail(email);
                                reg.setStatus("Not Checked");
                                reg.setData(x);
                                reg.setLikes(0);
                                reg.setLang(lang);
                                databaseReference.child(idd).setValue(reg);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(submit.this,Mainclass.class);
                                        startActivity(i);
                                    }
                                },600);
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
        Intent i = new Intent(submit.this,sendd.class);
        startActivity(i);
    }
}
