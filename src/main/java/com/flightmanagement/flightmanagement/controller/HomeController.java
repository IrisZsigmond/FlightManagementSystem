package com.flightmanagement.flightmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //Handles app's Database (ex: http://localhost:8080/)

    @GetMapping("/")
    public String home() {
        return "home/home"; // Returnează șablonul Thymeleaf 'home.html'
    }

    // optional method for ensuring navigation to /home
    @GetMapping("/home")
    public String index() {
        return "home/home";
    }
}