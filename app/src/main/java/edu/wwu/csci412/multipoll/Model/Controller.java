package edu.wwu.csci412.multipoll.Model;

import edu.wwu.csci412.multipoll.Controller.User;

public class Controller {

    private static User user = new User();
    private static Category category = new Category("Restaurant");

    private Group roommates = new Group("Roommates");
    private String fun = "Fun";
    private String food = "Food";

    private Poll birthday = new Poll(user, "Ben's Birthday");
    private Poll meeting = new Poll(user, "Meeting");
    private Poll dinner = new Poll(user, "Family Dinner");

    private Elements option1 = new Elements("Laser Tag", "");
    private Elements option2 = new Elements("Movie Night", "");
    private Elements option3 = new Elements("Arcade", "");

    private static Elements r1 = new Elements("Mambo Italiano", "");
    private static Elements r2 = new Elements("D'Annas", "");
    private static Elements r3 = new Elements("La Fiamma", "");


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

        r1.setCategory(category);
        r2.setCategory(category);
        r3.setCategory(category);
        category.add(r1);
        category.add(r2);
        category.add(r3);

    }
    public User getUser() {
        return user;
    }
}
