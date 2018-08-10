package no.hib;

import com.google.gson.Gson;
import no.hib.logic.interfaces.DoctorService;
import no.hib.models.Doctor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("api/doctors")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    Logger logger = Logger.getLogger(DoctorController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Doctor>> getDoctors(){
        List<Doctor> doctors = doctorService.getDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{ssn}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable(value = "ssn") String ssn){
        Doctor doctor = doctorService.getDoctor(ssn);
        return new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor){
        logger.debug(new Gson().toJson(doctor));
        Doctor created = doctorService.createDoctor(doctor);
        return new ResponseEntity<Doctor>(created, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{ssn}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable(value = "ssn") String ssn,
                                                 @RequestBody Doctor doctor){
        if(!ssn.equals(doctor.getSsn())){
            return new ResponseEntity<Doctor>(HttpStatus.BAD_REQUEST);
        }
        Doctor updated = doctorService.updateDoctor(doctor);
        return new ResponseEntity<Doctor>(updated, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{ssn}")
    public ResponseEntity deleteDoctor(@PathVariable(value = "ssn") String ssn){
        doctorService.deleteDoctor(ssn);
        return new ResponseEntity(HttpStatus.OK);
    }
}
