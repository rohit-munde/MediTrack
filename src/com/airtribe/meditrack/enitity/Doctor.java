package com.airtribe.meditrack.enitity;

import com.airtribe.meditrack.util.IdGenerator;

public class Doctor extends Person {
    private Specialization specialization;
    private double fees;

    public Doctor() {
    }

    public Doctor(String name, int age, String email, String contactNo, Specialization specialization, double fees) {
        super(IdGenerator.generateDoctorId(), name, age, email, contactNo);
        this.specialization = specialization;
        this.fees = fees;
    }

    public Doctor(int id, String name, int age, String email, String contactNo, Specialization specialization, double fees) {
        super(id, name, age, email, contactNo);
        this.specialization = specialization;
        this.fees = fees;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", email='" + getEmail() + '\'' +
                ", contactNo='" + getContactNo() + '\'' +
                ", specialization=" + specialization +
                ", fees=" + fees +
                '}';
    }
}
