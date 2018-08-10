package no.hib.logic;

import no.hib.logic.interfaces.LoginService;
import no.hib.models.AdminUser;
import no.hib.models.BankIdUser;
import no.hib.repository.AdminUserRepo;
import no.hib.repository.BankIdUserRepo;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService {

    
    
    @Autowired
    BankIdUserRepo bankIdUserRepository;
    
    @Autowired
    AdminUserRepo adminUserRepository;
    
    

    @Override
    public boolean validUser(BankIdUser bankIdUser) {
    	BankIdUser savedUser = null;
    	List<BankIdUser> savedUsers = bankIdUserRepository.getBankIdUser(bankIdUser.getSsn());
    	
    	if(savedUsers != null && savedUsers.size() > 0)
    		savedUser = savedUsers.get(0);
    	
        // old code :: BankIdUser savedUser = userRepository.getBankIdUser(bankIdUser.getSsn());
        if (savedUser != null && savedUser.getPassword().equals(bankIdUser.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validUser(AdminUser adminUser) {
    	
    	AdminUser savedUser = null;
    	List<AdminUser> adminUsers = adminUserRepository.getAdminUser(adminUser.getEmail());
    	
    	if(adminUsers != null && adminUsers.size() > 0)
        savedUser = adminUsers.get(0);
        
        if (savedUser != null && savedUser.getPassword().equals(adminUser.getPassword())) {
            return true;
        }
        return false; 
    	
    }
}
