package com.example.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.models.CartItem;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.repo.CartItemRepo;

import jakarta.transaction.Transactional;

@Service
public class CartItemService {

    private final CartItemRepo cartItemRepo;

    public CartItemService(CartItemRepo cartItemRepo) {
        this.cartItemRepo = cartItemRepo;
    }
    
    // when a method is transactional, if throws an exception
    // for any reason, all database writes and updates will be undo
    @Transactional 
    public CartItem addToCart(User user, Product product, int quantity) {

        // check if the product already exists in the cart
        Optional<CartItem> existingItem = cartItemRepo.findByUserAndProduct(user, product);


        // if the product already exists in the cart
        if (existingItem.isPresent()) {
            // to get the actual item from the Optional, we have
            // use .get()
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            return cartItemRepo.save(cartItem);

        } else {
            // if the product is not in the cart
            CartItem newItem = new CartItem(user, product, quantity);
            return cartItemRepo.save(newItem);
        }


    }
}
