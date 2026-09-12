import { getPatientAppointments } from "./services/patientServices.js";
import { createPatientRow } from "./components/patientRows.js";

let selectedDate = new Date().toISOString().split("T")[0];
let patientName = null;
let token = localStorage.getItem("token");

document.addEventListener("DOMContentLoaded", () => {
    const searchBar = document.getElementById("searchBar");
    const todayButton = document.getElementById("todayButton");
    const datePicker = document.getElementById("datePicker");

    if (datePicker) datePicker.value = selectedDate;

    if (searchBar) {
        searchBar.addEventListener("input", (e) => {
            patientName = e.target.value.trim() || null;
            loadAppointments();
        });
    }

    if (todayButton) {
        todayButton.addEventListener("click", () => {
            selectedDate = new Date().toISOString().split("T")[0];
            if (datePicker) datePicker.value = selectedDate;
            loadAppointments();
        });
    }

    if (datePicker) {
        datePicker.addEventListener("change", (e) => {
            selectedDate = e.target.value;
            loadAppointments();
        });
    }

    loadAppointments();
});

async function loadAppointments() {
    const tableBody = document.getElementById("patientTableBody");
    if (!tableBody) return;
    tableBody.innerHTML = "";

    try {
        const appointments = await getPatientAppointments("me", token, "doctor");

        const filtered = (appointments || []).filter((a) => {
            const matchesDate = !selectedDate || a.appointmentTime?.startsWith(selectedDate);
            const matchesName = !patientName || (a.patientName || "").toLowerCase().includes(patientName.toLowerCase());
            return matchesDate && matchesName;
        });

        if (filtered.length === 0) {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td colspan="5" class="noPatientRecord">No appointments found.</td>`;
            tableBody.appendChild(tr);
            return;
        }

        filtered.forEach((appointment) => {
            tableBody.appendChild(createPatientRow(appointment));
        });
    } catch (err) {
        console.error(err);
        const tr = document.createElement("tr");
        tr.innerHTML = `<td colspan="5" class="noPatientRecord">Error loading appointments.</td>`;
        tableBody.appendChild(tr);
    }
}
