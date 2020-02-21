package edu.wwu.csci412.multipoll.Controller;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.R;

public class GroupSelected extends FragmentActivity {

    public static Controller controller;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups_selected);
        controller = MainActivity.getController();
      
        // Display polls of selected groups in the fragment
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.displayPolls, new PollDisplay()).commit();
        }
    }
}
