package com.example.dellpc.scrivenmirror;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by DELL PC on 09-Jan-18.
 */

public class Custom2 extends PagerAdapter {
    private Context ctx;
    private LayoutInflater layoutInflater;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    final ArrayList<String> mai=new ArrayList<>();
    final ArrayList<String> namm=new ArrayList<>();
    final ArrayList<Integer> lik=new ArrayList<Integer>();
    final ArrayList<Integer> likk=new ArrayList<Integer>();
    public Custom2(Context ctx){
        this.ctx=ctx;
    }
    @Override
    public int getCount() {
        return 10000000;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(RelativeLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View item_view = layoutInflater.inflate(R.layout.swipe,container,false);
        final FirebaseAuth mauth=FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();
        String uid="";
        if(user!=null){
            uid=user.getUid();
        }
        final String finalUid = uid;
        if(!finalUid.equals("")){
            FirebaseDatabase.getInstance().getReference("UID").child(finalUid).orderByChild("llikes").equalTo(1)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                mai.add(snapshot.child("data").getValue(String.class));
                                namm.add(snapshot.child("name").getValue(String.class));
                            }
                            Collections.reverse(mai);
                            Collections.reverse(namm);
                            TextView textView = (TextView)item_view.findViewById(R.id.textView2);
                            TextView na = (TextView)item_view.findViewById(R.id.bynamee);
                            textView.setMovementMethod(new ScrollingMovementMethod());
                            textView.setTextSize(20);
                            textView.setText(mai.get(position));
                            na.setText("By- "+namm.get(position));
                            TextView likes = (TextView)item_view.findViewById(R.id.likess);
                            final MaterialFavoriteButton favoriteButton = (MaterialFavoriteButton)item_view.findViewById(R.id.materialFavoriteButton);
                            likes.setText("You Liked");
                            favoriteButton.setAnimateFavorite(true);
                            favoriteButton.setFavorite(true);
                            favoriteButton.setClickable(false);
                            container.addView(item_view);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
        return item_view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
