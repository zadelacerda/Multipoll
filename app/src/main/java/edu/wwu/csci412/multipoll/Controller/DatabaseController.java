package edu.wwu.csci412.multipoll.Controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.User;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import android.util.Log;
import com.google.firebase.database.Query;



public class DatabaseController {
    private DatabaseReference mFirebaseRef;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference userRef;
    private DatabaseReference groupRef;
    private ValueEventListener mPostListener;


    public void DatabaseController() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseRef = mFirebaseDatabase.getReference();
        //userRef = mFirebaseDatabase.getInstance().getReference().child("users");
        groupRef = mFirebaseDatabase.getReference("groups");
    }

    private User retUser;
    private String pass;
    public User getDataByUser(final String username) {

        //Query q = FirebaseDatabase.getInstance().getReference().child("users").equalTo(username);
        FirebaseDatabase.getInstance().getReference().child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                retUser.setPassword(dataSnapshot.child(username).child("password").getValue(String.class));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
        return retUser;
    }
}