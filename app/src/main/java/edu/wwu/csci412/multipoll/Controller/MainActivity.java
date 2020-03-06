package edu.wwu.csci412.multipoll.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;

public class MainActivity extends AppCompatActivity {
    public static Controller controller;
    public static DatabaseController dbcontroller;
    private static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new Controller();
        dbcontroller = new DatabaseController();
        user = controller.getUser();

        // Toolbar setup
        getSupportActionBar().setTitle("");

        Button createPoll = this.findViewById(R.id.createPoll);
        Button Groups = this.findViewById(R.id.Groups);

        createPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, ChooseGroup.class);
                startActivity(intent);
            }
        });
        //Page depends on if there are groups
        Groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int noGroup = 1;
                if (!user.getGroups().isEmpty()) {
                    noGroup = 0;
                }
                Intent intent;
                switch (noGroup) {
                    case 0:
                        intent = new Intent(MainActivity.this, Groups.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, NewGroup.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch ( id ) {
            case R.id.logout:
                intent = new Intent (MainActivity.this, Signin.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    public static Controller getController() {
        return controller;
    }

}
