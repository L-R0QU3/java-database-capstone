import { API_BASE_URL } from "../config/config.js";

const DOCTOR_API = API_BASE_URL + "/doctor";

export async function getDoctors() {
    try {
        const response = await fetch(`${DOCTOR_API}/all`);
        if (!response.ok) throw new Error("Failed to fetch doctors");
        return await response.json();
    } catch (error) {
        console.error("getDoctors error:", error);
        return [];
    }
}

export async function deleteDoctor(id, token) {
    try {
        const response = await fetch(`${DOCTOR_API}/${id}`, {
            method: "DELETE",
            headers: { "Authorization": `Bearer ${token}` }
        });
        const data = await response.json();
        return { success: response.ok, message: data.message || "Deleted" };
    } catch (error) {
        console.error("deleteDoctor error:", error);
        return { success: false, message: "Network error" };
    }
}

export async function saveDoctor(doctor, token) {
    try {
        const response = await fetch(`${DOCTOR_API}/save`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify(doctor)
        });
        const data = await response.json();
        return { success: response.ok, message: data.message || "Saved" };
    } catch (error) {
        console.error("saveDoctor error:", error);
        return { success: false, message: "Network error" };
    }
}

export async function filterDoctors(name, time, specialty) {
    try {
        const params = new URLSearchParams();
        if (name) params.append("name", name);
        if (time) params.append("time", time);
        if (specialty) params.append("specialty", specialty);

        const response = await fetch(`${DOCTOR_API}/filter?${params.toString()}`);
        if (!response.ok) throw new Error("Failed to filter doctors");
        return await response.json();
    } catch (error) {
        console.error("filterDoctors error:", error);
        return [];
    }
}
