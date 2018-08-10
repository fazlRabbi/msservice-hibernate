package no.hib.repository;



import no.hib.models.Appointment;
import no.hib.models.OtherSubject;
import no.hib.utils.SearchStringGenerator;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepo extends CrudRepository<Appointment, Integer>{
	
	
	@Query("SELECT t FROM Appointment t where t.appointmentTime != null and t.appointmentPreperation != null")
	public List<Appointment> getAppointments();
	
	@Query("SELECT t FROM Appointment t where t.uuid = :uuid")
    public List<Appointment> getAppointmentByUuid(@Param("uuid") String uuid);

	@Query("SELECT t FROM Appointment t where t.appointmentPreperation.uuid = :uuid")
    public List<Appointment> getAppointmentByPrepUuid(@Param("uuid") String uuid);
	
	@Query("SELECT t FROM Appointment t where t.appointmentPreperation.appointmentUuid = :uuid")
    public List<Appointment> getAppointmentByPrepAppUuid(@Param("uuid") String uuid);
	
	@Query("SELECT t FROM Appointment t where t.patientSsn = :patientSsn")
    public List<Appointment> getAppointmentsForPatient(@Param("patientSsn") String patientSsn); 
    
	@Query("SELECT t FROM Appointment t where t.patientSsn = :patientSsn and t.appointmentTime != null")
    public List<Appointment> getAppointmentsWithAppointmentTimeForPatient(@Param("patientSsn") String patientSsn); 
    
    
}
