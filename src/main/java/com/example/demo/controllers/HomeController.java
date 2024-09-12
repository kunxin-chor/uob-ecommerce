package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// The @Controller annoation tells Spring Boot that this is a controller that contains routes
// Spring Boot will automatically go through all the methods marked as routes
// and register them
@Controller
public class HomeController {
    
    // When the user goes to the / URL on the server, call this method
    @GetMapping("/")
    // Tell Spring Boot this methods returns a HTTP response
    @ResponseBody
    public String helloWorld() {
        return "<h1>Hello World</h1>";
    }

    @GetMapping("/about-us")
    @ResponseBody
    public String aboutUs() {
        return "<h1>About Us</h1>";
    }
}
