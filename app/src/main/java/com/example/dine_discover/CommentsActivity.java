/**
 * Author: Vivian Tran
 */
/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 * CommentsActivity is responsible for handling the UI of comments </p>
 *
 */
public class CommentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentsAdapter adapter;
    private List<Review> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        recyclerView = findViewById(R.id.comments_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("RESTAURANT");
        Button makeComment = (Button) findViewById(R.id.makeCommentButton);
        ImageView back = (ImageView) findViewById(R.id.commentBackButton);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(CommentsActivity.this, DiscoveryActivity.class);
            intent.putExtra("activity","reviews");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        });
        makeComment.setOnClickListener(view -> {
            Intent intent = new Intent(CommentsActivity.this, ReviewRestaurantActivity.class);
            intent.putExtra("activity","reviews");
            intent.putExtra("restaurant",restaurant);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        });

        if (restaurant != null) {

            reviews = restaurant.getReviews();
            adapter = new CommentsAdapter(this, reviews, restaurant);
            recyclerView.setAdapter(adapter);

        }

    }


}