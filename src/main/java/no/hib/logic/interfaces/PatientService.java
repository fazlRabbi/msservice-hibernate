package no.hib.logic.interfaces;

import no.hib.models.Patient;

import java.util.List;

public interface PatientService {

    boolean validPatient(Patient patient);
    Patient getPatient(String ssn);
    Patient createPatient(Patient patient);
    Patient updatePatient(Patient patient);
    List<Patient> getPatients();
    void deletePatient(String ssn);
}
