package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.exception.InvalidDataException;

public class Validator {

    public static void validateAge(int age) throws InvalidDataException {
        if (age < Constants.MIN_AGE || age > Constants.MAX_AGE) {
            throw new InvalidDataException("Age must be between " + Constants.MIN_AGE + " and " + Constants.MAX_AGE);
        }
    }

    public static void validateEmail(String email) throws InvalidDataException {
        if (email == null || !email.contains("@")) {
            throw new InvalidDataException("Invalid email format");
        }
    }

    public static void validateContactNo(String contactNo) throws InvalidDataException {
        if (contactNo == null || contactNo.length() < 10) {
            throw new InvalidDataException("Invalid contact number");
        }
    }

    public static void validateFees(double fees) throws InvalidDataException {
        if (fees < Constants.MIN_FEE || fees > Constants.MAX_FEE) {
            throw new InvalidDataException("Fees must be between " + Constants.MIN_FEE + " and " + Constants.MAX_FEE);
        }
    }

    public static void validateName(String name) throws InvalidDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Name cannot be empty");
        }
    }

    public static void validateString(String value, String fieldName) throws InvalidDataException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidDataException(fieldName + " cannot be empty");
        }
    }
}

