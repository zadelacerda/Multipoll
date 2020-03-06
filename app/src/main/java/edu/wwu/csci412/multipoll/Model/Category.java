package edu.wwu.csci412.multipoll.Model;

/* Category class to save previous elements for each category */

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String name; // name of category
    private String username; // saved per user not per group/poll
    private String id;
    private List<Element> elementList; // list of elements per category

    public Category(){
        elementList = new ArrayList<>();
    }

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

    public List<Element> getElements() {
        return elementList;
    }

    public void addElement(Element e){
        elementList.add(e);
    }

    public void setElements(List<Element> e) {
        elementList = e;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
