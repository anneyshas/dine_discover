/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.dine_discover.Card_Factory.CardIntentFactory;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * <p>
     * This method sets the content view to the layout defined in {@code activity_user_profile},
     * retrieves the current user's email to display a welcome message, and sets up click listeners
     * for various UI elements.
     * </p>
     *
     * @param savedInstanceState If the activity is being re-initialised after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}. <b>Note: Otherwise it is null.</b>
     *
     * @author Holly Jacob, Anneysha Sarkar
     *                           */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set the welcome text
        TextView welcome = (TextView) findViewById(R.id.profileWelcomeText) ;
        welcome.setText("Welcome " +FirebaseAuth.getInstance().getCurrentUser().getEmail());

        // Set up card click listeners using the Simple Factory
        setCardClickListener(R.id.cardView1, "Card1");
        setCardClickListener(R.id.cardView2, "Card2");
        setCardClickListener(R.id.cardView3, "Card3");
        setCardClickListener(R.id.cardView4, "Card4");
        setCardClickListener(R.id.cardView5, "Card5");
        setCardClickListener(R.id.cardViewMore, "CardMore");

        /**
         * Author: Holly Jacob
         */
        //get the search button
        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
            //Toast.makeText(UserProfileActivity.this,"search button pressed",Toast.LENGTH_SHORT).show();
        });
        /**
         * Author: Holly Jacob
         */
        //get the home button
        Button homeButton = (Button) findViewById(R.id.profileHomeButton);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            //Toast.makeText(UserProfileActivity.this,"home button pressed",Toast.LENGTH_SHORT).show();
        });
        /**
         * Author: Anneysha Sarkar
         */
        //get the logout button
        Button logoutButton = (Button) findViewById(R.id.userProfileLogoutButton);
        logoutButton.setOnClickListener(view -> {

            // Firebase signout
            FirebaseAuth.getInstance().signOut();

            // redirect to login activity
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(UserProfileActivity.this,"Logged out sucessfully",Toast.LENGTH_SHORT).show();

            // close current activity so users cant return to this screen by pressing the back button
            finish();
        });
        /**
         * Author: Holly Jacob
         */
        //set up the back button
        ImageView back = (ImageView) findViewById(R.id.userProfileBackButton);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
    /**
     * Author: Anneysha Sarkar
     */
    private void setCardClickListener(int cardViewId, String cardType) {
        CardView cardView = findViewById(cardViewId);
        cardView.setOnClickListener(view -> {
            Intent intent = CardIntentFactory.createIntent(UserProfileActivity.this, cardType);
            startActivity(intent);
        });
    }
}