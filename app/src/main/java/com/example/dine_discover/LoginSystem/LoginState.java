/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.LoginSystem;

import android.content.Context;
import android.widget.Button;

import com.example.dine_discover.User;
/**
 * <p>
 * LoginState is responsible for maintaining the state of the user </p>
 *
 */

public interface LoginState {
    /**
     * Interface representing the login state of a user.
     * <p>
     * This interface defines methods for logging in and out, and for retrieving the current login state of a user.
     * </p>
     *
     * @author Anneysha Sarkar
     */

    /**
     * Enum representing the current login state of a user.
     */
    enum CurrentLoginState {
        LOGGED_IN,
        LOGGED_OUT,
        GUEST
    }

    /**
     * Logs in the specified user.
     *
     * @param user the user to log in
     * @return the new login state of the user
     * @author Anneysha Sarkar
     */
    LoginState login(User user);
    /**
     * Logs out the specified user.
     *
     * @param user the user to log out
     * @return the new login state of the user
     * @author Anneysha Sarkar
     */
    LoginState logout(User user);
    /**
     * Sets the specified user to guest mode.
     *
     * @param user the user to set to guest mode
     * @return the new login state of the user
     * @author Anneysha Sarkar
     */
    LoginState guestMode(User user);
    /**
     * Gets the current login state of the user.
     *
     * @return the current login state
     * @author Anneysha Sarkar
     */
    CurrentLoginState getStateOfUser();
    /**
     * Changes the text of the specified buttons.
     *
     * @param leftButton the left button to change text
     * @param rightButton the right button to change text
     * @author Anneysha Sarkar
     */
    void changeButtonText(Button leftButton, Button rightButton);
    /**
     * Handles the click event for the left button.
     *
     * @param context the context in which the button is clicked
     * @param leftButton the left button that is clicked
     *
     * @author Anneysha Sarkar
     */
    void clickLeftButton(Context context, Button leftButton);
    /**
     * Handles the click event for the right button.
     *
     * @param context the context in which the button is clicked
     * @param rightButton the right button that is clicked
     * @author Anneysha Sarkar
     */
    void clickRightButton(Context context, Button rightButton);
    /**
     * Opens the wishlist view.
     *
     * @param context the context in which to open the wishlist
     * @author Anneysha Sarkar
     */
    void viewWishlist(Context context);
    /**
     * Navigates to the search page.
     *
     * @param context the context in which to navigate to the search page
     * @author Anneysha Sarkar
     */
    void goToSearchPage(Context context);
}
