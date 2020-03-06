package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.polls_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch ( id ) {
            case R.id.addPoll:
                intent = new Intent(GroupSelected.this, ChooseCategory.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }
    @Override public boolean onSupportNavigateUp() {
        onBackPressed();
        return true; }
}
