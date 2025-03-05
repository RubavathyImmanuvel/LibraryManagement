package com.example.library_management.controllers;

import com.example.library_management.models.User;
import com.example.library_management.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }
}
