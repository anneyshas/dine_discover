
/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import static com.example.dine_discover.LoginSystem.LoginActivity.getCurrentEmail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dine_discover.CurrentUser;
import com.example.dine_discover.LoginSystem.LoginActivity;
import com.example.dine_discover.SearchTreeFolder.SearchTree;

import java.util.ArrayList;

import com.example.dine_discover.SearchTreeFolder.SearchTreeManager;
import com.example.dine_discover.SearchTreeFolder.Wishlist;
import com.example.dine_discover.SearchTreeFolder.WishlistAVLTree;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;

public class DiscoveryActivity extends AppCompatActivity {
    /**
     * DiscoveryActivity is responsible for handling the discovery page
     * and user interactions involved. It is also responsible for saving
     * the wishlist to database upon discovery.
     *
     * @Holly Jacob
     * @author Gauri Chopra
     */
    SearchTree ST;
    ArrayList<Restaurant> STList;
    private FirebaseFirestore db;
    public static ArrayList<Restaurant> restaurantsArrayList;

    /**
     * Author: Holly Jacob and Sarah Palmer
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_discovery);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //get users wishlist
        Wishlist myWishlist = Wishlist.getInstance();

        String activity = (String) getIntent().getExtras().getSerializable("activity");
        if (activity.equals("reviews")){
            ST = SearchTreeManager.getSearchTree();
            try {
                Restaurant restauraunt = ST.getCurrentRestaurant();
                displayRestaurant(restauraunt);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (activity.equals("search")){
            String query = (String) getIntent().getExtras().getSerializable("QUERY");
            ST = SearchTreeManager.getSearchTree();
            if (ST != null) {
                ST.setQuery(query);
                STList = ST.searchTrees();
                next();
            }
        }
        /**
         * Author: Holly Jacob
         */
        //set up the back button
        ImageView backBtn = (ImageView) findViewById(R.id.discoveryBackButton);
        backBtn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DiscoveryActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setMessage("Warning, you are about to exit your search, Are you sure you are finished adding to your wishlist?");
            //builder.setIcon(R.drawable.ic_launcher);
            builder.setPositiveButton("Yes, Save and Exit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    Toast.makeText(DiscoveryActivity.this, "Exiting Search", Toast.LENGTH_SHORT).show();    // stop chronometer here
                    Intent intent = new Intent(DiscoveryActivity.this, UserProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("Stay on page", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        });
        /**
         * Author: Holly Jacob
         */
        Button profile = (Button) findViewById(R.id.discoveryProfileButton);
        profile.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DiscoveryActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setMessage("Warning, you are about to exit your search, Are you sure you are finished adding to your wishlist?");
            //builder.setIcon(R.drawable.ic_launcher);
            builder.setPositiveButton("Yes, Save and Exit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    Toast.makeText(DiscoveryActivity.this, "Exiting Search", Toast.LENGTH_SHORT).show();    // stop chronometer here
                    Intent intent = new Intent(DiscoveryActivity.this, UserProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("Stay on page", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        });
        Button addBtn = (Button) findViewById(R.id.addButton);
        addBtn.setOnClickListener(view -> {
            Toast.makeText(this, "ADD", Toast.LENGTH_SHORT).show();
            //Add the current restaurant to the list of restaurants to add and move to the next
            addToWishlist(myWishlist);
        });
        /**
         * Author: Sarah Palmer
         */
        Button rejectBtn = (Button) findViewById(R.id.rejectButton);
        rejectBtn.setOnClickListener(view -> {
            Toast.makeText(this, "Reject", Toast.LENGTH_SHORT).show();
            //move to the next restaurant in the AVL tree
            next();
        });
        Button saveBtn = (Button) findViewById(R.id.saveWishlistButton);
        saveBtn.setOnClickListener(view -> {
            Toast.makeText(this, "Saving...", Toast.LENGTH_SHORT).show();
            //save the list of accumulated restaurants and put them into the wishlist associated with this user


            getCurrentUserInstance(currentUser -> {
                if (currentUser != null) {
                    Log.d("Saving", "Saving in User: " + currentUser.dataToString());

                    // Create parser instance and pass currentUser
                    WishlistParser parser = new WishlistParser(currentUser);
                    WishlistToDB wishlistToDB = new WishlistToDB();
                    WishlistAVLTree wishlistAVL = new WishlistAVLTree();
                    wishlistToDB.storeWishlistDB(wishlistAVL.getStorageData(), getCurrentEmail());


                } else {
                    Log.d("CurrentUser", "No user data found.");
                }
            });

            //return to the user profile
            Intent intent = new Intent(DiscoveryActivity.this, UserProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

    }

    public void getCurrentUserInstance(LoginActivity.OnUserRetrievedListener listener) {
        db = FirebaseFirestore.getInstance();
        String loggedInEmail = getCurrentEmail();

        db.collection("user")
                .whereEqualTo("email", loggedInEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                            CurrentUser currentUser = new CurrentUser(document.getData());
                            listener.onUserRetrieved(currentUser);
                        } else {
                            listener.onUserRetrieved(null);
                        }
                    } else {
                        Log.w("Firestore", "Error querying user by email", task.getException());
                        listener.onUserRetrieved(null);
                    }
                });
    }
    /**
     * Author: Holly Jacob
     */
    public void displayRestaurant(Restaurant current){
        if (current == null){
            throw new IllegalArgumentException("Restaurant is Null");
        }
        TextView name = (TextView) findViewById(R.id.discoveryRestName);
        name.setText(current.getName());

        TextView cuisine = (TextView) findViewById(R.id.discoveryCuisine);
        cuisine.setText(current.getCuisine());

        TextView rating = (TextView) findViewById(R.id.discoveryRating);
        String ratingString = String.valueOf(current.getRating());
        rating.setText(ratingString);

        TextView address = (TextView) findViewById(R.id.discoveryRestAddress);
        address.setText(current.getAddress());

        TextView suburb = (TextView) findViewById(R.id.discoveryRestSuburb);
        suburb.setText(current.getAddressLine2());

        EditText url = (EditText) findViewById(R.id.discoveryRestaurantPageUrl);
        url.setText(current.getURL());

        // TODO DISPLAY COMMENTS
        TextView numberOfReviews = (TextView) findViewById(R.id.discorveryReviewNum);
        numberOfReviews.setText("("+current.getNum_reviews().intValue()+")");


//        TextView commentBtn = findViewById(R.id.intoCommentsButton);
//        commentBtn.setOnClickListener(view -> {
//            Toast.makeText(this, "Comment", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(DiscoveryActivity.this, CommentsActivity.class);
//            intent.putExtra("RESTAURANT", current);  // Pass the Restaurant object
//            startActivity(intent);
//        });
        TextView reviewsBtn = (TextView) findViewById(R.id.discoveryReviewButton);
        reviewsBtn.setOnClickListener(view -> {
            Toast.makeText(this, "go to comments", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Comment", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DiscoveryActivity.this, CommentsActivity.class);
            intent.putExtra("RESTAURANT", current);  // Pass the Restaurant object
            startActivity(intent);
        });

    }
    /**
     * Author: Sarah Palmer
     */
    public void next() {
        try {
            displayRestaurant(ST.getNextRestaurant());
        } catch (IOException e) {
            Log.e("ST set up", "Error building search tree: " + e.getMessage());
        }
    }
    /**
     * Author: Sarah Palmer
     */
    private void addToWishlist(Wishlist myWishlist) {
        try {
            myWishlist.addRestaurant(ST.getCurrentRestaurant());
            next();
        } catch (IOException e) {
            Log.e("ST set up", "Error building search tree: " + e.getMessage());
        }
    }

}