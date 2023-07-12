package com.eps.epsspringrestapi.utils;

import java.util.Arrays;
import java.util.List;

public class SpecialtyUtil {
    public static final List<String> SPECIALTIES = Arrays.asList(
        "Medicina general",
        "Cardiología",
        "Medicina interna",
        "Dermatología",
        "Rehabilitación física",
        "Psicología",
        "Odontología",
        "Radiología"
    );

    public static String getSpecialty(Integer index){
        return SPECIALTIES.get(index);
    }

    public static Integer getSpecialtyIndex(String specialty){
        return SPECIALTIES.indexOf(specialty);
    }

    public static boolean isValidSpecialty(Integer index){
        return index >= 0 && index < SPECIALTIES.size();
    }

    public static boolean isValidSpecialty(String specialty){
        return SPECIALTIES.contains(specialty);
    }
}
