package edu.wwu.csci412.multipoll.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Group;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.Model.UserInfo;
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
    }

    public void onStart( ) {
        super.onStart( );
        controller = MainActivity.getController();
        user = controller.getUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseRef = mFirebaseDatabase.getReference();
    }

    public void createGroup(View v){
        EditText gname = (EditText) findViewById(R.id.editgn);
        String groupName = gname.getText().toString();
        if(!groupName.equals("")) {
            TextView tv = (TextView) findViewById(R.id.newgroup);
            Group newg = new Group(groupName);
            String gId = mFirebaseRef.push().getKey();
            user.addGroup(newg);
            List<Group> ng = user.getGroups();
            newg.setGroupID(gId);
            mFirebaseRef.child("users").child(user.getUserName()).child("userGroups").setValue(ng);
            //String name = mFirebaseRef.equalTo("groups").equalTo("NewGroup").equalTo("name");
            Intent intent = new Intent(NewGroup.this, Groups.class);
            startActivity(intent);
        }


        //UserInfo test = MainActivity.dbcontroller.getDataByUser(groupName);

        //tv.setText(test.getPassword());







    }
}
