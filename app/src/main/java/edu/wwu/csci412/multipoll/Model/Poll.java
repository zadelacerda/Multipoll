package edu.wwu.csci412.multipoll.Model;

import java.util.ArrayList;
import java.util.List;

/* Poll class */

public class Poll {

    private String name; // name of poll
    private String owner; // ownerID of poll
    private String pollID; // poll ID
    private boolean status; // status of poll (active - true/ inactive - false)
    private String category; // type of category
    private ArrayList<Element> elements; // list of elements to vote on
    private String target; // group_id for this poll

    public Poll(User u, String n) {
        name = n;
        status = true;
        elements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String o) {
        owner = o;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean s) {
        status = s;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String c) {
        category = c;
    }

    public List<String> listElements(ArrayList<Element> elements) {
        List<String> elementNames = new ArrayList<>();
        for (int i=0; i<elements.size(); i++) {
            elementNames.add(elements.get(i).getName());
        }
        return elementNames;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Element> e) {
        elements = e;
    }

    public void addElement(Element element){
        elements.add(element);
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String t) {
        target = t;
    }

}
