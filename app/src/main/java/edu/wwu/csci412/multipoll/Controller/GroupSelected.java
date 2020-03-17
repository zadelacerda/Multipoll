package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;

public class GroupSelected extends AppCompatActivity {

    public static Controller controller;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_selected);
        controller = MainActivity.getController();
        User user = controller.getUser();
        Group currentGroup = user.getCurrentGroup();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Display polls of selected groups in the fragment
        if(savedInstanceState == null){
            getSupportActionBar().setTitle(currentGroup.getName());
            getSupportFragmentManager().beginTransaction().replace(R.id.displayPolls, new PollDisplay()).commit();
        }

        Button maps = findViewById(R.id.mapBtn);
        maps.setText("MAPS");
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (GroupSelected.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.polls_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch ( id ) {
            case R.id.addPoll:
                intent = new Intent(GroupSelected.this, CreatePoll.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }
    @Override public boolean onSupportNavigateUp() {
        Intent intent = new Intent(GroupSelected.this, Groups.class);
        startActivity(intent);
        return true; }
}
