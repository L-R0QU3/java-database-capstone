import { createDoctorCard } from "./components/doctorCard.js";
import { openModal } from "./components/modals.js";
import { getDoctors, filterDoctors } from "./services/doctorServices.js";
import { patientLogin, patientSignup } from "./services/patientServices.js";

document.addEventListener("DOMContentLoaded", () => {
    loadDoctorCards();

    const signupBtn = document.getElementById("patientSignup");
    if (signupBtn) signupBtn.addEventListener("click", () => openModal("patientSignup"));

    const loginBtn = document.getElementById("patientLogin");
    if (loginBtn) loginBtn.addEventListener("click", () => openModal("patientLogin"));

    const searchBar = document.getElementById("searchBar");
    const filterTime = document.getElementById("filterTime");
    const filterSpecialty = document.getElementById("filterSpecialty");

    if (searchBar) searchBar.addEventListener("input", filterDoctorsOnChange);
    if (filterTime) filterTime.addEventListener("change", filterDoctorsOnChange);
    if (filterSpecialty) filterSpecialty.addEventListener("change", filterDoctorsOnChange);
});

async function loadDoctorCards() {
    const doctors = await getDoctors();
    renderDoctorCards(doctors);
}

function renderDoctorCards(doctors) {
    const contentDiv = document.getElementById("content");
    contentDiv.innerHTML = "";

    if (!doctors || doctors.length === 0) {
        contentDiv.innerHTML = "<p>No doctors found.</p>";
        return;
    }

    doctors.forEach((doctor) => {
        contentDiv.appendChild(createDoctorCard(doctor));
    });
}

async function filterDoctorsOnChange() {
    const name = document.getElementById("searchBar").value.trim();
    const time = document.getElementById("filterTime").value;
    const specialty = document.getElementById("filterSpecialty").value;

    const doctors = await filterDoctors(name || null, time || null, specialty || null);
    renderDoctorCards(doctors);
}

window.signupPatient = async function (event) {
    event.preventDefault();
    const data = {
        name: document.getElementById("signupName").value,
        email: document.getElementById("signupEmail").value,
        password: document.getElementById("signupPassword").value,
        phone: document.getElementById("signupPhone").value,
        address: document.getElementById("signupAddress").value
    };

    const result = await patientSignup(data);
    if (result.success) {
        alert("Signup successful! Please login.");
        closeModal();
    } else {
        alert(result.message);
    }
};

window.loginPatient = async function (event) {
    event.preventDefault();
    const data = {
        email: document.getElementById("patientEmail").value,
        password: document.getElementById("patientPassword").value
    };

    const response = await patientLogin(data);
    if (response.ok) {
        const result = await response.json();
        localStorage.setItem("token", result.token);
        localStorage.setItem("userRole", "loggedPatient");
        window.location.href = "/pages/loggedPatientDashboard.html";
    } else {
        alert("Invalid credentials!");
    }
};
