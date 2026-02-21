package com.airtribe.meditrack;

import com.airtribe.meditrack.enitity.*;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.util.*;
import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.test.TestRunner;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static DoctorService doctorService = new DoctorService();
    private static PatientService patientService = new PatientService();
    private static AppointmentService appointmentService = new AppointmentService();
    private static BillService billService = new BillService();
    private static AIHelper aiHelper = new AIHelper(doctorService);
    private static Scanner sc = new Scanner(System.in);
    private static boolean running = true;

    public static void main(String[] args) {
        // Load persisted data if --loadData flag is provided
        if (args.length > 0 && args[0].equals("--loadData")) {
            loadPersistedData();
        }

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        Welcome to MediTrack v1.0       ║");
        System.out.println("║    Healthcare Management System        ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        while (running) {
            displayMainMenu();
            int choice = getIntInput();
            handleMainMenuChoice(choice);
        }

        savePersistedData();
        System.out.println("\n✓ Thank you for using MediTrack. Goodbye!");
        sc.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n┌────────────── MAIN MENU ──────────────┐");
        System.out.println("│ 1. Doctor Management                   │");
        System.out.println("│ 2. Patient Management                  │");
        System.out.println("│ 3. Appointment Management              │");
        System.out.println("│ 4. Billing & Revenue                   │");
        System.out.println("│ 5. AI Features (Recommendations)       │");
        System.out.println("│ 6. Reports & Analytics                 │");
        System.out.println("│ 7. Run Tests                           │");
        System.out.println("│ 8. Exit MediTrack                      │");
        System.out.println("└────────────────────────────────────────┘");
        System.out.print("Select option: ");
    }

    private static void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                doctorManagementMenu();
                break;
            case 2:
                patientManagementMenu();
                break;
            case 3:
                appointmentManagementMenu();
                break;
            case 4:
                billingManagementMenu();
                break;
            case 5:
                aiFeaturesMenu();
                break;
            case 6:
                reportsAndAnalyticsMenu();
                break;
            case 7:
                TestRunner testRunner = new TestRunner();
                testRunner.runAllTests();
                break;
            case 8:
                System.out.println("\n✓ Exiting MediTrack...");
                running = false;
                break;
            default:
                System.out.println("✗ Invalid option. Please try again.");
        }
    }

    // ============= DOCTOR MANAGEMENT =============
    private static void doctorManagementMenu() {
        boolean doctorMenu = true;
        while (doctorMenu) {
            System.out.println("\n┌────── DOCTOR MANAGEMENT ──────┐");
            System.out.println("│ 1. Add New Doctor              │");
            System.out.println("│ 2. View All Doctors            │");
            System.out.println("│ 3. Search Doctor by Name       │");
            System.out.println("│ 4. Search by Specialization    │");
            System.out.println("│ 5. Update Doctor               │");
            System.out.println("│ 6. Delete Doctor               │");
            System.out.println("│ 7. View Average Fees           │");
            System.out.println("│ 8. Back to Main Menu           │");
            System.out.println("└────────────────────────────────┘");
            System.out.print("Select option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addDoctor();
                    break;
                case 2:
                    doctorService.printAllDoctors();
                    break;
                case 3:
                    searchDoctorByName();
                    break;
                case 4:
                    searchDoctorBySpecialization();
                    break;
                case 5:
                    updateDoctor();
                    break;
                case 6:
                    deleteDoctor();
                    break;
                case 7:
                    System.out.println("✓ Average Doctor Fees: ₹" + String.format("%.2f", doctorService.getAverageFees()));
                    break;
                case 8:
                    doctorMenu = false;
                    break;
                default:
                    System.out.println("✗ Invalid option.");
            }
        }
    }

    private static void addDoctor() {
        System.out.println("\n--- Add New Doctor ---");
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter age: ");
        int age = getIntInput();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter contact number: ");
        String contactNo = sc.nextLine();
        System.out.print("Enter fees: ");
        double fees = getDoubleInput();

        System.out.println("\nSpecializations:");
        Specialization[] specs = Specialization.values();
        for (int i = 0; i < specs.length; i++) {
            System.out.println((i + 1) + ". " + specs[i]);
        }
        System.out.print("Select specialization (1-" + specs.length + "): ");
        int specChoice = getIntInput() - 1;

        if (specChoice >= 0 && specChoice < specs.length) {
            doctorService.createDoctor(name, age, email, contactNo, specs[specChoice], fees);
        } else {
            System.out.println("✗ Invalid specialization choice.");
        }
    }

    private static void searchDoctorByName() {
        System.out.print("Enter doctor name to search: ");
        String name = sc.nextLine();
        List<Doctor> doctors = doctorService.searchDoctorByName(name);
        if (doctors.isEmpty()) {
            System.out.println("✗ No doctors found with name: " + name);
        } else {
            System.out.println("\n✓ Found " + doctors.size() + " doctor(s):");
            doctors.forEach(System.out::println);
        }
    }

    private static void searchDoctorBySpecialization() {
        System.out.println("\nSpecializations:");
        Specialization[] specs = Specialization.values();
        for (int i = 0; i < specs.length; i++) {
            System.out.println((i + 1) + ". " + specs[i]);
        }
        System.out.print("Select specialization: ");
        int choice = getIntInput() - 1;

        if (choice >= 0 && choice < specs.length) {
            List<Doctor> doctors = doctorService.searchDoctorBySpecialization(specs[choice]);
            if (doctors.isEmpty()) {
                System.out.println("✗ No doctors found in this specialization.");
            } else {
                System.out.println("\n✓ Found " + doctors.size() + " doctor(s):");
                doctors.forEach(System.out::println);
            }
        }
    }

    private static void updateDoctor() {
        System.out.print("Enter doctor ID to update: ");
        int id = getIntInput();
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) return;

        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        System.out.print("Enter new age: ");
        int age = getIntInput();
        System.out.print("Enter new email: ");
        String email = sc.nextLine();
        System.out.print("Enter new contact number: ");
        String contactNo = sc.nextLine();
        System.out.print("Enter new fees: ");
        double fees = getDoubleInput();

        System.out.println("\nSpecializations:");
        Specialization[] specs = Specialization.values();
        for (int i = 0; i < specs.length; i++) {
            System.out.println((i + 1) + ". " + specs[i]);
        }
        System.out.print("Select specialization: ");
        int specChoice = getIntInput() - 1;

        if (specChoice >= 0 && specChoice < specs.length) {
            doctorService.updateDoctor(id, name, age, email, contactNo, specs[specChoice], fees);
        }
    }

    private static void deleteDoctor() {
        System.out.print("Enter doctor ID to delete: ");
        int id = getIntInput();
        doctorService.deleteDoctor(id);
    }

    // ============= PATIENT MANAGEMENT =============
    private static void patientManagementMenu() {
        boolean patientMenu = true;
        while (patientMenu) {
            System.out.println("\n┌────── PATIENT MANAGEMENT ──────┐");
            System.out.println("│ 1. Register New Patient        │");
            System.out.println("│ 2. View All Patients           │");
            System.out.println("│ 3. Search Patient by Name      │");
            System.out.println("│ 4. Search Patient by Age       │");
            System.out.println("│ 5. Search by Symptom           │");
            System.out.println("│ 6. Update Patient              │");
            System.out.println("│ 7. Delete Patient              │");
            System.out.println("│ 8. Back to Main Menu           │");
            System.out.println("└────────────────────────────────┘");
            System.out.print("Select option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    patientService.printAllPatients();
                    break;
                case 3:
                    searchPatientByName();
                    break;
                case 4:
                    searchPatientByAge();
                    break;
                case 5:
                    searchPatientBySymptom();
                    break;
                case 6:
                    updatePatient();
                    break;
                case 7:
                    deletePatient();
                    break;
                case 8:
                    patientMenu = false;
                    break;
                default:
                    System.out.println("✗ Invalid option.");
            }
        }
    }

    private static void addPatient() {
        System.out.println("\n--- Register New Patient ---");
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter age: ");
        int age = getIntInput();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter contact number: ");
        String contactNo = sc.nextLine();
        System.out.print("Enter symptoms (comma-separated): ");
        String symptomsInput = sc.nextLine();
        List<String> symptoms = PatientService.parseSymptoms(symptomsInput);

        patientService.createPatient(name, age, email, contactNo, symptoms);
    }

    private static void searchPatientByName() {
        System.out.print("Enter patient name to search: ");
        String name = sc.nextLine();
        List<Patient> patients = patientService.searchPatientByName(name);
        if (patients.isEmpty()) {
            System.out.println("✗ No patients found with name: " + name);
        } else {
            System.out.println("\n✓ Found " + patients.size() + " patient(s):");
            patients.forEach(System.out::println);
        }
    }

    private static void searchPatientByAge() {
        System.out.print("Enter age to search: ");
        int age = getIntInput();
        List<Patient> patients = patientService.searchPatientByAge(age);
        if (patients.isEmpty()) {
            System.out.println("✗ No patients found with age: " + age);
        } else {
            System.out.println("\n✓ Found " + patients.size() + " patient(s):");
            patients.forEach(System.out::println);
        }
    }

    private static void searchPatientBySymptom() {
        System.out.print("Enter symptom to search: ");
        String symptom = sc.nextLine();
        List<Patient> patients = patientService.searchPatientBySymptom(symptom);
        if (patients.isEmpty()) {
            System.out.println("✗ No patients found with symptom: " + symptom);
        } else {
            System.out.println("\n✓ Found " + patients.size() + " patient(s):");
            patients.forEach(System.out::println);
        }
    }

    private static void updatePatient() {
        System.out.print("Enter patient ID to update: ");
        int id = getIntInput();
        Patient patient = patientService.getPatientById(id);
        if (patient == null) return;

        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        System.out.print("Enter new age: ");
        int age = getIntInput();
        System.out.print("Enter new email: ");
        String email = sc.nextLine();
        System.out.print("Enter new contact number: ");
        String contactNo = sc.nextLine();
        System.out.print("Enter new symptoms (comma-separated): ");
        String symptomsInput = sc.nextLine();
        List<String> symptoms = PatientService.parseSymptoms(symptomsInput);

        patientService.updatePatient(id, name, age, email, contactNo, symptoms);
    }

    private static void deletePatient() {
        System.out.print("Enter patient ID to delete: ");
        int id = getIntInput();
        patientService.deletePatient(id);
    }

    // ============= APPOINTMENT MANAGEMENT =============
    private static void appointmentManagementMenu() {
        boolean appointmentMenu = true;
        while (appointmentMenu) {
            System.out.println("\n┌──── APPOINTMENT MANAGEMENT ────┐");
            System.out.println("│ 1. Schedule Appointment        │");
            System.out.println("│ 2. View All Appointments       │");
            System.out.println("│ 3. View Doctor Appointments    │");
            System.out.println("│ 4. View Patient Appointments   │");
            System.out.println("│ 5. Confirm Appointment         │");
            System.out.println("│ 6. Cancel Appointment          │");
            System.out.println("│ 7. Reschedule Appointment      │");
            System.out.println("│ 8. Back to Main Menu           │");
            System.out.println("└────────────────────────────────┘");
            System.out.print("Select option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    scheduleAppointment();
                    break;
                case 2:
                    appointmentService.viewAllAppointments();
                    break;
                case 3:
                    viewDoctorAppointments();
                    break;
                case 4:
                    viewPatientAppointments();
                    break;
                case 5:
                    confirmAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 7:
                    rescheduleAppointment();
                    break;
                case 8:
                    appointmentMenu = false;
                    break;
                default:
                    System.out.println("✗ Invalid option.");
            }
        }
    }

    private static void scheduleAppointment() {
        System.out.println("\n--- Schedule Appointment ---");
        System.out.print("Enter doctor ID: ");
        int doctorId = getIntInput();
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) return;

        System.out.print("Enter patient ID: ");
        int patientId = getIntInput();
        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) return;

        System.out.print("Enter appointment date (yyyy-MM-dd HH:mm:ss): ");
        String dateTimeStr = sc.nextLine();
        try {
            LocalDateTime appointmentTime = DateUtil.parseDateTime(dateTimeStr);
            appointmentService.scheduleAppointment(doctor, patient, appointmentTime);
        } catch (Exception e) {
            System.out.println("✗ Invalid date format: " + e.getMessage());
        }
    }

    private static void viewDoctorAppointments() {
        System.out.print("Enter doctor ID: ");
        int doctorId = getIntInput();
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        if (appointments.isEmpty()) {
            System.out.println("✗ No appointments found for this doctor.");
        } else {
            System.out.println("\n✓ Found " + appointments.size() + " appointment(s):");
            appointments.forEach(System.out::println);
        }
    }

    private static void viewPatientAppointments() {
        System.out.print("Enter patient ID: ");
        int patientId = getIntInput();
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        if (appointments.isEmpty()) {
            System.out.println("✗ No appointments found for this patient.");
        } else {
            System.out.println("\n✓ Found " + appointments.size() + " appointment(s):");
            appointments.forEach(System.out::println);
        }
    }

    private static void confirmAppointment() {
        System.out.print("Enter appointment ID to confirm: ");
        int appointmentId = getIntInput();
        appointmentService.confirmAppointment(appointmentId);
    }

    private static void cancelAppointment() {
        System.out.print("Enter appointment ID to cancel: ");
        int appointmentId = getIntInput();
        appointmentService.cancelAppointment(appointmentId);
    }

    private static void rescheduleAppointment() {
        System.out.print("Enter appointment ID to reschedule: ");
        int appointmentId = getIntInput();
        System.out.print("Enter new date/time (yyyy-MM-dd HH:mm:ss): ");
        String dateTimeStr = sc.nextLine();
        try {
            LocalDateTime newTime = DateUtil.parseDateTime(dateTimeStr);
            appointmentService.updateAppointment(appointmentId, newTime);
        } catch (Exception e) {
            System.out.println("✗ Invalid date format.");
        }
    }

    // ============= BILLING MANAGEMENT =============
    private static void billingManagementMenu() {
        boolean billingMenu = true;
        while (billingMenu) {
            System.out.println("\n┌───── BILLING MANAGEMENT ──────┐");
            System.out.println("│ 1. Generate Bill               │");
            System.out.println("│ 2. View All Bills              │");
            System.out.println("│ 3. View Patient Bills          │");
            System.out.println("│ 4. View Doctor Revenue         │");
            System.out.println("│ 5. Total Revenue & Tax         │");
            System.out.println("│ 6. Back to Main Menu           │");
            System.out.println("└────────────────────────────────┘");
            System.out.print("Select option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    generateBill();
                    break;
                case 2:
                    billService.printAllBills();
                    break;
                case 3:
                    viewPatientBills();
                    break;
                case 4:
                    viewDoctorRevenue();
                    break;
                case 5:
                    viewTotalRevenueAndTax();
                    break;
                case 6:
                    billingMenu = false;
                    break;
                default:
                    System.out.println("✗ Invalid option.");
            }
        }
    }

    private static void generateBill() {
        System.out.print("Enter appointment ID: ");
        int appointmentId = getIntInput();
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) {
            System.out.println("✗ Appointment not found.");
            return;
        }

        System.out.print("Enter amount: ");
        double amount = getDoubleInput();
        billService.generateBill(appointment, amount);
    }

    private static void viewPatientBills() {
        System.out.print("Enter patient ID: ");
        int patientId = getIntInput();
        List<Bill> bills = billService.getBillsByPatient(patientId);
        if (bills.isEmpty()) {
            System.out.println("✗ No bills found for this patient.");
        } else {
            System.out.println("\n✓ Found " + bills.size() + " bill(s):");
            bills.forEach(System.out::println);
        }
    }

    private static void viewDoctorRevenue() {
        System.out.print("Enter doctor ID: ");
        int doctorId = getIntInput();
        List<Bill> bills = billService.getBillsByDoctor(doctorId);
        if (bills.isEmpty()) {
            System.out.println("✗ No bills found for this doctor.");
        } else {
            double revenue = bills.stream().mapToDouble(Bill::getTotalAmount).sum();
            System.out.println("\n✓ Total Revenue: ₹" + String.format("%.2f", revenue));
        }
    }

    private static void viewTotalRevenueAndTax() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   FINANCIAL SUMMARY                ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ Total Revenue: ₹" + String.format("%-23.2f", billService.getTotalRevenue()) + "║");
        System.out.println("║ Total Tax (18%): ₹" + String.format("%-20.2f", billService.getTotalTax()) + "║");
        System.out.println("║ Total Bills: " + billService.getTotalBills());
        System.out.println("╚════════════════════════════════════╝");
    }

    // ============= AI FEATURES MENU =============
    private static void aiFeaturesMenu() {
        boolean aiMenu = true;
        while (aiMenu) {
            System.out.println("\n┌────── AI FEATURES MENU ────────┐");
            System.out.println("│ 1. Get Doctor Recommendations  │");
            System.out.println("│ 2. Suggest Appointment Slots   │");
            System.out.println("│ 3. Find Affordable Doctors     │");
            System.out.println("│ 4. Top Doctors                 │");
            System.out.println("│ 5. Back to Main Menu           │");
            System.out.println("└────────────────────────────────┘");
            System.out.print("Select option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    getDoctorRecommendations();
                    break;
                case 2:
                    suggestAppointmentSlots();
                    break;
                case 3:
                    findAffordableDoctors();
                    break;
                case 4:
                    viewTopDoctors();
                    break;
                case 5:
                    aiMenu = false;
                    break;
                default:
                    System.out.println("✗ Invalid option.");
            }
        }
    }

    private static void getDoctorRecommendations() {
        System.out.println("\n--- AI Doctor Recommendation ---");
        System.out.print("Enter symptoms (comma-separated): ");
        String symptomsInput = sc.nextLine();
        List<String> symptoms = PatientService.parseSymptoms(symptomsInput);

        List<Doctor> recommendedDoctors = aiHelper.recommendDoctorsBySymptoms(symptoms);
        if (recommendedDoctors.isEmpty()) {
            System.out.println("✗ No doctors available.");
        } else {
            System.out.println("\n✓ Recommended Doctors:");
            recommendedDoctors.forEach(System.out::println);
        }
    }

    private static void suggestAppointmentSlots() {
        List<String> slots = aiHelper.suggestAppointmentSlots();
        System.out.println("\n✓ Available Appointment Slots:");
        for (int i = 0; i < slots.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + slots.get(i));
        }
    }

    private static void findAffordableDoctors() {
        List<Doctor> affordable = aiHelper.getAffordableDoctors();
        if (affordable.isEmpty()) {
            System.out.println("✗ No affordable doctors available.");
        } else {
            System.out.println("\n✓ Affordable Doctors (Below Average Fees):");
            affordable.forEach(System.out::println);
        }
    }

    private static void viewTopDoctors() {
        System.out.print("How many top doctors to show? ");
        int limit = getIntInput();
        List<Doctor> topDoctors = aiHelper.getTopDoctors(limit);
        if (topDoctors.isEmpty()) {
            System.out.println("✗ No doctors available.");
        } else {
            System.out.println("\n✓ Top " + limit + " Doctor(s):");
            topDoctors.forEach(System.out::println);
        }
    }

    // ============= REPORTS & ANALYTICS =============
    private static void reportsAndAnalyticsMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║      REPORTS & ANALYTICS           ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ Total Doctors: " + doctorService.getTotalDoctors());
        System.out.println("║ Total Patients: " + patientService.getTotalPatients());
        System.out.println("║ Total Appointments: " + appointmentService.getTotalAppointments());
        System.out.println("║ Total Bills: " + billService.getTotalBills());
        System.out.println("║ Average Doctor Fees: ₹" + String.format("%.2f", doctorService.getAverageFees()) + "");
        System.out.println("║ Total Revenue: ₹" + String.format("%.2f", billService.getTotalRevenue()) + "");
        System.out.println("║ Total Tax Collected: ₹" + String.format("%.2f", billService.getTotalTax()) + "");
        System.out.println("╚════════════════════════════════════╝");
    }

    // ============= HELPER METHODS =============
    private static int getIntInput() {
        try {
            int value = sc.nextInt();
            sc.nextLine(); // Consume newline
            return value;
        } catch (Exception e) {
            sc.nextLine(); // Clear invalid input
            System.out.println("✗ Invalid input. Please enter a number.");
            return 0;
        }
    }

    private static double getDoubleInput() {
        try {
            double value = sc.nextDouble();
            sc.nextLine(); // Consume newline
            return value;
        } catch (Exception e) {
            sc.nextLine(); // Clear invalid input
            System.out.println("✗ Invalid input. Please enter a number.");
            return 0;
        }
    }

    private static void loadPersistedData() {
        System.out.println("Loading persisted data...");
        // Load doctors from CSV
        List<Doctor> doctors = CSVUtil.loadDoctorsFromCSV(Constants.DOCTORS_FILE);
        doctors.forEach(doc -> doctorService.createDoctor(
                doc.getName(), doc.getAge(), doc.getEmail(), doc.getContactNo(),
                doc.getSpecialization(), doc.getFees()
        ));

        // Load patients from CSV
        List<Patient> patients = CSVUtil.loadPatientsFromCSV(Constants.PATIENTS_FILE);
        patients.forEach(pat -> patientService.createPatient(
                pat.getName(), pat.getAge(), pat.getEmail(), pat.getContactNo(),
                pat.getSymptoms()
        ));
    }

    private static void savePersistedData() {
        System.out.println("\nSaving data...");
        CSVUtil.saveDoctorsToCSV(doctorService.getAllDoctors(), Constants.DOCTORS_FILE);
        CSVUtil.savePatientsToCSV(patientService.getAllPatients(), Constants.PATIENTS_FILE);
        System.out.println("✓ Data saved successfully!");
    }
}
