package com.airtribe.meditrack.service;

import com.airtribe.meditrack.enitity.Doctor;
import com.airtribe.meditrack.enitity.Specialization;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.Validator;
import com.airtribe.meditrack.exception.InvalidDataException;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorService {
    private final DataStore<Doctor> dataStore = new DataStore<>();

    /**
     * Create a new doctor with provided details
     */
    public Doctor createDoctor(String name, int age, String email, String contactNo, Specialization specialization, double fees) {
        try {
            Validator.validateName(name);
            Validator.validateAge(age);
            Validator.validateEmail(email);
            Validator.validateContactNo(contactNo);
            Validator.validateFees(fees);

            Doctor doctor = new Doctor(name, age, email, contactNo, specialization, fees);
            dataStore.create(doctor.getId(), doctor);
            System.out.println("✓ Doctor created successfully! ID: " + doctor.getId());
            return doctor;
        } catch (InvalidDataException e) {
            System.out.println("✗ Error creating doctor: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get doctor by ID
     */
    public Doctor getDoctorById(int id) {
        Doctor doctor = dataStore.read(id);
        if (doctor == null) {
            System.out.println("✗ Doctor with ID " + id + " not found");
        }
        return doctor;
    }

    /**
     * Get all doctors
     */
    public List<Doctor> getAllDoctors() {
        return dataStore.getValues();
    }

    /**
     * Update doctor details
     */
    public boolean updateDoctor(int id, String name, int age, String email, String contactNo, Specialization specialization, double fees) {
        try {
            Doctor doctor = getDoctorById(id);
            if (doctor == null) {
                return false;
            }

            Validator.validateName(name);
            Validator.validateAge(age);
            Validator.validateEmail(email);
            Validator.validateContactNo(contactNo);
            Validator.validateFees(fees);

            doctor.setName(name);
            doctor.setAge(age);
            doctor.setEmail(email);
            doctor.setContactNo(contactNo);
            doctor.setSpecialization(specialization);
            doctor.setFees(fees);
            dataStore.update(id, doctor);
            System.out.println("✓ Doctor updated successfully!");
            return true;
        } catch (InvalidDataException e) {
            System.out.println("✗ Error updating doctor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete doctor
     */
    public boolean deleteDoctor(int id) {
        if (dataStore.exists(id)) {
            dataStore.delete(id);
            System.out.println("✓ Doctor deleted successfully!");
            return true;
        }
        System.out.println("✗ Doctor with ID " + id + " not found");
        return false;
    }

    /**
     * Search doctor by name
     */
    public List<Doctor> searchDoctorByName(String name) {
        return dataStore.getValues().stream()
                .filter(doctor -> doctor.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Search doctors by specialization
     */
    public List<Doctor> searchDoctorBySpecialization(Specialization specialization) {
        return dataStore.getValues().stream()
                .filter(doctor -> doctor.getSpecialization() == specialization)
                .collect(Collectors.toList());
    }

    /**
     * Get average fees of all doctors
     */
    public double getAverageFees() {
        if (dataStore.isEmpty()) {
            return 0;
        }
        return dataStore.getValues().stream()
                .mapToDouble(Doctor::getFees)
                .average()
                .orElse(0);
    }

    /**
     * Print all doctors
     */
    public void printAllDoctors() {
        if (dataStore.isEmpty()) {
            System.out.println("No doctors found in the system");
            return;
        }
        System.out.println("\n=== All Doctors ===");
        dataStore.printAll();
    }

    /**
     * Get total number of doctors
     */
    public int getTotalDoctors() {
        return dataStore.size();
    }
}
