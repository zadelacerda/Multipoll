package edu.wwu.csci412.multipoll.Model;

/* Class for poll elements within each poll category */

public class Elements {

    private String name; //name of element
    private Category category; //name of category that element belongs to
    private String description; //description of element
    private int voteCounter; //vote counter for element

    public Elements(String name, String description){
        this.name = name;
        this.description = description;
        voteCounter = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVoteCounter() {
        return this.voteCounter;
    }

    public void setVoteCounter(int voteCounter) {
        this.voteCounter = voteCounter;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
