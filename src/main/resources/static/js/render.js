// =====================================================
// Rendering utilities
// =====================================================

/**
 * Redirect based on role if not already on the right page
 */
export function renderContent() {
    const role = localStorage.getItem("userRole");
    const path = window.location.pathname;

    // Only redirect on landing page
    if (path === "/" || path.endsWith("/index.html")) {
        if (role === "admin") window.location.href = "/admin/dashboard";
        else if (role === "doctor") window.location.href = "/doctor/dashboard";
        else if (role === "loggedPatient") window.location.href = "/pages/loggedPatientDashboard.html";
    }
}

/**
 * Store role in localStorage
 */
export function setRole(role) {
    localStorage.setItem("userRole", role);
    renderContent();
}

/**
 * Redirect to homepage
 */
export function goToHome() {
    window.location.href = "/";
}

// =====================================================
// Expose to global scope (for HTML onclick handlers)
// =====================================================
window.renderContent = renderContent;
window.setRole = setRole;
window.goToHome = goToHome;