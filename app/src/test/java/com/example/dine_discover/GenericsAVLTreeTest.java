package com.example.dine_discover;

import static org.junit.Assert.assertTrue;

import com.example.dine_discover.SearchTreeFolder.GenericsAVLTree;

import org.junit.Test;

import java.util.ArrayList;

public class GenericsAVLTreeTest {
    /**
     * Author: Holly Jacob
     * Tests the insert method for the generics avl tree correctly inserts a new restaurant
     */
    @Test
    public void testInsert(){
        Restaurant rest = new Restaurant();
        rest.setName("place1");
        rest.setAddress("unit 1");
        rest.setAddressLine2("suburb1");
        rest.setCuisine("food1");
        rest.setOutcode("1");
        rest.setPostcode("1");
        rest.setRating(1.0);
        rest.setReviews(new ArrayList<>());
        GenericsAVLTree tree = new GenericsAVLTree<>();
        tree.insertNode(rest,rest.getRating());

        Restaurant rest2 = new Restaurant();
        rest2.setName("place2");
        rest2.setAddress("unit 2");
        rest2.setAddressLine2("suburb2");
        rest2.setCuisine("food2");
        rest2.setOutcode("2");
        rest2.setPostcode("2");
        rest2.setRating(2.0);
        rest2.setReviews(new ArrayList<>());
        tree.insertNode(rest2,rest2.getRating());

        Restaurant rest3 = new Restaurant();
        rest3.setName("place3");
        rest3.setAddress("unit 3");
        rest3.setAddressLine2("suburb3");
        rest3.setCuisine("food3");
        rest3.setOutcode("3");
        rest3.setPostcode("3");
        rest3.setRating(3.0);
        rest3.setReviews(new ArrayList<>());
        tree.insertNode(rest3,rest3.getRating());
        ArrayList<Restaurant> restaurants = tree.toArrayList();
        assertTrue("Incorrect restaurant printed "+restaurants.get(0).toString()+" Instead of "+rest2.toString(),restaurants.get(0).equals(rest2));
        assertTrue("Incorrect restaurant printed "+restaurants.get(1).toString()+" Instead of "+rest.toString(),restaurants.get(1).equals(rest));
        assertTrue("Incorrect restaurant printed "+restaurants.get(2).toString()+" Instead of "+rest3.toString(),restaurants.get(2).equals(rest3));
    }
    /**
     * Author: Holly Jacob
     * Tests the insert method for the generics avl tree throws illegal argument expection when trying to insert a null restaurant
     */
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testInsertNull(){
        GenericsAVLTree tree = new GenericsAVLTree();
        tree.insertNode(null,null);
    }
    /**
     * Author: Holly Jacob
     * Tests the insert method for the generics avl tree does not insert duplicate restaurants
     */
    @Test
    public void testInsertDuplicate(){
        Restaurant rest = new Restaurant();
        rest.setName("place1");
        rest.setAddress("unit 1");
        rest.setAddressLine2("suburb1");
        rest.setCuisine("food1");
        rest.setOutcode("1");
        rest.setPostcode("1");
        rest.setRating(1.0);
        rest.setReviews(new ArrayList<>());
        GenericsAVLTree tree = new GenericsAVLTree<>();
        tree.insertNode(rest,rest.getRating());

        Restaurant rest2 = new Restaurant();
        rest2.setName("place1");
        rest2.setAddress("unit 1");
        rest2.setAddressLine2("suburb1");
        rest2.setCuisine("food1");
        rest2.setOutcode("1");
        rest2.setPostcode("1");
        rest2.setRating(1.0);
        rest2.setReviews(new ArrayList<>());
        tree.insertNode(rest2,rest2.getRating());

        ArrayList<Restaurant> restaurants = tree.toArrayList();
        assertTrue("Duplicate element added",restaurants.size()==1);
    }
    /**
     * Author: Holly Jacob
     * Tests the traverse method for the generic avl tree correctly filters the tree for restaurants matching the query value
     */
    @Test
    public void testTraverse(){
        Restaurant rest = new Restaurant();
        rest.setName("place1");
        rest.setAddress("unit 1");
        rest.setAddressLine2("suburb1");
        rest.setCuisine("food1");
        rest.setOutcode("1");
        rest.setPostcode("1");
        rest.setRating(1.0);
        rest.setReviews(new ArrayList<>());
        GenericsAVLTree tree = new GenericsAVLTree<>();
        tree.insertNode(rest,rest.getCuisine());

        Restaurant rest2 = new Restaurant();
        rest2.setName("place2");
        rest2.setAddress("unit 2");
        rest2.setAddressLine2("suburb2");
        rest2.setCuisine("food2");
        rest2.setOutcode("2");
        rest2.setPostcode("2");
        rest2.setRating(2.0);
        rest2.setReviews(new ArrayList<>());
        tree.insertNode(rest2,rest2.getCuisine());

        Restaurant rest3 = new Restaurant();
        rest3.setName("place3");
        rest3.setAddress("unit 3");
        rest3.setAddressLine2("suburb3");
        rest3.setCuisine("food3");
        rest3.setOutcode("3");
        rest3.setPostcode("3");
        rest3.setRating(3.0);
        rest3.setReviews(new ArrayList<>());
        tree.insertNode(rest3,rest3.getCuisine());

        Restaurant rest4 = new Restaurant();
        rest4.setName("place4");
        rest4.setAddress("unit 4");
        rest4.setAddressLine2("suburb3");
        rest4.setCuisine("food3");
        rest4.setOutcode("3");
        rest4.setPostcode("3");
        rest4.setRating(3.0);
        rest4.setReviews(new ArrayList<>());
        tree.insertNode(rest4,rest4.getCuisine());
        ArrayList<Restaurant> restaurants = tree.toArrayList();
        ArrayList<Restaurant> res = tree.traverse("food3");
        assertTrue("Incorrect results returned from query ",res.get(0).equals(rest3));
        assertTrue("Incorrect results returned from query ",res.size()==2);

    }
}
