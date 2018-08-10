package no.hib.repository;


import no.hib.models.Doctor;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DoctorRepo extends CrudRepository<Doctor, Integer>{
	
	
	
	@Query("SELECT t FROM Doctor t where t.ssn = :ssn")
    public List<Doctor> getDoctor(@Param("ssn") String ssn); 
    
	@Query("SELECT t FROM Doctor t where t.specialization != null")
    public List<Doctor> getDoctors(); 
    

}
