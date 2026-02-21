package com.airtribe.meditrack.enitity;

public enum Specialization {
    GENERAL("General Practice"),
    CARDIOLOGIST("Cardiology"),
    DERMATOLOGIST("Dermatology"),
    NEUROLOGIST("Neurology"),
    PEDIATRICIAN("Pediatrics"),
    PSYCHIATRIST("Psychiatry"),
    RADIOLOGIST("Radiology"),
    SURGEON("Surgery");

    private final String displayName;

    Specialization(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

