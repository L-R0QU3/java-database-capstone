package com.smartcare.service;

import com.smartcare.model.Appointment;
import com.smartcare.model.Doctor;
import com.smartcare.repository.AppointmentRepository;
import com.smartcare.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    // ===============================
    // Get available slots for a doctor on a date
    // ===============================
    public List<String> getDoctorAvailability(Long doctorId, LocalDate date) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        if (doctorOpt.isEmpty()) return new ArrayList<>();

        Doctor doctor = doctorOpt.get();
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

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
        return available;
    }

    // ===============================
    // Validate doctor credentials
    // ===============================
    public ResponseEntity<Map<String, String>> validateDoctor(String email, String password) {
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(email);
        if (doctorOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }
        Doctor doctor = doctorOpt.get();
        if (!password.equals(doctor.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }
        return ResponseEntity.ok(Map.of("message", "Login successful", "role", "doctor"));
    }

    // ===============================
    // CRUD operations
    // ===============================
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    public int saveDoctor(Doctor doctor) {
        try {
            if (doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
                return -1;
            }
            doctorRepository.save(doctor);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public int updateDoctor(Doctor doctor) {
        try {
            if (!doctorRepository.existsById(doctor.getId())) {
                return -1;
            }
            doctorRepository.save(doctor);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public int deleteDoctor(long id) {
        try {
            if (!doctorRepository.existsById(id)) {
                return -1;
            }
            appointmentRepository.deleteAllByDoctorId(id);
            doctorRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public List<Doctor> filterDoctors(String name, String specialty, String time) {
        return doctorRepository.filterDoctors(name, specialty, time);
    }
}
