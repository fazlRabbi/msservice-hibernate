package no.hib.repository;


import no.hib.models.AdminUser;
import no.hib.models.BankIdUser;
import no.hib.models.Settings;
import no.hib.utils.SearchStringGenerator;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SettingsRepo extends CrudRepository<Settings, Integer>{
	
	
	
	@Query("SELECT t FROM Settings t where t.appointmentPreperationMinStart != null")
    public List<Settings> getSettings(); 
    
   
}
