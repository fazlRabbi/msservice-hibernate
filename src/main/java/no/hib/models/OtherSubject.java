package no.hib.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class OtherSubject {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer _id;
	@Version
    private Integer _rev;
    private String uuid;
    private String name;

    public OtherSubject(){
    	
    }
    public OtherSubject(String name) {
        this.name = name;
    }

    public OtherSubject(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public OtherSubject(Integer _id, Integer _rev, String uuid, String name) {
        this._id = _id;
        this._rev = _rev;
        this.uuid = uuid;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        OtherSubject that = (OtherSubject) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
