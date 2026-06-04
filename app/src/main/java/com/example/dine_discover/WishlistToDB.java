/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import android.util.Log;

import com.example.dine_discover.SearchTreeFolder.WishlistAVLTree;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WishlistToDB {
    /**
     * CurrentUser contains code for storing the
     * wishlist in database after it has been
     * flattened and converted to Map. Thus,
     * it also contains the code related to
     * the conversion of Object to Map and vice versa.
     *
     ** @author Gauri Chopra
     */
    public WishlistAVLTree avlTree = new WishlistAVLTree();
    public ArrayList<Restaurant> storeList = new ArrayList<>();
    public List<Map<String, Object>> restaurantMap;
    public ArrayList<Restaurant> getArrayWishlist(){
            return avlTree.getStorageData();
        }


    public List<Map<String, Object>> convertArraytoMap(ArrayList<Restaurant> list){
        List<Map<String, Object>> restaurantMaps = new ArrayList<>();
        for (Restaurant restaurant : list) {
            restaurantMaps.add(restaurant.toMap());
        }
        return restaurantMaps;
    }

    public void storeWishlistDB(ArrayList<Restaurant> storeList, String userEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
     //   storeList = getArrayWishlist();
        restaurantMap = convertArraytoMap(storeList);
        db.collection("user")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                            String userId = document.getId();

                            db.collection("user").document(userId)
                                    .update("Wishlist", restaurantMap)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("Firestore", "Wishlist successfully added to user!");
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.w("Firestore", "Error adding wishlist to user", e);
                                    });

                        } else {
                            Log.d("Firestore", "No user found with the given email.");
                        }
                    } else {
                        Log.w("Firestore", "Error querying user by email", task.getException());
                    }
                });
    }

    public void retrieveWishlistDB(String userEmail, OnWishlistRetrievedListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                            List<Map<String, Object>> wishlistData = (List<Map<String, Object>>) document.get("Wishlist");

                            if (wishlistData != null) {
                                ArrayList<Restaurant> wishlist = convertMapToRestaurant(wishlistData);
                                listener.onWishlistRetrieved(wishlist);
                            } else {
                                Log.d("Firestore", "No wishlist found for the user.");
                                listener.onWishlistRetrieved(new ArrayList<>());
                            }
                        } else {
                            Log.d("Firestore", "No user found with the given email.");
                        }
                    } else {
                        Log.w("Firestore", "Error querying user by email", task.getException());
                    }
                });
    }

    private ArrayList<Restaurant> convertMapToRestaurant(List<Map<String, Object>> wishlistData) {
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        for (Map<String, Object> data : wishlistData) {
            String name = (String) data.get("name");
            String address = (String) data.get("address");
            String cuisine = (String) data.get("cuisine");
            String postcode = (String) data.get("postcode");
            String outcode = (String) data.get("outcode");
            String typeOfFood = (String) data.get("type_of_food");
            Double rating = data.get("rating") != null ? ((Number) data.get("rating")).doubleValue() : 0.0;
            int num_reviews = data.get("num_reviews") != null ? ((Number) data.get("num_reviews")).intValue() : 0;

            Restaurant restaurant = new Restaurant();
            restaurant.setName(name);
            restaurant.setAddress(address);
            restaurant.setCuisine(cuisine);
            restaurant.setPostcode(postcode);
            restaurant.setOutcode(outcode);
            restaurant.setRating(rating);
            restaurant.setNum_reviews(num_reviews);

            restaurantList.add(restaurant);
        }
        return restaurantList;
    }

    // Method to convert a List of Maps to a List of Restaurant objects
    private ArrayList<Restaurant> convertMapListToRestaurantList(List<Map<String, Object>> wishlistData) {
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        for (Map<String, Object> data : wishlistData) {
            Restaurant restaurant = new Restaurant();

            // Set the properties from the map
            restaurant.setAddress((String) data.get("address"));
            restaurant.setAddressLine2((String) data.get("address_line_2"));
            restaurant.setName((String) data.get("name"));
            restaurant.setCuisine((String) data.get("cuisine"));
            restaurant.setPostcode((String) data.get("postcode"));
            restaurant.setOutcode((String) data.get("outcode"));
            restaurant.setRating(data.get("rating") != null ? ((Number) data.get("rating")).doubleValue() : 0.0);
            restaurant.setNum_reviews(data.get("num_reviews") != null ? ((Number) data.get("num_reviews")).intValue() : 0);
            // Set other fields as needed

            restaurantList.add(restaurant);
        }
        return restaurantList;
    }


    // Interface for callback
    public interface OnWishlistRetrievedListener {
        void onWishlistRetrieved(ArrayList<Restaurant> wishlist);
    }




}
