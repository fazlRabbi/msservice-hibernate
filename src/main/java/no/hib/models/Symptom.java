package no.hib.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Symptom {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer _id;
	@Version
    private Integer _rev;
    private String uuid;
    private String name;
    private String description;
    private String severity;
    private String change;
    private boolean important;

    public Symptom(){
    	
    }
    public Symptom(String uuid, String name, String description, String severity, String change, boolean important) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.severity = severity;
        this.change = change;
        this.important = important;
    }

    public Symptom(String name, String description, String severity, String change, boolean important) {
        this.name = name;
        this.description = description;
        this.severity = severity;
        this.change = change;
        this.important = important;
    }

    public Symptom(Integer _id, Integer _rev, String uuid, String name, String description, String severity, String change, boolean important) {
        this._id = _id;
        this._rev = _rev;
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.severity = severity;
        this.change = change;
        this.important = important;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Symptom symptom = (Symptom) o;

        return uuid != null ? uuid.equals(symptom.uuid) : symptom.uuid == null;
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
