/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import com.example.dine_discover.LoginSystem.LoggedInState;
import com.example.dine_discover.LoginSystem.LoggedOutState;
import com.example.dine_discover.LoginSystem.LoginState;

import com.example.dine_discover.SearchTreeFolder.Wishlist;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Observer {
    /**
     * Represents a user in the system.
     * <p>
     * This class manages the user's login state, wishlist, and reviews. It also
     * provides methods for logging in, logging out, and switching to guest mode.
     * </p>
     *
     * @author Anneysha Sarkar, Holly Jacob
     */
    private String email;
    private String password;
    private LoginState loginState;          // if logged in, true, else false
    private Wishlist wishlist;
    private HashMap<Place, Review> myReviews;
    private boolean isNotifiedOfNewComment;
    private FirebaseFirestore db;


    public User(String email, String password){
        this.email = email;
        this.password = password;
        this.loginState = new LoggedOutState();
        this.wishlist = Wishlist.getInstance();
        this.myReviews = new HashMap<>();
        this.isNotifiedOfNewComment = false;
    }

    public User(String email){
        this.email = email;
        this.password = "";
        this.loginState = new LoggedOutState();
        this.wishlist = Wishlist.getInstance();
        this.myReviews = new HashMap<>();
        this.isNotifiedOfNewComment = false;

    }

    public void guestMode() {
        loginState = loginState.guestMode(this);
    }

    /**
     * If the state is correctly set for the user, then return true.
     * If the input state is not valid, return false.
     *
     * @return boolean true if user is logged in
     * @author Anneysha Sarkar
     */

    // Change the login state
    public void setState(LoginState newState) {
        this.loginState = newState;
    }

    // Perform login operation
    public void login() {
        this.loginState.login(this);
    }

    // Perform logout operation
    public void logout() {
        loginState.logout(this);
    }

    public boolean isLoggedIn(){
        if (this.getCurrentState().equals(LoginState.CurrentLoginState.LOGGED_IN)){
            return true;
        }

        return false;
    }

    /**
     * Returns the username if user is logged in
     * Else returns null
     * @return the user's email if they are logged in
     * @author Holly Jacob, Anneysha Sarkar
     */
    public String getUsername(){
        if (loginState instanceof LoggedInState){
            return this.email;
        }
        return null;
    }

    /**
     * Returns the password for a logged in user
     * @return password if logged in = true, else null
     * @author Anneysha Sarkar
     */
    public String getPassword(){
        if (this.loginState instanceof LoggedInState) {
            return this.password;
        }
        return null;
    }

    /**
     * Returns all places that the user added to their wishlist.
     * @return List of places in their wishList, otherwise wishlist is null.
     * @author Anneysha Sarkar
     */
    public Wishlist getWishList() {
        return Wishlist.getInstance();
    }


    /**
     * Provides the user's reviews.
     * <p>
     * This method returns an unmodifiable map of the user's reviews, where each review is associated with a place.
     * </p>
     *
     * @return an unmodifiable map of the user's reviews
     * @author Anneysha Sarkar
     */
    public HashMap<Place, Review> getMyReviews() {
        return (HashMap<Place, Review>) Collections.unmodifiableMap(this.myReviews);
    }
    /**
     * @return whether the current user is notified of the new comment
     */
    public boolean isNotifiedOfNewComment() {
        return isNotifiedOfNewComment;
    }


    /**
     * adds the new review the user just created for a visited place to the list of myReviews
     * @param place
     * @param review
     * @return boolean value if the review is successfully added to the list
     * @author Holly Jacob
     */
    public boolean createReview(Place place, Review review){
        if (review != null){
            this.myReviews.put(place,review);
            return true;
        }
        return false;
    }


    @Override
    public void update(String message) {
        this.isNotifiedOfNewComment = true;
    }

    public LoginState getCurrentState(){
        return this.loginState;
    }

}
