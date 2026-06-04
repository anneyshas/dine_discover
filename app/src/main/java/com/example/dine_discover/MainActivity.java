/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dine_discover.CurrentUser;
import com.example.dine_discover.LoginSystem.LoggedOutState;
import com.example.dine_discover.LoginSystem.LoginActivity;
import com.example.dine_discover.LoginSystem.LoginState;
import com.example.dine_discover.SearchTreeFolder.SearchTree;
import com.example.dine_discover.SearchTreeFolder.SearchTreeManager;
import com.example.dine_discover.LoginSystem.LoggedInState;
import com.example.dine_discover.LoginSystem.LoginActivity;
import com.example.dine_discover.Tokenizer_Parser.QueryParser;
import com.example.dine_discover.SearchTreeFolder.Wishlist;
import com.example.dine_discover.Tokenizer_Parser.QueryTokenizer;
import com.example.dine_discover.Tokenizer_Parser.SendRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.PersistentCacheSettings;
import java.util.ArrayList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    /**
     * MainActivity contains code for the home page of the app.
     * It also initialises processes such as data retrieval and search.
     * MainActivity.class is linked to activity_main.xml for its UI.
     *
     * @author Anneysha Sarkar, Gauri Chopra
     *
     */

    private RestaurantAPIService apiService;
    private SendRequest sendRequest;
    private QueryTokenizer tokenizer;
    private DBRetrieve dbRetrieve;
    private ExecutorService executorService;
    private Button loginButton;
    private Button signupButton;
    private Button wishlistButton;
    private Button searchButton;
    private User currentUser;
    private LoginState loginState;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ArrayList<Restaurant> restaurantsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executorService = Executors.newSingleThreadExecutor();


        runBackgroundTask();

        PersistentCacheSettings persistentCacheSettings = PersistentCacheSettings.newBuilder()
                .setSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setLocalCacheSettings(persistentCacheSettings)
                .build();

        db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);

        loginState = new LoggedOutState();



        Restaurant restaurant = new Restaurant();
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        restaurant.setAddress("24 place");
        restaurant.setAddressLine2("Kingston");
        restaurant.setName("Macdonalds");
        restaurant.setCuisine("Mexican");
        restaurant.setPostcode("2600");
        restaurant.setOutcode("ACT60");
        restaurant.setRating(2);
        restaurant.setNum_reviews(0);

        restaurantList.add(restaurant);
        if(restaurantList.isEmpty()){
            Log.d("Firestore", "arraylist is empty");

        } else Log.d("Firestore", "arraylist is not empty");


        //   WishlistToDB wishlistToDB = new WishlistToDB();
        //  CurrentUser currentuser = new CurrentUser()
        //    wishlistToDB.storeWishlistDB(restaurantList);




        //commented temporarily: uncomment later
        // addRestaurantsToFirestore();

        //deleteAllRestaurants();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialise buttons
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signUpHomeButton);
        wishlistButton = findViewById(R.id.createWishListHomeButton);
        searchButton = findViewById(R.id.searchHomeButton);

        // Check if the user is logged in
        currentUser = getCurrentUser();
        currentUser.getCurrentState().changeButtonText(loginButton, signupButton);

        // Handle login button click
        loginButton.setOnClickListener(view -> {
            currentUser.getCurrentState().clickLeftButton(MainActivity.this, loginButton);        });

        // Handle signup button click
        signupButton.setOnClickListener(view -> {
            currentUser.getCurrentState().clickRightButton(MainActivity.this, signupButton);
        });

        // Handle wishlist button click
        wishlistButton.setOnClickListener(view -> {
            currentUser.getCurrentState().viewWishlist(MainActivity.this);
        });

        // Handle search button click
        searchButton.setOnClickListener(view -> {
            currentUser.getCurrentState().goToSearchPage(MainActivity.this);
        });
    }

    /**
     * Updates the state of the button based on the current user's login status.
     * <p>
     * If the current user is logged in, the button state is set to {@code LoggedInButtonState}.
     * Otherwise, the button state is set to {@code LoggedOutButtonState}.
     * </p>
     *
     * @author Anneysha Sarkar
     */
//    private void updateButtonState() {
//        if (currentUser != null && currentUser.isLoggedIn()) {
//            buttonState = new LoggedInButtonState();
//        } else {
//            buttonState = new LoggedOutButtonState();
//        }
//    }

    /**
     * sets up the home page depending if the current user is logged in or not
     * <p>
     * This method returns nothing
     * </p>
     *
     * @author Holly Jacob
     */
//    private void setUpPage() {
//        if (currentUser != null && currentUser.isLoggedIn()) {
//            //set the text of the left button to be Profile
//            Button leftBtn = findViewById(R.id.loginButton);
//            leftBtn.setText("Profile");
//            //set the text of the right button to be Logout
//            Button rightBtn = findViewById(R.id.signUpHomeButton);
//            rightBtn.setText("Logout");
//            //set the onclick method of the left button to take to profile page
//            leftBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
//                    finish();
//                }
//            });
//            //set the onclick method of the right button to take to the login page and sign out the user
//            rightBtn.setOnClickListener(view -> {
//
//                // Firebase signout
//                FirebaseAuth.getInstance().signOut(); //TODO Ask Anneysha how to set the user state when logging out
//
//                // redirect to login activity
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                Toast.makeText(MainActivity.this, "Logged out sucessfully", Toast.LENGTH_SHORT).show();
//                // close current activity so users cant return to this screen by pressing the back button
//                finish();
//            });
//            //set the onclick method of the wishlist button to go to the wishlist activity
//            //TODO once Anneysha has finished the adapter design pattern for wishlist
//            //set the onlick of the discovery button to go to the search page
//            Button searchButton = findViewById(R.id.searchHomeButton);
//            searchButton.setOnClickListener(view -> {
//                buttonState.handleSearchButton(MainActivity.this);
//            });
//        } else {
//            //set the text of the  left button to be login
//            Button leftBtn = findViewById(R.id.loginButton);
//            leftBtn.setText("Login");
//            //set the text of the right button to be Sign up
//            Button rightBtn = findViewById(R.id.signUpHomeButton);
//            rightBtn.setText("Sign Up");
//            //set the onclick method of the left button to take to login page
//            // Handle login button click
//            leftBtn.setOnClickListener(view -> {
//                buttonState.handleLoginButton(MainActivity.this);
//            });
//            //set the onclick method of the right button to take to the signup page
//            // Handle signup button click
//            rightBtn.setOnClickListener(view -> {
//                buttonState.handleSignupButton(MainActivity.this);
//            });
//            //set the onclick method of the create wishlist button to be toast
//            wishlistButton = findViewById(R.id.createWishListHomeButton);
//            // Handle wishlist button click
//            wishlistButton.setOnClickListener(view -> {
//                buttonState.handleWishlistButton(MainActivity.this);
//            });
//            //set the onclick method of the discover now button to be toast
//            searchButton = findViewById(R.id.searchHomeButton);
//            // Handle search button click
//            searchButton.setOnClickListener(view -> {
//                buttonState.handleSearchButton(MainActivity.this);
//            });
//
//        }
//    }

    public void setState(LoginState state) {
        this.loginState = state;
    }


    /**
     * Retrieves the current user.
     * <p>
     * This method creates and returns a new {@code User} object with default values.
     * </p>
     *
     * @return a new {@code User} object with empty username and password.
     * @author Anneysha Sarkar
     */
    private User getCurrentUser() {
        return new User("", "");
    }

    private void runBackgroundTask() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Background task: Set up the SearchTree and pass it to the next activity
                setupSearchTree();
            }
        });
    }

    private void setupSearchTree() {
        // Perform SearchTree operations in the background
        SearchTreeManager.setSearchTree(this);

        // After the background task is complete, you can update the UI (e.g., show a message)
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Update UI (e.g., show a Toast message indicating completion)
                Toast.makeText(MainActivity.this, "SearchTree setup complete", Toast.LENGTH_SHORT).show();
                // You can also update other UI components like a progress bar, etc.
            }
        });

    }
}

