// =====================================================
// Utility Functions
// =====================================================

export function getToken() {
    return localStorage.getItem("token");
}

export function getUserRole() {
    return localStorage.getItem("userRole");
}

export function setToken(token) {
    localStorage.setItem("token", token);
}

export function setUserRole(role) {
    localStorage.setItem("userRole", role);
}

export function clearSession() {
    localStorage.removeItem("token");
    localStorage.removeItem("userRole");
}

export function formatDate(date) {
    const d = new Date(date);
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, "0");
    const day = String(d.getDate()).padStart(2, "0");
    return `${year}-${month}-${day}`;
}

export function formatTime(date) {
    const d = new Date(date);
    return d.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
}
