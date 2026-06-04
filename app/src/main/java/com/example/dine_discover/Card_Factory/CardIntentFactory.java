/*
 * Copyright (c) 2024 Group 10, 10X for COMP2100 Assignment
 * Proprietary License - All Rights Reserved
 */

package com.example.dine_discover.Card_Factory;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class CardIntentFactory {

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

    public static Intent createIntent(Context context, String cardType) {
        Intent intent;
        switch (cardType) {
            case "Card1":
                intent = new Intent(context, Card1Activity.class);
                intent.putExtra("cardTitle", "Card 1 Title");
                intent.putExtra("cardDescription", "This is the description for Card 1.");
                break;
            case "Card2":
                intent = new Intent(context, Card2Activity.class);
                intent.putExtra("cardTitle", "Card 2 Title");
                intent.putExtra("cardDescription", "This is the description for Card 2.");
                break;
            case "Card3":
                intent = new Intent(context, Card3Activity.class);
                intent.putExtra("cardTitle", "Card 3 Title");
                intent.putExtra("cardDescription", "This is the description for Card 3.");
                break;
            case "Card4":
                intent = new Intent(context, Card4Activity.class);
                intent.putExtra("cardTitle", "Card 4 Title");
                intent.putExtra("cardDescription", "This is the description for Card 4.");
                break;
            case "Card5":
                intent = new Intent(context, Card5Activity.class);
                intent.putExtra("cardTitle", "Card 5 Title");
                intent.putExtra("cardDescription", "This is the description for Card 5.");
                break;
            case "CardMore":
                intent = new Intent(context, CardMoreActivity.class);
                intent.putExtra("cardTitle", "Redirect to Wishlist");
                intent.putExtra("cardDescription", "Add the wishlist activity here.");
                break;
            default:
                throw new IllegalArgumentException("Invalid card type: " + cardType);
        }
        return intent;
    }
}
