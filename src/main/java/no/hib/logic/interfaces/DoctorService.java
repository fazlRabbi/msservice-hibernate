package no.hib.logic.interfaces;

import no.hib.models.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor getDoctor(String ssn);
    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(Doctor doctor);
    List<Doctor> getDoctors();
    void deleteDoctor(String ssn);
}
