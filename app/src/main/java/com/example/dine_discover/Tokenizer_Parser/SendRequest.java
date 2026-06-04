/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.Tokenizer_Parser;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.dine_discover.Restaurant;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class SendRequest {
    /**
     * Class is inactive (although kept at request of a tutor)
     * SendRequest is responsible for sending the requests to database
     * based on queries made.
     *
     * @author Gauri Chopra
     */
    private FirebaseFirestore db;

    public SendRequest() {
        db = FirebaseFirestore.getInstance();
    }

    public void sendQuery(QueryParser parser) {
        String suburb = parser.getSuburb();
        String cuisine = parser.getCuisine();
        Double rating = parser.getRating();
        String name = parser.getName();
        Query query = db.collection("restaurants");

        if (suburb != null) {
            query = query.whereEqualTo("suburb", suburb);
        }
        if (cuisine != null) {
            query = query.whereEqualTo("cuisine", cuisine);
        }
        if (rating != null) {
            query = query.whereEqualTo("rating", rating);
        }
        if (name != null) {
            query = query.whereEqualTo("name", name);
        }

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Restaurant restaurant = document.toObject(Restaurant.class);
                    //display in UI
                    Log.w(TAG, "restaurants:"+restaurant.getName()+restaurant.getAddress()+
                            restaurant.getRating()+restaurant.getCuisine());
                }
            } else {
                // Handle the error
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });

    }
}
