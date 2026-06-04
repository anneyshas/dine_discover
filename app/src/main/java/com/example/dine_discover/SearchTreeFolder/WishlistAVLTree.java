/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.SearchTreeFolder;

import android.util.Log;

import com.example.dine_discover.Restaurant;

import java.util.ArrayList;

/**
 * The class WishListAVLTree created the AVL trees based on the hashcode of each restaurant and stores a single
 * restaurant per node instead of within an ArrayList.
 *
 * This AVL tree uses a different node to the other AVL Trees.
 *
 * @author  Sarah Palmer
 * @since   2024-10-13
 */
// function different in each AVL Tree class
public class WishlistAVLTree {
    Node current;
    ArrayList<Restaurant> displayDataArray;

    public WishlistAVLTree() {
        this.displayDataArray = new ArrayList<Restaurant>();
    }

    public Node getCurrent() {
        if (current != null) {
            return current;
        }
        else throw new IllegalArgumentException("No node");
    }

    private int getHeight(Node current) {
        if (current == null) {
            return 0;
        }
        else {
            return current.height;
        }
    }

    private void setHeight(Node current) {
        current.height = Math.max(getHeight(current.leftNode), getHeight(current.rightNode)) + 1;
    }

    private int getBalanceFactor(Node current) {
        if (current == null) {
            return 0;
        }
        else {
            current.balanceFactor = getHeight(current.leftNode) - getHeight(current.rightNode);
            return current.balanceFactor;
        }
    }

    private Node rightRotation(Node current) {
        Node currentLeft = current.leftNode;

        current.leftNode = currentLeft.rightNode;
        currentLeft.rightNode = current;

        setHeight(current);
        setHeight(currentLeft);

        return currentLeft;
    }

    private Node leftRotation(Node current) {
        Node currentRight = current.rightNode;

        current.rightNode = currentRight.leftNode;
        currentRight.leftNode = current;

        setHeight(current);
        setHeight(currentRight);

        return currentRight;
    }

    private Node rebalance(Node current) {
        int bf = getBalanceFactor(current);

        // left heavy
        if (bf > 1) {
            // left left
            if (getBalanceFactor(current.leftNode) > 0) {
                return rightRotation(current);
            }
            //left right
            else {
                current.leftNode = leftRotation(current.leftNode);
                current = rightRotation(current);
            }
        }
        // right heavy
        if (bf < -1) {
            // right right
            if (getBalanceFactor(current.rightNode) < 0) {
                return leftRotation(current);
            }
            //right left
            else {
                current.rightNode = rightRotation(current.rightNode);
                current = leftRotation(current);
            }
        }
        return current;
    }

    /**
     * Inserts the restaurant by comparing the hashcode. Creates new node with restaurant object if
     * the restaurant does not exist.
     *
     * The node inserted is different to the node inserted in the other AVL trees.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    // function different in each AVL Tree class
    public void insertNode(Restaurant element) {
        current = insertNode(element, current);
    }

    private Node insertNode(Restaurant element, Node current) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be Null");
        }
        if (current == null) {
            current = new Node(element);
        }
        else if (element.compareTo(current.value) < 0) {
            current.leftNode = insertNode(element, current.leftNode);
        }
        else if(element.compareTo(current.value) > 0)
            current.rightNode = insertNode(element, current.rightNode);
        else throw new IllegalArgumentException("This restaurant already exists: " + element.toString());

        setHeight(current);
        return rebalance(current);
    }

    /**
     * Traverses the tree until the restaurant of interest is found. Once found the restaurant
     * is removed and the tree is adjusted / rebalanced in accordance.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    // THIS FUNCTION EXISTS ONLY WITHIN THIS AVL TREE CLASS
    public void deleteNode(Restaurant element) {
        current = deleteNode(element, current);
    }

    private Node deleteNode(Restaurant element, Node current) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be Null");
        }
        if (current == null) {
            current = new Node(element);
        }
        if (element.compareTo(current.value) < 0) {
            current.leftNode = deleteNode(element, current.leftNode);
        }
        else if(element.compareTo(current.value) > 0)
            current.rightNode = deleteNode(element, current.rightNode);


        else if (current.rightNode == null && current.leftNode == null) {
            current = null;
        }

        else if (current.leftNode == null) {
            current =  current.rightNode;
        }
        else if (current.rightNode == null) {
            current = current.leftNode;
        }

        else {
            Node inOrderSuccessor = findMinimumNode(current.rightNode);

            current.value = inOrderSuccessor.value;
            current.rightNode = deleteNode(inOrderSuccessor.value, current.rightNode);
        }

        if (current == null) {
            return null;
        }
        setHeight(current);
        return rebalance(current);
    }
    // HELPER FUNCTION FOR deleteNode(). THIS FUNCTION EXISTS ONLY WITHIN THIS AVL TREE CLASS
    private Node findMinimumNode(Node current) {
        while (current.leftNode != null) {
            current = findMinimumNode(current.leftNode);
        }
        return current;
    }

    public void preOrderPrint() {
        preOrderPrint(current);
    }

    private void preOrderPrint(Node current) {
        if (current != null) {
            System.out.println(current.value.getName() + ", " + current.value.hashCode()+ ", " + current.height);
            if (current.leftNode != null) {
                System.out.println("Left");
            }
            preOrderPrint(current.leftNode);
            if (current.rightNode != null) {
                System.out.println("Right");
            }
            preOrderPrint(current.rightNode);
        }
    }

    public String displayTree(int tabs, Node current) {
        // StringBuilder is faster than using string concatenation (which in java makes a new object per concatenation).
        if (current == null) {
            return "nothing";
        }
        StringBuilder sb = new StringBuilder(current.value.toString());
        sb.append("\n").append("\t".repeat(tabs)).append("├─").append(displayTree(tabs + 1, current.leftNode));
        sb.append("\n").append("\t".repeat(tabs)).append("├─").append(displayTree(tabs + 1, current.rightNode));
        return sb.toString();
    }

    // THIS FUNCTION EXISTS ONLY WITHIN THIS AVL TREE CLASS
    public ArrayList<Restaurant> getStorageData() {
//        if (!displayDataArray.isEmpty()) {
//            return displayDataArray;
//        }
        displayDataArray = new ArrayList<Restaurant>();
        if (current != null) {
            toArray(current);
            return displayDataArray;
        }
        else Log.e("set tree", "Error building Arraylist: " + new IllegalArgumentException("Empty Tree"));
        return null;
    }

    // THIS FUNCTION EXISTS ONLY WITHIN THIS AVL TREE CLASS
    private void toArray(Node current) {
        if (current != null) {
            Restaurant res = current.value;
            displayDataArray.add(res);
            toArray(current.leftNode);
            toArray(current.rightNode);
        }
    }

}
