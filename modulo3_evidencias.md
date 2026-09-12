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
