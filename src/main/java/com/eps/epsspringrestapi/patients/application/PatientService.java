package com.eps.epsspringrestapi.patients.application;
import com.eps.epsspringrestapi.patients.domain.Patient;
import com.eps.epsspringrestapi.patients.domain.PatientRepository;
import com.eps.epsspringrestapi.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public Page<Patient> getPatients(Pageable pageable){
        return patientRepository.findAll(pageable);
    }

    public ResponseEntity<Object> createPatient(Patient patient){
        // Check if cedula or email are already in use
        Optional<Patient> res = patientRepository.findPatientByCedula(patient.getCedula());
        if(res.isPresent()){
            return new ResponseBuilder(
                true,
                "cedula",
                "Cedula already in use",
                HttpStatus.CONFLICT
            ).send();
        }

        res = patientRepository.findPatientByEmail(patient.getEmail());
        if(res.isPresent()){
            return new ResponseBuilder(
                true,
                "email",
                "Email already in use",
                HttpStatus.CONFLICT
            ).send();
        }
        // Save patient
        patientRepository.save(patient);
        return new ResponseBuilder(
            patient,
            "Patient created",
            HttpStatus.CREATED
        ).send();
    }

    public ResponseEntity<Object> updatePatient(Patient patient, Long patientId){
        // Check if patient exists
        boolean patientExists = patientRepository.existsById(patientId);
        if(!patientExists){
            return new ResponseBuilder(
                true,
                "patient",
                "Patient not found",
                HttpStatus.NOT_FOUND
            ).send();
        }
        // Check if cedula or email are already in use
        Optional<Patient> res = patientRepository.findPatientByCedula(patient.getCedula());
        if(res.isPresent() && !res.get().getId().equals(patientId)){
            return new ResponseBuilder(
                true,
                "cedula",
                "Cedula already in use",
                HttpStatus.CONFLICT
            ).send();
        }

        res = patientRepository.findPatientByEmail(patient.getEmail());
        if(res.isPresent() && !res.get().getId().equals(patientId)){
            return new ResponseBuilder(
                true,
                "email",
                "Email already in use",
                HttpStatus.CONFLICT
            ).send();
        }
        // Update patient
        patient.setId(patientId);
        patientRepository.save(patient);
        return new ResponseBuilder(
            patient,
            "Patient updated",
            HttpStatus.ACCEPTED
        ).send();
    }

    public ResponseEntity<Object> deletePatient(Long patientId){
        boolean res = patientRepository.existsById(patientId);
        if(!res){
            return new ResponseBuilder(
                true,
                "patient",
                "Patient not found",
                HttpStatus.NOT_FOUND
            ).send();
        }
        patientRepository.deleteById(patientId);
        return new ResponseBuilder(
            "Patient deleted",
            HttpStatus.ACCEPTED
        ).send();
    }

}
