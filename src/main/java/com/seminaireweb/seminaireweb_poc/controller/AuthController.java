package com.seminaireweb.seminaireweb_poc.controller;

import com.seminaireweb.seminaireweb_poc.configuration.JwtUtil;
import com.seminaireweb.seminaireweb_poc.entity.Credit_cards;
import com.seminaireweb.seminaireweb_poc.entity.User;
import com.seminaireweb.seminaireweb_poc.service.Credit_cardsService;
import com.seminaireweb.seminaireweb_poc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;
    private final Credit_cardsService creditCardsService;

    public AuthController(UserService userService, Credit_cardsService creditCardsService) {
        this.userService = userService;
        this.creditCardsService = creditCardsService;
    }



    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            log.warn("Tentative de connexion avec des identifiants incomplets");
            return ResponseEntity.badRequest().body("Les identifiants sont incomplets");
        }

        try {
            User authTried = userService.getUserByUsername(username);

            if (authTried == null) {
                log.warn("Tentative de connexion avec un nom d'utilisateur inconnu: {}", username);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect");
            }

            if (!authTried.getPassword().equals(password)) {
                log.warn("Mot de passe incorrect pour l'utilisateur: {}", username);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect");
            }

            String token = JwtUtil.generateToken(authTried.getUsername(), authTried.getPassword());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (Exception e) {
            log.error("Erreur lors de l'authentification pour l'utilisateur: {}", username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue lors de l'authentification");
        }
    }

    @PostMapping(value = "/dab/login", produces = "application/json")
    public ResponseEntity<Object> loginDAB(@RequestBody Map<String, String> credentials) {
        String card_number = credentials.get("numbers");
        String pin_code = credentials.get("pin_code");

        if(card_number == null || pin_code == null){
            return ResponseEntity.badRequest().body("Les identifiants sont incomplets");
        }

        try {
            Credit_cards authTried = creditCardsService.getCreditCardByNumbers(card_number);

            if (authTried == null) {
                log.warn("Tentative de connexion avec un numero de carte inconnu: {}", card_number);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Carte non reconnu");
            }

            if (!authTried.getPin().equals(pin_code)) {
                log.warn("Code pin incorrect pour l'utilisateur: {}", card_number);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Code pin incorrect");
            }

            String token = JwtUtil.generateTokenDAB(authTried.getNumbers(), authTried.getPin());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (Exception e) {
            log.error("Erreur lors de l'authentification pour l'utilisateur: {}", card_number, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue lors de l'authentification");
        }


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
    @GetMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<Object> logout(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String terminal = JwtUtil.getTerminal(token);
            if(terminal.isEmpty()) return ResponseEntity.badRequest().body("Token invalide");
            return ResponseEntity.ok(Collections.singletonMap("terminal",JwtUtil.getTerminal(token) ));
        } catch (Exception e) {
            log.error("Erreur lors de la déconnexion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue lors de la déconnexion");
        }
    }
}
