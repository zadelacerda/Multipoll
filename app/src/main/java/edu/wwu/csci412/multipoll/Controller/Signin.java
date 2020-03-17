package edu.wwu.csci412.multipoll.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import edu.wwu.csci412.multipoll.Model.Category;
import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Element;
import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.Model.Poll;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.FirebaseDatabase;;
import edu.wwu.csci412.multipoll.R;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class Signin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private DatabaseReference dbReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MainActivity.controller = new Controller();

        setContentView(R.layout.account_signin);
        mAuth = FirebaseAuth.getInstance();
        //dbReference = FirebaseDatabase.getReference("users");

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void Login(View v){
        TextView txt = (TextView) findViewById(R.id.enterpass);
        EditText textu = (EditText) findViewById(R.id.edituser);
        EditText textp = (EditText) findViewById(R.id.editpass);
        String usern = (String) textu.getText().toString();
        String pass = (String) textp.getText().toString();
        if(!usern.equals("") && !pass.equals("")) {

            FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    EditText textu = (EditText) findViewById(R.id.edituser);
                    EditText textp = (EditText) findViewById(R.id.editpass);
                    String usern = (String) textu.getText().toString();
                    String pass = (String) textp.getText().toString();
                    int userTrue = 0;

                    //Cycles through the Users
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String un = snapshot.child("userName").getValue(String.class);
                        String pa = snapshot.child("password").getValue(String.class);

                        //Checks if username and pass are correct
                        if (un.equals(usern)) {
                            if(pa.equals(pass)) {
                                userTrue = 1;
                                MainActivity.getController().setUser(snapshot.getValue(User.class));
                                User user =  MainActivity.getController().getUser();
                                user.getGroups().clear();
                                user.getFriends().clear();
                                user.getUserCategories().clear();

                                //Fills model with groups
                                for( DataSnapshot snap : snapshot.child("userGroups").getChildren()){
                                    Group gr = new Group();
                                    gr.setGroupID(snap.child("groupID").getValue(String.class));
                                    gr.setName(snap.child("name").getValue(String.class));
                                    //Go through polls
                                    for( DataSnapshot snapp : snap.child("polls").getChildren()){
                                        Poll newp = new Poll();
                                        newp.setOwner(snapp.child("owner").getValue(String.class));
                                        newp.setName(snapp.child("name").getValue(String.class));
                                        newp.setStatus(snapp.child("status").getValue(Boolean.class));
                                        newp.setTarget(snapp.child("target").getValue(String.class));
                                        newp.setPollID(snapp.child("pollID").getValue(String.class));
                                        List<Element> Elist = new ArrayList<>();
                                        //Fills model with Elements
                                        List<String> voInit = new ArrayList<>();
                                        //Add NotVoted list to Polls
                                        for(DataSnapshot s : snapp.child("usersNotVoted").getChildren()){
                                            voInit.add(s.getValue(String.class));

                                        }
                                        newp.setUsersNotVoted(voInit);
                                        //Add element list to Polls
                                        for(DataSnapshot sn : snapp.child("elements").getChildren()){
                                            Element El = new Element(sn.child("name").getValue(String.class), sn.child("id").getValue(int.class));
                                            //Log.d("test", Integer.toString(sn.child("voteCounter").getValue(int.class)));
                                            El.setVoteCounter((sn.child("voteCounter").getValue(int.class)));


                                            Elist.add(El);
                                            Log.d("myTage",sn.child("name").getValue(String.class));
                                        }
                                        newp.setElements(Elist);


                                        gr.addPoll(newp);

                                    }
                                    //Add members to groups
                                    for(DataSnapshot snappy : snap.child("members").getChildren()){
                                        gr.addMember(snappy.getValue(String.class));
                                    }


                                    user.addGroup(gr);

                                }
                                //Fills model with friends
                                for( DataSnapshot snap : snapshot.child("friends").getChildren()){
                                    user.addFriend(snap.getValue(String.class));

                                }
                                int i = 0;
                                user.getUserCategories().clear();
                                //Fills model with Categories
                                for( DataSnapshot snap : snapshot.child("userCategories").getChildren()){
                                    Category cat = new Category();
                                    //Fills model with Elements
                                    for(DataSnapshot sn : snap.child("elements").getChildren()){
                                        cat.addElement(sn.getValue(Element.class));
                                    }
                                    cat.setName(snap.child("name").getValue(String.class));
                                    cat.setId(snap.child("id").getValue(String.class));
                                    cat.setUser(snapshot.child("userName").getValue(String.class));
                                    user.addUserCategory(cat);

                                    i++;

                                }
                                i = 0;

                            }
                        }
                        //If user exists in database
                        if(userTrue == 1){


                            Toast.makeText(Signin.this, "Signed In.",
                                    Toast.LENGTH_SHORT).show();
                            userTrue = 0;
                            Intent intent = new Intent(Signin.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_enter_main, R.anim.anim_exit_signin);
                            return;
                        }

                    }
                    Toast.makeText(Signin.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }
    }

    public void newUser(View v){
        Intent myIntent = new Intent(this, Signup.class);
        this.startActivity(myIntent);
    }

    public void updateView( FirebaseUser newUser ) {

    }
}
