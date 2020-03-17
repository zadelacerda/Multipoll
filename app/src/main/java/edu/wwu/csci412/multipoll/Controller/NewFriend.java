package edu.wwu.csci412.multipoll.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import edu.wwu.csci412.multipoll.Model.User;

import java.util.List;

import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.R;

public class NewFriend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
    }

    public void addFriend(View v){

        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            int userFound = 0;
            int existingFr = 0;
            EditText newfr = (EditText) findViewById(R.id.edituser);
            String usern = (String) newfr.getText().toString();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser = MainActivity.getController().getUser();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String un = ds.child("userName").getValue(String.class);
                    if (un.equals(usern)) {
                        userFound = 1;
                        if(!currentUser.getFriends().contains(usern)) {
                            currentUser.addFriend(usern);
                        }

                    }
                    if (un.equals(currentUser.getUserName())) {
                        for(DataSnapshot snap : ds.child("friends").getChildren()){
                            if(usern.equals(snap.getValue(String.class))){
                                existingFr = 1;
                            }
                        }
                    }
                    if(existingFr == 0 && userFound == 1){
                        List<String> nf = currentUser.getFriends();
                        //nf.add(usern);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUserName()).child("friends").setValue(nf);
                        Intent intent = new Intent (NewFriend.this, Friends.class);
                        startActivity(intent);
                    }
                }
                userFound = 0;
                existingFr = 0;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
