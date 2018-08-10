package no.hib.logic.interfaces;

import no.hib.models.OtherSubject;
import no.hib.models.Settings;
import no.hib.models.Symptom;

public interface SettingsService {

    Settings getSettings();
    Settings updateSettings(Settings settings);
    void deleteSubject(String uuid);
    void deleteSymptom(String uuid);
    OtherSubject updateSubject(OtherSubject otherSubject);
    Symptom updateSymptom(Symptom symptom);

    Symptom addSymptom(Symptom symptom);

    OtherSubject addSubject(OtherSubject otherSubject);
}
