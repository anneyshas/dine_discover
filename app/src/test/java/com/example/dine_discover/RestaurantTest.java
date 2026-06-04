package com.example.dine_discover;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestaurantTest {

    private Restaurant restaurant;
    private User user;
    private Review review;

    @Before
    public void setUp() {
        restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test Street");
        restaurant.setPostcode("12345");
        restaurant.setCuisine("Italian");
        restaurant.setRating(4.0);
        restaurant.setNum_reviews(10);

        // Setting up a mock user and review
        review = new Review("test@gmail.com", 5, "Great food!", new ArrayList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReview_InvalidRating() {
        restaurant.review(user, 6, review);
    }

    @Test
    public void testToMap() {
        Map<String, Object> restaurantMap = restaurant.toMap();

        // Check if the values in the map match the restaurant attributes
        assertEquals("Test Restaurant", restaurantMap.get("name"));
        assertEquals("123 Test Street", restaurantMap.get("address"));
        assertEquals("12345", restaurantMap.get("postcode"));
        assertEquals(4.0, restaurantMap.get("rating"));
        assertEquals("Italian", restaurantMap.get("cuisine"));
    }

    @Test
    public void testSetAndGetNumReviews() {
        restaurant.setNum_reviews(15);
        assertEquals(15.0, restaurant.getNum_reviews(), 0.0);
    }

    @Test
    public void testCompareTo_SameRestaurant() {
        Restaurant sameRestaurant = new Restaurant();
        sameRestaurant.setName("Test Restaurant");
        sameRestaurant.setAddress("123 Test Street");
        sameRestaurant.setPostcode("12345");
        sameRestaurant.setCuisine("Italian");

        assertEquals(0, restaurant.compareTo(sameRestaurant));
    }

    @Test
    public void testCompareTo_DifferentRestaurant() {
        Restaurant differentRestaurant = new Restaurant();
        differentRestaurant.setName("A Restaurant");
        differentRestaurant.setAddress("456 Other Street");
        differentRestaurant.setPostcode("54321");
        differentRestaurant.setCuisine("Mexican");

        assertNotEquals(0, restaurant.compareTo(differentRestaurant));
    }

    @Test
    public void testSetAndGetReviews() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        restaurant.setReviews(reviews);

        assertEquals(1, restaurant.getReviews().size());
        assertEquals(review, restaurant.getReviews().get(0));
    }
}
