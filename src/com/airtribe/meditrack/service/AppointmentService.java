package com.airtribe.meditrack.service;

import com.airtribe.meditrack.enitity.Appointment;
import com.airtribe.meditrack.enitity.AppointmentStatus;
import com.airtribe.meditrack.enitity.Doctor;
import com.airtribe.meditrack.enitity.Patient;
import com.airtribe.meditrack.util.DataStore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentService {
    private final DataStore<Appointment> dataStore = new DataStore<>();

    /**
     * Schedule a new appointment
     */
    public Appointment scheduleAppointment(Doctor doctor, Patient patient, LocalDateTime appointmentDateTime) {
        if (isDoctorAvailable(doctor, appointmentDateTime) && isPatientAvailable(patient, appointmentDateTime)) {
            Appointment appointment = new Appointment(doctor, patient, appointmentDateTime);
            dataStore.create(appointment.getId(), appointment);
            System.out.println("✓ Appointment scheduled successfully. ID: " + appointment.getId());
            return appointment;
        }
        System.out.println("✗ Doctor or Patient not available at this time");
        return null;
    }

    /**
     * Get appointment by ID
     */
    public Appointment getAppointmentById(int id) {
        return dataStore.read(id);
    }

    /**
     * Get all appointments
     */
    public List<Appointment> getAllAppointments() {
        return dataStore.getValues();
    }

    /**
     * Get appointments for a specific doctor
     */
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        return dataStore.getValues().stream()
                .filter(apt -> apt.getDoctor().getId() == doctorId)
                .collect(Collectors.toList());
    }

    /**
     * Get appointments for a specific patient
     */
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        return dataStore.getValues().stream()
                .filter(apt -> apt.getPatient().getId() == patientId)
                .collect(Collectors.toList());
    }

    /**
     * Cancel appointment
     */
    public boolean cancelAppointment(int id) {
        Appointment appointment = getAppointmentById(id);
        if (appointment != null) {
            appointment.setStatus(AppointmentStatus.CANCELLED);
            dataStore.update(id, appointment);
            System.out.println("✓ Appointment " + id + " cancelled successfully.");
            return true;
        }
        System.out.println("✗ Appointment with ID " + id + " not found.");
        return false;
    }

    /**
     * Confirm appointment
     */
    public boolean confirmAppointment(int id) {
        Appointment appointment = getAppointmentById(id);
        if (appointment != null) {
            appointment.setStatus(AppointmentStatus.CONFIRMED);
            dataStore.update(id, appointment);
            System.out.println("✓ Appointment " + id + " confirmed successfully.");
            return true;
        }
        System.out.println("✗ Appointment with ID " + id + " not found.");
        return false;
    }

    /**
     * Check if doctor is available at given time
     */
    public boolean isDoctorAvailable(Doctor doctor, LocalDateTime dateTime) {
        return dataStore.getValues().stream()
                .noneMatch(apt -> apt.getDoctor().getId() == doctor.getId() &&
                                  apt.getAppointmentDateTime().equals(dateTime) &&
                                  apt.getStatus() != AppointmentStatus.CANCELLED);
    }

    /**
     * Check if patient is available at given time
     */
    public boolean isPatientAvailable(Patient patient, LocalDateTime dateTime) {
        return dataStore.getValues().stream()
                .noneMatch(apt -> apt.getPatient().getId() == patient.getId() &&
                                  apt.getAppointmentDateTime().equals(dateTime) &&
                                  apt.getStatus() != AppointmentStatus.CANCELLED);
    }

    /**
     * Update appointment date/time
     */
    public boolean updateAppointment(int id, LocalDateTime newDateTime) {
        Appointment appointment = getAppointmentById(id);
        if (appointment != null) {
            appointment.setAppointmentDateTime(newDateTime);
            dataStore.update(id, appointment);
            System.out.println("✓ Appointment " + id + " updated successfully.");
            return true;
        }
        System.out.println("✗ Appointment with ID " + id + " not found.");
        return false;
    }

    /**
     * View all appointments
     */
    public void viewAllAppointments() {
        if (dataStore.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }
        System.out.println("\n=== All Appointments ===");
        dataStore.printAll();
    }

    /**
     * Get appointments by status
     */
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return dataStore.getValues().stream()
                .filter(apt -> apt.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Get total number of appointments
     */
    public int getTotalAppointments() {
        return dataStore.size();
    }
}


