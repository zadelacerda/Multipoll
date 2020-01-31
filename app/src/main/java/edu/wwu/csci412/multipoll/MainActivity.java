package edu.wwu.csci412.multipoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Data.Controller;

public class MainActivity extends AppCompatActivity {
    public static Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new Controller();

        Button createPoll = this.findViewById(R.id.createPollMain);
        Button Groups = this.findViewById(R.id.Groups);

        createPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, ChooseCategory.class);
                startActivity(intent);
            }
        });

        Groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, Groups.class);
                startActivity(intent);
            }
        });
    }

    public static Controller getController() {
        return controller;
    }

}
