// Header component (role-based)
// =====================================================

export function renderHeader() {
    const headerDiv = document.getElementById("header");
    if (!headerDiv) return;

    // On homepage, clear session
    if (window.location.pathname === "/" || window.location.pathname.endsWith("/index.html")) {
        localStorage.removeItem("userRole");
        localStorage.removeItem("token");
    }

    const role = localStorage.getItem("userRole");
    const token = localStorage.getItem("token");

    // Invalid session guard
    if ((role === "loggedPatient" || role === "admin" || role === "doctor") && !token) {
        localStorage.removeItem("userRole");
        alert("Session expired. Please log in again.");
        window.location.href = "/";
        return;
    }

    let headerContent = `<div class="header-logo"><a href="/"><img src="/assets/images/logo/logo.png" alt="SmartCare" height="40"></a><span>Hospital CMS</span></div><nav class="nav-items">`;

    if (role === "admin") {
        headerContent += `
            <button id="addDocBtn" class="adminBtn">Add Doctor</button>
            <a href="#" id="logoutBtn">Logout</a>`;
    } else if (role === "doctor") {
        headerContent += `
            <a href="/doctor/dashboard">Home</a>
            <a href="#" id="logoutBtn">Logout</a>`;
    } else if (role === "patient") {
        headerContent += `
            <a href="#" id="patientLoginHeader">Login</a>
            <a href="#" id="patientSignupHeader">Sign Up</a>`;
    } else if (role === "loggedPatient") {
        headerContent += `
            <a href="/pages/loggedPatientDashboard.html">Home</a>
            <a href="/pages/patientAppointments.html">Appointments</a>
            <a href="#" id="logoutBtn">Logout</a>`;
    }

    headerContent += `</nav>`;
    headerDiv.innerHTML = headerContent;

    attachHeaderListeners();
}

function attachHeaderListeners() {
    const addDocBtn = document.getElementById("addDocBtn");
    if (addDocBtn) {
        addDocBtn.addEventListener("click", () => {
            if (typeof openModal === "function") openModal("addDoctor");
        });
    }

    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", (e) => {
            e.preventDefault();
            logout();
        });
    }

    const patientLoginHeader = document.getElementById("patientLoginHeader");
    if (patientLoginHeader) {
        patientLoginHeader.addEventListener("click", (e) => {
            e.preventDefault();
            if (typeof openModal === "function") openModal("patientLogin");
        });
    }

    const patientSignupHeader = document.getElementById("patientSignupHeader");
    if (patientSignupHeader) {
        patientSignupHeader.addEventListener("click", (e) => {
            e.preventDefault();
            if (typeof openModal === "function") openModal("patientSignup");
        });
    }
}

function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("userRole");
    window.location.href = "/";
}

window.logout = logout;

// Auto-render on load
document.addEventListener("DOMContentLoaded", renderHeader);
