package no.hib.logic;

import no.hib.logic.interfaces.AppointmentService;
import no.hib.models.Appointment;
import no.hib.models.AppointmentPreperation;
import no.hib.repository.AppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("appointmentService")
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepo appointmentRepo;

    
    


    
    private void updateAppointmentInRepo(Appointment appointment) {
        
        List<Appointment> apps = appointmentRepo.getAppointmentByUuid(appointment.getUuid()); 
        if(apps != null && apps.size() > 0){
        	Appointment old = apps.get(0);
        	old.setAppointmentDate(appointment.getAppointmentDate());
        	old.setAppointmentPreperation(appointment.getAppointmentPreperation());
        	old.setDoctorSsn(appointment.getDoctorSsn());
        	old.setPatientSsn(appointment.getPatientSsn());
        	appointmentRepo.save(old);
        }
        
    }




    private void deleteAppointmentInRepo(String uuid) {
        List<Appointment> apps = appointmentRepo.getAppointmentByUuid(uuid);
        if(apps != null && apps.size() > 0){
        	appointmentRepo.delete(apps.get(0));
        }
    }

    private AppointmentPreperation getAppointmentPreperationFromAppointmentUuid(String uuid) {                
        List<Appointment> appointments = appointmentRepo.getAppointmentByPrepAppUuid(uuid); 
        AppointmentPreperation appointmentPreperation = appointments.get(0).getAppointmentPreperation();
        return appointmentPreperation;
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        String uuid = UUID.randomUUID().toString();
        appointment.setUuid(uuid);
        //appointment.set_id(uuid);

        AppointmentPreperation appointmentPreperation = new AppointmentPreperation(appointment.getUuid(),
                appointment.getAppointmentDate(), appointment.getAppointmentTime());
        String newUuid = UUID.randomUUID().toString();
        appointmentPreperation.setUuid(newUuid);
        appointment.setAppointmentPreperation(appointmentPreperation);
        appointmentRepo.save(appointment);
        return appointment;
    }

    @Override
    public List<Appointment> getAppointments() {
        List<Appointment> appointments = appointmentRepo.getAppointments();
        return appointments;
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        AppointmentPreperation appointmentPreperation = getAppointmentPreperationFromAppointmentUuid(appointment.getUuid());
        appointmentPreperation.setAppointmentDate(appointment.getAppointmentDate());
        appointmentPreperation.setAppointmentTime(appointment.getAppointmentTime());
        appointment.setAppointmentPreperation(appointmentPreperation);
        updateAppointmentInRepo(appointment);
        return appointment;
    }

    @Override
    public Appointment getAppointment(String uuid) {
        List<Appointment> appointments = appointmentRepo.getAppointmentByUuid(uuid);
        if(appointments != null && appointments.size() > 0)
        	return appointments.get(0);
        else 
        	return null;
        
    }

    @Override
    public void deleteAppointment(String uuid) {
    	deleteAppointmentInRepo(uuid);
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(String patientSsn) {
        List<Appointment> appointments = appointmentRepo.getAppointmentsForPatient(patientSsn);
        return appointments;
    }
}
