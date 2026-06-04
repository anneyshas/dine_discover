/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dine_discover.Card_Factory.CardMoreActivity;
import com.example.dine_discover.LoginSystem.LoginActivity;
import com.example.dine_discover.SearchTreeFolder.Wishlist;
import com.google.firebase.auth.FirebaseAuth;

public class WishlistRestaurant extends AppCompatActivity {

    Wishlist mywishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wishlist_restaurant);

        mywishlist = Wishlist.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //get the restaurant from the user's wishlist
        Restaurant rest = (Restaurant) getIntent().getExtras().getSerializable("restaurant");
        //display the restaurant
        displayRestaurant(rest);
        /**
         * Author: Holly Jacob
         */
        //set up the back button
        ImageView back = (ImageView) findViewById(R.id.wishlistRestaurantBackButton);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(WishlistRestaurant.this, CardMoreActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        /**
         * Author: Holly Jacob
         */
        //set up the review button
        Button review = (Button) findViewById(R.id.wishlistReviewButton);
        review.setOnClickListener(view -> {
            Intent intent = new Intent(WishlistRestaurant.this, ReviewRestaurantActivity.class);
            intent.putExtra("restaurant",rest);
            intent.putExtra("activity","wishlist");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        /**
         * Author: Vivian Tran
         */
        //set up the view comments button
        Button comments = (Button) findViewById(R.id.wishlistViewComments);
        comments.setOnClickListener(view -> {
            //TODO send intent to a reviews page
            TextView reviewsBtn = (TextView) findViewById(R.id.discoveryReviewButton);
            Toast.makeText(this, "go to comments", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Comment", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(WishlistRestaurant.this, CommentsActivity.class);
            intent.putExtra("RESTAURANT", rest);  // Pass the Restaurant object
            startActivity(intent);

        });
        /**
         * Author: Sarah Palmer
         */
        //set up the delete button
        Button delete = (Button) findViewById(R.id.wishlistDeleteButton);
        delete.setOnClickListener(view -> {
            //delete the restaurant rest from the wishlist
            mywishlist.removeRestaurant(rest);
            Toast.makeText(WishlistRestaurant.this,"deleting...",Toast.LENGTH_SHORT).show();
            //send the user back to the wishlist page
            Intent intent = new Intent(WishlistRestaurant.this, CardMoreActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });


    }
    /**
     * Author: Holly Jacob
     */
    public void displayRestaurant(Restaurant current){
        if (current == null){
            throw new IllegalArgumentException("Restaurant is Null");
        }
        TextView name = (TextView) findViewById(R.id.discoveryRestName);
        name.setText(current.getName());

        TextView cuisine = (TextView) findViewById(R.id.discoveryCuisine);
        cuisine.setText(current.getCuisine());

        TextView rating = (TextView) findViewById(R.id.discoveryRating);
        String ratingString = String.valueOf(current.getRating());
        rating.setText(ratingString);

        TextView address = (TextView) findViewById(R.id.discoveryRestAddress);
        address.setText(current.getAddress());

        TextView suburb = (TextView) findViewById(R.id.discoveryRestSuburb);
        suburb.setText(current.getAddressLine2());

        EditText url = (EditText) findViewById(R.id.wishlistRestaurantPageUrl);
        url.setText(current.getURL());

        // TODO DISPLAY COMMENTS
//        TextView numberOfReviews = (TextView) findViewById(R.id.discorveryReviewNum);
//        numberOfReviews.setText("("+current.getReviews().size()+")");

    }

    private String getCurrentUser(){
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            return FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        User newUser = new User("", "");
        return newUser.getUsername();
    }

}