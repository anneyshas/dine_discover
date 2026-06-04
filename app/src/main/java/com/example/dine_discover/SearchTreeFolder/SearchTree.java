/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.SearchTreeFolder;

import android.content.Context;

import com.example.dine_discover.JSON_Interpreter.JSONParser;
import com.example.dine_discover.JSON_Interpreter.JSONToString;
import com.example.dine_discover.JSON_Interpreter.JSONToken;
import com.example.dine_discover.JSON_Interpreter.JSONTokenizer;
import com.example.dine_discover.Restaurant;
import com.example.dine_discover.SearchActivity;
import com.example.dine_discover.Tokenizer_Parser.QueryParser;
import com.example.dine_discover.Tokenizer_Parser.QueryTokenizer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class SearchTree holds four types of AVL Trees for cuisine, rating, suburb and
 * name for the information in the restaurant.json file
 *
 * @author  Sarah Palmer
 * @since   2024-10-13
 */

public class SearchTree extends WishlistAVLTree implements Serializable {

    private static volatile SearchTree instance;
    transient GenericsAVLTree<String> cuisineAVLTree;
    transient GenericsAVLTree<Double> ratingAVLTree;
    transient GenericsAVLTree<String> suburbAVLTree;
    transient GenericsAVLTree<String> nameAVLTree;
    String JSONstring;
    int nodeIndex;
    transient ArrayList<Restaurant> restList;
    String[] queryResults;
    String query;
    String suburbQuery;
    String nameQuery;
    String cuisineQuery;
    String ratingQuery;
    Double ratingQDouble;

    // constructor
    private SearchTree(Context context) {
        this.nodeIndex = 0;
        this.restList = new ArrayList<Restaurant>();
        setAVLTrees(context);
    }

    public static SearchTree getInstance(Context context) {
        if (instance == null) {
            synchronized (SearchTree.class) {
                if (instance == null) {
                    instance = new SearchTree(context);
                }
            }
        }
        return instance;
    }

    public static void refreshInstance(Context context) {
        synchronized (SearchTree.class) {
            instance = new SearchTree(context);
        }
    }

    /**
     * Sets the String values for searchQuery, cuisineQuery and ratingQuery
     * from the output of getQuery in the class SearchActivity.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    public void setQuery(String input) {

        query = input;

        QueryTokenizer tokenizer = new QueryTokenizer(input);
        QueryParser queryparser = new QueryParser(tokenizer);
        queryparser.parse();
        cuisineQuery = queryparser.getCuisine();
        suburbQuery = queryparser.getSuburb();
        ratingQDouble = queryparser.getRating();
        ratingQuery = String.valueOf(ratingQDouble);
        nameQuery = queryparser.getName();

    }

    /**
     * removeRestaurant removes the restaurant from a users AVL Tree.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    public void setAVLTrees(Context context){
        JSONstring = JSONToString.loadJSONFile(context, "testwriter.json");

        List<JSONToken> JSONTokens = JSONTokenizer.tokens(JSONstring);
        JSONParser parser = new JSONParser(JSONTokens);
        parser.parse();

        cuisineAVLTree = parser.getCuisineTree();
        suburbAVLTree = parser.getSuburbTree();
        ratingAVLTree = parser.getRatingTree();
        nameAVLTree = parser.getNameTree();

    }

    public void resetTreesNewRestaurant(Restaurant restaurant) {
        cuisineAVLTree.rewriteRestaurant(restaurant, restaurant.getCuisine());
        suburbAVLTree.rewriteRestaurant(restaurant, restaurant.getAddressLine2());
        ratingAVLTree.rewriteRestaurant(restaurant, restaurant.getRating());
        nameAVLTree.rewriteRestaurant(restaurant, restaurant.getName());
        restList = instance.searchTrees();
    }

    /**
     * returns a sublist of the restaurants which conform to the users
     * query input set in the function, setQuery. this function uses the preset AVL trees
     * to optimise search time.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    public ArrayList<Restaurant> searchTrees() {
        ArrayList<Restaurant> finalList = new ArrayList<>();

        nameQuery = handleNullCases(nameQuery);
        ratingQuery = handleNullCases(ratingQuery);
        suburbQuery = handleNullCases(suburbQuery);
        cuisineQuery = handleNullCases(cuisineQuery);

        if (!nameQuery.isEmpty()) {
            finalList = nameAVLTree.traverse(nameQuery);
            if (finalList.isEmpty()) {
                nameQuery = "";
            }
            else {
                restList = filterAVL(finalList);
                return restList;
            }
        }
        if (!suburbQuery.isEmpty()) {
            finalList = suburbAVLTree.traverse(suburbQuery);
            if (finalList.isEmpty()) {
                suburbQuery = "";
            }
            else {
                restList = filterAVL(finalList);
                return restList;
            }
        }
        if (!cuisineQuery.isEmpty()) {
            finalList = cuisineAVLTree.traverse(cuisineQuery);
            if (finalList.isEmpty()) {
                cuisineQuery = "";
            }
            else {
                restList = filterAVL(finalList);
                return restList;
            }
        }
        if (!ratingQuery.isEmpty()) {
            finalList = ratingAVLTree.traverse(ratingQuery);
            restList = filterAVL(finalList);
            return restList;
        }

        return filterAVL(finalList);
    }

    private String handleNullCases(String s){
        if (s == null) {
            s = "";
        }
        return s;
    }

    /**
     * filters a list of restaurants returned from one of the nodes of
     * the AVL trees to return only the set which conform to the users Query input.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    private ArrayList<Restaurant> filterAVL(ArrayList<Restaurant> list) {
        ArrayList<Restaurant> finalList = new ArrayList<>();
        for (Restaurant r : list) {
            if (checkWithQuery(r)){
                finalList.add(r);
            }
        }
        return finalList;
    }

    /**
     * checks if a restaurant conforms to the inputs of the query
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    private boolean checkWithQuery(Restaurant r) {

        boolean cuisineFlag = true;

        if (!Objects.equals(cuisineQuery, r.getCuisine().toLowerCase()) && !Objects.equals(cuisineQuery, "") && cuisineQuery != null) {
            cuisineFlag = false;
        }
        if (!Objects.equals(nameQuery, r.getName().toLowerCase()) && !Objects.equals(nameQuery, "") && nameQuery != null) {
            cuisineFlag = false;
        }
        if (!Objects.equals(suburbQuery, r.getAddressLine2().toLowerCase()) && !Objects.equals(suburbQuery, "") && suburbQuery != null) {
            cuisineFlag = false;
        }
        if (!Objects.equals(ratingQDouble, r.getRating()) && ratingQDouble != 0.0) {
            cuisineFlag = false;
        }
        return cuisineFlag;
    }
    /**
     * splits the query string by comma.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    private void splitString() {
        queryResults = query.split(",");
    }

    /**
     * Get the next restaurant of the arraylist rest list,.
     *
     * @author  Sarah Palmer
     * @since   2024-10-13
     */
    public Restaurant getNextRestaurant() throws IOException {
        if (nodeIndex < restList.size() && !restList.isEmpty())  {
            Restaurant currentRestaurant = restList.get(nodeIndex);
            nodeIndex++;
            return currentRestaurant;
        }
        else throw new IOException("Error with getNextRestaurant: Cant get next");
    }

    public Restaurant getCurrentRestaurant() throws IOException {
        if (nodeIndex < restList.size() && !restList.isEmpty())  {
            return restList.get(nodeIndex - 1);
        }
        else throw new IOException("Error with getCurrentRestaurant: Cant get current");
    }

}
