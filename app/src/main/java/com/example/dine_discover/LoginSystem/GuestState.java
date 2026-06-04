/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.LoginSystem;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.example.dine_discover.Card_Factory.CardMoreActivity;
import com.example.dine_discover.DiscoveryActivity;
import com.example.dine_discover.SearchActivity;
import com.example.dine_discover.SignupActivity;
import com.example.dine_discover.User;
import com.example.dine_discover.UserProfileActivity;

public class GuestState implements LoginState{
    /**
     * Represents the guest state of a user.
     * <p>
     * This class implements the {@link LoginState} interface and defines the behavior for a user who is logged out.
     * </p>
     *
     * @author Anneysha Sarkar
     */

    @Override
    public LoginState login(User user) {
        LoginState state = new LoggedInState();
        user.setState(state);
        return state;
    }

    @Override
    public LoginState logout(User user) {
        LoginState state = new LoggedOutState();
        user.setState(state);
        return state;
    }

    @Override
    public LoginState guestMode(User user) {
        return this;
    }

    @Override
    public CurrentLoginState getStateOfUser() {
        return CurrentLoginState.GUEST;
    }

    @Override
    public void changeButtonText(Button loginButton, Button signupButton) {
        loginButton.setText("Profile");
        signupButton.setText("Signup");
    }

    @Override
    public void clickLeftButton(Context context, Button leftButton) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void clickRightButton(Context context, Button rightButton) {
        Intent intent = new Intent(context, SignupActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void viewWishlist(Context context) {
        Intent intent = new Intent(context, CardMoreActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void goToSearchPage(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
}
