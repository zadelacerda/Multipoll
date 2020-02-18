package edu.wwu.csci412.multipoll.Controller;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.R;

public class ChooseCategory  extends AppCompatActivity {

    public static Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_category);
        controller = MainActivity.getController();

        ArrayAdapter<String> arrayAdapter;
        ListView lv = findViewById(R.id.categories);

//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, controller.getUser().getUserCategories());
        lv.setAdapter(arrayAdapter);

//        lv.setOnItemClickListener((parent, view, position, id) -> {
//            String Temp
//        });

    }

}
