# SmartCare Clinic Management System - Architecture Design

## Section 1: Architecture summary

The SmartCare Clinic Management System is a full-stack web application designed to digitalize the operations of small and medium-sized outpatient clinics. It replaces manual spreadsheets with a modern, scalable, and secure online portal.

The system follows a **layered architecture** based on **Spring Boot MVC**:

- **Presentation Layer (Frontend):** Built with HTML, CSS, JavaScript, and Thymeleaf templates. It provides role-based dashboards for Admins, Doctors, and Patients.
- **Controller Layer:** Spring MVC `@Controller` and `@RestController` classes handle HTTP requests, validate input, and route to the appropriate services.
- **Service Layer:** Contains the business logic (appointment scheduling, prescription management, user management).
- **Repository Layer:** Spring Data JPA repositories interact with **MySQL** for relational data (patients, doctors, appointments) and **MongoDB** for flexible data (prescriptions).
- **Database Layer:**
  - **MySQL** stores structured data: users, roles, patients, doctors, appointments.
  - **MongoDB** stores unstructured/flexible data: prescriptions, medical notes.
- **Security:** Authentication and authorization using **JWT (JSON Web Tokens)** with role-based access control (RBAC).

The application is **containerized with Docker** and integrated with **GitHub Actions** for CI/CD (linting, testing, build verification).

### Why Spring Boot and this tech stack?

- **Spring Boot** simplifies configuration and accelerates development with auto-configuration and embedded servers.
- **Spring MVC + Thymeleaf** allows server-side rendering with dynamic content and role-based views.
- **Spring Data JPA + Hibernate** abstracts database access and supports both MySQL and MongoDB.
- **JWT** provides stateless, secure authentication suitable for REST APIs.
- **Docker + GitHub Actions** ensure consistent deployment and automated quality checks.

### REST APIs for scalable integration

The backend exposes RESTful APIs for patients, doctors, appointments, and prescriptions. These APIs are consumed by the Thymeleaf frontend and can also be used by future mobile apps or third-party integrations.

### Deployability and CI/CD compatibility

The application is packaged as a Docker container, making it portable across environments (development, staging, production). GitHub Actions run automated workflows for linting and testing on every push.

### Reference diagram
+-------------------+ +---------------------+ +------------------+
| Browser | <----> | Thymeleaf / HTML | <----> | Spring MVC |
| (Admin/Doctor/ | | CSS / JavaScript | | Controllers |
| Patient) | +---------------------+ +------------------+
+-------------------+ |
v
+---------------------+
| Service Layer |
| (Business Logic) |
+---------------------+
|
+--------------------------+--------------------------+
v v
+---------------------+ +---------------------+
| MySQL (JPA) | | MongoDB |
| Patients, Doctors, | | Prescriptions, |
| Appointments | | Medical Notes |
+---------------------+ +---------------------+


## Section 2: Numbered flow of data and control

1. The user (Admin, Doctor, or Patient) opens the browser and navigates to the SmartCare portal.
2. The frontend (Thymeleaf template) renders the login page. The user submits credentials.
3. The request is sent to the Spring MVC `AuthController` via HTTP POST.
4. The `AuthService` validates the credentials against the MySQL `users` table (via `UserRepository`).
5. If valid, a **JWT token** is generated and returned to the client.
6. The client stores the token and includes it in the `Authorization` header for subsequent requests.
7. Based on the user's role (`ADMIN`, `DOCTOR`, `PATIENT`), the frontend renders the corresponding dashboard.
8. When a **Patient** books an appointment:
   - The frontend sends a POST request to `/api/appointments`.
   - The `AppointmentController` validates the request and calls `AppointmentService`.
   - The service checks doctor availability, saves the appointment in MySQL, and returns the confirmation.
9. When a **Doctor** creates a prescription:
   - The frontend sends a POST request to `/api/prescriptions`.
   - The `PrescriptionController` calls `PrescriptionService`.
   - The prescription is saved in **MongoDB** (flexible schema).
10. When an **Admin** generates a report:
    - The frontend calls a stored procedure in MySQL via `ReportRepository`.
    - The result is returned as JSON and displayed in the dashboard.
11. All requests are logged, and sensitive operations require role-based authorization.
12. GitHub Actions run on every push: linting, unit tests, and Docker build verification.