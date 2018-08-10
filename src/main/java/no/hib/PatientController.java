package no.hib;

import no.hib.logic.interfaces.PatientService;
import no.hib.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("api/patients")
public class PatientController {

    @Autowired
    PatientService patientService;

    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	//System.out.println("#####---------------------------------------- inside bindingPreperation ---------------------------########################");
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    } 
    
    
    

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Patient>> getPatients() {
        List<Patient> patients = patientService.getPatients();
        if(patients != null && patients.size() > 0){
        	System.out.println("patients.get(0).getBirthday() :: " + patients.get(0).getBirthday());
        }
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{ssn}")
    public ResponseEntity<Patient> getPatient(@PathVariable(value = "ssn") String ssn) {
        Patient patient = patientService.getPatient(ssn);
        return new ResponseEntity<Patient>(patient, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
    	System.out.println("Inside patientcontroller:: createPatient..");
        Patient created = patientService.createPatient(patient);
        System.out.println("Returned from hibernate...");
        return new ResponseEntity<Patient>(created, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{ssn}")
    public ResponseEntity<Patient> updatePatient(@PathVariable(value = "ssn") String ssn,
                                                 @RequestBody Patient patient) {
        if (!ssn.equals(patient.getSsn())) {
            return new ResponseEntity<Patient>(HttpStatus.BAD_REQUEST);
        }
        Patient updated = patientService.updatePatient(patient);
        return new ResponseEntity<Patient>(updated, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{ssn}")
    public ResponseEntity deletePatient(@PathVariable(value = "ssn") String ssn) {
        patientService.deletePatient(ssn);
        return new ResponseEntity(HttpStatus.OK);
    }
}
