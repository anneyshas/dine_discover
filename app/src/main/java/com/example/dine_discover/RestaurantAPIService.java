/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RestaurantAPIService {
    // defines a GET request to the restaurant search endpoint
    @GET("restaurants/search")
    Call<RestaurantResponse> getRestaurants(
            @Header("X-RapidAPI-Key") String apiKey,  // pass the API key in the header
            @Query("location") String location,       // query parameter for location (e.g., "New York")
            @Query("radius") int radius,              // radius parameter (e.g., 1000 meters)
            @Query("cuisine") String cuisine          // optional: Filter by cuisine type (e.g., "Italian")
    );
}
