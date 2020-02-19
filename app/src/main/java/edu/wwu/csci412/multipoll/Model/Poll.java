package edu.wwu.csci412.multipoll.Model;

import java.util.ArrayList;

import edu.wwu.csci412.multipoll.Controller.User;

/* Poll class */

public class Poll {

    private String name; // name of poll
    private User owner; // owner of poll
    private boolean status; // status of poll (active - true/ inactive - false)
    private Category category; // type of category
    private ArrayList<Elements> elements; // list of elements to vote on
    private Group target; // target group for this poll

    public Poll(User u, String n) {
        name = n;
        status = true;
        owner = u;
        elements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User o) {
        owner = o;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean s) {
        status = s;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category c) {
        category = c;
    }

    public ArrayList<Elements> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Elements> e) {
        elements = e;
    }

    public void addElement(Elements element){
        elements.add(element);
    }

    public Group getTarget() {
        return target;
    }

    public void setTarget(Group t) {
        target = t;
    }
}
