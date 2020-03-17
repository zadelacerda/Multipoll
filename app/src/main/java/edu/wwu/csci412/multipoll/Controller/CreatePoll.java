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

public class CreatePoll extends AppCompatActivity {
    public static Controller controller;
    public static User user;

    EditText pollName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_poll);

        /* Fake Database */
        controller = MainActivity.getController();
        user = controller.getUser();

        /* Toolbar setup */
        getSupportActionBar().setTitle("Enter Poll Name");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pollName = (EditText) findViewById(R.id.createPoll);
        Controller.setpName(pollName.getText().toString());

        Button createNext = findViewById(R.id.createNext);
        createNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pollName.equals("")) {
                    Controller.setpName(pollName.getText().toString());
                    Toast.makeText(CreatePoll.this, "New Poll Created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreatePoll.this, ChooseCategory.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CreatePoll.this, "Name Is Empty", Toast.LENGTH_SHORT).show();
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
