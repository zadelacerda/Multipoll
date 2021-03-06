package edu.wwu.csci412.multipoll.Model;

import java.util.ArrayList;
import java.util.List;

import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.Model.Poll;

public class User {
    private static String firstName;
    private static String lastName;
    private static String phoneNumber;
    private static String userName;
    private static String password;
    private static String userID;
    private static List<String> friends;
    private static List<Category> userCategories;
    private static List<Group> userGroups;
    private static Group currentGroup;
    private static Poll currentPoll;
    private static Category currentCategory;
    private static String currentElement;
    private static String currentFriend;

    public User(){

    }

    public User(int b) {

        setFirstName("");
        setLastName("");
        setPhoneNumber("");
        setUserName("");
        setPassword("");
        setUserID("");
        userCategories = new ArrayList<Category>();
        userGroups = new ArrayList<Group>();
        friends = new ArrayList<String>();
        currentGroup = null;
        currentPoll = null;
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

    public static void setUserID(String userID) {
        User.userID = userID;
    }

    /*--------------------------------------------*/
    public List<String> listGroups(List<Group> groups) {
        List<String> groupNames = new ArrayList<>();
        for (int i=0; i<groups.size(); i++) {
            groupNames.add(groups.get(i).getName());
        }
        return groupNames;
    }
    public List<Group> getGroups() {
        return userGroups;
    }

    public static String getUserID() {
        return userID;
    }

    public static List<String> getFriends() {
        return friends;
    }

    public static void setFriends(List<String> friends) {
        User.friends = friends;
    }
    public void addFriend(String name){friends.add(name);}

    public static void setUserCategories(List<Category> userCategories) {
        User.userCategories = userCategories;
    }

    public static List<Group> getUserGroups() {
        return userGroups;
    }

    public static void setUserGroups(List<Group> userGroups) {
        User.userGroups = userGroups;
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
    public List<Category> getUserCategories() {
        return userCategories;
    }
    public Category getCurrentCategory() {
        return currentCategory;
    }

    public static String getCurrentFriend() {
        return currentFriend;
    }

    public Category getCategory(String c) {
        boolean found = false;
        int i = -1;
        while (!found) {
            i++;
            if (userCategories.get(i).getName().equals(c)) {
                found = true;
            }
        }
        return userCategories.get(i);
    }
    public String getCurrentElement() {
        return currentElement;
    }
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

    public static void setCurrentFriend(String currentFriend) {
        User.currentFriend = currentFriend;
    }

    public void addUserCategory(Category cat) {
        userCategories.add(cat);
    }
    public void setCurrentCategory(Category c) {
        currentCategory = c;
    }
    public void setCurrentElement(String e) {
        currentElement = e;
    }
/*--------------------------------------------*/
    public void removeGroup(Group g) {
        boolean found = false;
        int i = -1;
        while (!found) {
            if (g.getName().equals(userGroups.get(i).getName())) {
                userGroups.remove(i);
            }
        }
    }
    public void removeUserCategory(String cat) {
        boolean found = false;
        int i = -1;
        while (!found) {
            if (cat.equals(userCategories.get(i))) {
                userCategories.remove(i);
            }
        }
    }

    public List<String> listCategories(List<Category> categories) {
        List<String> catNames = new ArrayList<>();
        for (int i=0; i<categories.size(); i++) {
            catNames.add(categories.get(i).getName());
        }
        return catNames;
    }


}
