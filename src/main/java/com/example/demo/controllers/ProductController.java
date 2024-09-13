package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
