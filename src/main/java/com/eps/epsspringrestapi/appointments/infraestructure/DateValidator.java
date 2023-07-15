package com.eps.epsspringrestapi.appointments.infraestructure;

import java.time.LocalDateTime;

public class DateValidator {
    public static boolean isDateInFuture(LocalDateTime dateTime){
        return dateTime.isAfter(LocalDateTime.now());
    }

    public static boolean isTimeIn30MinutesInterval(LocalDateTime dateTime){
        return dateTime.getMinute() == 0 || dateTime.getMinute() == 30;
    }

    //Check if date is valid format
    public static boolean isValidFormat(String date) {
        try {
            final LocalDateTime parse = LocalDateTime.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Check if date is in the next 6 months
    public static boolean isInNext6Months(LocalDateTime dateTime){
        return dateTime.isBefore(LocalDateTime.now().plusMonths(6));
    }

}
