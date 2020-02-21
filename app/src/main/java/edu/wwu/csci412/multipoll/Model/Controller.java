package edu.wwu.csci412.multipoll.Model;

import edu.wwu.csci412.multipoll.Controller.DatabaseController;

public class Controller {

    private static User user = new User();
    private static Category category = new Category("Restaurant");


    private Group roommates = new Group("Roommates");
    private Category fun =  new Category("Fun");
    private Category food = new Category("Food");

    private Poll birthday = new Poll(user, "Ben's Birthday");
    private Poll meeting = new Poll(user, "Meeting");
    private Poll dinner = new Poll(user, "Family Dinner");

    private Element option1 = new Element("Laser Tag", "");
    private Element option2 = new Element("Movie Night", "");
    private Element option3 = new Element("Arcade", "");

    private static Element r1 = new Element("Mambo Italiano", "");
    private static Element r2 = new Element("D'Annas", "");
    private static Element r3 = new Element("La Fiamma", "");


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

        category.add(r1);
        category.add(r2);
        category.add(r3);

    }
    public User getUser() {
        return user;
    }
}
