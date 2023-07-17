package com.eps.epsspringrestapi.doctors.infraestructure;
import com.eps.epsspringrestapi.doctors.application.DoctorService;
import com.eps.epsspringrestapi.doctors.domain.Doctor;
import com.eps.epsspringrestapi.utils.ResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path="api/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public Page<Doctor> getDoctors(Pageable pageable){
        return doctorService.getDoctors(pageable);
    }

    @GetMapping(path = "all")
    public List<Doctor> getAllDoctors(@RequestParam(required = false) Integer specialty) {
        if(specialty != null){
            return doctorService.getDoctorsBySpecialty(specialty);
        }
        return doctorService.getAllDoctors();
    }


    @PostMapping
    public ResponseEntity<Object> postDoctor(@Valid @RequestBody Doctor doctor, BindingResult bindingResult){
        // Validate request body
        if(bindingResult.hasErrors()){
            return new ResponseBuilder(
                    true,
                    Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(),
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        return doctorService.createDoctor(doctor);
    }

    @PutMapping(path="{doctorId}")
    public ResponseEntity<Object> patchDoctor(@PathVariable("doctorId") Long doctorId, @Valid @RequestBody Doctor doctor, BindingResult bindingResult){
        // Validate request body
        if(bindingResult.hasErrors()){
            return new ResponseBuilder(
                    true,
                    Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(),
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        return doctorService.updateDoctor(doctor, doctorId);
    }

    @DeleteMapping(path="{doctorId}")
    public ResponseEntity<Object> deleteDoctor(@PathVariable("doctorId") Long doctorId){
        return doctorService.deleteDoctor(doctorId);
    }
}
