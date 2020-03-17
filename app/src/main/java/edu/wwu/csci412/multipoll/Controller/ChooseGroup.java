package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
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
    private ListView list;
    private EditText search;

    public static Controller controller;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_group);

        /* Connect XML with Java objects */
        list = findViewById(R.id.groupList);
        search = findViewById(R.id.searchGroup);

        /* Fake Database */
        controller = MainActivity.getController();
        user = controller.getUser();

        /* Toolbar setup */
        getSupportActionBar().setTitle("Choose Group");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* Search bar setup */
        search.setHint("Enter New Poll Name");

    }

    @Override
    protected void onResume() {
        super.onResume();
        /* Group list view setup */
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.listGroups(user.getGroups()));
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String groupName = search.getText().toString();
                if (!groupName.equals("")) {
                    EditText pollName = (EditText) findViewById(R.id.searchGroup);
                    Controller.setpName(pollName.getText().toString());
                    String TempListViewClickedValue = user.listGroups(user.getGroups()).get(position);
                    Intent intent = new Intent(ChooseGroup.this, ChooseCategory.class);
                    user.setCurrentGroup(user.getGroup(TempListViewClickedValue));
                    startActivity(intent);
                    Toast.makeText(ChooseGroup.this, "New Poll Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChooseGroup.this, "Name Is Empty", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choose_group_menu, menu);
        return true;
    }
    //Menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch ( id ) {
            case R.id.create:
                intent = new Intent (ChooseGroup.this, CreateGroup.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    // Back functionality
    @Override public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
