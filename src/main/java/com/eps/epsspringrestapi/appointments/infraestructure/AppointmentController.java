package com.eps.epsspringrestapi.appointments.infraestructure;

import com.eps.epsspringrestapi.appointments.application.AppointmentService;
import com.eps.epsspringrestapi.appointments.domain.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public Page<Appointment> getAppointments(Pageable pageable){
        return appointmentService.getAppointments(pageable);
    }

    @GetMapping(params = "specialty")
    public Object filterAppointmentsBySpecialty(@RequestParam Integer specialty, Pageable pageable){
        return appointmentService.filterAppointmentsBySpecialty(specialty, pageable);
    }

    @GetMapping(path="doctor/{cedula}")
    public Object getAppointmentsByDoctorCedula(Pageable pageable, @PathVariable("cedula") Long cedula){
        return appointmentService.getAppointmentsByDoctorCedula(pageable, cedula);
    }

    @GetMapping(path="patient/{cedula}")
    public Object getAppointmentsByPatientCedula(Pageable pageable, @PathVariable("cedula") Long cedula){
        return appointmentService.getAppointmentsByPatientCedula(pageable, cedula);
    }

    @PostMapping
    public ResponseEntity<Object> createAppointment(@RequestBody Appointment appointment){
        return appointmentService.createAppointment(appointment);
    }

    @PutMapping(path="{appointmentId}")
    public Object updateAppointment(@RequestBody Appointment appointment, @PathVariable("appointmentId") Long appointmentId){
        return appointmentService.updateAppointment(appointment, appointmentId);
    }

    @DeleteMapping(path="{appointmentId}")
    public ResponseEntity<Object> deleteAppointment(@PathVariable("appointmentId") Long appointmentId){
        return appointmentService.deleteAppointment(appointmentId);
    }
}
