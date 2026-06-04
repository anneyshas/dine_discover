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
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dine_discover.Card_Factory.CardMoreActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ReviewRestaurantActivity extends AppCompatActivity {
    private EditText editText;
    private  RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_restaurant);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String activity = (String) getIntent().getExtras().getSerializable("activity");
        Restaurant rest = (Restaurant) getIntent().getExtras().getSerializable("restaurant");
        //set up
        ImageView back = (ImageView) findViewById(R.id.reviewBackButton);
        editText = findViewById(R.id.editTextTextMultiLine);
        ratingBar = findViewById(R.id.ratingBar);
        //set default rating
        ratingBar.setRating(0);
        /**
         * Author: Holly Jacob
         */
        back.setOnClickListener(view -> {
            //check if user came from their wishlist page
            if (activity.equals("wishlist")) { //came from wishlist restaurant comments
                Intent intent = new Intent(ReviewRestaurantActivity.this, WishlistRestaurant.class);
                intent.putExtra("restaurant",rest);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else if (activity.equals("reviews")){ //came from search restaurant comments
                Intent intent = new Intent(ReviewRestaurantActivity.this, CommentsActivity.class);
                intent.putExtra("RESTAURANT",rest);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        // case if rating is set
        final int[] rating = {0};

        // Set the RatingBar change listener outside of the button click event
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float userRating, boolean fromUser) {
                if (fromUser) rating[0] = Math.round(userRating);
            }
        });

        Button enter = (Button) findViewById(R.id.enter);
        enter.setOnClickListener(view -> {
            //check if user came from their wishlist page
            String text = editText.getText().toString();
            List<Reply> replies = new ArrayList<>();
            if (rating[0] != 0 && text.isEmpty()){
                String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                int newRating = (int) (Math.round(rest.getRating()+ rating[0])/rest.getNum_reviews());
                rest.setRating(newRating);
                Toast.makeText(this, "Thank you for your rating", Toast.LENGTH_SHORT).show();
                ratingBar.setRating(0);

            }
            // case if comment is set
            else if (rating[0] == 0 && !text.isEmpty()){
                String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                Review review = new Review(user, 0, text,replies);
                rest.getReviews().add(review);
                editText.setText("");
                Toast.makeText(this, "Thank you for your comment", Toast.LENGTH_SHORT).show();

            }
            // case if both are set
            else if (rating[0] >=1 && !text.isEmpty()){
                String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                Review review = new Review(user, rating[0], text,replies);
                int newRating = (int) (Math.round(rest.getRating()+ rating[0])/rest.getNum_reviews());
                rest.setRating(newRating);
                rest.getReviews().add(review);
                editText.setText("");
                Toast.makeText(this, "Thank you for your review", Toast.LENGTH_SHORT).show();
                ratingBar.setRating(0);
            }

            // no info input from user
            else{
                Toast.makeText(this, "Please add a review", Toast.LENGTH_SHORT).show();
            }

        });

        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(view ->{
            editText.setText("");
        });
        /**
         * Author: Holly Jacob
         */
        back.setOnClickListener(view -> {
            //check if user came from their wishlist page
            if (activity.equals("wishlist")) { //came from wishlist restaurant comments
                Intent intent = new Intent(ReviewRestaurantActivity.this, WishlistRestaurant.class);
                intent.putExtra("restaurant",rest);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else if (activity.equals("reviews")){ //came from search restaurant comments
                Intent intent = new Intent(ReviewRestaurantActivity.this, CommentsActivity.class);
                intent.putExtra("RESTAURANT",rest);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


    }
}