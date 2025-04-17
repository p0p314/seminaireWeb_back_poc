package com.seminaireweb.seminaireweb_poc.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.apache.tomcat.util.json.JSONParser;

public class JwtUtil {
    private static final String SECRET = "my-secret-key";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public static String generateToken(String username ,String password) {
        return JWT.create()
                .withIssuer("platinium")
                .withClaim("username", username)
                .withClaim("Password", password)
                .withClaim("terminal", "web")
                .sign(algorithm);
    }
    public static String generateTokenDAB(String numbers ,String pin_code) {
        return JWT.create()
                .withIssuer("platinium")
                .withClaim("numbers", numbers)
                .withClaim("pin _code", pin_code)
                .withClaim("terminal", "dab")
                .sign(algorithm);
    }

    public static String validateToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("myapp")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }
    public static String getTerminal(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("platinium")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("terminal").asString();
    }
}
