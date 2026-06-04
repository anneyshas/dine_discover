/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrentUser {
    /**
     * CurrentUser contains code for organising the data returned by
     * getCurrentUserInstance into the email and wishlist of the user.
     *
     ** @author Gauri Chopra
     */
    private String email;
    private List<Restaurant> wishlist;

    // Constructor
    public CurrentUser(Map<String, Object> userData) {
        this.email = (String) userData.get("email");
        this.wishlist = new ArrayList<>();

        // initialising wishlist if it exists in userData
        if (userData.containsKey("Wishlist")) {
            List<Map<String, Object>> wishlistData = (List<Map<String, Object>>) userData.get("Wishlist");
            for (Map<String, Object> data : wishlistData) {
                wishlist.add(createRestaurantFromMap(data));
            }
        }
    }

    // helper method to create Restaurant object from Map
    private Restaurant createRestaurantFromMap(Map<String, Object> data) {
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress((String) data.get("address"));
        restaurant.setAddressLine2((String) data.get("address_line_2"));
        restaurant.setName((String) data.get("name"));
        restaurant.setOutcode((String) data.get("outcode"));
        restaurant.setPostcode((String) data.get("postcode"));
      //  restaurant.setRating((Double) data.get("rating"));
        restaurant.setRating(3);

        restaurant.setCuisine((String) data.get("cuisine"));
        restaurant.setURL((String) data.get("URL"));
        restaurant.setNum_reviews(((Long) data.get("num_reviews")).intValue());
        return restaurant;
    }

    // method to convert user data to string format
    public String dataToString() {
        return "CurrentUser{" +
                "email='" + email + '\'' +
                ", wishlist=" + wishlistToString() +
                '}';
    }

    public String getCurrentEmail(){
        return email;
    }

    // method to convert wishlist to string, including all restaurant fields
    private String wishlistToString() {
        StringBuilder wishlistString = new StringBuilder("[");
        for (int i = 0; i < wishlist.size(); i++) {
            wishlistString.append(wishlist.get(i).restaurantToString());
            if (i < wishlist.size() - 1) {
                wishlistString.append(", ");
            }
        }
        wishlistString.append("]");
        return wishlistString.toString();
    }
}
