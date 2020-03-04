package edu.wwu.csci412.multipoll.Model;

/* Class for poll elements within each poll category */

public class Element {

    private String name; //name of element
    private String description; //description of element
    private int voteCounter; //vote counter for element

    public Element(){

    }

    public Element(String n, String d){
         name = n;
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

}
