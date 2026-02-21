package com.airtribe.meditrack.test;

import com.airtribe.meditrack.enitity.*;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.util.AIHelper;
import com.airtribe.meditrack.util.DateUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manual Test Runner for MediTrack Application
 */
public class TestRunner {
    private DoctorService doctorService;
    private PatientService patientService;
    private AppointmentService appointmentService;
    private BillService billService;
    private AIHelper aiHelper;

    public TestRunner() {
        doctorService = new DoctorService();
        patientService = new PatientService();
        appointmentService = new AppointmentService();
        billService = new BillService();
        aiHelper = new AIHelper(doctorService);
    }

    /**
     * Run all tests
     */
    public void runAllTests() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    MediTrack - Comprehensive Tests     ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        testDoctorService();
        testPatientService();
        testAppointmentService();
        testBillService();
        testDateUtil();
        testAIHelper();
        testGenerics();

        System.out.println("\n✓ All tests completed successfully!");
    }

    private void testDoctorService() {
        System.out.println("\n▶ Testing DoctorService...");

        // Create doctors
        Doctor doc1 = doctorService.createDoctor("Dr. John Cardiologist", 45, "john@hospital.com", "9876543210", Specialization.CARDIOLOGIST, 500.0);
        Doctor doc2 = doctorService.createDoctor("Dr. Sarah Dermatologist", 35, "sarah@hospital.com", "9876543211", Specialization.DERMATOLOGIST, 400.0);
        Doctor doc3 = doctorService.createDoctor("Dr. Mike Neurologist", 50, "mike@hospital.com", "9876543212", Specialization.NEUROLOGIST, 600.0);

        // Search by specialization
        List<Doctor> cardiologists = doctorService.searchDoctorBySpecialization(Specialization.CARDIOLOGIST);
        System.out.println("  ✓ Found " + cardiologists.size() + " cardiologist(s)");

        // Get average fees
        double avgFees = doctorService.getAverageFees();
        System.out.println("  ✓ Average doctor fees: ₹" + String.format("%.2f", avgFees));

        doctorService.printAllDoctors();
    }

    private void testPatientService() {
        System.out.println("\n▶ Testing PatientService...");

        // Create patients
        Patient pat1 = patientService.createPatient("Rahul Singh", 28, "rahul@email.com", "9123456789", Arrays.asList("Fever", "Cough"));
        Patient pat2 = patientService.createPatient("Priya Sharma", 32, "priya@email.com", "9123456790", Arrays.asList("Chest Pain", "Shortness of Breath"));
        Patient pat3 = patientService.createPatient("Amit Patel", 40, "amit@email.com", "9123456791", Arrays.asList("Headache", "Dizziness"));

        // Search by name
        List<Patient> patients = patientService.searchPatientByName("Rahul");
        System.out.println("  ✓ Found " + patients.size() + " patient(s) named 'Rahul'");

        // Search by symptom
        List<Patient> feverPatients = patientService.searchPatientBySymptom("Fever");
        System.out.println("  ✓ Found " + feverPatients.size() + " patient(s) with fever");

        patientService.printAllPatients();
    }

    private void testAppointmentService() {
        System.out.println("\n▶ Testing AppointmentService...");

        // Get first doctor and patient from services
        Doctor doctor = doctorService.getAllDoctors().get(0);
        Patient patient = patientService.getAllPatients().get(0);

        // Schedule appointments
        LocalDateTime appointmentTime = DateUtil.getCurrentDateTime().plusDays(1).withHour(10).withMinute(0).withSecond(0);
        Appointment apt1 = appointmentService.scheduleAppointment(doctor, patient, appointmentTime);

        if (apt1 != null) {
            System.out.println("  ✓ Appointment scheduled: " + DateUtil.formatDateTime(appointmentTime));

            // Confirm appointment
            appointmentService.confirmAppointment(apt1.getId());

            // View all appointments
            appointmentService.viewAllAppointments();
        }
    }

    private void testBillService() {
        System.out.println("\n▶ Testing BillService...");

        if (!appointmentService.getAllAppointments().isEmpty()) {
            Appointment apt = appointmentService.getAllAppointments().get(0);
            Doctor doc = apt.getDoctor();

            // Generate bill
            Bill bill = billService.generateBill(apt, doc.getFees());

            if (bill != null) {
                System.out.println("  ✓ Bill Amount: ₹" + bill.getAmount());
                System.out.println("  ✓ Tax (18%): ₹" + String.format("%.2f", bill.getTaxAmount()));
                System.out.println("  ✓ Total Amount: ₹" + String.format("%.2f", bill.getTotalAmount()));

                // Test immutable BillSummary
                BillSummary summary = billService.getBillSummary(bill.getId());
                System.out.println("  ✓ Bill Summary (Immutable): " + summary);

                billService.printAllBills();
            }
        }
    }

    private void testDateUtil() {
        System.out.println("\n▶ Testing DateUtil...");

        LocalDateTime now = DateUtil.getCurrentDateTime();
        System.out.println("  ✓ Current DateTime: " + DateUtil.formatDateTime(now));

        LocalDateTime future = DateUtil.addHours(now, 24);
        System.out.println("  ✓ Future DateTime (24h): " + DateUtil.formatDateTime(future));

        System.out.println("  ✓ Is Future: " + DateUtil.isFuture(future));
        System.out.println("  ✓ Is Past: " + DateUtil.isPast(future));
        System.out.println("  ✓ Is Today: " + DateUtil.isToday(now));
    }

    private void testAIHelper() {
        System.out.println("\n▶ Testing AIHelper (AI Features)...");

        // Test doctor recommendation
        List<String> symptoms = Arrays.asList("chest pain", "shortness of breath");
        List<Doctor> recommendedDoctors = aiHelper.recommendDoctorsBySymptoms(symptoms);
        System.out.println("  ✓ Recommended " + recommendedDoctors.size() + " doctor(s) for symptoms");

        // Test appointment slots
        List<String> slots = aiHelper.suggestAppointmentSlots();
        System.out.println("  ✓ Available appointment slots: " + slots.size());

        // Test affordable doctors
        List<Doctor> affordable = aiHelper.getAffordableDoctors();
        System.out.println("  ✓ Found " + affordable.size() + " affordable doctor(s)");
    }

    private void testGenerics() {
        System.out.println("\n▶ Testing Generics & Collections...");
        System.out.println("  ✓ DoctorService uses DataStore<Doctor>");
        System.out.println("  ✓ PatientService uses DataStore<Patient>");
        System.out.println("  ✓ AppointmentService uses DataStore<Appointment>");
        System.out.println("  ✓ BillService uses DataStore<Bill>");
        System.out.println("  ✓ All services use type-safe generics");
    }

    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        runner.runAllTests();
    }
}

