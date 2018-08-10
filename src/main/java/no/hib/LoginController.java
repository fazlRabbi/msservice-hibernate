package no.hib;

import no.hib.logic.interfaces.LoginService;
import no.hib.models.AdminUser;
import no.hib.models.BankIdUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping(value="/api/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody BankIdUser bankIdUser){
    	System.out.println("Inside loginBySsn::");
        boolean validUser = loginService.validUser(bankIdUser);
        System.out.println("validUser :: " + validUser);
        if(validUser) {
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(value="/api/login/loginBySsn", method = RequestMethod.POST)
    public ResponseEntity loginBySsn(@RequestBody BankIdUser bankIdUser){
    	System.out.println("Inside loginBySsn::");
        boolean validUser = loginService.validUser(bankIdUser);
        System.out.println("validUser :: " + validUser);
        if(validUser) {
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value="/api/login/admin", method = RequestMethod.POST)
    public ResponseEntity loginBySsn(@RequestBody AdminUser adminUser){
    	System.out.println("Skipping login for admin");
    	
        boolean validUser = true; //  loginService.validUser(adminUser);
        if(validUser) {
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

}
