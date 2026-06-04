/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.Card_Factory;

import android.content.Intent;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dine_discover.Comment;
import com.example.dine_discover.DiscoveryActivity;
import com.example.dine_discover.LoginSystem.LoginActivity;
import com.example.dine_discover.MainActivity;
import com.example.dine_discover.R;
import com.example.dine_discover.Restaurant;
import com.example.dine_discover.SearchTreeFolder.Wishlist;
import com.example.dine_discover.SignupActivity;
import com.example.dine_discover.User;
import com.example.dine_discover.UserProfileActivity;
import com.example.dine_discover.WishListAdapter;
import com.example.dine_discover.WishlistParser;
import com.example.dine_discover.WishlistRestaurant;
import com.example.dine_discover.databinding.ActivityMainBinding;
import com.example.dine_discover.databinding.WishlistActivityBinding;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//import kotlin.collections.EmptySet;
/**
 * <p>
 * Responsible for displaying wishlist</p>
 * @author holly jacob
 * @author gauri chopra
 */

public class CardMoreActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * <p>
     * This method sets the content view to the layout defined in
     * {@code activity_card}, retrieves the title and description passed
     * through the intent, and sets these values in the corresponding
     * {@link TextView} elements.
     * </p>
     *
     * @author Anneysha Sarkar
     **/

    Wishlist myWishlist;
    WishlistActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = WishlistActivityBinding.inflate(getLayoutInflater());
        myWishlist = Wishlist.getInstance();
        setContentView(binding.getRoot());

        int[] imageId = {R.drawable.featured_restaurant1,R.drawable.featured_restaurant2,R.drawable.featured_restaurant3,R.drawable.featured_restaurant4
        ,R.drawable.featured_restaurant5};
//        //test data
//        String[] name = {"Place 1","Place 2","Place 3","Place 4","Place 5"};
//        String[] cuisine = {"Mexican","Indian","Asian","French","American"};
//        String[] suburb = {"Woden","Belconnen","Bruce","Tuggeranong","Gungahlin"};
//        //create test restaurants to display
//        ArrayList<Restaurant> restaurantsArrayList = new ArrayList<>();
//        for (int i = 0; i< imageId.length; i++){
//
//            Restaurant rest = new Restaurant();
//            rest.setName(name[i]);
//            rest.setCuisine(cuisine[i]);
//            rest.setAddress(suburb[i]);
//
//            restaurantsArrayList.add(rest);
//        }
        //create adapter for wishlist list view
        try {
            ArrayList<Restaurant> restaurants = myWishlist.convertToDisplayData();

            if (restaurants != null) {
                WishListAdapter wishListAdapter = new WishListAdapter(CardMoreActivity.this, restaurants);
                binding.wishlistview.setAdapter(wishListAdapter);
                binding.wishlistview.setClickable(true);
                binding.wishlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(CardMoreActivity.this, WishlistRestaurant.class);
                        intent.putExtra("restaurant", restaurants.get(position));
                        startActivity(intent);
                    }
                });
            } else {
                //wishlist is null
                TextView emptyText = (TextView) findViewById(R.id.wishlistEmptyText);
                emptyText.setText("Your Wishlist is Empty");
            }
        }catch (NullPointerException e){
            //wishlist is null
            TextView emptyText = (TextView) findViewById(R.id.wishlistEmptyText);
            emptyText.setText("Your Wishlist is Empty");
        }


        //set up the back button
        ImageView back = (ImageView) findViewById(R.id.wishlistBackButton);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(CardMoreActivity.this, UserProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        //String title = getIntent().getStringExtra("cardTitle");
        //String description = getIntent().getStringExtra("cardDescription");



//        // Set values in UI
//        TextView titleView = findViewById(R.id.titleView);
//        TextView descriptionView = findViewById(R.id.descriptionView);
//        titleView.setText(title);
//        descriptionView.setText(description);
    }

    private String getCurrentUserEmail(){
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            return FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        User newUser = new User("", "");
        return newUser.getUsername();
    }
}
