package edu.wwu.csci412.multipoll.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import edu.wwu.csci412.multipoll.Model.Category;
import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Element;
import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.Model.User;
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
                    //User cont = MainActivity.getController().getUser();
                    //int found = 0;
                    int userTrue = 0;
                    //DataSnapshot snap = null;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String un = snapshot.child("userName").getValue(String.class);
                        String pa = snapshot.child("password").getValue(String.class);

                        if (un.equals(usern)) {
                            if(pa.equals(pass)) {
                                userTrue = 1;
                                MainActivity.getController().setUser(snapshot.getValue(User.class));
                                User user =  MainActivity.getController().getUser();
                                user.getGroups().clear();
                                user.getFriends().clear();
                                user.getUserCategories().clear();
                                for( DataSnapshot snap : snapshot.child("userGroups").getChildren()){
                                    //Log.d("tag",snap.child("name").getValue(String.class));

                                    user.addGroup(snap.getValue(Group.class));

                                }
                                for( DataSnapshot snap : snapshot.child("friends").getChildren()){
                                    //Log.d("tag",snap.child("name").getValue(String.class));

                                    user.addFriend(snap.getValue(String.class));

                                }
                                int i = 0;
                                List<Element> Elist = new ArrayList<Element>();

                                user.getUserCategories().clear();
                                for( DataSnapshot snap : snapshot.child("userCategories").getChildren()){
                                    //Log.d("tag",snap.child("name").getValue(String.class));
                                    Category cat = new Category();

                                    for(DataSnapshot sn : snap.child("elementList").getChildren()){
                                        //Elist.add(sn.getValue(Element.class));
                                        cat.addElement(sn.getValue(Element.class));
                                    }
                                    cat.setName(snap.child("name").getValue(String.class));
                                    cat.setId(snap.child("id").getValue(String.class));
                                    cat.setUser(snapshot.child("userName").getValue(String.class));
                                    //int cat = Integer.parseInt(snap.child("id").getValue(String.class));
                                    user.addUserCategory(cat);


//                                    user.getUserCategories().get(cat).setElements(Elist);
//                                    Elist.clear();
                                    i++;

                                }
                                //snap = snapshot;
                            }
                        }

                        if(userTrue == 1){


//                            User user =  MainActivity.getController().getUser();
//                            user.getGroups().clear();
//                            user.getFriends().clear();
//                            FirebaseDatabase.getInstance().getReference().child("users").child(usern).child("userGroups").addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    for( DataSnapshot snap : dataSnapshot.getChildren()){
//                                        Log.d("tag",snap.child("name").getValue(String.class));
//
//                                        MainActivity.getController().getUser().addGroup(snap.getValue(Group.class));
//
//                                    }
//
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError error) {
//                                    // Failed to read value
//                                    Log.w(TAG, "Failed to read value.", error.toException());
//                                }
//                            });
//                            FirebaseDatabase.getInstance().getReference().child("users").child(usern).child("friends").addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    for( DataSnapshot snap : dataSnapshot.getChildren()){
//                                        //Log.d("tag",snap.child("name").getValue(String.class));
//
//                                        MainActivity.getController().getUser().addFriend(snap.getValue(String.class));
//
//                                    }
//
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError error) {
//                                    // Failed to read value
//                                    Log.w(TAG, "Failed to read value.", error.toException());
//                                }
//                            });
                            //MainActivity.getController().getUser().setUserGroups(snapshot.child("userGroups").getValue(List.class));
                            Toast.makeText(Signin.this, "Signed In.",
                                    Toast.LENGTH_SHORT).show();
                            userTrue = 0;
                            Intent intent = new Intent(Signin.this, MainActivity.class);
                            startActivity(intent);
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

//            mAuth.signInWithEmailAndPassword(usern, pass)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                Log.d(TAG, "signInWithEmail:success");
//                                FirebaseUser user = mAuth.getCurrentUser();
//
//                                txt.setText(user.getDisplayName());
//                                FirebaseDatabase.getInstance().getReference().child("users").child(usern).addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                        // This method is called once with the initial value and again
//                                        // whenever data at this location is updated.
//                                        MainActivity.getController().setUser(dataSnapshot.getValue(User.class));
//                                        String value = dataSnapshot.getValue(String.class);
//                                        Log.d(TAG, "Value is: " + value);
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError error) {
//                                        // Failed to read value
//                                        Log.w(TAG, "Failed to read value.", error.toException());
//                                    }
//                                });
//                                Intent intent = new Intent(Signin.this, MainActivity.class);
//                                startActivity(intent);
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                Toast.makeText(Signin.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                                updateView(null);
//                            }
//
//
//                        }
//                    });
        }
    }

    public void newUser(View v){
        Intent myIntent = new Intent(this, Signup.class);
        this.startActivity(myIntent);
    }

    public void updateView( FirebaseUser newUser ) {

    }
}
