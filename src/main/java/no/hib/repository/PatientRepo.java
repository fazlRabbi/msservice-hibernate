package no.hib.repository;


import no.hib.models.Patient;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PatientRepo extends CrudRepository<Patient, Integer>{
	
	
	
	@Query("SELECT t FROM Patient t where t.ssn = :ssn")
    public List<Patient> getPatient(@Param("ssn") String ssn); 
    
	@Query("SELECT t FROM Patient t")
    public List<Patient> getPatients(); 
    

}
