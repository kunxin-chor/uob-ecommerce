package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.models.Product;
import com.example.demo.repo.ProductRepo;

import jakarta.validation.Valid;

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

    // the results of the validation will be in the bindingResult parameter
    @PostMapping("/products/create")
    public String processCreateProductForm(@Valid @ModelAttribute Product newProduct, BindingResult bindingResult) {
        System.out.println(bindingResult);
        if (bindingResult.hasErrors()) {
            // re-render the create form if there are any validation errors
            // and skip the saving of the new product
            return "products/create";
        }

        // save the new product
        productRepo.save(newProduct);

        // a redirect tell the client to go a different URL
        return "redirect:/products";
    }

    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable Long id, Model model) {

        // find the product with the matching id
         Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException());

        System.out.println(product);

        // add it to the view model
        model.addAttribute("product", product);

        // details.html in the products folder
        return "products/details";
    }

  
    // 2. display the form which contains the existing data of the product
    @GetMapping("/products/{id}/edit")
    public String showUpdateProduct(@PathVariable Long id, Model model) {

          // 1. find by ID the product that the user wants to update
          var product = productRepo.findById(id)
                        .orElseThrow( () -> new RuntimeException("Product Not Found"));
        
        // 2. Pass it to the view model
        model.addAttribute("product", product);

        return "products/edit";

    }

    @PostMapping("/products/{id}/edit")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);  // ensure that we are updating the correct id
        productRepo.save(product);
        return "redirect:/products";
    }

    // we have two routes for deleting
    // 1. showing a delete form (asking the user if they really want to delete)

    @GetMapping("/products/{id}/delete")
    public String showDeleteProductForm(@PathVariable Long id, Model model) {

        // find the product that we want to delete
        var product = productRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Proudct not found"));

       model.addAttribute("product", product);                 

        return "products/delete";
    }

    // 2. process the delete
    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productRepo.deleteById(id);
        return "redirect:/products";
    }
}
