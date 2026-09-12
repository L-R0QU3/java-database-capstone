package com.smartcare.service;

import com.smartcare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenValidationService {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Validate token and check role.
     * Returns an empty map if valid, or a map with error details if invalid.
     */
    public Map<String, String> validateToken(String token, String expectedRole) {
        Map<String, String> errors = new HashMap<>();

        if (token == null || token.isEmpty()) {
            errors.put("error", "Missing token");
            return errors;
        }

        try {
            if (!jwtUtil.isTokenValid(token)) {
                errors.put("error", "Invalid or expired token");
                return errors;
            }

            String role = jwtUtil.extractRole(token);
            if (!expectedRole.equalsIgnoreCase(role)) {
                errors.put("error", "Role mismatch: expected " + expectedRole);
                return errors;
            }

        } catch (Exception e) {
            errors.put("error", "Token validation failed: " + e.getMessage());
        }

        return errors;
    }
}
