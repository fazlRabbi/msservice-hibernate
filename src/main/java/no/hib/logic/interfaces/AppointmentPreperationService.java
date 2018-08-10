package no.hib.logic.interfaces;

import no.hib.models.AppointmentPreperation;

import java.util.List;

public interface AppointmentPreperationService {

    AppointmentPreperation getAppointmentPreperation(String uuid);
    AppointmentPreperation updateAppointmentPreperation(AppointmentPreperation appointmentPreperation);
    List<AppointmentPreperation> getAppointmentPreperations(String patientSsn);
    AppointmentPreperation getNextAppointmentPreperation(String patientSsn);
}
