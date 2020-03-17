package edu.wwu.csci412.multipoll.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.speech.RecognizerIntent;
import java.util.List;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Poll;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.R;

public class MainActivity extends AppCompatActivity {
    public static Controller controller;
    public static DatabaseController dbcontroller;
    private static User user;

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    public static Boolean mLocationPermissionGranted = false;

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
                Intent intent = new Intent(MainActivity.this, ChooseGroup.class);
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
                        intent = new Intent(MainActivity.this, Groups.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        if (user.getGroups().size() != 0) {
            final Group newGroup = user.getGroups().get(user.getGroups().size() - 1);

            final Button recentGroup = this.findViewById(R.id.recentGroup);
            recentGroup.setText(newGroup.getName());
            recentGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.setCurrentGroup(newGroup);
                    Intent intent = new Intent(MainActivity.this, GroupSelected.class);
                    startActivity(intent);
                }
            });

            if(newGroup.getPolls().size() != 0) {
                final Poll newPoll = newGroup.getPolls().get(newGroup.getPolls().size() - 1);
                Button recentPoll = this.findViewById(R.id.recentPoll);
                recentPoll.setText(newPoll.getName());
                recentPoll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.setCurrentPoll(newPoll);
                        Intent intent = new Intent(MainActivity.this, PollResults.class);
                        startActivity(intent);

                    }
                });
            }
        }
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
                overridePendingTransition(R.anim.anim_enter_signin, R.anim.anim_exit_main);
                return true;

            case R.id.Voice:
                displaySpeechRecognizer();



            default:
                return super.onOptionsItemSelected( item );
        }
    }

    private static final int SPEECH_REQUEST_CODE = 0;

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        Intent newv;
        //Voice Commands
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            List<Group> groups = user.getGroups();

            for (int i = 0; i < groups.size(); i++){
                if(spokenText.equals(groups.get(i).getName().toLowerCase())){
                    String TempListViewClickedValue = user.listGroups(user.getGroups()).get(i);
                    Intent intent = new Intent (MainActivity.this, GroupSelected.class);
                    user.setCurrentGroup(user.getGroup(TempListViewClickedValue));
                    startActivity(intent);
                }
            }

            if(spokenText.equals("groups")){
                newv = new Intent (MainActivity.this, Groups.class);
                startActivity(newv);
            }
            else if(spokenText.equals("friends")){
                newv = new Intent (MainActivity.this, Friends.class);
                startActivity(newv);
            }
            else if(spokenText.equals("new poll")){
                newv = new Intent (MainActivity.this, ChooseGroup.class);
                startActivity(newv);
            }
            else if(spokenText.equals("add group")){
                newv = new Intent (MainActivity.this, CreateGroup.class);
                startActivity(newv);
            }
            else if(spokenText.equals("add friend")){
                newv = new Intent (MainActivity.this, NewFriend.class);
                startActivity(newv);
            }
            else if(spokenText.equals("close app")){
                MainActivity.this.finish();
                finishAffinity();
                System.exit(0);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Controller getController() {
        return controller;
    }

}
