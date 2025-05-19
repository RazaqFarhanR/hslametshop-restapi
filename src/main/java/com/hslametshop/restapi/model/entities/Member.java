package com.hslametshop.restapi.model.entities;

import java.util.List;
import java.util.UUID;

import com.hslametshop.restapi.model.interfaces.UserRolesEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_member")

public class Member extends User {

    @Column(name = "address")
    private String address;

    @Column(name = "is_banned")
    private boolean isBanned;

    @OneToMany(mappedBy = "member")
    private List<Transaction> transactions;

    public Member(String name, String pnumber, String email, String pass, UUID userId, String address,
            boolean isBanned) {
        super(userId, pnumber, email, pass, name);
        this.address = address;
        this.isBanned = isBanned;
    }

    public Member() {
        this.setRole(UserRolesEnum.MEMBER);
    }

    public void setName(String name) {
        super.setName(name);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPnumber(String pnumber) {
        super.setPhoneNumber(pnumber);
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    public void setPass(String pass) {
        super.setPass(pass);
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }
}