package edu.wwu.csci412.multipoll.Model;

/* Class for poll elements within each poll category */

public class Element {

    private boolean isSelected;
    private int id; //id of element
    private String name; //name of element
    private String description; //description of element
    private int voteCounter; //vote counter for element

    public Element(){

    }

    public Element(String n, int newID){
         name = n;
         voteCounter = 0;
         id = newID;
    }

    public Element(String n, int newID, String d){
        name = n;
        id = newID;
        description = d;
        voteCounter = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String d) {
         description = d;
    }

    public int getVoteCounter() {
        return  voteCounter;
    }

    public void setVoteCounter(int vc) {
         voteCounter = vc;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addVote(){
        voteCounter++;
    }

    public void remVote(){
        voteCounter--;
    }



}
