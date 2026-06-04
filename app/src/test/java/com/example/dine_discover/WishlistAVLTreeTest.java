package com.example.dine_discover;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.dine_discover.SearchTreeFolder.WishlistAVLTree;

import java.util.ArrayList;

public class WishlistAVLTreeTest {
    /**
     * Author: Holly Jacob
     * Tests the insert method for the wishlist avl tree correctly inserts a new restaurant
     */
    @Test
    public void testInsert(){
        WishlistAVLTree tree = new WishlistAVLTree();
        Restaurant rest = new Restaurant();
        rest.setName("Place1");
        tree.insertNode(rest);
        ArrayList<Restaurant> list = tree.getStorageData();
        assertEquals("Restaurant was not inserted into tree",rest.getName(),list.get(0).getName());
    }
    /**
     * Author: Holly Jacob
     * Tests the insert method for the wishlist avl tree throws illegal argument expection when trying to insert a null restaurant
     */
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testInsertNull(){
        WishlistAVLTree tree = new WishlistAVLTree();
        tree.insertNode(null);
    }
    /**
     * Author: Holly Jacob
     * Tests the insert method for the wishlist avl tree thows and illegal argument exception when trying to insert duplicate restaurants
     */
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testInsertDuplicate(){
        WishlistAVLTree tree = new WishlistAVLTree();
        Restaurant rest = new Restaurant();
        rest.setName("Place1");
        tree.insertNode(rest);
        tree.insertNode(rest);
    }
    /**
     * Author: Holly Jacob
     * Tests the delete method for the wishlist avl tree correctly deletes an inserted restaurant
     */
    @Test
    public void testDelete(){
        WishlistAVLTree tree = new WishlistAVLTree();
        Restaurant rest1 = new Restaurant();
        rest1.setName("Place1");
        tree.insertNode(rest1);
        Restaurant rest2 = new Restaurant();
        rest1.setName("Place2");
        tree.insertNode(rest2);
        ArrayList<Restaurant> list = tree.getStorageData();
        assertEquals("Restaurant was not inserted into tree",rest1.getName(),list.get(0).getName());
        tree.deleteNode(rest1);
        list = tree.getStorageData();
        assertTrue("Restaurant was not deleted",list.size() == 1);
    }
    /**
     * Author: Holly Jacob
     * Tests the delete method for the wishlist avl tree throws illegal argument exception when trying
     * to delete a null element
     */
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testDeleteNull(){
        WishlistAVLTree tree = new WishlistAVLTree();
        Restaurant rest1 = new Restaurant();
        rest1.setName("Place1");
        tree.insertNode(rest1);
        ArrayList<Restaurant> list = tree.getStorageData();
        assertEquals("Restaurant was not inserted into tree",rest1.getName(),list.get(0).getName());
        tree.deleteNode(null);
    }

}
