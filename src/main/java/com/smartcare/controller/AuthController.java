package com.smartcare.controller;

import com.smartcare.model.Admin;
import com.smartcare.repository.AdminRepository;
import com.smartcare.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AuthController {

    @Autowired private AdminRepository adminRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Optional<Admin> adminOpt = adminRepository.findByUsername(username);
        if (adminOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }

        Admin admin = adminOpt.get();
        // Note: for the capstone we stored plain passwords in the DB, so compare directly.
        // For production, use passwordEncoder.matches(password, admin.getPassword())
        if (!password.equals(admin.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(admin.getUsername(), "admin", admin.getId());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", "admin");
        return ResponseEntity.ok(response);
    }
}
