package no.hib.logic.interfaces;

import no.hib.models.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment createAppointment(Appointment appointment);
    List<Appointment> getAppointments();
    Appointment updateAppointment(Appointment appointment);
    Appointment getAppointment(String uuid);
    void deleteAppointment(String uuid);
    List<Appointment> getAppointmentsForPatient(String patientSsn);
}
