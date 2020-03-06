package edu.wwu.csci412.multipoll.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

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

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

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

        /* Real Database */
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

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
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.getCurrentCategory().getElements());
//        LinearLayout.LayoutParams buttonparam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
        for (int i = 0; i < user.getCurrentCategory().getElements().size(); i++) {
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
                String newString = search.getText().toString();
                if (!newString.equals("")) {
                    Element newElement = new Element(search.getText().toString(), arrayAdapter.getCount());
                    arrayAdapter.add(newElement);
                    newElement.setId(user.getCurrentCategory().getElements().size());
                    user.getCurrentCategory().addElement(newElement);
                    List<Element> newList = user.getCurrentCategory().getElements();
                    databaseReference.child("users").child(user.getUserName()).child("userCategories").child(user.getCurrentCategory().getId()).child("elementList").setValue(newList);
                    arrayAdapter.notifyDataSetChanged();
                    Toast.makeText(ChooseElements.this, "New Element Added", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ChooseElements.this, "Name Is Empty", Toast.LENGTH_SHORT).show();
                }
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
