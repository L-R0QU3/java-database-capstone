package com.smartcare.controller;

import com.smartcare.model.Prescription;
import com.smartcare.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prescription")
@CrossOrigin
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @PostMapping("/save")
    public ResponseEntity<?> savePrescription(@RequestBody Prescription prescription) {
        try {
            prescriptionRepository.save(prescription);
            return ResponseEntity.status(201).body(Map.of("message", "Prescription saved"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Error saving prescription"));
        }
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<?> getPrescription(@PathVariable Long appointmentId) {
        List<Prescription> list = prescriptionRepository.findAll().stream()
                .filter(p -> p.getAppointmentId() == appointmentId.intValue())
                .toList();

        if (list.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "No prescription found"));
        }
        return ResponseEntity.ok(Map.of("prescription", list.get(0)));
    }
}
