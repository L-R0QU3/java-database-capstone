import { openModal } from "../components/modals.js";
import { API_BASE_URL } from "../config/config.js";

const ADMIN_API = API_BASE_URL + "/admin";
const DOCTOR_API = API_BASE_URL + "/doctor/login";

window.adminLoginHandler = async function (event) {
    event.preventDefault();
    const username = document.getElementById("adminUsername").value;
    const password = document.getElementById("adminPassword").value;
    const admin = { username, password };

    try {
        const response = await fetch(`${ADMIN_API}/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(admin)
        });
        if (response.ok) {
            const data = await response.json();
            localStorage.setItem("token", data.token);
            localStorage.setItem("userRole", "admin");
            window.location.href = "/admin/dashboard";
        } else {
            alert("Invalid credentials!");
        }
    } catch (error) {
        console.error(error);
        alert("Network error");
    }
};

window.doctorLoginHandler = async function (event) {
    event.preventDefault();
    const email = document.getElementById("doctorEmail").value;
    const password = document.getElementById("doctorPassword").value;
    const doctor = { email, password };

    try {
        const response = await fetch(DOCTOR_API, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(doctor)
        });
        if (response.ok) {
            const data = await response.json();
            localStorage.setItem("token", data.token);
            localStorage.setItem("userRole", "doctor");
            window.location.href = "/doctor/dashboard";
        } else {
            alert("Invalid credentials!");
        }
    } catch (error) {
        console.error(error);
        alert("Network error");
    }
};
