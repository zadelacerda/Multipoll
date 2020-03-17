package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Element;
import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.Model.Poll;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;


public class PollResults extends AppCompatActivity {

    public static Controller controller;
    private static User user;
    private static Group currentGroup;
    public static int extraIds;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poll_results);

        controller = MainActivity.getController();
        user = controller.getUser();
        currentGroup = user.getCurrentGroup();

        getSupportActionBar().setTitle(user.getCurrentPoll().getName());

        // Connect GroupSelected activity to this fragment somehow
        ArrayAdapter<String> arrayAdapter;
        ListView lv = findViewById(R.id.pollOptions);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.getCurrentPoll().listElements(new ArrayList<Element>(user.getCurrentPoll().getElements())));
        lv.setAdapter(arrayAdapter);

        // List of elements for current category
        final ArrayList<Element> pollElements = new ArrayList<Element>( user.getCurrentPoll().getElements());

        // Set up vote array adapters nad list views
        final ArrayAdapter<String> voteList;
        ArrayAdapter<String> slashList;
        ArrayAdapter<String> maxVotes;
        ListView vL = findViewById(R.id.voteCounterList);
        ListView sL = findViewById(R.id.slashList);
        ListView mV = findViewById(R.id.maxVotes);

        // Init list of checkboxes
        LinearLayout checkList = this.findViewById(R.id.checkContainer);

        // Layout
        LinearLayout voteLayout = this.findViewById(R.id.voteCounters);
        final ArrayList<String> voteCounters = new ArrayList<>();
        final ArrayList<String> slashes = new ArrayList<>();
        final ArrayList<String> totalVotes = new ArrayList<>();
        final List<CheckBox> checks = new ArrayList<>();
        int totalVote = currentGroup.getMembers().size();
        final RadioGroup rg = new RadioGroup(this);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.setMargins(0, 22, 0, 22);
        for (final Element element : pollElements) {

                RadioButton checkBox = new RadioButton(this);
                checkBox.setId(element.getId());
                checkBox.setLayoutParams(lparams);
                rg.setLayoutParams(lparams);
                rg.addView(checkBox);

            // Add element vote count to voteCounterList
            voteCounters.add(Integer.toString(element.getVoteCounter()));
            // Create slashes list
            slashes.add("/");
            int maxVote = currentGroup.getMembers().size();
            // Create total votes list
            totalVotes.add(Integer.toString(maxVote));

        }

        //If the user has voted
        if(user.getCurrentPoll().hasVoted(user.getUserName())) {
            for (int i = 0; i < pollElements.size(); i++) {
                rg.getChildAt(i).setVisibility(View.GONE);
            }
        }

            voteList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, voteCounters);
        vL.setAdapter(voteList);

        slashList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, slashes);
        sL.setAdapter(slashList);
        maxVotes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, totalVotes);
        mV.setAdapter(maxVotes);
        if(!user.getCurrentPoll().hasVoted(user.getUserName())) { //If the user has not voted
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    //Find the group index for each user in the group
                    pollElements.get(rg.getCheckedRadioButtonId()).addVote();
                    user.getCurrentPoll().remUserVoted(user.getUserName());
                    int b = 0;
                    for (int j = 0; j < user.getGroups().size(); j++){
                        if(user.getGroups().get(j).getGroupID().equals(user.getCurrentGroup().getGroupID())){
                            b = j;
                        }
                    }
                    //Update the UsersNotVoted List
                    FirebaseDatabase.getInstance().getReference().child("users").child(user.getUserName()).child("userGroups")
                            .child(Integer.toString(b)).child("polls").child(user.getCurrentPoll().getPollID()).child("usersNotVoted")
                            .setValue(user.getCurrentPoll().getUsersNotVoted());
                    //For each member in the group
                    for (int i = 0; i < user.getCurrentGroup().getMembers().size(); i++) {
                        FirebaseDatabase.getInstance().getReference().child("users").child(user.getCurrentGroup().getMembers().get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String nme = dataSnapshot.child("userName").getValue(String.class);
                                //Update their groups
                                for (DataSnapshot snap : dataSnapshot.child("userGroups").getChildren()) {
                                    //If correct group
                                    if (snap.child("groupID").getValue(String.class).equals(user.getCurrentGroup().getGroupID())){
                                        List<Poll> pList = new ArrayList<>();
                                        //For all polls
                                        for( DataSnapshot snapp : snap.child("polls").getChildren()){
                                            //Double check against poll name and target group
                                            if(snapp.child("name").getValue(String.class).equals(user.getCurrentPoll().getName())){
                                                if(snapp.child("target").getValue(String.class).equals(user.getCurrentGroup().getGroupID())){
                                                    int vc = snapp.child("elements").child(Integer.toString(rg.getCheckedRadioButtonId())).child("voteCounter").getValue(int.class);
                                                    int vcInt = vc;
                                                    //Add vote to everyones database
                                                    FirebaseDatabase.getInstance().getReference().child("users").child(nme)
                                                            .child("userGroups").child(snap.getKey()).child("polls")
                                                            .child(snapp.getKey()).child("elements").child(Integer.toString(rg.getCheckedRadioButtonId())).child("voteCounter")
                                                            .setValue((vcInt + 1));
                                                }
                                            }

                                        }

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUserName()).child("userGroups")
                                .child(Integer.toString(b)).child("polls").child(user.getCurrentPoll().getPollID()).child("elements")
                                .child(Integer.toString(rg.getCheckedRadioButtonId())).child("voteCounter").setValue(user.getCurrentPoll().getElements().get(rg.getCheckedRadioButtonId()).getVoteCounter());
                    //Change the visibility of the other radiobuttons
                    for (int i = 0; i < pollElements.size(); i++) {
                        if (i != (rg.getCheckedRadioButtonId())) {
                            rg.getChildAt(i).setVisibility(View.GONE);
                        }

                    }
                    finish();
                    startActivity(getIntent());
                }

            });
        }
        //If the user has voted
        else {

        }

        checkList.addView(rg);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.results_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch ( id ) {
            case R.id.group:
                intent = new Intent(PollResults.this, GroupSelected.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

}
