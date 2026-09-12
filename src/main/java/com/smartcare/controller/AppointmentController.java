package com.smartcare.controller;

import com.smartcare.dto.AppointmentDTO;
import com.smartcare.model.Appointment;
import com.smartcare.repository.AppointmentRepository;
import com.smartcare.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointment")
@CrossOrigin
public class AppointmentController {

    @Autowired private AppointmentRepository appointmentRepository;
    @Autowired private JwtUtil jwtUtil;

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getDoctorAppointments(@PathVariable Long doctorId,
                                                    @RequestHeader(value = "Authorization", required = false) String authHeader) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        List<AppointmentDTO> dtos = appointments.stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("appointments", dtos));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getPatientAppointments(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        List<AppointmentDTO> dtos = appointments.stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("appointments", dtos));
    }

    private AppointmentDTO toDTO(Appointment a) {
        return new AppointmentDTO(
            a.getId(),
            a.getDoctor() != null ? a.getDoctor().getId() : null,
            a.getDoctor() != null ? a.getDoctor().getName() : null,
            a.getPatient() != null ? a.getPatient().getId() : null,
            a.getPatient() != null ? a.getPatient().getName() : null,
            a.getPatient() != null ? a.getPatient().getEmail() : null,
            a.getPatient() != null ? a.getPatient().getPhone() : null,
            a.getPatient() != null ? a.getPatient().getAddress() : null,
            a.getAppointmentTime(),
            a.getStatus()
        );
    }
}
