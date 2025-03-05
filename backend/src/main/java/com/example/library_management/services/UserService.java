package com.example.library_management.services;

import com.example.library_management.models.User;
import com.example.library_management.models.Role;
import com.example.library_management.repositories.UserRepository;
import com.example.library_management.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Default role = STUDENT unless specified as ADMIN
        if (user.getRole() == null) {
            user.setRole(Role.STUDENT);
        }

        return userRepository.save(user);
    }

    public String login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            User user = userOpt.get();
            return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        }

        return "Invalid email or password!";
    }
}
