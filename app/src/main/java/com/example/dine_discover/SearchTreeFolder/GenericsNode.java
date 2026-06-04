/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.SearchTreeFolder;

import com.example.dine_discover.Restaurant;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The ArrayListNode Class is a class to store the information of a single restaurant
 * for cooperation into an AVL tree
 *
 * @author  Sarah Palmer
 * @since   2024-10-10
 */

public class GenericsNode<T> implements Serializable {

    ArrayList<Restaurant> restaurantArrayList;
    GenericsNode<T> leftNode;
    GenericsNode<T> rightNode;
    int height;
    int balanceFactor;
    T comparisonValue;

    // Constructor for the node
    public GenericsNode(T comparisonValue) {
        this.restaurantArrayList = new ArrayList<Restaurant>();
        this.height = 1;
        this.balanceFactor = 0;
        this.rightNode = null;
        this.leftNode = null;
        this.comparisonValue = comparisonValue;
    }
}
