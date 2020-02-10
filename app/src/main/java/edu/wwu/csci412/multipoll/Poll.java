package edu.wwu.csci412.multipoll;

import java.util.ArrayList;
import java.util.List;

public class Poll {
    private String name;
    private ArrayList<String> options;
    private boolean state;

    public Poll(String n) {
        name = n;
        options = new ArrayList<>();
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
