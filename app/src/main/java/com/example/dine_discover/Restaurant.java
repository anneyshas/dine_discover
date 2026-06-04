/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant implements Comparable<Restaurant>, Serializable {
    /**
     * Restaurant contains the code related to a restaurant object,
     * including its fields and getter and setter methods.
     *
     *@author Gauri Chopra
     *
     */
    private String URL;
    private String address;
    private String address_line_2; // Note the underscore to match JSON key
    private String name;
    private String outcode;
    private String postcode;
    private double rating;
    private String cuisine;
    private int hash;

    public Restaurant(String address_line_2, String URL, String address, String name, String outcode, String postcode, double rating, String cuisine, int hash, double num_reviews, List<Review> reviews) {
        this.address_line_2 = address_line_2;
        this.URL = URL;
        this.address = address;
        this.name = name;
        this.outcode = outcode;
        this.postcode = postcode;
        this.rating = rating;
        this.cuisine = cuisine;
        this.hash = hash;
        this.num_reviews = num_reviews;
        this.reviews = reviews;
    }

    public void setNum_reviews(double num_reviews) {
        this.num_reviews = num_reviews;
    }

    private double num_reviews;
    private List<Review> reviews;

    public Restaurant(){
        this.reviews = new ArrayList<Review>();
    }


    public void review(User user, int rating, Review comment){
        if (rating>= 1 && rating <= 5){
            this.num_reviews = getNum_reviews()+1;
            this.rating = (getRating() + rating) / getNum_reviews();
        }
        else { throw new  IllegalArgumentException("Rating must be between 1 and 5");}
        this.reviews.add(comment);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> restaurantMap = new HashMap<>();
        restaurantMap.put("name", name);
        restaurantMap.put("address", address);
        restaurantMap.put("address_line_2", address_line_2);
        restaurantMap.put("outcode", outcode);
        restaurantMap.put("postcode", postcode);
        restaurantMap.put("rating", rating);
        restaurantMap.put("cuisine", cuisine);
        restaurantMap.put("URL", URL);
        restaurantMap.put("num_reviews", num_reviews);

        if (reviews != null && !reviews.isEmpty()) {
            List<Map<String, Object>> reviewList = new ArrayList<>();
            for (Review review : reviews) {
                reviewList.add(review.toMap());
            }
            restaurantMap.put("reviews", reviewList);
        }

        return restaurantMap;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    // Getters and setters
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Double getNum_reviews() {
        return num_reviews;
    }


    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressLine2() {
        return address_line_2;
    }

    public void setAddressLine2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutcode() {
        return outcode;
    }

    public void setOutcode(String outcode) {
        this.outcode = outcode;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }



    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }




    public int setHashCode() {
        if (name != null) {
            this.hash = name.hashCode();
        }
        if (address != null) {
            this.hash = hash ^ address.hashCode();
        }
        if (cuisine != null) {
            this.hash = hash ^ cuisine.hashCode();
        }
        if (URL != null) {
            this.hash = hash ^ URL.hashCode();
        }
        if (postcode != null) {
            this.hash = hash ^ postcode.hashCode();
        }
        return hash;
    }

    public int getHashCode() {
        return hash;
    }

    public ArrayList<String> getRestaurantFieldTypes() {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("URL");
        fields.add("address");
        fields.add("address_line_2");
        fields.add("name");
        fields.add("outcode");
        fields.add("postcode");
        fields.add("rating");
        fields.add("comments");
        return fields;
    }

    public String restaurantToString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", address_line_2='" + address_line_2 + '\'' +
                ", outcode='" + outcode + '\'' +
                ", postcode='" + postcode + '\'' +
                ", rating=" + rating +
                ", cuisine='" + cuisine + '\'' +
                ", URL='" + URL + '\'' +
                ", num_reviews=" + num_reviews +
                '}';
    }


    @Override
    public int compareTo(Restaurant r) {

        if (this.setHashCode() > r.setHashCode()) {
            // Current rating is greater, return 1
            return 1;
        } else if (this.setHashCode() < r.setHashCode()) {
            // Current rating is greater, return -1
            return -1;
//        } else if (this.rating == r.rating && this.setHashCode() != r.setHashCode()){
//            // Ratings are the same, return 0
//            return 1;
        } else {
            // restaurants are the same
            return 0;
        }
    }

    @Override
    public String toString() {
        return name;
    }


}
