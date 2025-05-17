package com.hslametshop.restapi.model.entities;

import java.io.Serializable;
import java.util.UUID;

import com.hslametshop.restapi.model.interfaces.UserRolesEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_admin")
public class Admin extends User implements Serializable {

    public Admin(String name, String pnumber, String email, String pass, UUID userId) {
        super(userId, pnumber, email, pass, name);
    }

    public Admin() {
        this.setRole(UserRolesEnum.ADMIN);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public void setPass(String pass) {
        super.setPass(pass);
    }

    @Override
    public void setPhoneNumber(String pNumber) {
        super.setPhoneNumber(pNumber);
    }

}