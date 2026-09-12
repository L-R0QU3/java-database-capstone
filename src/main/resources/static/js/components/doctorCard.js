import { deleteDoctor } from "../services/doctorServices.js";
import { getPatientData } from "../services/patientServices.js";

export function createDoctorCard(doctor) {
    const card = document.createElement("div");
    card.classList.add("doctor-card");

    const role = localStorage.getItem("userRole");

    // Info section
    const infoDiv = document.createElement("div");
    infoDiv.classList.add("doctor-info");

    const name = document.createElement("h3");
    name.textContent = doctor.name;

    const specialty = document.createElement("p");
    specialty.textContent = "Specialty: " + doctor.specialty;

    const email = document.createElement("p");
    email.textContent = "Email: " + doctor.email;

    const availability = document.createElement("p");
    availability.textContent = "Available: " + (doctor.availableTimes || []).join(", ");

    infoDiv.appendChild(name);
    infoDiv.appendChild(specialty);
    infoDiv.appendChild(email);
    infoDiv.appendChild(availability);

    // Actions section
    const actionsDiv = document.createElement("div");
    actionsDiv.classList.add("card-actions");

    if (role === "admin") {
        const removeBtn = document.createElement("button");
        removeBtn.textContent = "Delete";
        removeBtn.addEventListener("click", async () => {
            if (!confirm(`Delete ${doctor.name}?`)) return;
            const token = localStorage.getItem("token");
            const result = await deleteDoctor(doctor.id, token);
            if (result.success) card.remove();
            else alert(result.message);
        });
        actionsDiv.appendChild(removeBtn);
    } else if (role === "patient") {
        const bookNow = document.createElement("button");
        bookNow.textContent = "Book Now";
        bookNow.addEventListener("click", () => {
            alert("Please login as a patient first.");
        });
        actionsDiv.appendChild(bookNow);
    } else if (role === "loggedPatient") {
        const bookNow = document.createElement("button");
        bookNow.textContent = "Book Now";
        bookNow.addEventListener("click", async () => {
            const token = localStorage.getItem("token");
            const patient = await getPatientData(token);
            if (patient) {
                window.location.href = `/pages/patientAppointments.html?doctorId=${doctor.id}`;
            } else {
                alert("Could not load patient data");
            }
        });
        actionsDiv.appendChild(bookNow);
    }

    card.appendChild(infoDiv);
    card.appendChild(actionsDiv);
    return card;
}
