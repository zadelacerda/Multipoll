package edu.wwu.csci412.multipoll.Model;

import edu.wwu.csci412.multipoll.Controller.User;

public class Controller {

    private static User user = new User();

    private static Group roommates = new Group("Roommates");
    private static String fun = "Fun";
    private static String food = "Food";

    private static Poll birthday = new Poll(user, "Ben's Birthday");
    private static Poll meeting = new Poll(user, "Meeting");
    private static Poll dinner = new Poll(user, "Family Dinner");

    private static Elements option1 = new Elements("Laser Tag", "");
    private static Elements option2 = new Elements("Movie Night", "");
    private static Elements option3 = new Elements("Arcade", "");


    public Controller() {
        user.addGroup(roommates);
        user.addUserCategory(fun);
        user.addUserCategory(food);

        user.getGroup("Roommates").addPoll(birthday);
        user.getGroup("Roommates").addPoll(meeting);
        user.getGroup("Roommates").addPoll(dinner);

        user.getGroup("Roommates").getPoll("Ben's Birthday").addElement(option1);
        user.getGroup("Roommates").getPoll("Ben's Birthday").addElement(option2);
        user.getGroup("Roommates").getPoll("Ben's Birthday").addElement(option3);

    }
    public User getUser() {
        return user;
    }
}
