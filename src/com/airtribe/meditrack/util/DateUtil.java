package com.airtribe.meditrack.util;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);

    /**
     * Parse a string to LocalDateTime
     * @param dateTimeString Format: yyyy-MM-dd HH:mm:ss
     * @return LocalDateTime object
     * @throws DateTimeParseException if format is invalid
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid datetime format. Expected: " + DATE_TIME_FORMAT, dateTimeString, e.getErrorIndex());
        }
    }

    /**
     * Parse a string to LocalDate
     * @param dateString Format: yyyy-MM-dd
     * @return LocalDate object
     */
    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date format. Expected: " + DATE_FORMAT, dateString, e.getErrorIndex());
        }
    }

    /**
     * Parse a string to LocalTime
     * @param timeString Format: HH:mm:ss
     * @return LocalTime object
     */
    public static LocalTime parseTime(String timeString) {
        try {
            return LocalTime.parse(timeString, timeFormatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid time format. Expected: " + TIME_FORMAT, timeString, e.getErrorIndex());
        }
    }

    /**
     * Format LocalDateTime to string
     * @param dateTime LocalDateTime object
     * @return Formatted string (yyyy-MM-dd HH:mm:ss)
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(dateTimeFormatter);
    }

    /**
     * Format LocalDate to string
     * @param date LocalDate object
     * @return Formatted string (yyyy-MM-dd)
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(dateFormatter);
    }

    /**
     * Format LocalTime to string
     * @param time LocalTime object
     * @return Formatted string (HH:mm:ss)
     */
    public static String formatTime(LocalTime time) {
        if (time == null) {
            return null;
        }
        return time.format(timeFormatter);
    }

    /**
     * Check if a datetime is in the future
     */
    public static boolean isFuture(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }

    /**
     * Check if a datetime is in the past
     */
    public static boolean isPast(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }

    /**
     * Check if a datetime is today
     */
    public static boolean isToday(LocalDateTime dateTime) {
        return dateTime.toLocalDate().equals(LocalDate.now());
    }

    /**
     * Get current datetime
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    /**
     * Add hours to a datetime
     */
    public static LocalDateTime addHours(LocalDateTime dateTime, int hours) {
        return dateTime.plusHours(hours);
    }

    /**
     * Add days to a date
     */
    public static LocalDate addDays(LocalDate date, int days) {
        return date.plusDays(days);
    }
}

