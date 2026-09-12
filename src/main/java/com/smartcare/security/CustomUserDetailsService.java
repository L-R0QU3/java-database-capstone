package com.smartcare.security;

import com.smartcare.model.Admin;
import com.smartcare.model.Doctor;
import com.smartcare.model.Patient;
import com.smartcare.repository.AdminRepository;
import com.smartcare.repository.DoctorRepository;
import com.smartcare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private AdminRepository adminRepository;
    @Autowired private DoctorRepository doctorRepository;
    @Autowired private PatientRepository patientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try admin
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            return new User(admin.get().getUsername(), admin.get().getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        // Try doctor
        Optional<Doctor> doctor = doctorRepository.findByEmail(username);
        if (doctor.isPresent()) {
            return new User(doctor.get().getEmail(), doctor.get().getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_DOCTOR")));
        }

        // Try patient
        Optional<Patient> patient = patientRepository.findByEmail(username);
        if (patient.isPresent()) {
            return new User(patient.get().getEmail(), patient.get().getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_PATIENT")));
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
