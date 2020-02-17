package edu.wwu.csci412.multipoll.Model;

/* Category class to save previous elements for each category */

import java.util.ArrayList;

import edu.wwu.csci412.multipoll.Controller.User;

public class Category {

    private static String name; // name of category
    private static User user; // saved per user not per group/poll
    private static ArrayList<Elements> elements; // list of elements per category

    public Category(String name) {
        this.name = name;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Category.name = name;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Category.user = user;
    }

    public static ArrayList<Elements> getElements() {
        return elements;
    }

    public static void setElements(ArrayList<Elements> elements) {
        Category.elements = elements;
    }
}
