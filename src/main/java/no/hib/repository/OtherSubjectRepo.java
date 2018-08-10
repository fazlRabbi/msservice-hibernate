package no.hib.repository;


import no.hib.models.OtherSubject;
import no.hib.models.Symptom;
import no.hib.utils.SearchStringGenerator;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OtherSubjectRepo extends CrudRepository<OtherSubject, Integer>{
	
	
	
	@Query("SELECT t FROM OtherSubject t where t.name != null")
    public List<OtherSubject> getSubjects(); 
    
	
	@Query("SELECT t FROM OtherSubject t where t.uuid = :uuid")
    public List<OtherSubject> getSubjectByUuid(@Param("uuid") String uuid);

}
