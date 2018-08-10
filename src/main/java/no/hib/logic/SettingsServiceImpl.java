package no.hib.logic;

import no.hib.logic.interfaces.SettingsService;
import no.hib.models.OtherSubject;
import no.hib.models.Settings;
import no.hib.models.Symptom;
import no.hib.repository.OtherSubjectRepo;
import no.hib.repository.SettingsRepo;
import no.hib.repository.SymptomRepo;
import no.hib.utils.UtfConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("settingsService")
@Transactional
public class SettingsServiceImpl implements SettingsService {



    @Autowired
    SettingsRepo settingsRepo;
    
    @Autowired
    SymptomRepo symptomRepo;

    @Autowired
    OtherSubjectRepo otherSubjectRepo;
    
    private Settings getSettingsFromRepo(){
    	List<Settings> settingses = settingsRepo.getSettings();
    	if (settingses == null || settingses.size() == 0) {
    		Settings s = new Settings(3, new ArrayList<Symptom>(), new ArrayList<OtherSubject>());
    		
            return settingsRepo.save(s);
            
        } else {
        	return settingses.get(0);
        }    	
    }
    
    private Settings updateSettingsInRepo(Settings settings) {
        
        Settings old = getSettings();
        
        old.setDefaultOtherSubjects(new ArrayList<OtherSubject>());
        old.setDefaultSymptoms(new ArrayList<Symptom>());
        old.setAppointmentPreperationMinStart(settings.getAppointmentPreperationMinStart());
        old.setUuid(settings.getUuid());
        
        settingsRepo.save(old);

        return old;
    }
    
    private void updateSymptomInRepo(Symptom symptom) {
        
        List<Symptom> symptoms = symptomRepo.getSymptomByUuid(symptom.getUuid()); ///////@TODO:: database.find(Symptom.class, symptom.getUuid());

        if(symptoms != null && symptoms.size() > 0){
        	Symptom s = symptoms.get(0);
        	s.setChange(symptom.getChange());
        	s.setDescription(symptom.getDescription());        	
        	s.setName(symptom.getName());
        	s.setSeverity(symptom.getSeverity());
        	symptomRepo.save(s);
        }

    }
    
    private void updateSubjectInRepo(OtherSubject otherSubject) {
        
    	List<OtherSubject> subjects = otherSubjectRepo.getSubjectByUuid(otherSubject.getUuid());
    	if(subjects!=null && subjects.size() > 0){
    		OtherSubject s = subjects.get(0);
    		s.setName(otherSubject.getName());
    		otherSubjectRepo.save(s);
    	}    	
    }

    
    @Override
    public Settings getSettings() {
    	Settings settings = getSettingsFromRepo();
    	
    	
        List<OtherSubject> subjects = otherSubjectRepo.getSubjects();
        List<Symptom> symptoms = symptomRepo.getSymptoms();

        settings.setDefaultOtherSubjects(subjects);
        settings.setDefaultSymptoms(symptoms);
        Settings utf = convertToUtfSettings(settings);
        return utf;
    }

    
    
    @Override
    public Settings updateSettings(Settings settings) {
        Settings utf = convertFromUtfSettings(settings);
        Settings updated = updateSettingsInRepo(utf); //settingsRepository.updateSettings(utf);
        return updated;
    }

    @Override
    public void deleteSubject(String uuid) {
        List<OtherSubject> subjects = otherSubjectRepo.getSubjectByUuid(uuid);
        if(subjects != null && subjects.size() > 0)
        	otherSubjectRepo.delete(subjects.get(0));
    }

    @Override
    public void deleteSymptom(String uuid) {
    	System.out.println("Deleting symptom with uuid : " + uuid);
        List<Symptom> symptoms = symptomRepo.getSymptomByUuid(uuid);
        if(symptoms != null && symptoms.size() > 0)
        	symptomRepo.delete(symptoms.get(0));
    }

    @Override
    public OtherSubject updateSubject(OtherSubject otherSubject) {
        OtherSubject utf = convertFromUtfOtherSubject(otherSubject);
        updateSubjectInRepo(utf);
        return otherSubject;
    }

    @Override
    public Symptom updateSymptom(Symptom symptom) {
        symptom.setSeverity("");
        symptom.setChange("");
        Symptom utf = convertFromUtfSymptom(symptom);
        updateSymptomInRepo(utf);
        return symptom;
    }

    @Override
    public Symptom addSymptom(Symptom symptom) {
        String uuid = UUID.randomUUID().toString();
        //symptom.set_id(uuid);
        symptom.setUuid(uuid);
        symptom.setSeverity("");
        symptom.setChange("");

        Symptom utf = convertFromUtfSymptom(symptom);
        symptomRepo.save(utf);
        return symptom;
    }

    @Override
    public OtherSubject addSubject(OtherSubject otherSubject) {
        String uuid = UUID.randomUUID().toString();
        //otherSubject.set_id(uuid);
        otherSubject.setUuid(uuid);
        OtherSubject utf = convertFromUtfOtherSubject(otherSubject);
        otherSubjectRepo.save(utf);
        return otherSubject;
    }

    private Settings convertToUtfSettings(Settings settings) {
        List<Symptom> nonSymptoms = new ArrayList<>();
        for (Symptom symptom : settings.getDefaultSymptoms()) {
            symptom.setDescription(UtfConverter.convertToUtf(symptom.getDescription()));
            symptom.setName(UtfConverter.convertToUtf(symptom.getName()));
            nonSymptoms.add(symptom);
        }

        List<OtherSubject> nonOthers = new ArrayList<>();
        for (OtherSubject otherSubject : settings.getDefaultOtherSubjects()) {
            otherSubject.setName(UtfConverter.convertToUtf(otherSubject.getName()));
            nonOthers.add(otherSubject);
        }

        settings.setDefaultSymptoms(nonSymptoms);
        settings.setDefaultOtherSubjects(nonOthers);

        return settings;
    }

    private Settings convertFromUtfSettings(Settings settings) {
        List<Symptom> nonSymptoms = new ArrayList<>();
        for (Symptom symptom : settings.getDefaultSymptoms()) {
            symptom.setDescription(UtfConverter.convertFromUtf(symptom.getDescription()));
            symptom.setName(UtfConverter.convertFromUtf(symptom.getName()));
            nonSymptoms.add(symptom);
        }

        List<OtherSubject> nonOthers = new ArrayList<>();
        for (OtherSubject otherSubject : settings.getDefaultOtherSubjects()) {
            otherSubject.setName(UtfConverter.convertFromUtf(otherSubject.getName()));
            nonOthers.add(otherSubject);
        }

        settings.setDefaultSymptoms(nonSymptoms);
        settings.setDefaultOtherSubjects(nonOthers);

        return settings;
    }


    private List<Symptom> convertToUtfSymptoms(List<Symptom> symptoms) {
        List<Symptom> nonSymptoms = new ArrayList<>();
        for (Symptom symptom : symptoms) {
            symptom.setDescription(UtfConverter.convertToUtf(symptom.getDescription()));
            symptom.setName(UtfConverter.convertToUtf(symptom.getName()));
            nonSymptoms.add(symptom);
        }
        return nonSymptoms;
    }

    private List<Symptom> convertFromUtfSymptoms(List<Symptom> symptoms) {
        List<Symptom> nonSymptoms = new ArrayList<>();
        for (Symptom symptom : symptoms) {
            symptom.setDescription(UtfConverter.convertFromUtf(symptom.getDescription()));
            symptom.setName(UtfConverter.convertFromUtf(symptom.getName()));
            nonSymptoms.add(symptom);
        }
        return nonSymptoms;
    }

    private List<OtherSubject> convertToUtfOtherSubjects(List<OtherSubject> otherSubjects) {
        List<OtherSubject> nonOthers = new ArrayList<>();
        for (OtherSubject otherSubject : otherSubjects) {
            otherSubject.setName(UtfConverter.convertToUtf(otherSubject.getName()));
            nonOthers.add(otherSubject);
        }
        return nonOthers;
    }

    private List<OtherSubject> convertFromUtfOtherSubjects(List<OtherSubject> otherSubjects) {
        List<OtherSubject> nonOthers = new ArrayList<>();
        for (OtherSubject otherSubject : otherSubjects) {
            otherSubject.setName(UtfConverter.convertFromUtf(otherSubject.getName()));
            nonOthers.add(otherSubject);
        }

        return nonOthers;
    }

    private Symptom convertToUtfSymptom(Symptom symptom) {
        symptom.setDescription(UtfConverter.convertToUtf(symptom.getDescription()));
        symptom.setName(UtfConverter.convertToUtf(symptom.getName()));
        return symptom;
    }

    private Symptom convertFromUtfSymptom(Symptom symptom) {
        symptom.setDescription(UtfConverter.convertFromUtf(symptom.getDescription()));
        symptom.setName(UtfConverter.convertFromUtf(symptom.getName()));
        return symptom;
    }

    private OtherSubject convertToUtfOtherSubject(OtherSubject otherSubject) {
        otherSubject.setName(UtfConverter.convertToUtf(otherSubject.getName()));
        return otherSubject;
    }

    private OtherSubject convertFromUtfOtherSubject(OtherSubject otherSubject) {
        otherSubject.setName(UtfConverter.convertFromUtf(otherSubject.getName()));
        return otherSubject;
    }
}
