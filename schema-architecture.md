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

1. **Authentication:** The user (Admin, Doctor, or Patient) submits credentials via the login page. The request is sent to the Spring MVC `AuthController`, which delegates to `AuthService` to validate the credentials against the MySQL `users` table.
2. **Token Generation:** Upon successful validation, a **JWT token** is generated and returned to the client, which stores it for subsequent requests.
3. **Role-Based Access:** The client includes the JWT in the `Authorization` header. The backend validates the token and determines the user's role (`ADMIN`, `DOCTOR`, `PATIENT`).
4. **Dashboard Rendering:** Based on the role, the Thymeleaf frontend renders the appropriate dashboard (Admin panel, Doctor schedule, or Patient portal).
5. **Appointment Booking (MySQL):** A Patient books an appointment. The request goes to `AppointmentController`, is validated by `AppointmentService`, and the appointment is saved in **MySQL**.
6. **Prescription Creation (MongoDB):** A Doctor creates a prescription. The request is handled by `PrescriptionController` and saved in **MongoDB** for flexible schema storage.
7. **Reporting and CI/CD:** Admins generate reports using **MySQL stored procedures**. Meanwhile, on every push, **GitHub Actions** run linting, unit tests, and Docker build verification.