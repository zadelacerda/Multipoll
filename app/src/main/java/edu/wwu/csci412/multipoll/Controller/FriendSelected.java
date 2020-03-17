package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import edu.wwu.csci412.multipoll.Model.User;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;

public class FriendSelected extends AppCompatActivity {
    public static Controller controller;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_selected);

        // Toolbar setup
        getSupportActionBar().setTitle("Selected Friend");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get controller variables
        controller = MainActivity.getController();
        user = controller.getUser();
        Button delete = this.findViewById(R.id.deleteFriend);

        FirebaseDatabase.getInstance().getReference().child("users").child(user.getCurrentFriend()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String frFirst = dataSnapshot.child("firstName").getValue(String.class);
                String frLast = dataSnapshot.child("lastName").getValue(String.class);
                TextView fst = FriendSelected.this.findViewById(R.id.firstName);
                TextView lst = FriendSelected.this.findViewById(R.id.lastName);

                fst.setText(frFirst);
                lst.setText(frLast);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> fList = user.getFriends();

                fList.remove(user.getCurrentFriend());

                FirebaseDatabase.getInstance().getReference().child("users").child(user.getUserName()).child("friends").setValue(fList);
            }
        });

    }

    // Back button functionality
    @Override public boolean onSupportNavigateUp() {
        onBackPressed();
        return true; }
}
