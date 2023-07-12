package com.eps.epsspringrestapi.patients.application;
import com.eps.epsspringrestapi.patients.domain.Patient;
import com.eps.epsspringrestapi.patients.domain.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PatientService {
    HashMap<String, Object> data;
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatients(){
        return patientRepository.findAll();
    }

    public ResponseEntity<Object> createPatient(Patient patient){
        data = new HashMap<>();
        // Check if cedula or email are already in use
        Optional<Patient> res = patientRepository.findPatientByCedula(patient.getCedula());
        if(res.isPresent()){
            data.put("error", true);
            data.put("message", "Cedula already in use");
            return new ResponseEntity<>(
                data,
                HttpStatus.BAD_REQUEST
            );
        }

        res = patientRepository.findPatientByEmail(patient.getEmail());
        if(res.isPresent()){
            data.put("error", true);
            data.put("message", "Email already in use");
            return new ResponseEntity<>(
                data,
                HttpStatus.BAD_REQUEST
            );
        }
        // Save patient
        patientRepository.save(patient);
        data.put("data", patient);
        data.put("message", "Patient created");
        return new ResponseEntity<>(
            data,
            HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updatePatient(Patient patient, Long patientId){
        data = new HashMap<>();
        // Check if patient exists
        boolean patientExists = patientRepository.existsById(patientId);
        if(!patientExists){
            data.put("error", true);
            data.put("message", "Patient not found");
            return new ResponseEntity<>(
                data,
                HttpStatus.BAD_REQUEST
            );
        }
        // Check if cedula or email are already in use
        Optional<Patient> res = patientRepository.findPatientByCedula(patient.getCedula());
        if(res.isPresent() && !res.get().getId().equals(patientId)){
            data.put("error", true);
            data.put("message", "Cedula already in use");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.BAD_REQUEST
            );
        }

        res = patientRepository.findPatientByEmail(patient.getEmail());
        if(res.isPresent() && !res.get().getId().equals(patientId)){
            data.put("error", true);
            data.put("message", "Email already in use");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.BAD_REQUEST
            );
        }
        // Save patient
        patientRepository.save(patient);
        data.put("data", patient);
        data.put("message", "Patient created");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> deletePatient(Long patientId){
        data = new HashMap<>();
        boolean res = patientRepository.existsById(patientId);
        if(!res){
            data.put("error", true);
            data.put("message", "Patient not found");
            return new ResponseEntity<>(
                data,
                HttpStatus.BAD_REQUEST
            );
        }
        patientRepository.deleteById(patientId);
        data.put("message", "Patient deleted");
        return new ResponseEntity<>(
            data,
            HttpStatus.ACCEPTED
        );
    }

}
