package no.hib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import no.hib.logic.interfaces.AppointmentService;
import no.hib.models.Appointment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("api/appointments")
public class AppointmentController {


    @Autowired
    AppointmentService appointmentService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	System.out.println("############-----------------------------------------------initBinder (AppointmentController)----------------------#####################");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
    
    static Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getAppointments(){
        List<Appointment> appointments = appointmentService.getAppointments();
        
        if(appointments != null && appointments.size() > 0){
        	System.out.println("appointments.get(0).getAppointmentDate() == " + appointments.get(0).getAppointmentDate());
        	System.out.println("appointments.get(0).getAppointmentTime() == " + appointments.get(0).getAppointmentTime());
        }
        
        
        return new ResponseEntity<>(gson2.toJson(appointments), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{uuid}")
    public ResponseEntity<String> getAppointment(@PathVariable(value = "uuid") String uuid){
        Appointment appointment = appointmentService.getAppointment(uuid);
        return new ResponseEntity<>(gson2.toJson(appointment), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{patientSsn}/all")
    public ResponseEntity<String> getAppointmentsForPatient(@PathVariable(value = "patientSsn") String patientSsn){
        List<Appointment> appointments = appointmentService.getAppointmentsForPatient(patientSsn);
        return new ResponseEntity<>(gson2.toJson(appointments), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createAppointment(@RequestBody String jsonString){
        System.out.println("Received data: " + jsonString);
        
        Type type = new TypeToken<Appointment>() {}.getType();
        Appointment appointment = new Gson().fromJson(jsonString, type);
        System.out.println("parsing via Gson successfull...");
        Appointment created = appointmentService.createAppointment(appointment);
        String str = new Gson().toJson(created);
        System.out.println("Sending data: " + str);
        return new ResponseEntity<>(str, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{uuid}")
    public ResponseEntity<String> updateAppointment(@PathVariable(value = "uuid") String uuid,
                                                         @RequestBody String jsonString){
    	
    	Type type = new TypeToken<Appointment>() {}.getType();
        Appointment appointment = new Gson().fromJson(jsonString, type);
        
        if(!uuid.equals(appointment.getUuid())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Appointment updated = appointmentService.updateAppointment(appointment);
        return new ResponseEntity<>(gson2.toJson(updated), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{uuid}")
    public ResponseEntity deleteAppointment(@PathVariable(value = "uuid") String uuid){
        appointmentService.deleteAppointment(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
