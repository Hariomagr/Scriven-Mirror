package com.example.dellpc.scrivenmirror;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Mainclass extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private ViewPager viewPager;
    private Custom custom;
    private Custom1 custom1;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private AVLoadingIndicatorView avLoadingIndicatorView1;
    FirebaseUser user;
    TextView dddd;
    TextView likks;
    TextView bynnn;
    TextView te;
    MaterialFavoriteButton materialFavoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainclass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        materialFavoriteButton = (MaterialFavoriteButton)findViewById(R.id.materialFavoriteButton1);
        avLoadingIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.indica12);
        avLoadingIndicatorView.hide();
        avLoadingIndicatorView1 = (AVLoadingIndicatorView)findViewById(R.id.indicare);
        avLoadingIndicatorView.smoothToShow();
        materialFavoriteButton.setAlpha(0);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(100);
        dddd = (TextView)findViewById(R.id.dddd);
        likks = (TextView)findViewById(R.id.likkess);
        bynnn = (TextView)findViewById(R.id.bynamme);
        te = (TextView)findViewById(R.id.textView8);
        dddd.setTextSize(17);
        dddd.setMovementMethod(new ScrollingMovementMethod());
        materialFavoriteButton.setFavorite(true);
        materialFavoriteButton.setAnimateFavorite(true);
        materialFavoriteButton.setClickable(false);


        if(haveNetworkConnection()==false) {
            Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        FirebaseDatabase.getInstance().getReference("Submissions").orderByChild("status").equalTo("Accepted").limitToLast(1)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        avLoadingIndicatorView.hide();
                      avLoadingIndicatorView1.hide();
                      materialFavoriteButton.setAlpha(255);
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            String ddd=snapshot.child("data").getValue(String.class);
                            String nnn=snapshot.child("name").getValue(String.class);
                            Integer lll=snapshot.child("likes").getValue(Integer.class);
                            dddd.setText(ddd);
                            likks.setText(String.valueOf(lll)+" likes");
                            bynnn.setText("By- "+nnn);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });









        String x="Welcome";
        String y=null;
        String z = "";
        mauth = FirebaseAuth.getInstance();
        user = mauth.getCurrentUser();
        if(user!=null) {
             x = user.getDisplayName();
             y = user.getPhotoUrl().toString();
             z = user.getEmail();
        }







        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        if(z.equals("scrivenmirror@gmail.com")){
            navigationView.getMenu().findItem(R.id.subs).setVisible(true);
        }
        View headerView = navigationView.getHeaderView(0);
        final TextView proname = (TextView)headerView.findViewById(R.id.textView3);
        final CircleImageView proima = (CircleImageView) headerView.findViewById(R.id.imageview);
        Glide.with(Mainclass.this).load(y).into(proima);
        proname.setText(x);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(proima.getDrawable()==null)
                    proima.setImageResource(R.mipmap.images);
                if(proname.getText().toString().equals(""))
                    proname.setText("Welcome");
            }
        },1000);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainclass, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Are you sure...");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "LogOut",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FirebaseAuth.getInstance().signOut();
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(@NonNull Status status) {
                                            Intent i = new Intent(Mainclass.this,Homescreen.class);
                                            startActivity(i);
                                        }
                                    }
                            );
                        }
                    });

            builder1.setNegativeButton(
                    "Stay",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            dddd.setAlpha(0);
            bynnn.setAlpha(0);
            likks.setAlpha(0);
            te.setAlpha(0);
            dddd.setMovementMethod(null);
            materialFavoriteButton.setAlpha(0);
            avLoadingIndicatorView.smoothToShow();
            custom= new Custom(this);
            custom.notifyDataSetChanged();
            viewPager.setAdapter(custom);
            viewPager.setBackgroundColor(Color.WHITE);
            avLoadingIndicatorView.hide();

        } else if (id == R.id.nav_gallery) {
            dddd.setAlpha(0);
            bynnn.setAlpha(0);
            likks.setAlpha(0);
            te.setAlpha(0);
            dddd.setMovementMethod(null);
            materialFavoriteButton.setAlpha(0);
            avLoadingIndicatorView.smoothToShow();
            custom1= new Custom1(this);
            custom1.notifyDataSetChanged();
            viewPager.setAdapter(custom1);
            viewPager.setBackgroundColor(Color.WHITE);
            avLoadingIndicatorView.hide();
        } else if (id == R.id.nav_slideshow) {
            dddd.setAlpha(0);
            bynnn.setAlpha(0);
            likks.setAlpha(0);
            te.setAlpha(0);
            dddd.setMovementMethod(null);

            materialFavoriteButton.setAlpha(0);
            avLoadingIndicatorView.smoothToShow();
            avLoadingIndicatorView1.smoothToShow();
            if(user!=null){
                FirebaseDatabase.getInstance().getReference("UID").child(user.getUid()).orderByChild("llikes").equalTo(1)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                avLoadingIndicatorView.hide();
                                avLoadingIndicatorView1.hide();
                                if(dataSnapshot.exists()){
                                    Custom2 custom2= new Custom2(getApplicationContext());
                                    custom2.notifyDataSetChanged();
                                    viewPager.setAdapter(custom2);
                                    viewPager.setBackgroundColor(Color.WHITE);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"No Liked Post",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(Mainclass.this,Mainclass.class);
                                    startActivity(i);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
           }
            else
                Toast.makeText(getApplicationContext(),"Not properly looged in",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.invite){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LINK");
            databaseReference.setValue("");
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String sharebody = "https://goo.gl/P2srkp";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Scriven Mirror");
            sharingIntent.putExtra(Intent.EXTRA_TEXT,sharebody);
            startActivity(Intent.createChooser(sharingIntent,"Share via"));
        }   else if(id  == R.id.subs){
            Intent i =new Intent(Mainclass.this,submissionadmin.class);
            startActivity(i);
        } else if(id == R.id.Submit){
            if(user==null){
                Toast.makeText(getApplication(),"Not Logged in properly",Toast.LENGTH_SHORT).show();
            }
            else {
                Intent i = new Intent(Mainclass.this, sendd.class);
                startActivity(i);
            }
        }   else if(id==R.id.fb){
            Uri uri = Uri.parse("fb://page/1848998782021020");
            Intent likeing = new Intent(Intent.ACTION_VIEW,uri);
            likeing.setPackage("com.facebook.katana");
            try{
                startActivity(likeing);
            } catch (ActivityNotFoundException e){
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://facebook.com/scrivenmirror")));
            }
        } else if(id == R.id.insta){
            Uri uri = Uri.parse("http://instagram.com/_u/scrivenmirror");
            Intent likeing = new Intent(Intent.ACTION_VIEW,uri);
            likeing.setPackage("com.instagram.android");
            try{
                startActivity(likeing);
            } catch (ActivityNotFoundException e){
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://instagram.com/scrivenmirror")));
            }
        } else if(id == R.id.deve){
            Intent i = new Intent(Mainclass.this,devecl.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
