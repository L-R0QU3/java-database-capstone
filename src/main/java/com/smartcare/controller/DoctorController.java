package com.smartcare.controller;

import com.smartcare.model.Appointment;
import com.smartcare.model.Doctor;
import com.smartcare.repository.AppointmentRepository;
import com.smartcare.repository.DoctorRepository;
import com.smartcare.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

    // ===============================
    // Get all doctors
    // ===============================
    @GetMapping("/all")
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // ===============================
    // Get doctor availability by ID and date
    // ===============================
    @GetMapping("/availability/{doctorId}/{date}")
    public ResponseEntity<?> getDoctorAvailability(@PathVariable Long doctorId,
                                                    @PathVariable String date) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        if (doctorOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Doctor not found"));
        }
        Doctor doctor = doctorOpt.get();
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime start = localDate.atStartOfDay();
        LocalDateTime end = localDate.atTime(LocalTime.MAX);

        List<Appointment> booked = appointmentRepository
                .findByDoctorIdAndAppointmentTimeBetween(doctorId, start, end);

        List<String> bookedSlots = booked.stream()
                .map(a -> a.getAppointmentTime().toLocalTime().toString())
                .toList();

        List<String> available = new ArrayList<>();
        for (String slot : doctor.getAvailableTimes()) {
            String startTime = slot.split("-")[0];
            if (!bookedSlots.contains(startTime)) {
                available.add(slot);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("doctorId", doctorId);
        response.put("date", date);
        response.put("availableSlots", available);
        return ResponseEntity.ok(response);
    }

    // ===============================
    // Doctor login
    // ===============================
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

    // ===============================
    // Save (add) doctor
    // ===============================
    @PostMapping("/save")
    public ResponseEntity<?> saveDoctor(@RequestBody Doctor doctor) {
        Doctor saved = doctorRepository.save(doctor);
        return ResponseEntity.ok(Map.of("success", true, "message", "Doctor saved", "doctor", saved));
    }

    // ===============================
    // Update doctor
    // ===============================
    @PutMapping("/update")
    public ResponseEntity<?> updateDoctor(@RequestBody Doctor doctor) {
        if (!doctorRepository.existsById(doctor.getId())) {
            return ResponseEntity.status(404).body(Map.of("message", "Doctor not found"));
        }
        doctorRepository.save(doctor);
        return ResponseEntity.ok(Map.of("success", true, "message", "Doctor updated"));
    }

    // ===============================
    // Delete doctor
    // ===============================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Doctor not found"));
        }
        appointmentRepository.deleteAllByDoctorId(id);
        doctorRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Deleted"));
    }

    // ===============================
    // Filter doctors
    // ===============================
    @GetMapping("/filter")
    public List<Doctor> filterDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String time,
            @RequestParam(required = false) String specialty) {
        return doctorRepository.filterDoctors(name, specialty, time);
    }
}
