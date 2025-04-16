package com.seminaireweb.seminaireweb_poc.controller;

import com.seminaireweb.seminaireweb_poc.configuration.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping(value = "/login", produces = "application/json")
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
