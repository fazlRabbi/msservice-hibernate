package no.hib.logic;

import no.hib.logic.interfaces.DoctorService;
import no.hib.models.Doctor;
import no.hib.repository.DoctorRepo;
import no.hib.utils.SearchStringGenerator;
import no.hib.utils.UtfConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("doctorService")
@Transactional
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepo doctorRepo;



    
    private Doctor getDoctorFromRepo(String ssn) {        
        List<Doctor> doctors = doctorRepo.getDoctor(ssn); ///////@TODO:: database.findByIndex(searchString, Doctor.class);
        if(doctors == null || doctors.size() == 0) return null;
        return doctors.get(0);
    }


    private Doctor updateDoctorInRepo(Doctor doctor) {
        Doctor old = getDoctorFromRepo(doctor.getSsn());
        old.setFirstName(doctor.getFirstName());
        old.setLastName(doctor.getLastName());
        old.setSpecialization(doctor.getSpecialization());
        old.setUuid(doctor.getUuid());
        doctorRepo.save(old);
        return old;
    }

    
    @Override
    public Doctor getDoctor(String ssn) {
        Doctor doctor = getDoctorFromRepo(ssn);
        doctor = convertToUtf(doctor);
        return doctor;
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        String uuid = UUID.randomUUID().toString();
        doctor.setUuid(uuid);
        //doctor.set_id(uuid);
        doctor = convertFromUtf(doctor);
        Doctor created = doctorRepo.save(doctor);
        return created;
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        doctor = convertFromUtf(doctor);
        Doctor updated = updateDoctorInRepo(doctor);
        return updated;
    }

    @Override
    public List<Doctor> getDoctors() {
        List<Doctor> doctors = doctorRepo.getDoctors();
        List<Doctor> utfVersions = new ArrayList<>();
        for(Doctor d : doctors){
            utfVersions.add(convertToUtf(d));
        }
        return utfVersions;
    }

    @Override
    public void deleteDoctor(String ssn) {
    	List<Doctor> doctors = doctorRepo.getDoctor(ssn);
    	if(doctors != null && doctors.size() > 0)
    		doctorRepo.delete(doctors.get(0));
    }

    private Doctor convertToUtf(Doctor doctor){
        doctor.setFirstName(UtfConverter.convertToUtf(doctor.getFirstName()));
        doctor.setLastName(UtfConverter.convertToUtf(doctor.getLastName()));
        doctor.setSpecialization(UtfConverter.convertToUtf(doctor.getSpecialization()));
        return doctor;
    }

    private Doctor convertFromUtf(Doctor doctor) {
        doctor.setFirstName(UtfConverter.convertFromUtf(doctor.getFirstName()));
        doctor.setLastName(UtfConverter.convertFromUtf(doctor.getLastName()));
        doctor.setSpecialization(UtfConverter.convertFromUtf(doctor.getSpecialization()));
        return doctor;
    }
}
