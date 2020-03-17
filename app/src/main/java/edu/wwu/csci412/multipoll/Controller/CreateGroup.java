package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
        //getSupportActionBar().setTitle(user.getCurrentCategory().getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* Search view setup */
        search.setHint("Enter Group Name");
//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                //(CreateGroup.this).arrayAdapter.getFilter().filter(s);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        /* List of elements for current category */
//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.getFriends());
//        LinearLayout.LayoutParams buttonparam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
        for (int i = 0; i < user.getFriends().size(); i++) {
            String friend = user.getFriends().get(i);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(i);
            checkBox.setText(friend);
            checks.add(checkBox);
//            checkBox.setLayoutParams(buttonparam);
            checkBox.setWidth(linearLayout.getWidth());
            linearLayout.addView(checkBox);
        }

        /* Floating Add Button Setup */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText e = (EditText) findViewById(R.id.searchFriend);
                String name = e.getText().toString();
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
//                    for (int i = 0; i < newGroup.getMembers().size(); i++){
//                        databaseReference.child("users").child(newGroup.getMembers().get(i)).child("userGroups").push().setValue(newGroup);
//
//                    }

                    for (int i = 0; i < newGroup.getMembers().size(); i++) {
                        FirebaseDatabase.getInstance().getReference().child("users").child(newGroup.getMembers().get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<Group> gList = new ArrayList<>();
                                String un = dataSnapshot.child("userName").getValue(String.class);
                                Log.d("myTag", un);
                                for (DataSnapshot snap : dataSnapshot.child("userGroups").getChildren()) {
                                    Group gr = new Group();
                                    gr.setGroupID(snap.child("groupID").getValue(String.class));
                                    gr.setName(snap.child("name").getValue(String.class));
                                    //List<Poll> Plist = new ArrayList<>();
                                    for (DataSnapshot snapp : snap.child("polls").getChildren()) {
                                        Poll newp = new Poll();
                                        newp.setOwner(snapp.child("owner").getValue(String.class));
                                        newp.setName(snapp.child("name").getValue(String.class));
                                        newp.setStatus(snapp.child("status").getValue(Boolean.class));
                                        newp.setTarget(snapp.child("target").getValue(String.class));
                                        newp.setPollID(snapp.child("pollID").getValue(String.class));
                                        //gr.addPoll(newp);
                                        //Group gr = new Group();
                                        List<Element> Elist = new ArrayList<>();
                                        //Group gr = new Group();
                                        //Fills model with Elements
                                        for (DataSnapshot sn : snapp.child("elements").getChildren()) {
                                            Elist.add(sn.getValue(Element.class));
                                            Log.d("myTage", sn.child("name").getValue(String.class));
                                            //newp.addElement(sn.getValue(Element.class));
                                        }
                                        newp.setElements(Elist);

                                        //gr.setName(snap.child("name").getValue(String.class));
                                        //gr.setGroupID(snap.child("groupID").getValue(String.class));
                                        //gr.setOwner(snapshot.child("owner").getValue(String.class));
                                        //user.getGroups().get(Integer.parseInt(snap.child("groupID").getValue(String.class))).addPoll();

                                        //i++;
                                        gr.addPoll(newp);
                                        //gr.setMembers(newGroup.getMembers());
                                        //Log.d("myTage",gr.getPolls().get(0).getElements().get(0).getName());

                                    }


                                    for( DataSnapshot mems : snap.child("members").getChildren()){
                                        gr.addMember(mems.getValue(String.class));

                                    }





                                    //user.addGroup(snap.getValue(Group.class));
//                                user.addGroup(gr);
                                    gList.add(gr);
                                    if(un.equals(user.getUserName())){
                                        user.addGroup(gr);
                                    }
                                }
                                gList.add(newGroup);
                                if(un.equals(user.getUserName())){

                                    user.setUserGroups(gList);
                                    Log.d("tag", "Test that here"); //Getting here
                                    databaseReference.child("users").child(user.getUserName()).child("userGroups").setValue(gList);
                                }
                                FirebaseDatabase.getInstance().getReference().child("users").child(un).child("userGroups").setValue(gList);
                                //FirebaseDatabase.getInstance().getReference().child("users").child().child("userGroups").setValue(gList);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

//                    user.addGroup(newGroup);
//                    List<Group> ng = user.getGroups();
//                    databaseReference.child("users").child(user.getUserName()).child("userGroups").setValue(ng);

                    //String newString = search.getText().toString();
//                if () {
//                    String newFriend = search.getText().toString();
//                    user.getFriends().add(newFriend);
//                    List<String> newList = user.getFriends();
//                    databaseReference.child("users").child(user.getUserName()).child("userCategories").child(user.getCurrentCategory().getId()).child("elementList").setValue(newList); // @@@@@@@@@@@@@@@ ANDREW HEREE
//                    arrayAdapter.notifyDataSetChanged();
//                    LinearLayout linearLayout = findViewById(R.id.friendContainer);
//                    CheckBox checkBox = new CheckBox(CreateGroup.this);
//                    checkBox.setId(newList.size() - 1);
//                    checkBox.setText(newFriend);
////                  checkBox.setLayoutParams(buttonparam);
//                    checkBox.setWidth(linearLayout.getWidth());
//                    linearLayout.addView(checkBox);
//                    Toast.makeText(CreateGroup.this, "New Friend Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent (CreateGroup.this, Groups.class);
                    startActivity(intent);
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