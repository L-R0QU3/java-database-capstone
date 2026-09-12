package com.smartcare.controller;

import com.smartcare.model.Patient;
import com.smartcare.repository.PatientRepository;
import com.smartcare.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin
public class PatientController {

    @Autowired private PatientRepository patientRepository;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Patient patient) {
        if (patientRepository.findByEmail(patient.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body(Map.of("message", "Email already exists"));
        }
        Patient saved = patientRepository.save(patient);
        return ResponseEntity.ok(Map.of("success", true, "message", "Signup successful", "patient", saved));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<Patient> patientOpt = patientRepository.findByEmail(email);
        if (patientOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }

        Patient patient = patientOpt.get();
        if (!password.equals(patient.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(patient.getEmail(), "patient", patient.getId());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", "patient");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentPatient(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long patientId = jwtUtil.extractUserId(token);
        return patientRepository.findById(patientId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of("message", "Not found")));
    }
}
