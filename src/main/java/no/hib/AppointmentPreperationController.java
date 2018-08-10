package no.hib;

import no.hib.logic.interfaces.AppointmentPreperationService;
import no.hib.models.AppointmentPreperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("api/preperation")
public class AppointmentPreperationController {

    @Autowired
    AppointmentPreperationService appointmentPreperationService;

    static Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();
    
    @RequestMapping(method = RequestMethod.GET, value = "/{uuid}", produces = "application/json; charset=utf-8")
    public ResponseEntity<String> getAppointmentPreperation(@PathVariable(value = "uuid") String uuid){
    	System.out.println("Inside getAppointmentPreperation::");
        AppointmentPreperation appointmentPreperation = appointmentPreperationService.getAppointmentPreperation(uuid);
        return new ResponseEntity<>( gson2.toJson(appointmentPreperation), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{patientSsn}/all")
    public ResponseEntity<String> getAppointmentPreperations(@PathVariable(value = "patientSsn") String patientSsn){
        List<AppointmentPreperation> appointmentPreperations = appointmentPreperationService.getAppointmentPreperations(patientSsn);
        return new ResponseEntity<>( gson2.toJson(appointmentPreperations), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{patientSsn}/next")
    public ResponseEntity<String> getNextAppointmentPreperation(@PathVariable(value = "patientSsn") String patientSsn){
        AppointmentPreperation appointmentPreperation = appointmentPreperationService.getNextAppointmentPreperation(patientSsn);
        return new ResponseEntity<>(gson2.toJson(appointmentPreperation), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{uuid}")
    public ResponseEntity<String> updateAppointmentPreperation(@PathVariable(value = "uuid") String uuid,
                                                         @RequestBody String jsonString){
    	
    	Type type = new TypeToken<AppointmentPreperation>() {}.getType();
    	AppointmentPreperation appointmentPreperation = new Gson().fromJson(jsonString, type);
    	System.out.println("Inside updateAppointmentPreperation :: ");
        if(!uuid.equals(appointmentPreperation.getUuid())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AppointmentPreperation updated = appointmentPreperationService.updateAppointmentPreperation(appointmentPreperation);
        return new ResponseEntity<>(gson2.toJson(updated), HttpStatus.OK);
    }
}
