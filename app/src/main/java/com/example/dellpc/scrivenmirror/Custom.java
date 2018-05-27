package com.example.dellpc.scrivenmirror;

import android.content.Context;
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

public class Custom extends PagerAdapter {
    private Context ctx;
    private LayoutInflater layoutInflater;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    final ArrayList<String> mai=new ArrayList<>();
    final ArrayList<String> namm=new ArrayList<>();
    final ArrayList<Integer> lik=new ArrayList<Integer>();
    final ArrayList<Integer> likk=new ArrayList<Integer>();
    public Custom(Context ctx){
        this.ctx=ctx;
    }
    @Override
    public int getCount() {

        return 10000000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View item_view = layoutInflater.inflate(R.layout.swipe,container,false);
        final TextView likes = (TextView)item_view.findViewById(R.id.likess);
        final FirebaseAuth mauth=FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();
        String uid="";
        if(user!=null){
            uid=user.getUid();
        }
        final MaterialFavoriteButton favoriteButton = (MaterialFavoriteButton)item_view.findViewById(R.id.materialFavoriteButton);
        final String finalUid = uid;

        FirebaseDatabase.getInstance().getReference("Submissions").orderByChild("lang").equalTo("English")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(final DataSnapshot snapshot:dataSnapshot.getChildren()){
                            mai.add(snapshot.child("data").getValue(String.class));
                            lik.add(snapshot.child("likes").getValue(Integer.class));
                            namm.add(snapshot.child("name").getValue(String.class));
                            if(snapshot.child("USER").child(finalUid).exists()) {
                                likk.add(snapshot.child("USER").child(finalUid).child("Like").getValue(Integer.class));
                            }
                            else {
                                likk.add(0);
                            }


                        }
                        Collections.reverse(likk);
                        Collections.reverse(mai);
                        Collections.reverse(lik);
                        Collections.reverse(namm);
                        if(mai.size()!=0){
                        likes.setText(String.valueOf(lik.get(position))+" likes");}
                        if(!finalUid.equals("")){
                            favoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                @Override
                                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                    if(buttonView.isFavorite()==true){
                                        if(mai.size()!=0){
                                        likes.setText(String.valueOf(lik.get(position)+1)+" likes");}
                                        FirebaseDatabase.getInstance().getReference("UID").child(finalUid).orderByChild("data").equalTo(mai.get(position))
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if(dataSnapshot.exists()){
                                                            DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                                                            String key = nodeDataSnapshot.getKey();
                                                            DatabaseReference don = FirebaseDatabase.getInstance().getReference("UID").child(finalUid);
                                                            don.child(key).child("llikes").setValue(1);
                                                            FirebaseDatabase.getInstance().getReference("Submissions").orderByChild("data").equalTo(mai.get(position))
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            DataSnapshot nodeDataSnapshott = dataSnapshot.getChildren().iterator().next();
                                                                            String key = nodeDataSnapshott.getKey();
                                                                            Integer inli = nodeDataSnapshott.child("likes").getValue(Integer.class);
                                                                            DatabaseReference donn = FirebaseDatabase.getInstance().getReference("Submissions");
                                                                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                                                            if(snapshot.child("USER").child(finalUid).exists()){
                                                                                if(snapshot.child("USER").child(finalUid).child("Like").getValue(Integer.class)!=1){
                                                                            donn.child(key).child("likes").setValue(inli+1);
                                                                            }}}donn.child(key).child("USER").child(finalUid).child("Like").setValue(1);
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(DatabaseError databaseError) {

                                                                        }
                                                                    });

                                                        }
                                                        else{
                                                            DatabaseReference sub = FirebaseDatabase.getInstance().getReference("UID").child(finalUid);
                                                            String idd = sub.push().getKey();
                                                            likes reg = new likes();
                                                            reg.setName(namm.get(position));
                                                            reg.setData(mai.get(position));
                                                            reg.setLlikes(1);
                                                            sub.child(idd).setValue(reg);
                                                            FirebaseDatabase.getInstance().getReference("Submissions").orderByChild("data").equalTo(mai.get(position))
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            DataSnapshot nodeDataSnapshott = dataSnapshot.getChildren().iterator().next();
                                                                            String key = nodeDataSnapshott.getKey();
                                                                            Integer inli = nodeDataSnapshott.child("likes").getValue(Integer.class);
                                                                            DatabaseReference donn = FirebaseDatabase.getInstance().getReference("Submissions");
                                                                            donn.child(key).child("likes").setValue(inli+1);
                                                                            donn.child(key).child("USER").child(finalUid).child("Like").setValue(1);
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                    }
                                    else {
                                        if(mai.size()!=0){
                                        likes.setText(String.valueOf(lik.get(position))+" likes");}
                                        FirebaseDatabase.getInstance().getReference("UID").child(finalUid).orderByChild("data").equalTo(mai.get(position))
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                                                        String key = nodeDataSnapshot.getKey();
                                                        DatabaseReference don = FirebaseDatabase.getInstance().getReference("UID").child(finalUid);
                                                        don.child(key).child("llikes").setValue(0);
                                                        FirebaseDatabase.getInstance().getReference("Submissions").orderByChild("data").equalTo(mai.get(position))
                                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        DataSnapshot nodeDataSnapshott = dataSnapshot.getChildren().iterator().next();
                                                                        String key = nodeDataSnapshott.getKey();
                                                                        Integer inli = nodeDataSnapshott.child("likes").getValue(Integer.class);
                                                                        DatabaseReference donn = FirebaseDatabase.getInstance().getReference("Submissions");
                                                                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                                                        if(snapshot.child("USER").child(finalUid).exists()){
                                                                        if(snapshot.child("USER").child(finalUid).child("Like").getValue(Integer.class)!=0){
                                                                        donn.child(key).child("likes").setValue(inli-1);
                                                                        }}}donn.child(key).child("USER").child(finalUid).child("Like").setValue(0);

                                                                    }

                                                                    @Override
                                                                    public void onCancelled(DatabaseError databaseError) {

                                                                    }
                                                                });
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                    }
                                }
                            });
                        }
                        if(mai.size()!=0) {
                            final TextView textView = (TextView) item_view.findViewById(R.id.textView2);
                            TextView na = (TextView) item_view.findViewById(R.id.bynamee);
                            if (likk.get(position) == 0) {
                                favoriteButton.setFavorite(false);
                            } else if (likk.get(position) == 1) {
                                favoriteButton.setFavorite(true);
                            }

                            textView.setMovementMethod(new ScrollingMovementMethod());
                            textView.setText(mai.get(position));
                            na.setText("By- " + namm.get(position));
                        }


                            container.addView(item_view);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });







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
