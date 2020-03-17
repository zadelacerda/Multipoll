package edu.wwu.csci412.multipoll.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/* Poll class */

public class Poll {

    private String name; // name of poll
    private String owner; // ownerID of poll
    private String pollID; // poll ID
    private boolean status; // status of poll (active - true/ inactive - false)
    private String category; // type of category
    private List<Element> elements; // list of elements to vote on
    private String target; // group_id for this poll
    private List<String> usersNotVoted;

    public Poll(){

    }

    public Poll(String u, String n) {
        name = n;
        owner = u;
        status = true;
        elements = new ArrayList<>();
        usersNotVoted = new ArrayList<>();
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

    public List<String> listElements(List<Element> elements) {
        List<String> elementNames = new ArrayList<>();
        for (int i=0; i<elements.size(); i++) {
            elementNames.add(elements.get(i).getName());
        }
        return elementNames;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> e) {
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

    public String getPollID() {
        return pollID;
    }

    public void setPollID(String pollID) {
        this.pollID = pollID;
    }

    public List<Element> getElementList() {
        return elements;
    }

    public List<String> getUsersNotVoted() {
        return usersNotVoted;
    }

    public void setUsersNotVoted(List<String> u) {
        usersNotVoted = u;
    }
    public void remUserVoted(String user){
        usersNotVoted.remove(user);
    }
    public void setHasNotVoted(String user){
        usersNotVoted.add(user);
    }
    public Boolean hasVoted(String user){
        Boolean userFound = false;

        for(int i = 0; i < usersNotVoted.size(); i++){
            String names = usersNotVoted.get(i);

            if(names.equals(user)){
                userFound = true;
            }
        }
        if(userFound){
            return false;
        }
        else{
            return true;
        }
    }
}
