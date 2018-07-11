package com.oauth2sociallogin.demo.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rusha on 7/10/2018.
 */

@Entity
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
@Table(name = "user", schema = "demodb")
public class User extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "active")
    private Boolean active;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
