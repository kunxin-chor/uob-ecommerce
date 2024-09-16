package com.example.demo.services;

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
        return null;
    }
}
