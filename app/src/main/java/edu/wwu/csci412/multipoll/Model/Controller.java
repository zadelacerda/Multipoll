package edu.wwu.csci412.multipoll.Model;

import edu.wwu.csci412.multipoll.Controller.DatabaseController;

public class Controller {

    private static User user = new User();

    private Group roommates = new Group("Roommates");
    private Group coworkers = new Group("Co-workers");
    private Category restaurants =  new Category("Restaurants");
    private Category movies = new Category("Movies");
    private Category date_ideas = new Category("Date Ideas");

    private Poll birthday = new Poll(user, "Ben's Birthday");
    private Poll meeting = new Poll(user, "Meeting");
    private Poll dinner = new Poll(user, "Family Dinner");

    private Element option1 = new Element("Laser Tag", 0);
    private Element option2 = new Element("Movie Night", 1);
    private Element option3 = new Element("Arcade", 2);

    private Element r1 = new Element("Mambo Italiano", 0);
    private Element r2 = new Element("D'Annas", 1);
    private Element r3 = new Element("La Fiamma", 2);


    public Controller() {
        user.addGroup(roommates);
        user.addGroup(coworkers);
        user.addUserCategory(movies);
        user.addUserCategory(date_ideas);
        restaurants.add(r1);
        restaurants.add(r2);
        restaurants.add(r3);
        user.addUserCategory(restaurants);


        user.setCurrentPoll(dinner);
//        user.getCategory("restaurants").add(r1);
//        user.getCategory("restaurants").add(r3);
//        user.getCategory("restaurants").add(r2);
        user.getCurrentPoll().addElement(r2);
        user.getCurrentPoll().addElement(r3);

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
