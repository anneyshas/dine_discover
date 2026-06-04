/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

public class Place {

    public static int placeID;
    public static String name;
    public static String location;
    public static String rating;
    public static String openingHours;
    public static String cusine;
    private static int placeIDSequenceCounter = 0;

    // Constructor for class
    public Place(String PlaceID) {
        this.placeID = placeID;
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.openingHours = openingHours;
        this.cusine = cusine;
    }

    public Place() {
        this.placeID = placeIDSequenceCounter;
    }

    public static int generateNewPlaceID() {
        placeIDSequenceCounter++;
        return placeIDSequenceCounter;
    }

    public static int getPlaceID() {
        return placeIDSequenceCounter;
    }

    public static String getName() {
        return name;
    }

    public static String getCusine() {
        return cusine;
    }

    public static String getLocation() {
        return location;
    }

    public static String getOpeningHours() {
        return openingHours;
    }

    public static String getRating() {
        return rating;
    }
}

