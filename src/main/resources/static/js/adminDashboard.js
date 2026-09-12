import { openModal } from "./components/modals.js";
import { getDoctors, filterDoctors, saveDoctor } from "./services/doctorServices.js";
import { createDoctorCard } from "./components/doctorCard.js";

document.addEventListener("DOMContentLoaded", () => {
    loadDoctorCards();

    const addBtn = document.getElementById("addDocBtn");
    if (addBtn) {
        addBtn.addEventListener("click", () => openModal("addDoctor"));
    }

    const searchBar = document.getElementById("searchBar");
    const filterTime = document.getElementById("filterTime");
    const filterSpecialty = document.getElementById("filterSpecialty");

    if (searchBar) searchBar.addEventListener("input", filterDoctorsOnChange);
    if (filterTime) filterTime.addEventListener("change", filterDoctorsOnChange);
    if (filterSpecialty) filterSpecialty.addEventListener("change", filterDoctorsOnChange);
});

async function loadDoctorCards() {
    const contentDiv = document.getElementById("content");
    if (!contentDiv) return;
    contentDiv.innerHTML = "";

    const doctors = await getDoctors();
    renderDoctorCards(doctors);
}

function renderDoctorCards(doctors) {
    const contentDiv = document.getElementById("content");
    contentDiv.innerHTML = "";

    if (!doctors || doctors.length === 0) {
        contentDiv.innerHTML = '<p class="no-results">No doctors found.</p>';
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

window.adminAddDoctor = async function (event) {
    event.preventDefault();
    const token = localStorage.getItem("token");
    if (!token) {
        alert("Please login as admin first.");
        return;
    }

    const selectedTimes = Array.from(
        document.querySelectorAll("#timeCheckboxes input:checked")
    ).map((cb) => cb.value);

    const doctor = {
        name: document.getElementById("docName").value,
        specialty: document.getElementById("docSpecialty").value,
        email: document.getElementById("docEmail").value,
        password: document.getElementById("docPassword").value,
        phone: document.getElementById("docPhone").value,
        availableTimes: selectedTimes
    };

    const result = await saveDoctor(doctor, token);
    if (result.success) {
        alert("Doctor added successfully!");
        if (typeof closeModal === "function") closeModal();
        loadDoctorCards();
    } else {
        alert(result.message);
    }
};
