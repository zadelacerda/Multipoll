package edu.wwu.csci412.multipoll.Controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.Model.UserInfo;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.Query;



public class DatabaseController {
    private DatabaseReference mFirebaseRef;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference userRef;
    private DatabaseReference groupRef;
    private ValueEventListener mPostListener;
    private DatabaseReference myref;


    public void DatabaseController() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseRef = mFirebaseDatabase.getReference().child("users");

    }

    private static UserInfo retUser;
    private String pass;
    public UserInfo getDataByUser(final String username) {
        //DatabaseReference userref = mFirebaseRef.child(username);
        //final User retUser = new User(0);
        //retUser.setPassword("Fake");
        retUser = new UserInfo();
        //Query q = ref.equalTo(username);
        FirebaseDatabase.getInstance().getReference().child("users").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserInfo newu = dataSnapshot.getValue(UserInfo.class);
                    //newu is retrieving data correctly and logs of it are outputting correct info
                    //retUser = newu;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d("myTag", retUser.getPassword());
        return retUser;
    }
}