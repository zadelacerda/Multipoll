package edu.wwu.csci412.multipoll.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.R;

public class MainActivity extends AppCompatActivity {
    public static Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        controller = new Controller();

        Button createPoll = this.findViewById(R.id.createPoll);
        Button Groups = this.findViewById(R.id.Groups);
//        Button SignUp = this.findViewById(R.id.)

        createPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, ChooseGroup.class);
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
