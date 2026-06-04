/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import android.util.Log;

import com.example.dine_discover.CurrentUser;
import com.example.dine_discover.LoginSystem.LoginActivity;
import com.example.dine_discover.SearchTreeFolder.WishlistAVLTree;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WishlistParser {
    /**
     * WishlistParser contains code for parsing the returned
     * wishlist back into restaurant objects, returning the parsed
     * wishlist, and generating a new wishlist with the updated database
     * information.
     *
     ** @author Gauri Chopra
     */

    LoginActivity loginActivity = new LoginActivity();
    private CurrentUser currentUser;


    public WishlistParser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Restaurant> getArrayRestaurantsDB(){
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        if (currentUser != null) {
            String wishlistString = currentUser.dataToString();
            restaurantList = (ArrayList<Restaurant>) parseWishlist(wishlistString);
        }
        return restaurantList;
    }

    public void getCurrentWishlist() {
        if (currentUser != null) {
            String wishlistString = currentUser.dataToString();
            List<Restaurant> restaurantList = parseWishlist(wishlistString);

            for (Restaurant restaurant : restaurantList) {
                Log.d("Wishlist Parser", "printing restaurants... " + restaurant.restaurantToString());
            }
        } else {
            Log.w("Wishlist Parser", "Current user is null. Unable to retrieve wishlist.");
        }
    }

    public WishlistAVLTree regenerateWishlist(){
        WishlistAVLTree avlTree = new WishlistAVLTree();
        if (currentUser != null) {
            String wishlistString = currentUser.dataToString();
            List<Restaurant> restaurantList = parseWishlist(wishlistString);
            for (Restaurant restaurant : restaurantList) {
                avlTree.insertNode(restaurant);
            }
        }
        return avlTree;
    }

    private List<Restaurant> parseWishlist(String wishlistString) {
        List<Restaurant> restaurantList = new ArrayList<>();

        /** Example wishlist string: "CurrentUser{email='gauri.testing@gmail.com',
         * wishlist=[Restaurant{name='Macdonalds', address='24 place', address_line_2='Kingston',
         * outcode='ACT60', postcode='2600', rating=2.0, cuisine='Mexican', URL='null',
         * comments=null, num_reviews=0, type_of_food='FastFood'}]}"**/


        //first extracting the portion of the string containing the restaurant information
        String wishlistPart = wishlistString.substring(wishlistString.indexOf("[") + 1, wishlistString.indexOf("]"));

        // then splitting the string to get individual restaurant entries
        String[] restaurantEntries = wishlistPart.split("\\},");

        for (String entry : restaurantEntries) {
            entry = entry.trim();
            //(handling edge cases)
            if (!entry.endsWith("}")) {
                entry += "}";
            }
            restaurantList.add(parseRestaurant(entry));
        }

        return restaurantList;
    }

    private Restaurant parseRestaurant(String entry) {
        Restaurant restaurant = new Restaurant();

        entry = entry.trim();

        Pattern pattern = Pattern.compile(
                "name='(.*?)', " +
                        "address='(.*?)', " +
                        "address_line_2='(.*?)', " +
                        "outcode='(.*?)', " +
                        "postcode='(.*?)', " +
                        "rating=([\\d.]+), " +
                        "cuisine='(.*?)', " +
                        "URL='(.*?)', " +
                        "comments=(null|'.*?'), " +
                        "num_reviews=(\\d+), " +
                        "type_of_food='(.*?)'"
        );

        Matcher matcher = pattern.matcher(entry);

        if (matcher.find()) {
            restaurant.setName(matcher.group(1));
            restaurant.setAddress(matcher.group(2));
            restaurant.setAddressLine2(matcher.group(3));
            restaurant.setOutcode(matcher.group(4));
            restaurant.setPostcode(matcher.group(5));
            restaurant.setRating(Double.parseDouble(matcher.group(6)));
            restaurant.setCuisine(matcher.group(7));
            restaurant.setURL(matcher.group(8));

            String commentsValue = matcher.group(9);
//            if (commentsValue.equals("null")) {
//                restaurant.setComments(new ArrayList<>());
//            } else {
//                ArrayList<String> commentsList = new ArrayList<>();
//                commentsList.add(commentsValue.replace("'", ""));
//                restaurant.setComments(commentsList);
//            }
//
//            restaurant.setNum_reviews(Integer.parseInt(matcher.group(10)));
//            restaurant.setTypeOfFood(matcher.group(11));
        } else {
            Log.w("Wishlist Parser", "No match found for restaurant entry: " + entry);
        }

        return restaurant;
    }






}
