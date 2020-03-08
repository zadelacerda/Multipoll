package edu.wwu.csci412.multipoll.Model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private String groupID;
    private ArrayList<String> members;
    private ArrayList<Poll> polls;
    public Group(){
        members = new ArrayList<>();
        polls = new ArrayList<>();
    }
    public Group(String n) {
        name = n;
        members = new ArrayList<>();
        polls = new ArrayList<>();
    }
/*--------------------------------------------*/
/*--------------------------------------------*/
    public String getName() {
        return name;
    }

    public String getGroupID() {
        return groupID;
    }

    public ArrayList<String> getMembers() {
        return members;
    }
    public ArrayList<Poll> getPolls() {
        return polls;
    }
    public List<String> listPolls() {
        List<String> pollNames = new ArrayList<>();
        //int nameIndex = 0;
        for (int i=0; i<polls.size(); i++) {
            pollNames.add(polls.get(i).getName());
        }
        return pollNames;
    }
    public Poll getPoll(String p) {
        boolean found = false;
        int i = -1;
        while (!found) {
            i++;
            if (polls.get(i).getName().equals(p)) {
                found = true;
            }
        }
        return polls.get(i);
    }
/*--------------------------------------------*/
    public void addMember(String u) {
        members.add(u);
    }
    public void addPoll(Poll p) {
        polls.add(p);
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
