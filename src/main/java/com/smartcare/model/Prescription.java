package com.smartcare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "prescriptions")
public class Prescription {

    @Id
    private String id;

    @NotNull
    private Long patientId;

    @NotBlank
    @Size(max = 100)
    private String patientName;

    @NotNull
    private Long doctorId;

    @NotBlank
    @Size(max = 100)
    private String doctorName;

    private Long appointmentId;

    private LocalDateTime date;

    @NotNull
    private List<Medicine> medicines;

    @Size(max = 500)
    private String notes;

    private List<String> tags;

    // Clase interna para medicamentos
    public static class Medicine {
        private String name;
        private String dosage;
        private String duration;

        // Getters y Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDosage() { return dosage; }
        public void setDosage(String dosage) { this.dosage = dosage; }
        public String getDuration() { return duration; }
        public void setDuration(String duration) { this.duration = duration; }
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public List<Medicine> getMedicines() { return medicines; }
    public void setMedicines(List<Medicine> medicines) { this.medicines = medicines; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}