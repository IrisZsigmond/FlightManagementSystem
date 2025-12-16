package com.flightmanagement.flightmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Gestionează URL-ul de bază al aplicației (ex: http://localhost:8080/)
     */
    @GetMapping("/")
    public String home() {
        return "home/home"; // Returnează șablonul Thymeleaf 'home.html'
    }

    /**
     * O metodă opțională pentru a asigura că navigarea la /home funcționează de asemenea
     */
    @GetMapping("/home")
    public String index() {
        return "home/home";
    }
}