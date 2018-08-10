package no.hib.repository;


import no.hib.models.AdminUser;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AdminUserRepo extends CrudRepository<AdminUser, Integer>{
	
	@Query("SELECT t FROM AdminUser t where t.email = :email")
	public List<AdminUser> getAdminUser(@Param("email") String email); 


}
