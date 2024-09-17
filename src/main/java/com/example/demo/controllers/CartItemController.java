package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import com.example.demo.models.User;
import com.example.demo.models.Product;
import com.example.demo.services.CartItemService;
import com.example.demo.services.ProductService;
import com.example.demo.services.UserService;

@Controller
@RequestMapping("/cart")
public class CartItemController {
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public CartItemController(CartItemService cartItemService, ProductService productService, UserService userService) {
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.userService = userService;
    }

    // - when a route includes the Principal in the parameters,
    // the Principal will information about the currently logged in user
    // - redirect attributes are for the flash message
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            Principal principal,
                            RedirectAttributes redirectAttributes

    
    ) {

        try {
        // get the current logged in user
        User user = userService.findUserByUsername(principal.getName());

        // find the product
        // .orElseThrow() -> performs a .get(); however if .isPresent() returns false
        // throws an exception
        Product product = productService.findById(productId).orElseThrow( () -> 
            new IllegalArgumentException("Product not found")
        );

        cartItemService.addToCart(user, product, quantity);

        redirectAttributes.addFlashAttribute("message", 
                String.format("Added %d %s to your cart", quantity, product.getName()));

        return "redirect:/cart";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error",
                String.format("Error when adding product: %s", e.getMessage())
            );
            return "redirect:/products";
        }
        

    }
}
