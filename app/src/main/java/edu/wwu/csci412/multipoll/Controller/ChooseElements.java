package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import javax.security.auth.callback.Callback;

import edu.wwu.csci412.multipoll.Model.Category;
import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;

public class ChooseElements extends AppCompatActivity {

    public static Controller controller;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_elements);
        controller = MainActivity.getController();
        user = controller.getUser();

        // Toolbar setup
        getSupportActionBar().setTitle(user.getCurrentCategory().getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* set up search view */
        SearchView searchView = findViewById(R.id.search_elements);
        searchView.setQueryHint("Search " + user.getCurrentPoll().getCategory());
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                getUsers(newText, new Callback() {
//                    @Override
//                    public void onCallback(Object value) {
//                        restaurants = (ArrayList<RestaurantFirebase>) value;
//                        restaurantAdapter.updateData(restaurants);
//                    }
//                });
//
//                return false;
//            }
//        });




        /* list view of elements for current category */
        ArrayAdapter<String> arrayAdapter;
        ListView lv = findViewById(R.id.elements);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.getCurrentPoll().listElements(user.getCurrentPoll().getElements()));
        lv.setAdapter(arrayAdapter);

        /* on click listener for list view buttons */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String TempListViewClickedValue = user.getCurrentPoll().listElements(user.getCurrentPoll().getElements()).get(position);
                user.setCurrentElement(TempListViewClickedValue);
                Intent intent = new Intent (ChooseElements.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    // Back functionality
    @Override public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
