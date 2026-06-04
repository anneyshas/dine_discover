package com.example.dine_discover;

import com.example.dine_discover.LoginSystem.LoggedInState;
import com.example.dine_discover.LoginSystem.LoggedOutState;
import com.example.dine_discover.LoginSystem.LoginState;
import com.example.dine_discover.SearchTreeFolder.Wishlist;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class UserTest {

    private User user;
    private User loggedInUser;
    private Place place1;
    private Review review1;

    @Before
    public void setUp() {
        // Initialize a user who is logged out
        user = new User("user@example.com", "password123");

        // Initialize a user who is logged in
        loggedInUser = new User("loggedin@example.com", "securepassword");
        loggedInUser.setState(new LoggedInState());

        // Create a sample place and review
        place1 = new Place("1");
        List<Reply> reply = new ArrayList<>();
        String user = "email@gmail.com";
        review1 = new Review(user,5,"Great place!", reply);
    }

    @Test
    public void testLoginState() {
        // Test initial state is LoggedOut
        assertFalse(user.isLoggedIn());

        // Change the login state to logged in
        user.setState(new LoggedInState());
        assertTrue(user.isLoggedIn());

        user.logout();
        assertFalse(user.isLoggedIn());
    }

    @Test
    public void testGetUsername() {
        // Test if username is returned when the user is logged in
        assertEquals("loggedin@example.com", loggedInUser.getUsername());

        // Test if null is returned when the user is logged out
        assertNull(user.getUsername());
    }

    @Test
    public void testGetPassword() {
        // Test if password is returned when the user is logged in
        assertEquals("securepassword", loggedInUser.getPassword());

        // Test if null is returned when the user is logged out
        assertNull(user.getPassword());
    }






    @Test
    public void testNotificationForNewComment() {
        assertFalse(user.isNotifiedOfNewComment());
        user.update("New comment added");
        assertTrue(user.isNotifiedOfNewComment());
    }

}
