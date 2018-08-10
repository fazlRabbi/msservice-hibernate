package no.hib.logic;

import no.hib.logic.interfaces.AppointmentService;
import no.hib.logic.interfaces.PatientService;
import no.hib.models.AdminUser;
import no.hib.models.Appointment;
import no.hib.models.BankIdUser;
import no.hib.models.Patient;
import no.hib.repository.BankIdUserRepo;
import no.hib.repository.PatientRepo;
import no.hib.repository.SettingsRepo;
import no.hib.utils.SearchStringGenerator;
import no.hib.utils.UtfConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("patientService")
@Transactional
public class PatientServiceImpl implements PatientService {

	@Autowired
	PatientRepo patientRepo;
	


    @Autowired
    SettingsRepo userRepository;

    @Autowired
    BankIdUserRepo bankIdUserRepository;


    
    
    @Autowired
    AppointmentService appointmentService;

    private Patient getPatientfromRepo(String ssn) {        
        List<Patient> patients = patientRepo.getPatient(ssn); 
        if(patients.size() == 0) return null;
        return patients.get(0);
    }

    private Patient updatePatientInRepo(Patient patient) {
        
        Patient old = getPatientfromRepo(patient.getSsn());
        old.setAddress(patient.getAddress());
        old.setBirthday(patient.getBirthday());
        old.setCity(patient.getCity());
        old.setFirstName(patient.getFirstName());
        old.setLastName(patient.getLastName());
        old.setSex(patient.getSex());
        old.setSsn(patient.getSsn());
        old.setUuid(patient.getUuid());
        old.setZipCode(patient.getZipCode());
        patientRepo.save(old);
        return old;
    }

    
    @Override
    public boolean validPatient(Patient patient) {
        boolean valid = validGivenName(patient.getFirstName()) && validFamilyName(patient.getLastName()) &&
                validSsn(patient.getSsn()) && validGender(patient.getSex());
        return valid;
    }

    @Override
    public Patient getPatient(String ssn) {
        Patient patient = getPatientfromRepo(ssn);
        patient = convertToUtf(patient);
        return patient;
    }

    @Override
    public Patient createPatient(Patient patient) {
        String uuid = UUID.randomUUID().toString();
        patient.setUuid(uuid);
        //patient.set_id(uuid);
        patient = convertFromUtf(patient);
        Patient created = patientRepo.save(patient);
        
        // old code:: userRepository.createBankIdUser(patient.getSsn(), "Password");
        
        BankIdUser bankIdUser = new BankIdUser(patient.getSsn(), "Password");
        bankIdUserRepository.save(bankIdUser);
        
        return created;
    }

    @Override
    public Patient updatePatient(Patient patient) {
        patient = convertFromUtf(patient);
        Patient updated = updatePatientInRepo(patient);
        return updated;
    }

    @Override
    public List<Patient> getPatients() {
        List<Patient> patients = patientRepo.getPatients();
        List<Patient> utfVersions = new ArrayList<>();
        for(Patient pat : patients){
            utfVersions.add(convertToUtf(pat));
        }
        return utfVersions;
    }

    @Override
    public void deletePatient(String ssn) {
    	Patient p = getPatientfromRepo(ssn);
    	patientRepo.delete(p);
    	
        // old code:: userRepository.deleteBankIdUser(ssn);
        
        List<BankIdUser> bankIdUsers = bankIdUserRepository.getBankIdUser(ssn);
        if(bankIdUsers != null){
        	for(int i = 0; i < bankIdUsers.size(); ++i)
        	bankIdUserRepository.delete(bankIdUsers.get(i));
        }
        
        for(Appointment appointment : appointmentService.getAppointmentsForPatient(ssn)){
            appointmentService.deleteAppointment(appointment.getUuid());
        }
    }

    private boolean validGender(String sex) {
        return sex.toLowerCase().equals("male") || sex.toLowerCase().equals("female");
    }

    private boolean validFamilyName(String lastName) {
        return lastName.length() > 2;
    }

    private boolean validSsn(String ssn) {
        return ssn.length() == 11;  //TODO real validation
    }

    private boolean validGivenName(String firstName) {
        return firstName.length() > 2;
    }

    private Patient convertToUtf(Patient patient){
        patient.setFirstName(UtfConverter.convertToUtf(patient.getFirstName()));
        patient.setLastName(UtfConverter.convertToUtf(patient.getLastName()));
        patient.setAddress(UtfConverter.convertToUtf(patient.getAddress()));
        patient.setCity(UtfConverter.convertToUtf(patient.getCity()));
        return patient;
    }

    private Patient convertFromUtf(Patient patient) {
        patient.setFirstName(UtfConverter.convertFromUtf(patient.getFirstName()));
        patient.setLastName(UtfConverter.convertFromUtf(patient.getLastName()));
        patient.setAddress(UtfConverter.convertFromUtf(patient.getAddress()));
        patient.setCity(UtfConverter.convertFromUtf(patient.getCity()));
        return patient;
    }

}
