package edu.wwu.csci412.multipoll.Model;

import java.util.ArrayList;
import java.util.List;

import edu.wwu.csci412.multipoll.Controller.User;

public class Group {
    private static String name;
    private static ArrayList<User> members;
    private static ArrayList<Poll> polls;

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
    public ArrayList<User> getMembers() {
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
    public void addMember(User u) {
        members.add(u);
    }
    public void addPoll(Poll p) {
        polls.add(p);
    }
}
