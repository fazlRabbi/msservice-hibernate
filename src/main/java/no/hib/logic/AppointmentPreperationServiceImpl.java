package no.hib.logic;

import no.hib.logic.interfaces.AppointmentPreperationService;
import no.hib.models.Appointment;
import no.hib.models.AppointmentPreperation;
import no.hib.models.OtherSubject;
import no.hib.models.Symptom;
import no.hib.repository.AppointmentRepo;
import no.hib.repository.OtherSubjectRepo;
import no.hib.repository.SymptomRepo;
import no.hib.utils.SearchStringGenerator;
import no.hib.utils.UtfConverter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("appointmentPreperationService")
@Transactional
public class AppointmentPreperationServiceImpl implements AppointmentPreperationService {


    @Autowired
    AppointmentRepo appointmentRepo;
    
    @Autowired
    SymptomRepo symptomRepo;
    

    @Autowired
    OtherSubjectRepo otherSubjectRepo;
    
    private List<AppointmentPreperation> getAppointmentPreperationsFromRepo(String patientSsn) {
        
        
        List<Appointment> appointments = appointmentRepo.getAppointmentsWithAppointmentTimeForPatient(patientSsn); 
        List<AppointmentPreperation> appointmentPreperations = new ArrayList<>();
        for(Appointment appointment : appointments){
            appointmentPreperations.add(appointment.getAppointmentPreperation());
        }
        return appointmentPreperations;
    }
    
    
    private void updateAppointmentPreperationInRepo(AppointmentPreperation appointmentPreperation) {
        
    	System.out.println(appointmentPreperation.getUuid());
    	System.out.println(appointmentPreperation.getAppointmentUuid());
    	Appointment oldAppointment = null;
    	List<Appointment> apps = appointmentRepo.getAppointmentByUuid(appointmentPreperation.getAppointmentUuid());
    	
    	System.out.println(apps);
    	if(apps != null && apps.size() > 0)
    		oldAppointment = apps.get(0);
    	
    	System.out.println("oldAppointment::");
    	System.out.println(oldAppointment);
        AppointmentPreperation oldPrep = oldAppointment.getAppointmentPreperation();

        if(appointmentPreperation.getNeedForConsultation() != null ){
            oldPrep.setNeedForConsultation(appointmentPreperation.getNeedForConsultation());
        }

        if(appointmentPreperation.getSymptoms() != null){
            oldPrep.setSymptoms(appointmentPreperation.getSymptoms());
        }

        if(appointmentPreperation.getSymptomListUpdated() != null ){
            oldPrep.setSymptomListUpdated(appointmentPreperation.getSymptomListUpdated());
        }

        if(appointmentPreperation.getHasSideEffects() != null ){
            oldPrep.setHasSideEffects(appointmentPreperation.getHasSideEffects());
        }

        if(appointmentPreperation.getNewSideEffectsDegree() != null ){
            oldPrep.setNewSideEffectsDegree(appointmentPreperation.getNewSideEffectsDegree());
        }

        if(appointmentPreperation.getOldSideEffectsDegree() != null ){
            oldPrep.setOldSideEffectsDegree(appointmentPreperation.getOldSideEffectsDegree());
        }

        if(appointmentPreperation.getSideEffectsNote() != null ){
            oldPrep.setSideEffectsNote(appointmentPreperation.getSideEffectsNote());
        }

        if(appointmentPreperation.getSideEffectsUpdated() != null ){
            oldPrep.setSideEffectsUpdated(appointmentPreperation.getSideEffectsUpdated());
        }

        if(appointmentPreperation.getOtherSubjects() != null){
            oldPrep.setOtherSubjects(appointmentPreperation.getOtherSubjects());
        }

        if(appointmentPreperation.getOtherSubjectsNote() != null ){
            oldPrep.setOtherSubjectsNote(appointmentPreperation.getOtherSubjectsNote());
        }

        appointmentRepo.save(oldAppointment);
        
    }
    
    
    private AppointmentPreperation getAppointmentPreperationFromRepo(String uuid) {
        List<Appointment> appointments = appointmentRepo.getAppointmentByPrepUuid(uuid); 
        AppointmentPreperation appointmentPreperation = appointments.get(0).getAppointmentPreperation();
        return appointmentPreperation;
    }
    
    

    
    @Override
    public AppointmentPreperation getAppointmentPreperation(String uuid) {
        AppointmentPreperation appointmentPreperation = getAppointmentPreperationFromRepo(uuid);
        appointmentPreperation = convertToUtf(appointmentPreperation);
        return appointmentPreperation;
    }

    @Override
    public AppointmentPreperation updateAppointmentPreperation(AppointmentPreperation appointmentPreperation) {
        AppointmentPreperation old = getAppointmentPreperation(appointmentPreperation.getUuid());
        old = convertToUtf(old);
        old = replaceUpdatedSymptomsAndAddNewOnes(old, appointmentPreperation);
        old = removeOldSymptoms(old, appointmentPreperation);

        old = replaveUpdatedOthersAndAddNewOnes(old, appointmentPreperation);
        old = removeOldOthers(old, appointmentPreperation);

        /*
        appointmentPreperation.setSymptoms(old.getSymptoms());
        appointmentPreperation.setOtherSubjects(old.getOtherSubjects());
        appointmentPreperation = convertFromUtf(appointmentPreperation);
        */
        
        
        
        
        
        old.setHasSideEffects(appointmentPreperation.getHasSideEffects());
        old.setNeedForConsultation(appointmentPreperation.getNeedForConsultation());
        old.setNewSideEffectsDegree(appointmentPreperation.getNewSideEffectsDegree());
        old.setOldSideEffectsDegree(appointmentPreperation.getOldSideEffectsDegree());
        old.setOtherSubjectsNote(appointmentPreperation.getOtherSubjectsNote());
        old.setSideEffectsNote(appointmentPreperation.getSideEffectsNote());
        old.setSideEffectsUpdated(appointmentPreperation.getSideEffectsUpdated());
        old.setSymptomListUpdated(appointmentPreperation.getSymptomListUpdated());
        old.setSideEffectsAreImportant(appointmentPreperation.isSideEffectsAreImportant());
        
        
        
        updateAppointmentPreperationInRepo(old);
        return old;
    }

    @Override
    public List<AppointmentPreperation> getAppointmentPreperations(String patientSsn) {
        List<AppointmentPreperation> appointmentPreperations = getAppointmentPreperationsFromRepo(patientSsn);
        List<AppointmentPreperation> utfVersions = new ArrayList<>();
        for (AppointmentPreperation appointmentPreperation : appointmentPreperations) {
            utfVersions.add(convertToUtf(appointmentPreperation));
        }
        return utfVersions;
    }

    @Override
    public AppointmentPreperation getNextAppointmentPreperation(String patientSsn) {
        List<AppointmentPreperation> appointmentPreperations = getAppointmentPreperations(patientSsn);
        AppointmentPreperation next = null;
        DateTime nextDateTime = null;
        DateTime now = new DateTime();

        for (AppointmentPreperation appointmentPreperation : appointmentPreperations) {

            DateTime appointmentTime = new DateTime(appointmentPreperation.getAppointmentDate().getYear(),
                    appointmentPreperation.getAppointmentDate().getMonthValue(), appointmentPreperation.getAppointmentDate().getDayOfMonth(),
                    appointmentPreperation.getAppointmentTime().getHour(), appointmentPreperation.getAppointmentTime().getMinute());

            if (next == null && appointmentTime.isAfter(now)) {
                next = appointmentPreperation;
                nextDateTime = appointmentTime;
                continue;
            }
            if (next == null) continue;
            if (appointmentTime.isAfter(now) && nextDateTime.isAfter(appointmentTime)) {
                next = appointmentPreperation;
                nextDateTime = appointmentTime;
            }
        }
        return next;
    }

    private AppointmentPreperation convertToUtf(AppointmentPreperation appointmentPreperation) {
        appointmentPreperation.setNeedForConsultation(UtfConverter.convertToUtf(appointmentPreperation.getNeedForConsultation()));
        appointmentPreperation.setSymptomListUpdated(UtfConverter.convertToUtf(appointmentPreperation.getSymptomListUpdated()));
        appointmentPreperation.setSideEffectsUpdated(UtfConverter.convertToUtf(appointmentPreperation.getSideEffectsUpdated()));
        for (Symptom symptom : appointmentPreperation.getSymptoms()) {
            symptom.setName(UtfConverter.convertToUtf(symptom.getName()));
            symptom.setDescription(UtfConverter.convertToUtf(symptom.getDescription()));
        }

        for (OtherSubject otherSubject : appointmentPreperation.getOtherSubjects()) {
            otherSubject.setName(UtfConverter.convertToUtf(otherSubject.getName()));
        }
        return appointmentPreperation;
    }

    private AppointmentPreperation convertFromUtf(AppointmentPreperation appointmentPreperation) {
        appointmentPreperation.setNeedForConsultation(UtfConverter.convertFromUtf(appointmentPreperation.getNeedForConsultation()));
        appointmentPreperation.setSymptomListUpdated(UtfConverter.convertFromUtf(appointmentPreperation.getSymptomListUpdated()));
        appointmentPreperation.setSideEffectsUpdated(UtfConverter.convertFromUtf(appointmentPreperation.getSideEffectsUpdated()));

        for (Symptom symptom : appointmentPreperation.getSymptoms()) {
            symptom.setName(UtfConverter.convertFromUtf(symptom.getName()));
            symptom.setDescription(UtfConverter.convertFromUtf(symptom.getDescription()));
        }

        for (OtherSubject otherSubject : appointmentPreperation.getOtherSubjects()) {
            otherSubject.setName(UtfConverter.convertFromUtf(otherSubject.getName()));
        }
        return appointmentPreperation;
    }

    private AppointmentPreperation removeOldOthers(AppointmentPreperation old, AppointmentPreperation appointmentPreperation) {
        
        for (int i = 0; i < old.getOtherSubjects().size(); ++i) {
        	OtherSubject oldOther = old.getOtherSubjects().get(i);
            if (appointmentPreperation.getOtherSubjects().contains(oldOther)) {
                
            }
            else {
            	old.getOtherSubjects().remove(oldOther);
            	--i;
            }
        }
        
        return old;
    }

    private AppointmentPreperation replaveUpdatedOthersAndAddNewOnes(AppointmentPreperation old, AppointmentPreperation appointmentPreperation) {
        for (OtherSubject otherSubject : appointmentPreperation.getOtherSubjects()) {
            if (!old.getOtherSubjects().contains(otherSubject)) {
                String uuid = UUID.randomUUID().toString();
                otherSubject.setUuid(uuid);
                otherSubject = otherSubjectRepo.save(otherSubject);
                old.addNewOther(otherSubject);
            }
        }
        return old;
    }

    private AppointmentPreperation removeOldSymptoms(AppointmentPreperation old, AppointmentPreperation appointmentPreperation) {
        
        for (int i = 0; i <  old.getSymptoms().size(); ++i) {
        	Symptom oldSymptom = old.getSymptoms().get(i);
            if (appointmentPreperation.getSymptoms().contains(oldSymptom)) {
                
            }
            else {
            	old.getSymptoms().remove(oldSymptom);
            	--i;
            }
        }
        
        return old;
    }

    private AppointmentPreperation replaceUpdatedSymptomsAndAddNewOnes(AppointmentPreperation old, AppointmentPreperation appointmentPreperation) {
        for (Symptom symptom : appointmentPreperation.getSymptoms()) {
            if (old.getSymptoms().contains(symptom)) { //eksistere fra før (oppdatering)
                old.replaceSymptom(symptom);
            } else {            	
                String uuid = UUID.randomUUID().toString();
                symptom.setUuid(uuid);
                symptom = symptomRepo.save(symptom);
                old.addNewSymptom(symptom); // eksistere ikke i old, en ny versjon
            }
        }
        return old;
    }


}
