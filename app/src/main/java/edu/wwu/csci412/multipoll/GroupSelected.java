package edu.wwu.csci412.multipoll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import Data.Controller;

public class GroupSelected extends FragmentActivity {

    public static Controller controller;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupselected);
        controller = MainActivity.getController();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.displayPolls, new PollDisplay()).commit();
        }
    }
}
