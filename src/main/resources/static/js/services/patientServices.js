import { API_BASE_URL } from "../config/config.js";

const PATIENT_API = API_BASE_URL + "/patient";

export async function patientSignup(data) {
    try {
        const response = await fetch(`${PATIENT_API}/signup`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
        const result = await response.json();
        return { success: response.ok, message: result.message || "OK" };
    } catch (error) {
        console.error("patientSignup error:", error);
        return { success: false, message: "Network error" };
    }
}

export async function patientLogin(data) {
    return await fetch(`${PATIENT_API}/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });
}

export async function getPatientData(token) {
    try {
        const response = await fetch(`${PATIENT_API}/me`, {
            headers: { "Authorization": `Bearer ${token}` }
        });
        if (!response.ok) throw new Error("Failed");
        return await response.json();
    } catch (error) {
        console.error("getPatientData error:", error);
        return null;
    }
}

export async function getPatientAppointments(id, token, user) {
    try {
        const response = await fetch(`${API_BASE_URL}/appointment/${user}/${id}`, {
            headers: { "Authorization": `Bearer ${token}` }
        });
        if (!response.ok) throw new Error("Failed");
        const data = await response.json();
        return data.appointments || [];
    } catch (error) {
        console.error("getPatientAppointments error:", error);
        return null;
    }
}

export async function filterAppointments(condition, name, token) {
    try {
        const params = new URLSearchParams({ condition, name });
        const response = await fetch(`${API_BASE_URL}/appointment/filter?${params}`, {
            headers: { "Authorization": `Bearer ${token}` }
        });
        if (!response.ok) throw new Error("Failed");
        return await response.json();
    } catch (error) {
        console.error("filterAppointments error:", error);
        return [];
    }
}
