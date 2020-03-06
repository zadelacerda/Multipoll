package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import edu.wwu.csci412.multipoll.Model.Category;
import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;

import static edu.wwu.csci412.multipoll.Controller.ChooseGroup.user;

public class ChooseCategory  extends AppCompatActivity {
    private ArrayAdapter<String> arrayAdapter;
    private EditText search;

    public static Controller controller;
    public static User user;

    private List<String> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_category);

        /* Connect XML with Java objects */
        search = findViewById(R.id.searchCategory);
        ListView list = findViewById(R.id.categoryList);
        FloatingActionButton fab = findViewById(R.id.addCategory);


        /* Fake Database */
        controller = MainActivity.getController();
        user = controller.getUser();


        getSupportActionBar().setTitle("Choose Category " + "(" + user.getCurrentGroup().getName() + ")");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* Search bar setup */
        search.setHint("Search Categories");
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (ChooseCategory.this).arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /* Category list view setup */
        categoryList = user.listCategories(user.getUserCategories());
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryList);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

                String TempListViewClickedValue = user.listCategories(user.getUserCategories()).get(position);

                Intent intent = new Intent (ChooseCategory.this, ChooseElements.class);
                user.setCurrentCategory(user.getCategory(TempListViewClickedValue));

                startActivity(intent);
            }
        });

        /* Floating Button */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newCategory = search.getText().toString();
                arrayAdapter.add(newCategory);
                user.addUserCategory(new Category(newCategory));
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    // Back functionality
    @Override public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
