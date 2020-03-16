package edu.wwu.csci412.multipoll.Model;

/* Category class to save previous elements for each category */

import java.util.ArrayList;

public class Category {

    private String name; // name of category
    private String username; // saved per user not per group/poll
    private ArrayList<Element> elementList; // list of elements per category

    public Category(String n) {
        name = n;
        elementList = new ArrayList<>();
    }

    public  String getName() {
        return name;
    }

    public  void setName(String n) {
        name = n;
    }

    public String getUser() {
        return username;
    }

    public  void setUser(String u) {
        username = u;
    }

    public ArrayList<Element> getElements() {
        return elementList;
    }

    public void add(Element e){
        elementList.add(e);
    }

    public void setElements(ArrayList<Element> e) {
        elementList = e;
    }
}
