package com.techelevator.model.ordering;

import java.util.HashMap;
import java.util.Map;

public class Cart {


    private final Map<String, Integer> cartItems;

    /*
    This class is a container that gives two methods - add items to cart and remove items to cart
     */
    public Cart() {
        cartItems = new HashMap<>();

    }

    public void addToCart(String productCode, int amount) {

        if (cartItems.containsKey(productCode)) {
            amount += cartItems.get(productCode);
        }

        cartItems.put(productCode, amount);

    }

    public boolean removeFromCart(String productCode, int amount) {

        boolean successRemoved = false;
        if (cartItems.containsKey(productCode)) {
            int amountInCart = cartItems.get(productCode);
            int remain = amountInCart - amount;
            if (remain < 0) {
                cartItems.remove(productCode);
            } else {
                cartItems.put(productCode, remain);
            }
            successRemoved = true;
        }
        return successRemoved;
    }


    public Map<String, Integer> getCartItems() {
        return cartItems;
    }
}
