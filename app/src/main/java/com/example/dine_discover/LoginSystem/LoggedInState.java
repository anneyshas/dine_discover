/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.LoginSystem;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import com.example.dine_discover.Card_Factory.CardMoreActivity;
import com.example.dine_discover.SearchActivity;
import com.example.dine_discover.User;
import com.example.dine_discover.UserProfileActivity;

/**
 * Represents the logged in state of a user.
 * <p>
 * This class implements the {@link LoginState} interface and defines the behaviour for a user who is logged in.
 * </p>
 *
 * @author Anneysha Sarkar
 */
public class LoggedInState implements LoginState {

    /**
     * Logs in the specified user.
     * <p>
     * This method prints a message indicating the user is already logged in.
     * </p>
     *
     * @param user the user to log in
     * @author Anneysha Sarkar
     */
    @Override
    public LoginState login(User user) {
        // Already logged in
        return this;
    }

    /**
     * Logs out the specified user.
     * <p>
     * This method transitions the user's state to {@code LoggedOutState} and prints a message indicating the user has logged out.
     * </p>
     *
     * @param user the user to log out
     * @author Anneysha Sarkar
     */
    @Override
    public LoginState logout(User user) {
        // Switch to logged out state
        LoginState newState = new LoggedOutState();
        user.setState(newState);
        return newState;
    }

    @Override
    public LoginState guestMode(User user) {
        // Switch to guest mode
        LoginState newState = new GuestState();
        user.setState(newState);
        return newState;
    }

    /**
     * Retrieves the current login state of the user.
     * <p>
     * This method returns {@code CurrentLoginState.LOGGED_IN} to indicate the user is logged in.
     * </p>
     *
     * @return the current login state of the user
     * @author Anneysha Sarkar
     */
    @Override
    public CurrentLoginState getStateOfUser() {
        return CurrentLoginState.LOGGED_IN;
    }

    @Override
    public void changeButtonText(Button leftButton, Button rightButton) {
        leftButton.setText("Profile");
        rightButton.setText("Logout");
    }

    @Override
    public void clickLeftButton(Context context, Button leftButton) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void clickRightButton(Context context, Button rightButton) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void viewWishlist(Context context) {
        System.out.println("Viewing Wishlist.");
        Intent intent = new Intent(context, CardMoreActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void goToSearchPage(Context context) {
        System.out.println("Navigating to Discovery Page.");
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
}
