package edu.wwu.csci412.multipoll.Controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;

public class GroupSelected extends AppCompatActivity {

    public static Controller controller;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups_selected);
        controller = MainActivity.getController();
        User user = controller.getUser();
        Group currentGroup = user.getCurrentGroup();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Display polls of selected groups in the fragment
        if(savedInstanceState == null){
            getSupportActionBar().setTitle(currentGroup.getName());
            getSupportFragmentManager().beginTransaction().replace(R.id.displayPolls, new PollDisplay()).commit();
        }
    }

    @Override public boolean onSupportNavigateUp() {
        onBackPressed();
        return true; }
}
