package com.airtribe.meditrack.enitity;

import java.util.List;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.IdGenerator;

public class Patient extends Person {
    private List<String> symptoms;

    public Patient() {}

    public Patient(List<String> symptomps) {
        this.symptoms = symptomps;
    }

    public Patient(String name, int age, String email, String contactNo, List<String> symptoms) {
        super(IdGenerator.generatePatientId(), name, age, email, contactNo);
        this.symptoms = symptoms;
    }

    public Patient(int id, String name, int age, String email, String contactNo, List<String> symptoms) {
        super(id, name, age, email, contactNo);
        this.symptoms = symptoms;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", email='" + getEmail() + '\'' +
                ", contactNo='" + getContactNo() + '\'' +
                ", symptoms=" + symptoms +
                '}';
    }
}
