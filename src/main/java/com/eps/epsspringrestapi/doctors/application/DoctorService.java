package com.eps.epsspringrestapi.doctors.application;
import com.eps.epsspringrestapi.doctors.domain.Doctor;
import com.eps.epsspringrestapi.doctors.domain.DoctorRepository;
import com.eps.epsspringrestapi.utils.ResponseBuilder;
import com.eps.epsspringrestapi.utils.SpecialtyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    public Page<Doctor> getDoctors(Pageable pageable){
        return doctorRepository.findAll(pageable);
    }

    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

    public List<Doctor> getDoctorsBySpecialty(Integer specialtyId) {
        if (SpecialtyUtil.isValidSpecialty(specialtyId)) {
            String specialty = SpecialtyUtil.getSpecialty(specialtyId);
            return doctorRepository.findDoctorsBySpecialty(specialty);
        } else {
            return new ArrayList<>();
        }
    }

    public ResponseEntity<Object> createDoctor(Doctor doctor){
        // Check if cedula or email are already in use
        Optional<Doctor> res = doctorRepository.findDoctorByCedula(doctor.getCedula());
        if(res.isPresent()){
            return new ResponseBuilder(
                    true,
                    "cedula",
                    "Cedula already in use",
                    HttpStatus.CONFLICT
            ).send();
        }

        res = doctorRepository.findDoctorByEmail(doctor.getEmail());
        if(res.isPresent()){
            return new ResponseBuilder(
                    true,
                    "email",
                    "Email already in use",
                    HttpStatus.CONFLICT
            ).send();
        }
        // Save doctor
        doctorRepository.save(doctor);
        return new ResponseBuilder(
                doctor,
                "Doctor created",
                HttpStatus.CREATED
        ).send();
    }

    public ResponseEntity<Object> updateDoctor(Doctor doctor, Long doctorId){
        // Check if doctor exists
        boolean doctorExists = doctorRepository.existsById(doctorId);
        if(!doctorExists){
            return new ResponseBuilder(
                    true,
                    "doctor",
                    "Doctor not found",
                    HttpStatus.NOT_FOUND
            ).send();
        }
        // Check if cedula or email are already in use
        Optional<Doctor> res = doctorRepository.findDoctorByCedula(doctor.getCedula());
        if(res.isPresent() && !res.get().getId().equals(doctorId)){
            return new ResponseBuilder(
                    true,
                    "cedula",
                    "Cedula already in use",
                    HttpStatus.CONFLICT
            ).send();
        }

        res = doctorRepository.findDoctorByEmail(doctor.getEmail());
        if(res.isPresent() && !res.get().getId().equals(doctorId)){
            return new ResponseBuilder(
                    true,
                    "email",
                    "Email already in use",
                    HttpStatus.CONFLICT
            ).send();
        }
        // Update doctor
        doctor.setId(doctorId);
        doctorRepository.save(doctor);
        return new ResponseBuilder(
                doctor,
                "Doctor updated",
                HttpStatus.ACCEPTED
        ).send();
    }

    public ResponseEntity<Object> deleteDoctor(Long doctorId){
        boolean res = doctorRepository.existsById(doctorId);
        if(!res){
            return new ResponseBuilder(
                    true,
                    "doctor",
                    "Doctor not found",
                    HttpStatus.NOT_FOUND
            ).send();
        }
        doctorRepository.deleteById(doctorId);
        return new ResponseBuilder(
                "Doctor deleted",
                HttpStatus.ACCEPTED
        ).send();
    }

}
