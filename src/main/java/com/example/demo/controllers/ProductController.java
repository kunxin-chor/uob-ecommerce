package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.models.Product;
import com.example.demo.repo.ProductRepo;

@Controller
public class ProductController {

    private final ProductRepo productRepo;

    @Autowired
    // Dependency injection = when Spring Boot creates an instance
    // of ProductController, it will automatically create an instance
    // of ProductRepo and pass it to the new instance of ProductController
    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }
    
    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> products = productRepo.findAll();
        model.addAttribute("products", products);
        return "products/index";
    }

    // When we want to add forms, we always need 2 routes
    // 1 route to display the form
    // 1 route to process the form
    @GetMapping("/products/create")
    public String showCreateProductForm(Model model) {

        // send an empty instance of the Product model to the template
        var newProduct = new Product();

        // add the instance of the new product model to the view model
        model.addAttribute("product", newProduct);

        return "products/create";
    }

    @PostMapping("/products/create")
    public String processCreateProductForm(@ModelAttribute Product newProduct) {
        // save the new product
        productRepo.save(newProduct);

        // a redirect tell the client to go a different URL
        return "redirect:/products";
    }
}
