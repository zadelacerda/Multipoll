package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import edu.wwu.csci412.multipoll.Model.Category;
import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Element;
import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.Model.Poll;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;


public class ChooseElements extends AppCompatActivity {
    private ArrayAdapter<Element> arrayAdapter;
    private EditText search;

    public static Controller controller;
    public static User user;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    List<CheckBox> checks = new ArrayList<>();

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choose_element_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch ( id ) {
            case R.id.sendPoll:
                createPoll();
                intent = new Intent(ChooseElements.this, PollResults.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_sendpoll, R.anim.anim_enter_pollresults);
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_elements);

        /* Connect XML with Java objects */
        search = findViewById(R.id.searchElement);
        LinearLayout linearLayout = findViewById(R.id.elementContainer);
        FloatingActionButton fab = findViewById(R.id.addElement);

        /* Fake Database */
        controller = MainActivity.getController();
        user = controller.getUser();

        /* Real Database */
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        // Toolbar setup
        getSupportActionBar().setTitle(user.getCurrentCategory().getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* Search view setup */
        search.setHint("Search/Add Element");
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (ChooseElements.this).arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /* List of elements for current category */
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.getCurrentCategory().getElements());
        checks.clear();
        for (int i = 0; i < user.getCurrentCategory().getElements().size(); i++) {
            Element element = arrayAdapter.getItem(i);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(element.getId());
            checkBox.setText(element.getName());

            checks.add(checkBox);
            checkBox.setWidth(linearLayout.getWidth());
            linearLayout.addView(checkBox);
        }

        /* Floating Add Button Setup */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newString = search.getText().toString();
                if (!newString.equals("")) {
                    Element newElement = new Element(search.getText().toString(), arrayAdapter.getCount());
                    arrayAdapter.add(newElement);
                    newElement.setId(user.getCurrentCategory().getElements().size());
                    user.getCurrentCategory().addElement(newElement);
                    List<Element> newList = user.getCurrentCategory().getElements();
                    databaseReference.child("users").child(user.getUserName()).child("userCategories").child(user.getCurrentCategory().getId()).child("elements").setValue(newList);

                    arrayAdapter.notifyDataSetChanged();
                    LinearLayout linearLayout = findViewById(R.id.elementContainer);
                    CheckBox checkBox = new CheckBox(ChooseElements.this);
                    checkBox.setId(newElement.getId());
                    checkBox.setText(newElement.getName());
                    checkBox.setWidth(linearLayout.getWidth());
                    checks.add(checkBox);
                    linearLayout.addView(checkBox);
                    Toast.makeText(ChooseElements.this, "New Element Added", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                } else {
                    Toast.makeText(ChooseElements.this, "Name Is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Creating a new Poll
    public void createPoll(){
        final Poll newPoll = new Poll(user.getUserName(), Controller.getpName());
            int ind = 0;
            //Find which elements are checked
        for (int i = 0; i < checks.size(); i++) {
            if(checks.get(i).isChecked()){

                newPoll.addElement(user.getCurrentCategory().getElements().get(i));
                newPoll.getElements().get(ind).setId(ind);
                ind++;
            }
        }


        newPoll.setTarget(user.getCurrentGroup().getGroupID());
        List<Poll> newList = user.getCurrentGroup().getPolls();
        newPoll.setPollID(Integer.toString(newList.size()));
        newPoll.setUsersNotVoted(user.getCurrentGroup().getMembers());
        newList.add(newPoll);
        user.setCurrentPoll(newPoll);
        user.getCurrentGroup().setPolls(newList);
        //Loop through all the members in the group, adding the poll for all of them
        for (int i = 0; i < user.getCurrentGroup().getMembers().size(); i++) {
            FirebaseDatabase.getInstance().getReference().child("users").child(user.getCurrentGroup().getMembers().get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String nme = dataSnapshot.child("userName").getValue(String.class);
                    //Go through groups
                    for (DataSnapshot snap : dataSnapshot.child("userGroups").getChildren()) {

                        if (snap.child("groupID").getValue(String.class).equals(user.getCurrentGroup().getGroupID())){
                            List<Poll> pList = new ArrayList<>();
                            //Go through Polls
                            for( DataSnapshot snapp : snap.child("polls").getChildren()){
                                Poll newp = new Poll();
                                newp.setOwner(snapp.child("owner").getValue(String.class));
                                newp.setName(snapp.child("name").getValue(String.class));
                                newp.setStatus(snapp.child("status").getValue(Boolean.class));
                                newp.setTarget(snapp.child("target").getValue(String.class));
                                newp.setPollID(snapp.child("pollID").getValue(String.class));

                                List<Element> Elist = new ArrayList<>();
                                //Populate Poll's NotVoted arrays
                                for(DataSnapshot s : snapp.child("notVoted").getChildren()){
                                    newp.setHasNotVoted(s.getValue(String.class));
                                }
                                //Populate Poll's Element list
                                for(DataSnapshot sn : snapp.child("elements").getChildren()){
                                    Elist.add(sn.getValue(Element.class));
                                    Log.d("myTage",sn.child("name").getValue(String.class));
                                }
                                newp.setElements(Elist);
                                pList.add(newp);
                            }
                            pList.add(newPoll);
                            String k = snap.getKey();
                            FirebaseDatabase.getInstance().getReference().child("users").child(nme).child("userGroups").child(k).child("polls").setValue(pList);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }



    // Back functionality
    @Override public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
