package no.hib.repository;


import no.hib.models.Symptom;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SymptomRepo extends CrudRepository<Symptom, Integer>{
	
	
	
	@Query("SELECT t FROM Symptom t where t.severity != null")
    public List<Symptom> getSymptoms(); 
    
	@Query("SELECT t FROM Symptom t where t.uuid = :uuid")
    public List<Symptom> getSymptomByUuid(@Param("uuid") String uuid);
	
}
