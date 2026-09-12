package com.smartcare.repository;

import com.smartcare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);

    @Query("SELECT DISTINCT d FROM Doctor d JOIN d.availableTimes t WHERE " +
           "(:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:specialty IS NULL OR d.specialty = :specialty) AND " +
           "(:time IS NULL OR t = :time)")
    List<Doctor> filterDoctors(@Param("name") String name,
                               @Param("specialty") String specialty,
                               @Param("time") String time);
}
