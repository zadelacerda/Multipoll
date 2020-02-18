package edu.wwu.csci412.multipoll.Model;

import java.util.ArrayList;

import edu.wwu.csci412.multipoll.Controller.User;

/* Poll class */

public class Poll {

    private static String name; // name of poll
    private static User owner; // owner of poll
    private static boolean status; // status of poll (active - true/ inactive - false)
    private static Category category; // type of category
    private static ArrayList<Elements> elements; // list of elements to vote on
    private static Group target; // target group for this poll

    public Poll(User user, String name) {
        this.name = name;
        status = true;
        owner = user;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Poll.name = name;
    }

    public static User getOwner() {
        return owner;
    }

    public static void setOwner(User owner) {
        Poll.owner = owner;
    }

    public static boolean isStatus() {
        return status;
    }

    public static void setStatus(boolean status) {
        Poll.status = status;
    }

    public static Category getCategory() {
        return category;
    }

    public static void setCategory(Category category) {
        Poll.category = category;
    }

    public static ArrayList<Elements> getElements() {
        return elements;
    }

    public static void setElements(ArrayList<Elements> elements) {
        Poll.elements = elements;
    }

    public static void addElement(Elements element){
        elements.add(element);
    }

    public static Group getTarget() {
        return target;
    }

    public static void setTarget(Group target) {
        Poll.target = target;
    }
}
