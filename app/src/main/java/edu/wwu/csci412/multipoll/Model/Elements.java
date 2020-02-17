package edu.wwu.csci412.multipoll.Model;

/* Class for poll elements within each poll category */

public class Elements {

    private static String name; //name of element
    private static String description; //description of element
    private static int voteCounter; //vote counter for element

    public Elements(String name, String description){
        this.name = name;
        this.description = description;
        voteCounter = 0;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Elements.name = name;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        Elements.description = description;
    }

    public static int getVoteCounter() {
        return voteCounter;
    }

    public static void setVoteCounter(int voteCounter) {
        Elements.voteCounter = voteCounter;
    }
}
