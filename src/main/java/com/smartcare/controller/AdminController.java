package com.smartcare.controller;

import com.smartcare.model.Doctor;
import com.smartcare.repository.DoctorRepository;
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
public class AdminController {

    @Autowired private DoctorRepository doctorRepository;

    @GetMapping("/all")
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveDoctor(@RequestBody Doctor doctor) {
        Doctor saved = doctorRepository.save(doctor);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Doctor saved");
        response.put("doctor", saved);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Doctor not found"));
        }
        doctorRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Deleted"));
    }

    @GetMapping("/filter")
    public List<Doctor> filterDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String time,
            @RequestParam(required = false) String specialty) {
        return doctorRepository.filterDoctors(name, specialty, time);
    }
}
