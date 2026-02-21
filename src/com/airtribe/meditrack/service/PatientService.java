package com.airtribe.meditrack.service;

import com.airtribe.meditrack.enitity.Patient;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.Validator;
import com.airtribe.meditrack.exception.InvalidDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatientService {
    private final DataStore<Patient> dataStore = new DataStore<>();

    /**
     * Create a new patient
     */
    public Patient createPatient(String name, int age, String email, String contactNo, List<String> symptoms) {
        try {
            Validator.validateName(name);
            Validator.validateAge(age);
            Validator.validateEmail(email);
            Validator.validateContactNo(contactNo);

            Patient patient = new Patient(name, age, email, contactNo, symptoms);
            dataStore.create(patient.getId(), patient);
            System.out.println("✓ Patient created successfully! ID: " + patient.getId());
            return patient;
        } catch (InvalidDataException e) {
            System.out.println("✗ Error creating patient: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get patient by ID
     */
    public Patient getPatientById(int id) {
        Patient patient = dataStore.read(id);
        if (patient == null) {
            System.out.println("✗ Patient with ID " + id + " not found");
        }
        return patient;
    }

    /**
     * Get all patients
     */
    public List<Patient> getAllPatients() {
        return dataStore.getValues();
    }

    /**
     * Update patient details
     */
    public boolean updatePatient(int id, String name, int age, String email, String contactNo, List<String> symptoms) {
        try {
            Patient patient = getPatientById(id);
            if (patient == null) {
                return false;
            }

            Validator.validateName(name);
            Validator.validateAge(age);
            Validator.validateEmail(email);
            Validator.validateContactNo(contactNo);

            patient.setName(name);
            patient.setAge(age);
            patient.setEmail(email);
            patient.setContactNo(contactNo);
            patient.setSymptoms(symptoms);
            dataStore.update(id, patient);
            System.out.println("✓ Patient updated successfully!");
            return true;
        } catch (InvalidDataException e) {
            System.out.println("✗ Error updating patient: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete patient
     */
    public boolean deletePatient(int id) {
        if (dataStore.exists(id)) {
            dataStore.delete(id);
            System.out.println("✓ Patient deleted successfully!");
            return true;
        }
        System.out.println("✗ Patient with ID " + id + " not found");
        return false;
    }

    /**
     * Search patient by name
     */
    public List<Patient> searchPatientByName(String name) {
        return dataStore.getValues().stream()
                .filter(patient -> patient.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Search patients by age
     */
    public List<Patient> searchPatientByAge(int age) {
        return dataStore.getValues().stream()
                .filter(patient -> patient.getAge() == age)
                .collect(Collectors.toList());
    }

    /**
     * Search patients by symptom
     */
    public List<Patient> searchPatientBySymptom(String symptom) {
        return dataStore.getValues().stream()
                .filter(patient -> patient.getSymptoms().stream()
                        .anyMatch(s -> s.toLowerCase().contains(symptom.toLowerCase())))
                .collect(Collectors.toList());
    }

    /**
     * Print all patients
     */
    public void printAllPatients() {
        if (dataStore.isEmpty()) {
            System.out.println("No patients found in the system");
            return;
        }
        System.out.println("\n=== All Patients ===");
        dataStore.printAll();
    }

    /**
     * Get total number of patients
     */
    public int getTotalPatients() {
        return dataStore.size();
    }

    /**
     * Parse symptoms from comma-separated string
     */
    public static List<String> parseSymptoms(String symptomsInput) {
        List<String> symptoms = new ArrayList<>();
        if (symptomsInput != null && !symptomsInput.isEmpty()) {
            String[] symptomsArray = symptomsInput.split(",");
            for (String symptom : symptomsArray) {
                symptoms.add(symptom.trim());
            }
        }
        return symptoms;
    }
}
