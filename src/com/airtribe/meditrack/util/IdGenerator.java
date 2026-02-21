package com.airtribe.meditrack.util;

public class IdGenerator {
    private static int appointmentId = 1000;
    private static int doctorId = 2000;
    private static int patientId = 3000;

    public static synchronized int generateAppointmentId() {
        return appointmentId++;
    }

    public static synchronized int generateDoctorId() {
        return doctorId++;
    }

    public static synchronized int generatePatientId() {
        return patientId++;
    }

    public static void reset() {
        appointmentId = 1000;
        doctorId = 2000;
        patientId = 3000;
    }
}
