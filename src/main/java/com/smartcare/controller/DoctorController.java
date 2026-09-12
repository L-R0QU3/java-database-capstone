package com.smartcare.controller;

import com.smartcare.model.Appointment;
import com.smartcare.model.Doctor;
import com.smartcare.repository.AppointmentRepository;
import com.smartcare.repository.DoctorRepository;
import com.smartcare.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin
public class DoctorController {

    @Autowired private DoctorRepository doctorRepository;
    @Autowired private AppointmentRepository appointmentRepository;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> doctorLogin(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(email);
        if (doctorOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }

        Doctor doctor = doctorOpt.get();
        if (!password.equals(doctor.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(doctor.getEmail(), "doctor", doctor.getId());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", "doctor");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentDoctor(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long doctorId = jwtUtil.extractUserId(token);
        return doctorRepository.findById(doctorId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of("message", "Not found")));
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<?> getDoctorAppointments(@PathVariable Long id,
                                                    @RequestHeader("Authorization") String authHeader) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(id);
        return ResponseEntity.ok(Map.of("appointments", appointments));
    }
}
