package com.airtribe.meditrack.util;

import com.airtribe.meditrack.enitity.Doctor;
import com.airtribe.meditrack.enitity.Specialization;
import com.airtribe.meditrack.service.DoctorService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AI Helper for intelligent doctor recommendations and appointment suggestions
 */
public class AIHelper {
    private final DoctorService doctorService;

    // Symptom to specialization mapping
    private static final Map<String, Specialization> SYMPTOM_SPECIALIZATION_MAP = new HashMap<>();

    static {
        // Cardiovascular symptoms -> Cardiologist
        SYMPTOM_SPECIALIZATION_MAP.put("chest pain", Specialization.CARDIOLOGIST);
        SYMPTOM_SPECIALIZATION_MAP.put("heart", Specialization.CARDIOLOGIST);
        SYMPTOM_SPECIALIZATION_MAP.put("palpitation", Specialization.CARDIOLOGIST);
        SYMPTOM_SPECIALIZATION_MAP.put("blood pressure", Specialization.CARDIOLOGIST);

        // Skin issues -> Dermatologist
        SYMPTOM_SPECIALIZATION_MAP.put("skin", Specialization.DERMATOLOGIST);
        SYMPTOM_SPECIALIZATION_MAP.put("rash", Specialization.DERMATOLOGIST);
        SYMPTOM_SPECIALIZATION_MAP.put("acne", Specialization.DERMATOLOGIST);
        SYMPTOM_SPECIALIZATION_MAP.put("itch", Specialization.DERMATOLOGIST);

        // Brain/Nerve issues -> Neurologist
        SYMPTOM_SPECIALIZATION_MAP.put("headache", Specialization.NEUROLOGIST);
        SYMPTOM_SPECIALIZATION_MAP.put("migraine", Specialization.NEUROLOGIST);
        SYMPTOM_SPECIALIZATION_MAP.put("seizure", Specialization.NEUROLOGIST);
        SYMPTOM_SPECIALIZATION_MAP.put("dizziness", Specialization.NEUROLOGIST);

        // Children -> Pediatrician
        SYMPTOM_SPECIALIZATION_MAP.put("child", Specialization.PEDIATRICIAN);
        SYMPTOM_SPECIALIZATION_MAP.put("baby", Specialization.PEDIATRICIAN);

        // Mental health -> Psychiatrist
        SYMPTOM_SPECIALIZATION_MAP.put("depression", Specialization.PSYCHIATRIST);
        SYMPTOM_SPECIALIZATION_MAP.put("anxiety", Specialization.PSYCHIATRIST);
        SYMPTOM_SPECIALIZATION_MAP.put("stress", Specialization.PSYCHIATRIST);

        // Imaging -> Radiologist
        SYMPTOM_SPECIALIZATION_MAP.put("xray", Specialization.RADIOLOGIST);
        SYMPTOM_SPECIALIZATION_MAP.put("scan", Specialization.RADIOLOGIST);

        // Surgical -> Surgeon
        SYMPTOM_SPECIALIZATION_MAP.put("surgery", Specialization.SURGEON);
        SYMPTOM_SPECIALIZATION_MAP.put("fracture", Specialization.SURGEON);
    }

    public AIHelper(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Recommend doctors based on patient symptoms
     */
    public List<Doctor> recommendDoctorsBySymptoms(List<String> symptoms) {
        // Get the most relevant specialization from symptoms
        Specialization relevantSpecialization = findRelevantSpecialization(symptoms);

        if (relevantSpecialization != null) {
            List<Doctor> recommendedDoctors = doctorService.searchDoctorBySpecialization(relevantSpecialization);
            if (!recommendedDoctors.isEmpty()) {
                System.out.println("✓ Found " + recommendedDoctors.size() + " " + relevantSpecialization + " specialist(s)");
                return recommendedDoctors;
            }
        }

        // Fallback: return general practitioners
        List<Doctor> generalDoctors = doctorService.searchDoctorBySpecialization(Specialization.GENERAL);
        System.out.println("✓ Recommending General Practitioners");
        return generalDoctors;
    }

    /**
     * Find the most relevant specialization from symptoms
     */
    private Specialization findRelevantSpecialization(List<String> symptoms) {
        for (String symptom : symptoms) {
            String lowerSymptom = symptom.toLowerCase().trim();
            for (Map.Entry<String, Specialization> entry : SYMPTOM_SPECIALIZATION_MAP.entrySet()) {
                if (lowerSymptom.contains(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Get available appointment time slots
     */
    public List<String> suggestAppointmentSlots() {
        return List.of(
                "09:00 AM - 09:30 AM",
                "10:00 AM - 10:30 AM",
                "11:00 AM - 11:30 AM",
                "02:00 PM - 02:30 PM",
                "03:00 PM - 03:30 PM",
                "04:00 PM - 04:30 PM"
        );
    }

    /**
     * Get doctors sorted by average rating (fee-based proxy)
     */
    public List<Doctor> getTopDoctors(int limit) {
        return doctorService.getAllDoctors().stream()
                .sorted((d1, d2) -> Double.compare(d1.getFees(), d2.getFees()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Get doctors with lowest fees
     */
    public List<Doctor> getAffordableDoctors() {
        double averageFee = doctorService.getAverageFees();
        return doctorService.getAllDoctors().stream()
                .filter(doctor -> doctor.getFees() <= averageFee)
                .sorted((d1, d2) -> Double.compare(d1.getFees(), d2.getFees()))
                .collect(Collectors.toList());
    }
}

