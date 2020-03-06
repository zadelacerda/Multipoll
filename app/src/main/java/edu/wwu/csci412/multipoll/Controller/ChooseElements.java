package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import edu.wwu.csci412.multipoll.Model.Category;
import edu.wwu.csci412.multipoll.Model.Controller;
import edu.wwu.csci412.multipoll.Model.Element;
import edu.wwu.csci412.multipoll.Model.Group;
import edu.wwu.csci412.multipoll.Model.User;
import edu.wwu.csci412.multipoll.R;


public class ChooseElements extends AppCompatActivity {
    private ArrayAdapter<Element> arrayAdapter;
    private EditText search;

    public static Controller controller;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_elements);

        /* Connect XML with Java objects */
        search = findViewById(R.id.searchElement);
        LinearLayout linearLayout = findViewById(R.id.elementContainer);
        FloatingActionButton fab = findViewById(R.id.addElement);

        /* Fake Database */
        controller = MainActivity.getController();
        user = controller.getUser();

        // Toolbar setup
        getSupportActionBar().setTitle(user.getCurrentCategory().getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* Search view setup */
        search.setHint("Search Elements");
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (ChooseElements.this).arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /* List of elements for current category */
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.getCurrentPoll().getElements());
//        LinearLayout.LayoutParams buttonparam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
        for (int i = 0; i < arrayAdapter.getCount(); i++) {
            Element element = arrayAdapter.getItem(i);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(element.getId());
            checkBox.setText(element.getName());
//            checkBox.setLayoutParams(buttonparam);
            checkBox.setWidth(linearLayout.getWidth());
            linearLayout.addView(checkBox);
        }

        /* Floating Add Button Setup */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Element newElement = new Element(search.getText().toString());
                arrayAdapter.add(newElement);
                user.getCurrentCategory().add(newElement);
                arrayAdapter.notifyDataSetChanged();
            }
        });
//        setContentView(linearLayout);
    }

//        public void onCheckboxClicked(View view){
//        }
//        }

    // Back functionality
    @Override public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
