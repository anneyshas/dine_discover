/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

/**
 * Class inactive but kept at advice of tutor.
 * Class for retrieving restaurant data from Firebase Firestore.
 * <p>
 * This class handles fetching all restaurant data from the Firestore database and provides methods
 * to filter the restaurants based on user preferences.
 * </p>
 *
 * @author Gauri Chopra
 */
public class DBRetrieve {
    private FirebaseFirestore db;
    public List<Restaurant> restaurantList;

    /**
     * Constructor for DB_Retrieve.
     * <p>
     * Initialises the Firestore instance and the restaurant list.
     * </p>
     *
     * @author Gauri Chopra
     */
    public DBRetrieve() {
        db = FirebaseFirestore.getInstance(); // TODO: put the database id (a string)
        restaurantList = new ArrayList<>();
    }

    /**
     * Fetches all restaurants from the Firestore database.
     * <p>
     * This method retrieves all restaurant documents from the "restaurants" collection,
     * converts them to {@link Restaurant} objects, and adds them to the restaurant list.
     * </p>
     *
     * @author Gauri Chopra
     */
    public void fetchAllRestaurants() {
        Log.d(TAG, "entered fetchallrestaurants method");

        CollectionReference restaurantsRef = db.collection("restaurants");

        // Clear the list before fetching new data to avoid duplicates
        restaurantList.clear();

        // Add the listener directly without calling restaurantsRef.get() twice
        restaurantsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        // Convert document to Restaurant object
                        Restaurant restaurant = document.toObject(Restaurant.class);

                        // Handle potential issues with field names
                        if (restaurant != null) {
                            restaurant.setAddressLine2(document.getString("address line 2"));
                            restaurantList.add(restaurant);
                        }
                    }

                    // Log fetched restaurants
                    for (Restaurant r : restaurantList) {
                        Log.d(TAG, "Fetched: " + r.getName());
                    }
                }
            } else {
                Log.d(TAG, "Error fetching documents: ", task.getException());
            }
        });
    }

    /**
     * Gets the list of restaurants.
     *
     * @return the list of restaurants
     * @author Gauri Chopra
     */
    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    /**
     * Filters the restaurants based on user preferences.
     * <p>
     * This method filters the restaurant list based on the provided preferences map.
     * </p>
     *
     * @param preferences a map of preferences to filter the restaurants
     * @return a list of restaurants that match the preferences
     * @author Gauri Chopra
     */
    public List<Restaurant> filterRestaurants(Map<String, Object> preferences) {
        List<Restaurant> filteredRestaurants = new ArrayList<>();

        for (Restaurant restaurant : restaurantList) {
            boolean matches = true; // Start assuming the restaurant matches

            // Check for each preference in the map
            for (Map.Entry<String, Object> entry : preferences.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                // Compare the restaurant field to the value
                switch (key) {
                    case "name":
                        if (!restaurant.getName().equalsIgnoreCase((String) value)) {
                            matches = false;
                        }
                        break;
                    case "address_line_2":
                        if (!restaurant.getAddressLine2().equalsIgnoreCase((String) value)) {
                            matches = false;
                        }
                        break;
                    case "cuisine":
                        if (!restaurant.getCuisine().equalsIgnoreCase((String) value)) {
                            matches = false;
                        }
                        break;
                    case "rating":
                        if (restaurant.getRating() != (double) value) {
                            matches = false;
                        }
                        break;
                    default:
                        break;
                }

                // If any condition fails, break out of the loop early
                if (!matches) {
                    break;
                }
            }

            // If all preferences match, add it to the filtered list
            if (matches) {
                filteredRestaurants.add(restaurant);
            }
        }

        return filteredRestaurants;
    }
}
