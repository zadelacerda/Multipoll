package Data;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<String> categoryNames = new ArrayList<>();

    public Controller() {
        categoryNames.add("Fun");
        categoryNames.add("Food");
    }

    public List<String> getCategoryNames() {
        return this.categoryNames;
    }
}
