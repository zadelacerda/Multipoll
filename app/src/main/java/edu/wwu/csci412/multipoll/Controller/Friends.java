package edu.wwu.csci412.multipoll.Controller;


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

import androidx.appcompat.app.AppCompatActivity;

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
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.listGroups(user.getGroups()));
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
                switch(foundUser) {
                    case 1:
                        addFriendOutput = "Success! Added new friend.";
                        break;
                    case 0:
                        addFriendOutput = "Could not find username";
                        break;
                }

                Toast.makeText( Friends.this, addFriendOutput, Toast.LENGTH_SHORT).show( );
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
