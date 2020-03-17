package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import edu.wwu.csci412.multipoll.Model.Category;
import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Element;
import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.Model.Poll;

import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;


public class CreateGroup extends AppCompatActivity {
    private ArrayAdapter<User> arrayAdapter;
    private EditText search;
    List<CheckBox> checks = new ArrayList<>();
    public static Controller controller;
    public static User user;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);

       /* Toolbar setup */
        getSupportActionBar().setTitle("Create Group");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* Connect XML with Java objects */
        search = findViewById(R.id.searchFriend);
        LinearLayout linearLayout = findViewById(R.id.friendContainer);
        FloatingActionButton fab = findViewById(R.id.addFriend);

        /* Fake Database */
        controller = MainActivity.getController();
        user = controller.getUser();

        /* Real Database */
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        // Toolbar setup

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* Search view setup */
        search.setHint("Enter Group Name");
        //Create checkboxes for Friends list

        for (int i = 0; i < user.getFriends().size(); i++) {
            String friend = user.getFriends().get(i);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(i);
            checkBox.setText(friend);
            checks.add(checkBox);

            checkBox.setWidth(linearLayout.getWidth());
            linearLayout.addView(checkBox);
        }

        /* Floating Add Button Setup */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e = (EditText) findViewById(R.id.searchFriend);
                String name = e.getText().toString();
                //If the bar isn't blank, create new group with selected members
                if(!search.getText().toString().equals("")) {


                    int ind = 0;
                    final Group newGroup = new Group(search.getText().toString());
                    newGroup.addMember(user.getUserName());
                    newGroup.setName(name);
                    String gId = databaseReference.push().getKey();
                    newGroup.setGroupID(gId);


                    for (int i = 0; i < checks.size(); i++) {
                        if (checks.get(i).isChecked()) {
                            newGroup.addMember(user.getFriends().get(i));


                        }
                    }

                    //Create the new group for each member in the group
                    for (int i = 0; i < newGroup.getMembers().size(); i++) {
                        FirebaseDatabase.getInstance().getReference().child("users").child(newGroup.getMembers().get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<Group> gList = new ArrayList<>();
                                String un = dataSnapshot.child("userName").getValue(String.class);
                                Log.d("myTag", un);
                                //Re-Add Groups
                                for (DataSnapshot snap : dataSnapshot.child("userGroups").getChildren()) {
                                    Group gr = new Group();
                                    gr.setGroupID(snap.child("groupID").getValue(String.class));
                                    gr.setName(snap.child("name").getValue(String.class));
                                    //Re-Add Polls
                                    for (DataSnapshot snapp : snap.child("polls").getChildren()) {
                                        Poll newp = new Poll();
                                        newp.setOwner(snapp.child("owner").getValue(String.class));
                                        newp.setName(snapp.child("name").getValue(String.class));
                                        newp.setStatus(snapp.child("status").getValue(Boolean.class));
                                        newp.setTarget(snapp.child("target").getValue(String.class));
                                        newp.setPollID(snapp.child("pollID").getValue(String.class));
                                        List<Element> Elist = new ArrayList<>();
                                        //Fills model with Elements
                                        for (DataSnapshot sn : snapp.child("elements").getChildren()) {
                                            Elist.add(sn.getValue(Element.class));
                                            Log.d("myTage", sn.child("name").getValue(String.class));
                                        }
                                        newp.setElements(Elist);

                                        gr.addPoll(newp);


                                    }
                                    //Add all the members
                                    for( DataSnapshot mems : snap.child("members").getChildren()){
                                        gr.addMember(mems.getValue(String.class));

                                    }

                                    gList.add(gr);
                                    //Add to model if the member is the current user
                                    if(un.equals(user.getUserName())){
                                        user.addGroup(gr);
                                    }
                                }
                                gList.add(newGroup);
                                //Add to members database user
                                if(un.equals(user.getUserName())){

                                    user.setUserGroups(gList);
                                    databaseReference.child("users").child(user.getUserName()).child("userGroups").setValue(gList);
                                }
                                FirebaseDatabase.getInstance().getReference().child("users").child(un).child("userGroups").setValue(gList);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }


            }
        });
    }

    // Back functionality
    @Override public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

