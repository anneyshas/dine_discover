/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.LoginSystem;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.example.dine_discover.SignupActivity;
import com.example.dine_discover.User;

/**
 * Represents the logged out state of a user.
 * <p>
 * This class implements the {@link LoginState} interface and defines the behavior for a user who is logged out.
 * </p>
 *
 * @author Anneysha Sarkar
 */
public class LoggedOutState implements LoginState {

    /**
     * Logs in the specified user.
     * <p>
     * This method transitions the user's state to {@code LoggedInState} and prints a message indicating the user has logged in.
     * </p>
     *
     * @param user the user to log in
     * @author Anneysha Sarkar
     */
    @Override
    public LoginState login(User user) {
        LoginState state = new LoggedInState();
        user.setState(state);  // Transition to logged in state
        return state;
    }

    /**
     * Logs out the specified user.
     * <p>
     * This method prints a message indicating the user is already logged out.
     * </p>
     *
     * @param user the user to log out
     * @author Anneysha Sarkar
     */
    @Override
    public LoginState logout(User user) {
        return this;
    }

    @Override
    public LoginState guestMode(User user){
        LoginState state = new GuestState();
        user.setState(state);
        return state;
    }

    /**
     * Retrieves the current login state of the user.
     * <p>
     * This method returns {@code CurrentLoginState.LOGGED_OUT} to indicate the user is logged out.
     * </p>
     *
     * @return the current login state of the user
     * @author Anneysha Sarkar
     */
    @Override
    public CurrentLoginState getStateOfUser() {
        return CurrentLoginState.LOGGED_OUT;
    }


    @Override
    public void changeButtonText(Button leftButton, Button rightButton) {
        leftButton.setText("Login");
        rightButton.setText("Signup");
    }

    @Override
    public void clickLeftButton(Context context, Button leftButton) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void clickRightButton(Context context, Button rightButton) {
        Intent intent = new Intent(context, SignupActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void viewWishlist(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void goToSearchPage(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

}
