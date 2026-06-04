package com.example.dine_discover;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ReplyTest {

    private Reply reply1;
    private Reply reply2;

    @Before
    public void setUp() {
        // Initialize the Reply objects
        reply1 = new Reply("user1@example.com", "This is a reply");
        reply2 = new Reply("user2@example.com", "Another reply");
    }

    @Test
    public void testConstructor() {
        // Test if the constructor initializes fields correctly
        assertEquals("user1@example.com", reply1.getUser());
        assertEquals("This is a reply", reply1.getComment());

        assertEquals("user2@example.com", reply2.getUser());
        assertEquals("Another reply", reply2.getComment());
    }

    @Test
    public void testSettersAndGetters() {
        // Test setting and getting userEmail
        reply1.setUserEmail("newuser@example.com");
        assertEquals("newuser@example.com", reply1.getUser());

        // Test setting and getting reply comment
        reply1.setComment("Updated reply");
        assertEquals("Updated reply", reply1.getComment());
    }

    @Test
    public void testToMap() {
        // Create the expected map
        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("userEmail", "user1@example.com");
        expectedMap.put("reply", "This is a reply");

        // Test if the toMap() method returns the correct map
        assertEquals(expectedMap, reply1.toMap());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Create a duplicate Reply object for testing equality
        Reply duplicateReply = new Reply("user1@example.com", "This is a reply");

        // Test if the replies are considered equal
        assertTrue(reply1.equals(duplicateReply));
        assertEquals(reply1.hashCode(), duplicateReply.hashCode());

        // Test that different replies are not equal
        assertFalse(reply1.equals(reply2));
    }

    @Test
    public void testToString() {
        // Test if the toString method returns the expected string
        String expectedString = "User user1@example.com comments: This is a reply";
        assertEquals(expectedString, reply1.toString());
    }

    @Test
    public void testDefaultConstructor() {
        // Test the default constructor and setting fields later
        Reply defaultReply = new Reply();
        defaultReply.setUserEmail("user3@example.com");
        defaultReply.setComment("Third reply");

        assertEquals("user3@example.com", defaultReply.getUser());
        assertEquals("Third reply", defaultReply.getComment());
    }
}
