/**
 * Author: Holly Jacob
 */
/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WishListAdapter extends ArrayAdapter<Restaurant> {
    public WishListAdapter(Context context, ArrayList<Restaurant> wishlistArrayList){
        super(context,R.layout.restaurant_list_item,wishlistArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Restaurant rest = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.restaurant_list_item,parent,false);
        }
        ImageView image = convertView.findViewById(R.id.wishlistRestaurantPic);
        TextView restName = convertView.findViewById(R.id.wishlistRestaurantName);
        TextView restCuisine = convertView.findViewById(R.id.wishlistRestaurantCuisine);
        TextView restSuburb = convertView.findViewById(R.id.wishlistRestaurantSuburb);
        EditText restUrl = convertView.findViewById(R.id.wishlistRestaurantUrl);

        //image.setImageResource(rest.getCuisine()); //TODO implement a way to get image from corresponding cuisine type
        restName.setText(rest.getName());
        restCuisine.setText(rest.getCuisine());
        restSuburb.setText(rest.getAddressLine2());
        restUrl.setText(rest.getURL());

        return convertView;



    }
}
