# SmartCare Clinic Management System - User Stories

## Overview

This document contains the user stories for the SmartCare Clinic Management System, organized by role: Admin, Doctor, and Patient.

---

## 👤 Admin User Stories

### US-A01: Register new doctors
**As an** admin, **I want** to register new doctors in the system **so that** patients can book appointments with them.
- **Acceptance Criteria:**
  - The registration form includes name, specialty, email, and phone.
  - Email must be unique.
  - After saving, the doctor appears in the available doctors list.

### US-A02: Manage patient accounts
**As an** admin, **I want** to create, update, and deactivate patient accounts **so that** I can keep the patient database accurate.
- **Acceptance Criteria:**
  - Admin can view all registered patients.
  - Admin can edit patient details (name, contact, address).
  - Admin can deactivate a patient account without deleting their history.

### US-A03: View all appointments
**As an** admin, **I want** to view all appointments in the system **so that** I can monitor clinic activity.
- **Acceptance Criteria:**
  - The list shows date, time, patient name, doctor name, and status.
  - Admin can filter by date range, doctor, or patient.
  - Admin can export the list to CSV.

### US-A04: Generate monthly reports
**As an** admin, **I want** to generate monthly reports of appointments **so that** I can analyze clinic performance.
- **Acceptance Criteria:**
  - The report includes total appointments, cancellations, and no-shows.
  - The report can be filtered by month and doctor.
  - The report uses a MySQL stored procedure.

### US-A05: Manage user roles and permissions
**As an** admin, **I want** to assign roles (Admin, Doctor, Patient) to users **so that** access to system features is properly controlled.
- **Acceptance Criteria:**
  - Only admins can change user roles.
  - Role changes are logged with timestamp and admin ID.
  - Role-based access is enforced on all endpoints.

---

## 🩺 Doctor User Stories

### US-D01: View my daily schedule
**As a** doctor, **I want** to view my appointments for the day **so that** I can organize my time.
- **Acceptance Criteria:**
  - The schedule is sorted by appointment time.
  - Each appointment shows patient name, time, and reason.
  - Doctor can click an appointment to view patient history.

### US-D02: Update my availability
**As a** doctor, **I want** to set my available time slots **so that** patients can only book when I am free.
- **Acceptance Criteria:**
  - Doctor can add, edit, and remove time slots.
  - Changes are reflected immediately in the patient booking view.
  - Past slots cannot be edited.

### US-D03: Create prescriptions
**As a** doctor, **I want** to create digital prescriptions for my patients **so that** they have a record of their treatment.
- **Acceptance Criteria:**
  - The prescription form includes medicine names, dosage, and notes.
  - Prescriptions are saved in MongoDB (flexible schema).
  - Patients can view and download their prescriptions.

### US-D04: View patient medical history
**As a** doctor, **I want** to view a patient's medical history **so that** I can make informed decisions.
- **Acceptance Criteria:**
  - The history shows past appointments, diagnoses, and prescriptions.
  - Only the treating doctor can access the full history.
  - Data is retrieved from both MySQL (appointments) and MongoDB (prescriptions).

### US-D05: Cancel or reschedule appointments
**As a** doctor, **I want** to cancel or reschedule appointments **so that** I can handle emergencies.
- **Acceptance Criteria:**
  - Doctor can cancel an appointment with a reason.
  - The patient is notified of the change.
  - The slot becomes available for other patients.

---

## 🧑‍🦱 Patient User Stories

### US-P01: Register and log in
**As a** patient, **I want** to register and log in to the portal **so that** I can manage my appointments.
- **Acceptance Criteria:**
  - Registration requires name, email, password, and phone.
  - Password is encrypted with BCrypt.
  - Login returns a JWT token.

### US-P02: Book an appointment
**As a** patient, **I want** to book an appointment with a doctor **so that** I can receive medical care.
- **Acceptance Criteria:**
  - Patient can search doctors by specialty.
  - Available time slots are shown.
  - Confirmation is displayed after booking.

### US-P03: View my appointments
**As a** patient, **I want** to view my upcoming and past appointments **so that** I can track my visits.
- **Acceptance Criteria:**
  - Upcoming appointments are sorted by date.
  - Past appointments show status (completed, cancelled).
  - Patient can cancel an upcoming appointment.

### US-P04: View and download prescriptions
**As a** patient, **I want** to view and download my prescriptions **so that** I have a record of my treatment.
- **Acceptance Criteria:**
  - The list shows date, doctor, medicines, and dosage.
  - Patient can download the prescription as PDF.
  - Only the authenticated patient can view their own prescriptions.

### US-P05: Update my profile
**As a** patient, **I want** to update my personal information **so that** my contact details are current.
- **Acceptance Criteria:**
  - Patient can edit name, phone, and address.
  - Email cannot be changed (it is the unique identifier).
  - Changes are saved and reflected immediately.

---

## Summary

| Role | Number of Stories |
|------|-------------------|
| Admin | 5 |
| Doctor | 5 |
| Patient | 5 |
| **Total** | **15** |