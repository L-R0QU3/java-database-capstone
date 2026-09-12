// =====================================================
// Modal component
// =====================================================

export function openModal(type) {
    const modal = document.getElementById("modal");
    const modalBody = document.getElementById("modal-body");
    if (!modal || !modalBody) return;

    modal.style.display = "block";

    switch (type) {
        case "adminLogin":
            modalBody.innerHTML = adminLoginForm();
            break;
        case "doctorLogin":
            modalBody.innerHTML = doctorLoginForm();
            break;
        case "patientLogin":
            modalBody.innerHTML = patientLoginForm();
            break;
        case "patientSignup":
            modalBody.innerHTML = patientSignupForm();
            break;
        case "addDoctor":
            modalBody.innerHTML = addDoctorForm();
            break;
        default:
            modalBody.innerHTML = "<p>Unknown modal</p>";
    }
}

export function closeModal() {
    const modal = document.getElementById("modal");
    if (modal) modal.style.display = "none";
}

window.openModal = openModal;
window.closeModal = closeModal;

// =====================================================
// Modal forms
// =====================================================

function adminLoginForm() {
    return `
        <h2>Admin Login</h2>
        <form id="adminLoginForm" onsubmit="adminLoginHandler(event)">
            <input type="text" id="adminUsername" placeholder="Username" required>
            <input type="password" id="adminPassword" placeholder="Password" required>
            <button type="submit" class="btn-primary">Login</button>
        </form>
    `;
}

function doctorLoginForm() {
    return `
        <h2>Doctor Login</h2>
        <form id="doctorLoginForm" onsubmit="doctorLoginHandler(event)">
            <input type="email" id="doctorEmail" placeholder="Email" required>
            <input type="password" id="doctorPassword" placeholder="Password" required>
            <button type="submit" class="btn-primary">Login</button>
        </form>
    `;
}

function patientLoginForm() {
    return `
        <h2>Patient Login</h2>
        <form id="patientLoginForm" onsubmit="loginPatient(event)">
            <input type="email" id="patientEmail" placeholder="Email" required>
            <input type="password" id="patientPassword" placeholder="Password" required>
            <button type="submit" class="btn-primary">Login</button>
        </form>
    `;
}

function patientSignupForm() {
    return `
        <h2>Patient Sign Up</h2>
        <form id="patientSignupForm" onsubmit="signupPatient(event)">
            <input type="text" id="signupName" placeholder="Full Name" required>
            <input type="email" id="signupEmail" placeholder="Email" required>
            <input type="password" id="signupPassword" placeholder="Password" required>
            <input type="text" id="signupPhone" placeholder="Phone" required>
            <input type="text" id="signupAddress" placeholder="Address">
            <button type="submit" class="btn-primary">Sign Up</button>
        </form>
    `;
}

function addDoctorForm() {
    return `
        <h2>Add Doctor</h2>
        <form id="addDoctorForm" onsubmit="adminAddDoctor(event)">
            <input type="text" id="docName" placeholder="Full Name" required>
            <input type="text" id="docSpecialty" placeholder="Specialty" required>
            <input type="email" id="docEmail" placeholder="Email" required>
            <input type="password" id="docPassword" placeholder="Password" required>
            <input type="text" id="docPhone" placeholder="Phone">
            <label>Available Times:</label>
            <div id="timeCheckboxes">
                <label><input type="checkbox" value="09:00-10:00"> 09:00-10:00</label>
                <label><input type="checkbox" value="10:00-11:00"> 10:00-11:00</label>
                <label><input type="checkbox" value="11:00-12:00"> 11:00-12:00</label>
                <label><input type="checkbox" value="14:00-15:00"> 14:00-15:00</label>
                <label><input type="checkbox" value="15:00-16:00"> 15:00-16:00</label>
                <label><input type="checkbox" value="16:00-17:00"> 16:00-17:00</label>
            </div>
            <button type="submit" class="btn-primary">Add Doctor</button>
        </form>
    `;
}
