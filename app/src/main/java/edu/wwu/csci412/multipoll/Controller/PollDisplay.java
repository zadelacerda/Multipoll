package edu.wwu.csci412.multipoll.Controller;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Element;
import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.Model.Poll;

import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;

public class PollDisplay extends Fragment {
    public static Controller controller;
    private static User user;
    private static Group currentGroup;
    private static Poll currentPoll;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_poll_display, container, false);

        // Initialize controller variables
        controller = MainActivity.getController();
        user = controller.getUser();
        currentGroup = user.getCurrentGroup();
        currentPoll = user.getCurrentPoll();

        // Connect GroupSelected activity to this fragment somehow
        ArrayAdapter<String> arrayAdapter;
        ListView lv = view.findViewById(R.id.groupPolls);


        // Populate view with polls from the current group
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, currentGroup.listPolls());
        lv.setAdapter(arrayAdapter);

        // Select chosen poll
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String TempListViewClickedValue = currentGroup.getPolls().get(position).getPollID();
                user.setCurrentPoll(currentGroup.getPoll(TempListViewClickedValue));
                Intent intent = new Intent(getActivity(), PollResults.class);
                startActivity(intent);

            }
        });

        return view;
    }

}
