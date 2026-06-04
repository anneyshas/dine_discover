/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.SearchTreeFolder;

//import com.example.dine_discover.Restaurant;

import com.example.dine_discover.Restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 * The class CuisineAVL Tree creates an AVL Tree based on the restaurant field cuisine.
 * Each node is created based on the cuisine type.
 * If a restaurant does not have a cuisine type which is within the current AVL tree, a new node is
 * created and the restaurant is added into the ArrayList within each node.
 * If restaurant does have a cuisine type which exists in the current AVL tree, the restaurant is
 * added to the ArrayList of the node which matches the cuisine type of the restaurant.
 *
 * @author  Sarah Palmer
 * @since   2024-10-13
 */
public class GenericsAVLTree<T extends Comparable<T>> implements Serializable {

    GenericsNode<T> root;
    GenericsNode<T> current;

    public GenericsAVLTree() {
    }

    public GenericsNode<T> getRoot() {
        if (root != null) {
            return root;
        }
        else throw new IllegalArgumentException("No node");
    }

    /**
     * returns the height of the current node.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    private int getHeight(GenericsNode<T> current) {
        if (current == null) {
            return 0;
        }
        else {
            return current.height;
        }
    }

    /**
     * Sets the height of the current node.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    private void setHeight(GenericsNode<T> current) {
        current.height = Math.max(getHeight(current.leftNode), getHeight(current.rightNode)) + 1;
    }

    /**
     * returns the balance factor for the current node.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    private int getBalanceFactor(GenericsNode<T> current) {
        if (current == null) {
            return 0;
        }
        else {
            current.balanceFactor = getHeight(current.leftNode) - getHeight(current.rightNode);
            return current.balanceFactor;
        }
    }

    /**
     * Performs a right rotation.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    private GenericsNode<T> rightRotation(GenericsNode<T> current) {

        GenericsNode<T> currentLeft = current.leftNode;

        current.leftNode = currentLeft.rightNode;
        currentLeft.rightNode = current;

        setHeight(current);
        setHeight(currentLeft);

        return currentLeft;
    }
    /**
     * Performs a left rotation.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    private GenericsNode<T> leftRotation(GenericsNode<T> current) {
        GenericsNode<T> currentRight = current.rightNode;

        current.rightNode = currentRight.leftNode;
        currentRight.leftNode = current;

        setHeight(current);
        setHeight(currentRight);

        return currentRight;
    }

    /**
     * Rebalances a tree determined by the balance factor of a node.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    private GenericsNode<T> rebalance(GenericsNode<T> current) {
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
     * Inserts a restaurant into the AVL tree. Creates a new node if the cuisine type
     * does not exist within the current tree and saves the restaurant within the ArrayList of
     * that node. Else the restaurant is saved into the ArrayList of the node which matches the
     * cuisine of the restaurant.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    // function different in each AVL Tree class
    public void insertNode(Restaurant restaurant, T comparisonValue) {
        root = insertNode(restaurant, comparisonValue, root);
    }

    private GenericsNode<T> insertNode(Restaurant restaurant, T comparisonValue, GenericsNode<T> currentNode) {

        if (restaurant == null) {
            throw new IllegalArgumentException("Element cannot be Null");
        }

        if (currentNode == null) {
            GenericsNode<T> newGenericsNode = new GenericsNode<T>(comparisonValue);
            newGenericsNode.restaurantArrayList.add(restaurant);
            return newGenericsNode;
        }

        if (comparisonValue.compareTo(currentNode.comparisonValue) == 0 && checkRestaurantNotExist(currentNode, restaurant)) {
            currentNode.restaurantArrayList.add(restaurant);
            return currentNode;
        }

        else if (comparisonValue.compareTo(currentNode.comparisonValue) < 0) {
            currentNode.leftNode = insertNode(restaurant, comparisonValue, currentNode.leftNode);
        }
        else if(comparisonValue.compareTo(currentNode.comparisonValue)> 0) {
            currentNode.rightNode = insertNode(restaurant, comparisonValue, currentNode.rightNode);
        }

        setHeight(currentNode);
        return rebalance(currentNode);
    }

    /**
     * Finds the restaurant in the
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */

    public void rewriteRestaurant(Restaurant restaurant, T comparisonValue) {
        root = rewriteRestaurant(restaurant, comparisonValue, root);
    }

    private GenericsNode<T> rewriteRestaurant(Restaurant restaurant, T comparisonValue, GenericsNode<T> currentNode) {

        if (restaurant == null) {
            throw new IllegalArgumentException("Element cannot be Null");
        }

        if (currentNode == null) {
            GenericsNode<T> newGenericsNode = new GenericsNode<T>(comparisonValue);
            newGenericsNode.restaurantArrayList.add(restaurant);
            return newGenericsNode;
        }

        if (comparisonValue.compareTo(currentNode.comparisonValue) == 0 && !checkRestaurantNotExist(currentNode, restaurant)) {
            currentNode.restaurantArrayList.remove(restaurant);
            Restaurant rest = new Restaurant(restaurant.getAddressLine2(), restaurant.getURL(), restaurant.getAddress(), restaurant.getName(), restaurant.getOutcode(), restaurant.getPostcode(), restaurant.getRating(), restaurant.getCuisine(), restaurant.getHashCode(), restaurant.getNum_reviews(), restaurant.getReviews()) ;
            currentNode.restaurantArrayList.add(rest);
            return currentNode;
        }

        else if (comparisonValue.compareTo(currentNode.comparisonValue) < 0) {
            currentNode.leftNode = insertNode(restaurant, comparisonValue, currentNode.leftNode);
        }
        else if(comparisonValue.compareTo(currentNode.comparisonValue)> 0) {
            currentNode.rightNode = insertNode(restaurant, comparisonValue, currentNode.rightNode);
        }

        setHeight(currentNode);
        return rebalance(currentNode);
    }

    /**
     * Checks that a restaurant doesn't exist in the ArrayList of a node. Returns true if the
     * restaurant does NOT exist within the node.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    private boolean checkRestaurantNotExist(GenericsNode<T> current, Restaurant element) {
        for (Restaurant restaurant : current.restaurantArrayList) {
            if (restaurant.setHashCode() == element.setHashCode()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Traverses through the AVL tree until a node is found which equals the string
     * input provided and then returns the ArrayList saved in that node of the tree.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    // function different in each AVL Tree class
    public ArrayList<Restaurant> traverse(String query) {
        if (root == null || query.isEmpty()) {
            return new ArrayList<>();
        }

        current = root;
        ArrayList<Restaurant> matchingRestaurants = new ArrayList<>();

        Stack<GenericsNode<T>> stack = new Stack<>();
        while (!stack.isEmpty() || current != null) {

            while (current != null) {
                stack.push(current);
                current = current.leftNode;
            }

            current = stack.pop();

            if (current.comparisonValue instanceof String) {
                String CV = (String) current.comparisonValue;
                if (CV.equalsIgnoreCase(query)) {
                    matchingRestaurants.addAll(current.restaurantArrayList);
                }
            }
            else if (current.comparisonValue instanceof Double) {
                Double q = Double.parseDouble(query);
                if (current.comparisonValue.equals(q)) {
                    matchingRestaurants.addAll(current.restaurantArrayList);
                }
            }

            current = current.rightNode;
        }

        return matchingRestaurants;
    }

    /**
     * print function
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    public void preOrderPrint() {
        preOrderPrint(root);
    }

    private void preOrderPrint(GenericsNode<T> current) {
        if (current != null) {
            System.out.println(current);
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

    /**
     * Print function inspired by the code in COMP2100 lab 4.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    public String displayTree(int tabs, GenericsNode<T> current) {
        // StringBuilder is faster than using string concatenation (which in java makes a new object per concatenation).
        if (current == null) {
            return "nothing";
        }
        StringBuilder sb = new StringBuilder(current.comparisonValue.toString());
        sb.append("\n").append("\t".repeat(tabs)).append("├─").append(displayTree(tabs + 1, current.leftNode));
        sb.append("\n").append("\t".repeat(tabs)).append("├─").append(displayTree(tabs + 1, current.rightNode));
        return sb.toString();
    }

    public ArrayList<Restaurant> toArrayList() {
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        collectRestaurants(root, restaurantList);
        return restaurantList;
    }

    private void collectRestaurants(GenericsNode<T> currentNode, ArrayList<Restaurant> restaurantList) {
        if (currentNode == null) {
            return;
        }

        restaurantList.addAll(currentNode.restaurantArrayList);

        collectRestaurants(currentNode.leftNode, restaurantList);
        collectRestaurants(currentNode.rightNode, restaurantList);
    }

}
