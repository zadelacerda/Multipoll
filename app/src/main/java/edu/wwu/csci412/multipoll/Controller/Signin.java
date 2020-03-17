package edu.wwu.csci412.multipoll.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import edu.wwu.csci412.multipoll.R;

public class Signin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private DatabaseReference dbReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signin);
        mAuth = FirebaseAuth.getInstance();
        //dbReference = FirebaseDatabase.getReference("users");

    }
    public void Login(View v){
//        final TextView txt = (TextView) findViewById(R.id.enterpass);
//        final EditText textu = (EditText) findViewById(R.id.edituser);
//        EditText textp = (EditText) findViewById(R.id.editpass);
//        String user = (String) textu.getText().toString();
//        String pass = (String) textp.getText().toString();
//        if(!user.equals("") && !pass.equals("")) {
//            mAuth.signInWithEmailAndPassword(user, pass)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                Log.d(TAG, "signInWithEmail:success");
//                                FirebaseUser user = mAuth.getCurrentUser();
//
//                                txt.setText(user.getDisplayName());
                                Intent intent = new Intent(Signin.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.anim_enter_main, R.anim.anim_exit_signin);
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                Toast.makeText(Signin.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                                updateView(null);
//                            }

                            // ...
//                        }
//                    });
 //              }
    }

    public void newUser(View v){
        Intent myIntent = new Intent(this, Signup.class);
        this.startActivity(myIntent);
    }

    public void updateView( FirebaseUser newUser ) {

    }
}
