/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dine_discover.LoginSystem.LoginActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private final String[] cuisine = {""};
    private final String[] rating = {""};
    private final String[] cuisineTypes = new String[]{
            "None Specified","Chinese", "Thai", "Italian", "Japanese", "Indian",
            "Mexican", "American", "Mediterranean", "French", "Spanish"};
    private final String[] ratingTypes = new String[]{"Any","1","2","3","4","5"};
    public String search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        mAuth = FirebaseAuth.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //get the toolbar title to change the text for the page
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("New Search");

        /**
         * Author: Holly Jacob
         */
        //get the search Enter button
        Button searchButton = (Button) findViewById(R.id.searchEnterButton);
        searchButton.setOnClickListener(view -> {
            String query = getQuery();
            search = getQuery();
            //Toast.makeText(SearchActivity.this,query,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SearchActivity.this, DiscoveryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);    // clean restart, we don't want old search terms to show
            //intent.putExtra("ROOTTREE", ST);
            intent.putExtra("QUERY", query);
            intent.putExtra("activity","search");
            startActivity(intent);
            finish(); // close current activity and make inaccessible using the back button

        });
        /**
         * Author: Holly Jacob
         */
        //get Logout button
        Button logoutButton = (Button) findViewById(R.id.userProfileLogoutButton);
        logoutButton.setOnClickListener(view -> {
            // Sign out from firebase
            mAuth.signOut();

            // Redirect to login Activity
            Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);    // clean restart, we don't want old search terms to show
            startActivity(intent);
            Toast.makeText(SearchActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            finish(); // close current activity and make inaccessible using the back button

        });
        /**
         * Author: Holly Jacob
         */
        //set up the back button
        ImageView back = (ImageView) findViewById(R.id.userProfileBackButton);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(SearchActivity.this, UserProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        //set up drop downs
        //get the spinner from the xml.
        Spinner selectedCuisine = findViewById(R.id.cuisineDropdown);
        Spinner selectedRating = findViewById(R.id.ratingDropdown);
        TextView searchText = findViewById(R.id.searchbarText);

        //create a list of items for the spinner.

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cuisineTypes);
        //set the spinners adapter to the previously created one.
        selectedCuisine.setAdapter(adapter);


        //create a list of items for the spinner.
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapterRating = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ratingTypes);
        //set the spinners adapter to the previously created one.
        selectedRating.setAdapter(adapterRating);
        /**
         * Author: Holly Jacob
         */
        selectedCuisine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    cuisine[0] = adapterView.getItemAtPosition(i).toString();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /**
         * Author: Holly Jacob
         */
        selectedRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    rating[0] = adapterView.getItemAtPosition(i).toString();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    /**
     * Author: Holly Jacob
     */
    public String getQuery(){
        //get the contents of the search text field
        TextView searchText = (TextView) findViewById(R.id.searchbarText);
        String query = searchText.getText().toString();

        //get the contents of drop downs and add them to the query if they are specified
        //query += ":q";
        if (!cuisine[0].equals(cuisineTypes[0])){
            query += ","+ cuisine[0];
        }
        if (!rating[0].equals(ratingTypes[0])){
            query += ","+ rating[0];
        }
        return query;

    }
}