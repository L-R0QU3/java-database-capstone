# Módulo 3: Database Setup - Evidencias

## MySQL - Tablas creadas
- admin
- appointment
- doctor
- doctor_available_times
- patient

## Consultas de verificación

### Doctores
SELECT * FROM doctor LIMIT 5;
(25 registros)

### Pacientes
SELECT * FROM patient LIMIT 5;
(25 registros)

### Citas
SELECT * FROM appointment ORDER BY appointment_time LIMIT 5;
(25 registros)

### Administrador
SELECT * FROM admin;
(1 registro)

### MongoDB - Prescripciones
db.prescriptions.find().limit(5).pretty();
(24 documentos)

## Scripts SQL
- `inserts.sql` contiene todos los INSERT para MySQL.

## Stored Procedures
(próximamente)

## Stored Procedures

### 1. GetDailyAppointmentReportByDoctor
- Propósito: Reporte diario de citas agrupadas por doctor.
- Ejemplo: `CALL GetDailyAppointmentReportByDoctor('2025-05-01');`
- Resultado: 1 cita (Jane Doe con Dr. Emily Adams).

### 2. GetDoctorWithMostPatientsByMonth
- Propósito: Doctor con más pacientes en un mes/año.
- Ejemplo: `CALL GetDoctorWithMostPatientsByMonth(5, 2025);`
- Resultado: doctor_id = 1, patients_seen = 25.

### 3. GetDoctorWithMostPatientsByYear
- Propósito: Doctor con más pacientes en un año.
- Ejemplo: `CALL GetDoctorWithMostPatientsByYear(2025);`
- Resultado: doctor_id = 1, patients_seen = 25.