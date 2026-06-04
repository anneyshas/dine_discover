/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.SearchTreeFolder;

import com.example.dine_discover.Restaurant;

import java.io.Serializable;
/**
 * <p>
 *  Node class is responsible for maintaining information for the WishListAVLTree class.
 *
 * @author Sarah Palmer
 * @since   2024-10-13
 */

public class Node {

    Restaurant value;
    Node leftNode;
    Node rightNode;
    int height;
    int balanceFactor;

    public Node(Restaurant value) {
        this.value = value;
        this.height = 1;
        this.balanceFactor = 0;
        this.rightNode = null;
        this.leftNode = null;
    }

}
