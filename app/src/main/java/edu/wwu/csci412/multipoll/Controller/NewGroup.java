package edu.wwu.csci412.multipoll.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Group;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;

public class NewGroup extends AppCompatActivity {
    private DatabaseReference mFirebaseRef;
    private FirebaseDatabase mFirebaseDatabase;
    public static User user;
    public static Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_group);

        // Get Friends
        controller = MainActivity.getController();
        user = controller.getUser();

        // Initialize views
        SearchView searchFriends = findViewById(R.id.searchFriends);
        searchFriends.setFocusableInTouchMode(true);
        ArrayAdapter<String> arrayAdapter;
        ListView lv = findViewById(R.id.friendsList);

        // Make group list
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.listGroups(user.getGroups()));
        lv.setAdapter(arrayAdapter);

        // Select group
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String TempListViewClickedValue = user.listGroups(user.getGroups()).get(position);
                Intent intent = new Intent (NewGroup.this, FriendSelected.class);
                //user.setCurrentGroup(user.getGroup(TempListViewClickedValue));
                startActivity(intent);
            }
        });
    }

    public void onStart( ) {
        super.onStart( );
        controller = MainActivity.getController();
        user = controller.getUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseRef = mFirebaseDatabase.getReference("groups");
    }

    public void createGroup(View v){
        EditText gname = (EditText) findViewById(R.id.editgn);
        String groupName = gname.getText().toString();
        Group newg = new Group(groupName);
        String gId =  mFirebaseRef.push().getKey();
        user.addGroup(newg);
        newg.setGroupID(gId);
        mFirebaseRef.child(gId).setValue(newg);
        //String name = mFirebaseRef.equalTo("groups").equalTo("NewGroup").equalTo("name");
        Intent intent = new Intent (NewGroup.this, Groups.class);
        startActivity(intent);


        //User test = MainActivity.dbcontroller.getDataByUser(groupName);

        //gname.setText(test.getPassword());

    }
}
