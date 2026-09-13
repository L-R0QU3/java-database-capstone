package com.smartcare.service;

import com.smartcare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Autowired
    private JwtUtil jwtUtil;

    public String generateToken(String identifier, String role, Long userId) {
        return jwtUtil.generateToken(identifier, role, userId);
    }

    public String extractIdentifier(String token) {
        Claims claims = jwtUtil.extractClaims(token);
        return claims.getSubject();
    }

    public boolean validateToken(String token, String userRole) {
        try {
            if (!jwtUtil.isTokenValid(token)) return false;
            String role = jwtUtil.extractRole(token);
            return userRole.equalsIgnoreCase(role);
        } catch (Exception e) {
            return false;
        }
    }
}
