package com.airtribe.meditrack.enitity;

import java.time.LocalDateTime;

/**
 * Immutable BillSummary class - thread-safe and cannot be modified after creation
 */
public final class BillSummary {
    private final int billId;
    private final String patientName;
    private final String doctorName;
    private final double amount;
    private final double taxAmount;
    private final double totalAmount;
    private final LocalDateTime billDate;

    public BillSummary(Bill bill) {
        this.billId = bill.getId();
        this.patientName = bill.getAppointment().getPatient().getName();
        this.doctorName = bill.getAppointment().getDoctor().getName();
        this.amount = bill.getAmount();
        this.taxAmount = bill.getTaxAmount();
        this.totalAmount = bill.getTotalAmount();
        this.billDate = bill.getBillDate();
    }

    public int getBillId() {
        return billId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public double getAmount() {
        return amount;
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

    @Override
    public String toString() {
        return "BillSummary{" +
                "billId=" + billId +
                ", patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", amount=" + amount +
                ", taxAmount=" + taxAmount +
                ", totalAmount=" + totalAmount +
                ", billDate=" + billDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillSummary that = (BillSummary) o;

        if (billId != that.billId) return false;
        if (Double.compare(that.amount, amount) != 0) return false;
        if (Double.compare(that.taxAmount, taxAmount) != 0) return false;
        if (Double.compare(that.totalAmount, totalAmount) != 0) return false;
        if (!patientName.equals(that.patientName)) return false;
        if (!doctorName.equals(that.doctorName)) return false;
        return billDate.equals(that.billDate);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = billId;
        result = 31 * result + patientName.hashCode();
        result = 31 * result + doctorName.hashCode();
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(taxAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + billDate.hashCode();
        return result;
    }
}

