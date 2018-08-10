package no.hib.models;



import java.time.LocalDate;
import java.time.LocalTime;


import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import no.hib.utils.LocalDateAttributeConverter;
import no.hib.utils.LocalDateTimeAttributeConverter;




@Entity
@Table(name = "appointment")
public class Appointment {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer _id;
	
	@Version
    private Integer _rev;
    private String uuid;

    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate appointmentDate;
    
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalTime appointmentTime;
    
    @OneToOne(targetEntity=AppointmentPreperation.class,  fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private AppointmentPreperation appointmentPreperation;
    private String patientSsn;
    private String doctorSsn;

    public Appointment(){
    	
    }
    public Appointment(LocalDate appointmentDate, LocalTime appointmentTime, AppointmentPreperation appointmentPreperation, String patientSsn, String doctorSsn) {
        this.appointmentTime = appointmentTime;
        this.appointmentDate = appointmentDate;
        this.appointmentPreperation = appointmentPreperation;
        this.patientSsn = patientSsn;
        this.doctorSsn = doctorSsn;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    //@OneToOne(cascade = CascadeType.ALL)
    public AppointmentPreperation getAppointmentPreperation() {
        return appointmentPreperation;
    }

    public void setAppointmentPreperation(AppointmentPreperation appointmentPreperation) {
        this.appointmentPreperation = appointmentPreperation;
    }

    public String getPatientSsn() {
        return patientSsn;
    }

    public void setPatientSsn(String patientSsn) {
        this.patientSsn = patientSsn;
    }

    public String getDoctorSsn() {
        return doctorSsn;
    }

    public void setDoctorSsn(String doctorSsn) {
        this.doctorSsn = doctorSsn;
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
