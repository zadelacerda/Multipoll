package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import edu.wwu.csci412.multipoll.Model.Category;
import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;

public class ChooseGroup extends AppCompatActivity {
    private ArrayAdapter<String> arrayAdapter;

    public static Controller controller;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_group);

        /* Connect XML with Java objects */
        ListView list = findViewById(R.id.groupList);
        final EditText search = findViewById(R.id.searchGroup);
        FloatingActionButton fab = findViewById(R.id.addGroup);

        /* Fake Database */
        controller = MainActivity.getController();
        user = controller.getUser();

        /* Toolbar setup */
        getSupportActionBar().setTitle("Choose Group");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* Search bar setup */
        search.setHint("Search Groups");
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (ChooseGroup.this).arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /* Group list view setup */
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.listGroups(user.getGroups()));
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String TempListViewClickedValue = user.listGroups(user.getGroups()).get(position);
                Intent intent = new Intent (ChooseGroup.this, ChooseCategory.class);
                user.setCurrentGroup(user.getGroup(TempListViewClickedValue));
                startActivity(intent);
            }
        });

        /* Floating Button */
            fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText gname = (EditText) findViewById(R.id.editgn);
                String groupName = search.getText().toString();
                if (!groupName.equals("")) {
                    TextView tv = (TextView) findViewById(R.id.newgroup);
                    Group newg = new Group(groupName);
                    String gId = FirebaseDatabase.getInstance().getReference().push().getKey();
                    arrayAdapter.add(newg.getName());
                    ListView list = findViewById(R.id.groupList);
                    list.setAdapter(arrayAdapter);
                    user.addGroup(newg);
                    List<Group> ng = user.getGroups();
                    newg.setGroupID(gId);
                    FirebaseDatabase.getInstance().getReference().child("users").child(user.getUserName()).child("userGroups").setValue(ng);
                    Toast.makeText(ChooseGroup.this, "New Group Added", Toast.LENGTH_SHORT).show();

//              Intent myIntent = new Intent(this, NewGroup.class);
//              this.startActivity(myIntent);
                } else {
                    Toast.makeText(ChooseGroup.this, "Name Is Empty", Toast.LENGTH_SHORT).show();
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
