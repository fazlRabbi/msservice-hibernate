package no.hib.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Settings {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer _id;
	@Version
    private Integer _rev;
    private String uuid;
    private int appointmentPreperationMinStart;
    
    @Column
    @ElementCollection(targetClass=Symptom.class)
    private List<Symptom> defaultSymptoms;
    
    @Column
    @ElementCollection(targetClass=OtherSubject.class)
    private List<OtherSubject> defaultOtherSubjects;

    public Settings(){
    	
    }
    public Settings(int appointmentPreperationMinStart, List<Symptom> defaultSymptoms, List<OtherSubject> defaultOtherSubjects) {
        this.appointmentPreperationMinStart = appointmentPreperationMinStart;
        this.defaultSymptoms = defaultSymptoms;
        this.defaultOtherSubjects = defaultOtherSubjects;
    }

    public int getAppointmentPreperationMinStart() {
        return appointmentPreperationMinStart;
    }

    public void setAppointmentPreperationMinStart(int appointmentPreperationMinStart) {
        this.appointmentPreperationMinStart = appointmentPreperationMinStart;
    }

    public List<Symptom> getDefaultSymptoms() {
        return defaultSymptoms;
    }

    public void setDefaultSymptoms(List<Symptom> defaultSymptoms) {
        this.defaultSymptoms = defaultSymptoms;
    }

    public List<OtherSubject> getDefaultOtherSubjects() {
        return defaultOtherSubjects;
    }

    public void setDefaultOtherSubjects(List<OtherSubject> defaultOtherSubjects) {
        this.defaultOtherSubjects = defaultOtherSubjects;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer get_rev() {
        return _rev;
    }

    public void set_rev(Integer _rev) {
        this._rev = _rev;
    }
}
