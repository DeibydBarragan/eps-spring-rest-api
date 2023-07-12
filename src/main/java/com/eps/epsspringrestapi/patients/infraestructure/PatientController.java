package com.eps.epsspringrestapi.patients.infraestructure;

import com.eps.epsspringrestapi.patients.application.PatientService;
import com.eps.epsspringrestapi.patients.domain.Patient;
import com.eps.epsspringrestapi.utils.ResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(path="api/patients")
public class PatientController {
    HashMap<String, Object> data;
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getPatients(){
        return patientService.getPatients();
    }

    @PostMapping
    public ResponseEntity<Object> postPatient(@Valid @RequestBody Patient patient, BindingResult bindingResult){
        // Validate request body
        if(bindingResult.hasErrors()){
            return new ResponseBuilder(
                true,
                Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        return patientService.createPatient(patient);
    }

    @PatchMapping(path="{patientId}")
    public ResponseEntity<Object> patchPatient(@PathVariable("patientId") Long patientId, @Valid @RequestBody Patient patient, BindingResult bindingResult){
        // Validate request body
        if(bindingResult.hasErrors()){
            return new ResponseBuilder(
                true,
                Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        return patientService.updatePatient(patient, patientId);
    }

    @DeleteMapping(path="{patientId}")
    public ResponseEntity<Object> deletePatient(@PathVariable("patientId") Long patientId){
        return patientService.deletePatient(patientId);
    }
}
