package edu.wwu.csci412.multipoll.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.Model.UserInfo;
import edu.wwu.csci412.multipoll.R;


public class Signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private DatabaseReference mFirebaseRef;
    private FirebaseDatabase mFirebaseDatabase;
    private String uId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signup);

        /*Initialize Database info*/
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseRef = mFirebaseDatabase.getReference("users");
    }

    public void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    /*Onclick method to create a new authentication account and add profile to database*/
    public void createAccount( View v){

        EditText texte = (EditText) findViewById(R.id.editemail);
        EditText textp = (EditText) findViewById(R.id.editpass);
        EditText textu = (EditText) findViewById(R.id.edituser);
        final String email = (String) texte.getText().toString();
        final String username = (String) textu.getText().toString();
        final String pass = (String) textp.getText().toString();

        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User cont =MainActivity.getController().getUser();
                int found = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String value = snapshot.child("userName").getValue(String.class);

                    if(value.equals(username)){
                        found = 1;

                    }

                }
                if(found == 0){
                    User newuser = new User(0);
                    newuser.setPassword(pass);
                    newuser.setUserID(uId);
                    newuser.setUserName(username);
                    cont.setPassword(pass);
                    cont.setUserID(uId);
                    cont.setUserName(username);
                    /*Add new user to database*/
                    mFirebaseRef.child(username).setValue(newuser);
                    Intent intent = new Intent (Signup.this, MainActivity.class);
                    startActivity(intent);
                }
                return;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

//        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    /* Sign in success, update UI with the signed-in user's information*/
//                    Log.d(TAG, "createUserWithEmail:success");
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    //User newu = new User(0);
//                    uId = user.getUid();
//                    User newuser = new User(0);
//                    UserProfileChangeRequest change = new UserProfileChangeRequest.Builder()
//                            .setDisplayName(username).build();
//                    user.updateProfile(change);
//                    /*Add the details of the new user into the object*/
//
//                    newuser.setPassword(pass);
//                    newuser.setUserID(uId);
//                    newuser.setUserName(username);
//                    /*Add new user to database*/
//                    mFirebaseRef.child(username).setValue(newuser);
//                    updatedView(user);
//                    Intent intent = new Intent (Signup.this, MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    /* If sign in fails, display a message to the user.*/
//                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                    Toast.makeText(Signup.this, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show();
//                    updatedView(null);
//                }
//
//            }
//        });
    }


    public void updatedView(FirebaseUser newUser){

    }
}
