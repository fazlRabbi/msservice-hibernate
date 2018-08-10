package no.hib.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class AdminUser {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer _id;
	
	@Version
    private Integer _rev;
    private String email;
    private String password;

    public AdminUser(){
    	
    }
    public AdminUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
