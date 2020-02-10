package edu.wwu.csci412.multipoll;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static String firstName;
    private static String lastName;
    private static String phoneNumber;
    private static String userName;
    private static String password;
    private static ArrayList<String> userCategories;
    private static ArrayList<Group> userGroups;
    private static Group currentGroup;
    private static Poll currentPoll;

    public User() {
        setFirstName("");
        setLastName("");
        setPhoneNumber("");
        setUserName("");
        setPassword("");
        userCategories = new ArrayList<>();
        userGroups = new ArrayList<>();
    }
/*--------------------------------------------*/
/*--------------------------------------------*/
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
/*--------------------------------------------*/
    public void setFirstName(String fN) {
        firstName = fN;
    }
    public void setLastName(String lN) {
        lastName = lN;
    }
    public void setPhoneNumber(String pN) {
        phoneNumber = pN;
    }
    public void setUserName(String uN) {
        userName = uN;
    }
    public void setPassword(String p) {
        password = p;
    }
/*--------------------------------------------*/
    public List<String> listGroups(ArrayList<Group> groups) {
        List<String> groupNames = new ArrayList<>();
        for (int i=0; i<groups.size(); i++) {
            groupNames.add(groups.get(i).getName());
        }
        return groupNames;
    }
    public ArrayList<Group> getGroups() {
        return userGroups;
    }
    public Group getGroup(String g) {
        boolean found = false;
        int i = -1;
        while (!found) {
            i++;
            if (userGroups.get(i).getName().equals(g)) {
                found = true;
            }
        }
        return userGroups.get(i);
    }
    public Group getCurrentGroup() {
        return currentGroup;
    }
    public Poll getCurrentPoll() {
        return currentPoll;
    }
    public ArrayList<String> getUserCategories() {
        return userCategories;
    }
    // Remove user group as well
/*--------------------------------------------*/
    public void addGroup(Group g) {
        userGroups.add(g);
    }
    public void setCurrentGroup(Group g) {
        currentGroup = g;
    }
    public void setCurrentPoll(Poll p) {
        currentPoll = p;
    }
    public void addUserCategory(String cat) {
        userCategories.add(cat);
    }
    // Remove user category as well
}
