package edu.wwu.csci412.multipoll.Controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Element;
import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;


public class PollResults extends AppCompatActivity {

    public static Controller controller;
    private static User user;
    private static Group currentGroup;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poll_results);

        controller = MainActivity.getController();
        user = controller.getUser();
        currentGroup = user.getCurrentGroup();

        getSupportActionBar().setTitle(user.getCurrentPoll().getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        // Connect GroupSelected activity to this fragment somehow
        ArrayAdapter<String> arrayAdapter;
        ListView lv = findViewById(R.id.pollOptions);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.getCurrentPoll().listElements(user.getCurrentPoll().getElements()));
        lv.setAdapter(arrayAdapter);

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String TempListViewClickedValue = currentGroup.listPolls().get(position);
//                if (TempListViewClickedValue.equals("Categories")) {
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatFragment()).addToBackStack(null).commit();
//                }
//                if (TempListViewClickedValue.equals("Theme")) {
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThemeFragment()).addToBackStack(null).commit();
//                }
//
//            }
//        });

        // List of elements for current category
        ArrayList<Element> pollElements = user.getCurrentPoll().getElements();

        // Set up vote array adapters nad list views
        ArrayAdapter<String> voteList;
        ArrayAdapter<String> slashList;
        ArrayAdapter<String> maxVotes;
        ListView vL = findViewById(R.id.voteCounterList);
        ListView sL = findViewById(R.id.slashList);
        ListView mV = findViewById(R.id.maxVotes);

        // Init list of checkboxes
        LinearLayout checkList = this.findViewById(R.id.checkContainer);

        // Layo
        LinearLayout voteLayout = this.findViewById(R.id.voteCounters);
        ArrayList<String> voteCounters = new ArrayList<>();
        ArrayList<String> slashes = new ArrayList<>();
        ArrayList<String> totalVotes = new ArrayList<>();
        int totalVote = currentGroup.getMembers().size();

        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.setMargins(0, 22, 0, 22);
        for (Element element : pollElements) {
            CheckBox checkBox = new CheckBox(this);
            //checkBox.setId(element.getId());
            //checkBox.setText(element.getName());
            checkBox.setLayoutParams(lparams);
            checkList.addView(checkBox);

            // Add element vote count to voteCounterList
            voteCounters.add(Integer.toString(element.getVoteCounter()));
            // Create slashes list
            slashes.add("/");
            // Create total votes list
            totalVotes.add(Integer.toString(totalVote));

        }

        voteList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, voteCounters);
        vL.setAdapter(voteList);
        slashList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, slashes);
        sL.setAdapter(slashList);
        maxVotes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, totalVotes);
        mV.setAdapter(maxVotes);

    }

    @Override public boolean onSupportNavigateUp() {
        onBackPressed();
        return true; }

}
