package com.seminaireweb.seminaireweb_poc.controller;

import com.seminaireweb.seminaireweb_poc.configuration.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        return JwtUtil.generateToken(username, password);
    }

    @GetMapping("/protectedRoute")
    public String protectedRoute(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = JwtUtil.validateToken(token);
            return "Accès autorisé pour : " + username;
        } catch (Exception e) {
            return "Token invalide : " + e.getMessage();
        }
    }
}
