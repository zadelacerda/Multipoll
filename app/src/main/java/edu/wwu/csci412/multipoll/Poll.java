package edu.wwu.csci412.multipoll;

import java.util.ArrayList;
import java.util.List;

public class Poll {
    private String name;
    private ArrayList<String> options;
    private boolean state;
    private User owner;

    public Poll(User u, String n) {
        name = n;
        options = new ArrayList<>();
        owner = u;
    }

    public String getName() {
        return name;
    }
    public List<String> getOptions() {
        return options;
    }
    public void addOption(String o) {
        options.add(o);
    }
    public void removeOption(String o) {
        for (int i=0; i<options.size(); i++) {
            if (options.get(i).equals(o)) {
                options.remove(i);
            }
        }
    }
}
