package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;

public class Groups extends AppCompatActivity {
    public static Controller controller;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups);
        controller = MainActivity.getController();
        user = controller.getUser();

        ArrayAdapter<String> arrayAdapter;
        ListView lv = findViewById(R.id.groups);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.listGroups(user.getGroups()));
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String TempListViewClickedValue = user.listGroups(user.getGroups()).get(position);
                Intent intent = new Intent (Groups.this, GroupSelected.class);
                user.setCurrentGroup(user.getGroup(TempListViewClickedValue));
                startActivity(intent);
            }
        });
    }
}
