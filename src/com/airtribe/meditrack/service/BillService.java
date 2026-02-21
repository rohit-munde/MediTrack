package com.airtribe.meditrack.service;

import com.airtribe.meditrack.enitity.Bill;
import com.airtribe.meditrack.enitity.BillSummary;
import com.airtribe.meditrack.enitity.Appointment;
import com.airtribe.meditrack.util.DataStore;
import java.util.List;
import java.util.stream.Collectors;

public class BillService {
    private final DataStore<Bill> dataStore = new DataStore<>();

    /**
     * Generate bill for an appointment
     */
    public Bill generateBill(Appointment appointment, double amount) {
        Bill bill = new Bill(appointment, amount);
        dataStore.create(bill.getId(), bill);
        System.out.println("✓ Bill generated successfully. ID: " + bill.getId());
        return bill;
    }

    /**
     * Get bill by ID
     */
    public Bill getBillById(int id) {
        return dataStore.read(id);
    }

    /**
     * Get all bills
     */
    public List<Bill> getAllBills() {
        return dataStore.getValues();
    }

    /**
     * Get bills for a specific patient
     */
    public List<Bill> getBillsByPatient(int patientId) {
        return dataStore.getValues().stream()
                .filter(bill -> bill.getAppointment().getPatient().getId() == patientId)
                .collect(Collectors.toList());
    }

    /**
     * Get bills for a specific doctor
     */
    public List<Bill> getBillsByDoctor(int doctorId) {
        return dataStore.getValues().stream()
                .filter(bill -> bill.getAppointment().getDoctor().getId() == doctorId)
                .collect(Collectors.toList());
    }

    /**
     * Get total revenue
     */
    public double getTotalRevenue() {
        return dataStore.getValues().stream()
                .mapToDouble(Bill::getTotalAmount)
                .sum();
    }

    /**
     * Get total tax collected
     */
    public double getTotalTax() {
        return dataStore.getValues().stream()
                .mapToDouble(Bill::getTaxAmount)
                .sum();
    }

    /**
     * Print all bills
     */
    public void printAllBills() {
        if (dataStore.isEmpty()) {
            System.out.println("No bills found in the system");
            return;
        }
        System.out.println("\n=== All Bills ===");
        dataStore.printAll();
    }

    /**
     * Get bill summary (immutable)
     */
    public BillSummary getBillSummary(int billId) {
        Bill bill = getBillById(billId);
        if (bill != null) {
            return new BillSummary(bill);
        }
        return null;
    }

    /**
     * Get total number of bills
     */
    public int getTotalBills() {
        return dataStore.size();
    }
}

