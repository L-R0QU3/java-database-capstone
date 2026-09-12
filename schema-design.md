# Database Design for SmartCare Clinic Management System

This document describes the database design for the SmartCare Clinic Management System, covering both the relational (MySQL) and document-based (MongoDB) data models.

---

## MySQL Database Design

Structured, validated, and interrelated data works well in MySQL. This is the core operational data for the clinic.

### Instructions:
1. List at least 4 tables the clinic system needs.
   - Start with: `patients`, `doctors`, `appointments`, `admin`
   - Add others if needed: `clinics`, `locations`, `payments`
2. For each table:
   - Define columns
   - Specify data types
   - Mark primary and foreign key relationships
3. Consider constraints:
   - Should some fields be `NOT NULL`, `UNIQUE`, or `NULL INCREMENT`?
   - Should we validate email or phone formats?
4. Ask yourself:
   - What happens if a patient is deleted? Should appointments also be deleted?
   - Should a doctor be allowed to have overlapping appointments?

---

### Table: `admin`

| Column       | Type         | Constraints             |
|--------------|--------------|-------------------------|
| admin_id     | INT          | PRIMARY KEY, AUTO_INCREMENT |
| full_name    | VARCHAR(100) | NOT NULL                |
| email        | VARCHAR(150) | NOT NULL, UNIQUE        |
| password     | VARCHAR(255) | NOT NULL (BCrypt hash)  |
| phone        | VARCHAR(20)  | NULL                    |
| role         | VARCHAR(20)  | NOT NULL, DEFAULT 'ADMIN' |
| created_at   | TIMESTAMP    | DEFAULT CURRENT_TIMESTAMP |

---

### Table: `doctors`

| Column         | Type         | Constraints                     |
|----------------|--------------|---------------------------------|
| doctor_id      | INT          | PRIMARY KEY, AUTO_INCREMENT     |
| full_name      | VARCHAR(100) | NOT NULL                        |
| specialty      | VARCHAR(100) | NOT NULL                        |
| email          | VARCHAR(150) | NOT NULL, UNIQUE                |
| phone          | VARCHAR(20)  | NULL                            |
| password       | VARCHAR(255) | NOT NULL (BCrypt hash)          |
| availability   | TEXT         | NULL (JSON with slots)          |
| created_at     | TIMESTAMP    | DEFAULT CURRENT_TIMESTAMP       |

---

### Table: `patients`

| Column         | Type         | Constraints                     |
|----------------|--------------|---------------------------------|
| patient_id     | INT          | PRIMARY KEY, AUTO_INCREMENT     |
| full_name      | VARCHAR(100) | NOT NULL                        |
| email          | VARCHAR(150) | NOT NULL, UNIQUE                |
| phone          | VARCHAR(20)  | NULL                            |
| password       | VARCHAR(255) | NOT NULL (BCrypt hash)          |
| address        | VARCHAR(255) | NULL                            |
| date_of_birth  | DATE         | NULL                            |
| created_at     | TIMESTAMP    | DEFAULT CURRENT_TIMESTAMP       |

---

### Table: `appointments`

| Column         | Type         | Constraints                            |
|----------------|--------------|----------------------------------------|
| appointment_id | INT          | PRIMARY KEY, AUTO_INCREMENT            |
| patient_id     | INT          | FOREIGN KEY → patients(patient_id)     |
| doctor_id      | INT          | FOREIGN KEY → doctors(doctor_id)       |
| appointment_time | DATETIME   | NOT NULL                               |
| status         | ENUM         | NOT NULL, DEFAULT 'SCHEDULED'          |
| notes          | TEXT         | NULL                                   |
| created_at     | TIMESTAMP    | DEFAULT CURRENT_TIMESTAMP              |

**ENUM values for `status`:** `SCHEDULED`, `COMPLETED`, `CANCELLED`, `NO_SHOW`

**Foreign key behavior:**
- `ON DELETE CASCADE` for `patient_id` → if a patient is deleted, their appointments are also removed.
- `ON DELETE RESTRICT` for `doctor_id` → a doctor cannot be deleted if they have appointments (must reassign first).

---

### Table: `clinics`

| Column       | Type         | Constraints                     |
|--------------|--------------|---------------------------------|
| clinic_id    | INT          | PRIMARY KEY, AUTO_INCREMENT     |
| name         | VARCHAR(100) | NOT NULL                        |
| location     | VARCHAR(255) | NOT NULL                        |
| phone        | VARCHAR(20)  | NULL                            |
| email        | VARCHAR(150) | NULL                            |

---

### Table: `payments`

| Column         | Type         | Constraints                            |
|----------------|--------------|----------------------------------------|
| payment_id     | INT          | PRIMARY KEY, AUTO_INCREMENT            |
| appointment_id | INT          | FOREIGN KEY → appointments(appointment_id) |
| amount         | DECIMAL(10,2)| NOT NULL                               |
| payment_date   | DATETIME     | NOT NULL                               |
| method         | VARCHAR(50)  | NOT NULL (e.g., CASH, CARD, INSURANCE) |
| status         | VARCHAR(20)  | NOT NULL, DEFAULT 'PENDING'            |

---

### Design Questions & Answers

**What happens if a patient is deleted?**
Appointments are deleted via `ON DELETE CASCADE`. Their medical history in MongoDB is kept (soft-deleted) for legal reasons.

**Should a doctor be allowed to have overlapping appointments?**
No. A unique constraint on `(doctor_id, appointment_time)` prevents overlaps.

**Should we validate email or phone formats?**
Yes. Email is validated with a regex pattern; phone is validated for numeric format at the application layer (Bean Validation).

---

## MongoDB Collection Design

Some data doesn't fit well into rigid tables, such as prescriptions, feedback, logs, and messages.

### Instructions:
1. Think of one collection that complements your MySQL schema.
   - Use: `prescriptions`, `feedback`, `logs`, or `messages`
2. Provide an example document using JSON syntax.
3. Be creative:
   - Add fields like `tags`, `metadata`, or nested structures
   - Use arrays or embedded documents if they make sense

---

### Collection: `prescriptions`

```json
{
  "_id": "ObjectId('64a1b2c3d4e5f6a7b8c9d0e1')",
  "patientId": 26,
  "patientName": "John Smith",
  "doctorId": 11,
  "doctorName": "Dr. Emily Adams",
  "appointmentId": 131,
  "date": "2025-05-23T10:30:00Z",
  "medicines": [
    {
      "name": "Vitamin C tablets",
      "dosage": "Twice a day",
      "duration": "14 days"
    },
    {
      "name": "Ibuprofen",
      "dosage": "Every 8 hours if pain",
      "duration": "5 days"
    }
  ],
  "notes": "Patient should avoid caffeine while taking medication.",
  "tags": ["vitamin", "pain-relief", "short-term"],
  "attachments": [
    {
      "type": "pdf",
      "url": "https://smartcare.com/prescriptions/64a1b2c3.pdf"
    }
  ],
  "metadata": {
    "createdBy": "doctor_11",
    "version": 1,
    "lastUpdated": "2025-05-23T10:30:00Z"
  }
}