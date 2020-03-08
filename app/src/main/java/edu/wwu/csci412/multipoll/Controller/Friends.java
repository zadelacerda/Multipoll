package edu.wwu.csci412.multipoll.Controller;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;

public class Friends extends AppCompatActivity {
    public static Controller controller;
    public static User user;

    SearchView searchFriends;
    EditText addFriend;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);

        // Set toolbar
        getSupportActionBar().setTitle("Friends");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        controller = MainActivity.getController();
        user = controller.getUser();

        // Initialize views
        searchFriends = findViewById(R.id.searchFriends);
        addFriend = findViewById(R.id.addFriend);
        searchFriends.setFocusableInTouchMode(true);
        addFriend.setFocusableInTouchMode(true);
        addBtn = findViewById(R.id.addBtn);
        ArrayAdapter<String> arrayAdapter;
        ListView lv = findViewById(R.id.friends);

        // Make group list
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.getFriends());
        lv.setAdapter(arrayAdapter);

        // Select group
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String TempListViewClickedValue = user.listGroups(user.getGroups()).get(position);
                Intent intent = new Intent (Friends.this, FriendSelected.class);
                //user.setCurrentGroup(user.getGroup(TempListViewClickedValue));
                startActivity(intent);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int foundUser = 0;
                String addFriendOutput = "";
                FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    int userFound = 0;
                    int existingFr = 0;
                    //EditText newfr = (EditText) findViewById(R.id.edituser);
                    String usern = (String) addFriend.getText().toString();

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User currentUser = MainActivity.getController().getUser();
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            String un = ds.child("userName").getValue(String.class);
                            if (un.equals(usern)) {
                                userFound = 1;
                                if(!currentUser.getFriends().contains(usern)) {
                                    currentUser.addFriend(usern);
                                    Toast.makeText(Friends.this, "New Friend Added", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Friends.this, "Invalid Username", Toast.LENGTH_SHORT).show();

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
                                Toast.makeText( Friends.this, "Added" + usern, Toast.LENGTH_SHORT).show( );
                                //nf.add(usern);
                                FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUserName()).child("friends").setValue(nf);
//                                Intent intent = new Intent (NewFriend.this, Friends.class);
//                                startActivity(intent);
                            }
                        }
                        userFound = 0;
                        existingFr = 0;

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                addFriend.setText("");

            }
        });


    }

    // Back functionality
    @Override public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // So the editText don't auto focus
    @Override
    protected void onResume() {
        super.onResume();
        searchFriends.clearFocus();
        addFriend.clearFocus();
    }
}
