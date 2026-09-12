export function createPatientRow(appointment) {
    const tr = document.createElement("tr");

    const idTd = document.createElement("td");
    idTd.textContent = appointment.patientId || appointment.id;

    const nameTd = document.createElement("td");
    nameTd.textContent = appointment.patientName || "N/A";

    const phoneTd = document.createElement("td");
    phoneTd.textContent = appointment.patientPhone || "N/A";

    const emailTd = document.createElement("td");
    emailTd.textContent = appointment.patientEmail || "N/A";

    const actionTd = document.createElement("td");
    const btn = document.createElement("button");
    btn.textContent = "Prescribe";
    btn.classList.add("prescription-btn");
    btn.addEventListener("click", () => {
        window.location.href = `/pages/addPrescription.html?appointmentId=${appointment.id}`;
    });
    actionTd.appendChild(btn);

    tr.appendChild(idTd);
    tr.appendChild(nameTd);
    tr.appendChild(phoneTd);
    tr.appendChild(emailTd);
    tr.appendChild(actionTd);

    return tr;
}
