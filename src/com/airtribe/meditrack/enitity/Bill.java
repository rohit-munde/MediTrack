package com.airtribe.meditrack.enitity;

import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.constants.Constants;
import java.time.LocalDateTime;

public class Bill {
    private int id;
    private Appointment appointment;
    private double amount;
    private double taxAmount;
    private double totalAmount;
    private LocalDateTime billDate;

    public Bill() {
    }

    public Bill(Appointment appointment, double amount) {
        this.id = IdGenerator.generateAppointmentId();
        this.appointment = appointment;
        this.amount = amount;
        this.taxAmount = amount * Constants.TAX_RATE;
        this.totalAmount = amount + this.taxAmount;
        this.billDate = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
        this.taxAmount = amount * Constants.TAX_RATE;
        this.totalAmount = amount + this.taxAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", appointment=" + appointment.getId() +
                ", amount=" + amount +
                ", taxAmount=" + taxAmount +
                ", totalAmount=" + totalAmount +
                ", billDate=" + billDate +
                '}';
    }
}

