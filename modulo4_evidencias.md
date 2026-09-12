# Módulo 4: Frontend + MVC - Evidencias

## Funcionalidades implementadas

### Frontend
- `index.html` (landing page con selección de rol)
- `adminDashboard.html` (gestión de doctores)
- `doctorDashboard.html` (agenda de citas)
- `patientDashboard.html` (búsqueda de doctores y reserva)

### Componentes JS
- `header.js` (navegación por rol)
- `footer.js` (footer común)
- `modals.js` (modales de login/signup/addDoctor)
- `doctorCard.js` (tarjetas de doctor)
- `patientRows.js` (filas de citas)

### Servicios JS
- `doctorServices.js` (CRUD de doctores)
- `patientServices.js` (signup, login, citas)
- `services/index.js` (login handlers)

### Backend
- `JwtUtil.java` (generación y validación de JWT)
- `SecurityConfig.java` (configuración de Spring Security + CORS)
- `CustomUserDetailsService.java`
- `AuthController.java` (login admin)
- `AdminController.java` (CRUD doctores)
- `DoctorController.java` (login doctor + citas)
- `PatientController.java` (signup + login)
- `TokenValidationService.java` (validación de token por rol)
- `DashboardController.java` (MVC con validación de token)

## Flujos probados

| Flujo | Resultado |
|-------|-----------|
| Admin login (`admin`/`admin@1234`) | ✅ Redirige a `/admin/dashboard` |
| Doctor login (`dr.adams@example.com`/`pass12345`) | ✅ Redirige a `/doctor/dashboard` |
| Patient signup y login | ✅ Redirige a `/pages/patientDashboard.html` |
| Lista de doctores en Admin | ✅ Carga 25 doctores desde MySQL |
| Lista de doctores en Patient | ✅ Muestra tarjetas con Book Now |
| Filtros por especialidad y tiempo | ✅ Funcionan |
| Validación de token en rutas MVC | ✅ `/adminDashboard/{token}` |

## Thymeleaf config (application.properties)
```properties
spring.web.resources.static-locations=classpath:/static/
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML
spring.thymeleaf.cache=false
spring.thymeleaf.encoding=UTF-8