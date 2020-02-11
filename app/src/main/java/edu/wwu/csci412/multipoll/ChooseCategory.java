package edu.wwu.csci412.multipoll;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import java.lang.reflect.Array;

import Data.Controller;

public class ChooseCategory  extends AppCompatActivity {

    public static Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosecategory);
        controller = MainActivity.getController();

        ArrayAdapter<String> arrayAdapter;
        ListView lv = findViewById(R.id.categories);

//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, controller.getUser().getUserCategories());
        lv.setAdapter(arrayAdapter);

    }

}
