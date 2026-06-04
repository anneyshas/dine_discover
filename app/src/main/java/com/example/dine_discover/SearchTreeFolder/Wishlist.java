/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.SearchTreeFolder;

import com.example.dine_discover.User;
import com.example.dine_discover.Restaurant;

import java.util.*;

/**
 * The Wishlist Class is a class to store the information of the restaurants a user wishes to visit.
 * This class implements the multi-ton design pattern to ensure that only one wishlist is created for one user.
 *
 * @author  Sarah Palmer
 * @since   2024-10-13
 */

public class Wishlist {
    private static Wishlist instance;
    private final WishlistAVLTree storageDataAVL;

    private Wishlist() {
        storageDataAVL = new WishlistAVLTree();
    }

    /**
     * GetInstance returns the Wishlist of a user. Implements multi-ton to ensure
     * that only one AVL Tree is ever created and used for a single users wishlist.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    public static Wishlist getInstance() {
        if (instance == null) {  // First check without locking
            synchronized (Wishlist.class) {  // Locking to prevent race conditions
                if (instance == null) {  // Double-check locking for thread safety
                    instance = new Wishlist();
                }
            }
        }
        return instance;
    }

    public WishlistAVLTree getStorageDataAVL() {
        return storageDataAVL;
    }


    /**
     * addRestaurant adds the restaurant to a users AVL Tree.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    public void addRestaurant(Restaurant restaurant) {
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant cannot be null");
        }
        storageDataAVL.insertNode(restaurant);
    }

    /**
     * removeRestaurant removes the restaurant from a users AVL Tree.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    public void removeRestaurant(Restaurant restaurant) {
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant cannot be null");
        }
        storageDataAVL.deleteNode(restaurant);
    }

    /**
     * Flattens the AVL Tree into an ArrayList of restaurants and sorts the array list
     * using the compareTo function within the Restaurant Class.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    public ArrayList<Restaurant> convertToDisplayData() {
        ArrayList<Restaurant> data = storageDataAVL.getStorageData();
        if (data == null) {
            return new ArrayList<Restaurant>();
        }
        Collections.sort(data);
        return data;
    }

}
