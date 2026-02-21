package com.airtribe.meditrack.util;

import com.airtribe.meditrack.enitity.Doctor;
import com.airtribe.meditrack.enitity.Patient;
import com.airtribe.meditrack.enitity.Specialization;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CSV Utility for reading and writing CSV files
 */
public class CSVUtil {
    private static final String CSV_SEPARATOR = ",";

    /**
     * Save doctors to CSV file
     */
    public static void saveDoctorsToCSV(List<Doctor> doctors, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            writer.write("ID,Name,Age,Email,ContactNo,Specialization,Fees");
            writer.newLine();

            // Write data
            for (Doctor doctor : doctors) {
                String line = doctor.getId() + CSV_SEPARATOR +
                        doctor.getName() + CSV_SEPARATOR +
                        doctor.getAge() + CSV_SEPARATOR +
                        doctor.getEmail() + CSV_SEPARATOR +
                        doctor.getContactNo() + CSV_SEPARATOR +
                        doctor.getSpecialization().name() + CSV_SEPARATOR +
                        doctor.getFees();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("✓ Doctors saved to " + filePath);
        } catch (IOException e) {
            System.out.println("✗ Error saving doctors to CSV: " + e.getMessage());
        }
    }

    /**
     * Load doctors from CSV file
     */
    public static List<Doctor> loadDoctorsFromCSV(String filePath) {
        List<Doctor> doctors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }

                String[] fields = line.split(CSV_SEPARATOR);
                if (fields.length >= 7) {
                    try {
                        int id = Integer.parseInt(fields[0]);
                        String name = fields[1];
                        int age = Integer.parseInt(fields[2]);
                        String email = fields[3];
                        String contactNo = fields[4];
                        Specialization spec = Specialization.valueOf(fields[5]);
                        double fees = Double.parseDouble(fields[6]);

                        Doctor doctor = new Doctor(id, name, age, email, contactNo, spec, fees);
                        doctors.add(doctor);
                    } catch (Exception e) {
                        System.out.println("⚠ Skipping invalid doctor record: " + line);
                    }
                }
            }
            System.out.println("✓ Loaded " + doctors.size() + " doctors from " + filePath);
        } catch (IOException e) {
            System.out.println("⚠ No existing doctors file found: " + filePath);
        }
        return doctors;
    }

    /**
     * Save patients to CSV file
     */
    public static void savePatientsToCSV(List<Patient> patients, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            writer.write("ID,Name,Age,Email,ContactNo,Symptoms");
            writer.newLine();

            // Write data
            for (Patient patient : patients) {
                String symptomsStr = String.join("|", patient.getSymptoms());
                String line = patient.getId() + CSV_SEPARATOR +
                        patient.getName() + CSV_SEPARATOR +
                        patient.getAge() + CSV_SEPARATOR +
                        patient.getEmail() + CSV_SEPARATOR +
                        patient.getContactNo() + CSV_SEPARATOR +
                        symptomsStr;
                writer.write(line);
                writer.newLine();
            }
            System.out.println("✓ Patients saved to " + filePath);
        } catch (IOException e) {
            System.out.println("✗ Error saving patients to CSV: " + e.getMessage());
        }
    }

    /**
     * Load patients from CSV file
     */
    public static List<Patient> loadPatientsFromCSV(String filePath) {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }

                String[] fields = line.split(CSV_SEPARATOR, -1);
                if (fields.length >= 6) {
                    try {
                        int id = Integer.parseInt(fields[0]);
                        String name = fields[1];
                        int age = Integer.parseInt(fields[2]);
                        String email = fields[3];
                        String contactNo = fields[4];
                        List<String> symptoms = new ArrayList<>();
                        if (!fields[5].isEmpty()) {
                            symptoms = Arrays.asList(fields[5].split("\\|"));
                        }

                        Patient patient = new Patient(id, name, age, email, contactNo, symptoms);
                        patients.add(patient);
                    } catch (Exception e) {
                        System.out.println("⚠ Skipping invalid patient record: " + line);
                    }
                }
            }
            System.out.println("✓ Loaded " + patients.size() + " patients from " + filePath);
        } catch (IOException e) {
            System.out.println("⚠ No existing patients file found: " + filePath);
        }
        return patients;
    }
}

