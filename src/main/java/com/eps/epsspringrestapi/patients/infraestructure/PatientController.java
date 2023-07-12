package com.eps.epsspringrestapi.patients.infraestructure;

import com.eps.epsspringrestapi.patients.application.PatientService;
import com.eps.epsspringrestapi.patients.domain.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="api/patients")
public class PatientController {
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
    public ResponseEntity<Object> postPatient(@RequestBody Patient patient){
        return patientService.createPatient(patient);
    }

    @PatchMapping(path="{patientId}")
    public ResponseEntity<Object> patchPatient(@PathVariable("patientId") Long patientId, @RequestBody Patient patient){
        return patientService.updatePatient(patient, patientId);
    }

    @DeleteMapping(path="{patientId}")
    public ResponseEntity<Object> deletePatient(@PathVariable("patientId") Long patientId){
        return patientService.deletePatient(patientId);
    }
}
