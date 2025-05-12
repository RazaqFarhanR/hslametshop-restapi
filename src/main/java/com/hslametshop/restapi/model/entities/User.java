package com.hslametshop.restapi.model.entities;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "userid", updatable = false, nullable = false)
    private UUID userid;

    private String name;

    @Column(name = "pnumber")
    private String pNumber;

    private String email;

    private String pass;

    public User() {}

    public User(String name, String pNumber, String email, String pass, UUID userid) {
        this.name = name;
        this.pNumber = pNumber;
        this.email = email;
        this.pass = pass;
        this.userid = userid;
    }

    public UUID getId() {
        return userid;
    }

    public void setId(UUID userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
