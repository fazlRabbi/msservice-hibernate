package no.hib.repository;



import no.hib.models.BankIdUser;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BankIdUserRepo extends CrudRepository<BankIdUser, Integer>{
	
	
	@Query("SELECT t FROM BankIdUser t where t.ssn = :ssn")
	public List<BankIdUser> getBankIdUser(@Param("ssn") String ssn);
}
