package no.hib;

import no.hib.logic.interfaces.SettingsService;
import no.hib.models.OtherSubject;
import no.hib.models.Settings;
import no.hib.models.Symptom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.gson.Gson;

import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("api/settings")
public class SettingsController {

    @Autowired
    SettingsService settingsService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Settings> getSettings(){
        Settings settings = settingsService.getSettings();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<Settings>(settings, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{uuid}")
    public ResponseEntity<Settings> updateSettings(@PathVariable(value = "uuid") String uuid,
                                                   @RequestBody Settings settings){
    	System.out.println("Inside updateSettings ::");
        if(!uuid.equals(settings.getUuid())){
            return new ResponseEntity<Settings>(HttpStatus.BAD_REQUEST);
        }
        Settings updated = settingsService.updateSettings(settings);
        System.out.println(new Gson().toJson(updated));
        
        return new ResponseEntity<Settings>(updated, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/symptoms/{uuid}")
    public ResponseEntity<Symptom> updateSymptoms(@PathVariable(value = "uuid") String uuid,
                               @RequestBody Symptom symptom){
        if(!uuid.equals(symptom.getUuid())){
            return new ResponseEntity<Symptom>(HttpStatus.BAD_REQUEST);
        }
        Symptom updated = settingsService.updateSymptom(symptom);
        return new ResponseEntity<Symptom>(updated, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/othersubjects/{uuid}")
    public ResponseEntity<OtherSubject> updateDefaultSubjects(@PathVariable(value = "uuid") String uuid,
                                      @RequestBody OtherSubject otherSubject){
        if(!uuid.equals(otherSubject.getUuid())){
            return new ResponseEntity<OtherSubject>(HttpStatus.BAD_REQUEST);
        }
        OtherSubject updated = settingsService.updateSubject(otherSubject);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/symptoms")
    public ResponseEntity<List<Symptom>> getDefaultSymptoms(){
        List<Symptom> symptoms = settingsService.getSettings().getDefaultSymptoms();
        return new ResponseEntity<List<Symptom>>(symptoms, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/othersubjects")
    public ResponseEntity<List<OtherSubject>> getDefaultSubjects(){
        List<OtherSubject> otherSubjects = settingsService.getSettings().getDefaultOtherSubjects();
        return new ResponseEntity<List<OtherSubject>>(otherSubjects, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/preperationStart")
    public ResponseEntity<Integer> getPreperationStart(){
    	System.out.println("Inside getPreperationStart ::");
        int preperationStart = settingsService.getSettings().getAppointmentPreperationMinStart();
        System.out.println(preperationStart);
        return new ResponseEntity<Integer>(preperationStart, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/symptoms/{uuid}")
    public void deleteSymptom(@PathVariable(value = "uuid") String uuid){
        settingsService.deleteSymptom(uuid);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/othersubjects/{uuid}")
    public void deleteOtherSubject(@PathVariable(value = "uuid") String uuid){
        settingsService.deleteSubject(uuid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/symptoms")
    public ResponseEntity<Symptom> addSymptom(@RequestBody Symptom symptom){
        Symptom added = settingsService.addSymptom(symptom);
        return new ResponseEntity<Symptom>(added, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/othersubjects")
    public ResponseEntity<OtherSubject> addOtherSubject(@RequestBody OtherSubject otherSubject){
        OtherSubject added = settingsService.addSubject(otherSubject);
        return new ResponseEntity<OtherSubject>(added, HttpStatus.CREATED);
    }
}
