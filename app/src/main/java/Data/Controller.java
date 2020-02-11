package Data;

import java.util.ArrayList;
import java.util.List;

import edu.wwu.csci412.multipoll.Group;
import edu.wwu.csci412.multipoll.Poll;
import edu.wwu.csci412.multipoll.User;

public class Controller {

    private User user = new User();

    private static Group roommates = new Group("Roommates");
    private static String fun = "Fun";
    private static String food = "Food";

    private static Poll birthday = new Poll("Ben's Birthday");
    private static Poll meeting = new Poll("Meeting");
    private static Poll dinner = new Poll("Family Dinner");


    private static String option1 = "Laser Tag";
    private static String option2 = "Movie Night";
    private static String option3 = "Arcade";


    public Controller() {
        user.addGroup(roommates);
        user.addUserCategory(fun);
        user.addUserCategory(food);

        user.getGroup("Roommates").addPoll(birthday);
        user.getGroup("Roommates").addPoll(meeting);
        user.getGroup("Roommates").addPoll(dinner);

        user.getGroup("Roommates").getPoll("Ben's Birthday").addOption(option1);
        user.getGroup("Roommates").getPoll("Ben's Birthday").addOption(option2);
        user.getGroup("Roommates").getPoll("Ben's Birthday").addOption(option3);

    }
    public User getUser() {
        return user;
    }
}
