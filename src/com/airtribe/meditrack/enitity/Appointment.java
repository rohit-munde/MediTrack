package com.airtribe.meditrack.enitity;

import java.time.LocalDateTime;
import com.airtribe.meditrack.util.IdGenerator;

public class Appointment {
    private int id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status;

    public Appointment() {}

    public Appointment(Doctor doctor, Patient patient, LocalDateTime appointmentDateTime) {
        this.id = IdGenerator.generateAppointmentId();
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDateTime = appointmentDateTime;
        this.status = AppointmentStatus.PENDING;
    }

    public Appointment(int id, Doctor doctor, Patient patient, LocalDateTime appointmentDateTime, AppointmentStatus status) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDateTime = appointmentDateTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", doctor=" + doctor.getName() +
                ", patient=" + patient.getName() +
                ", appointmentDateTime=" + appointmentDateTime +
                ", status=" + status +
                '}';
    }
}


