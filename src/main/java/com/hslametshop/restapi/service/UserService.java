package com.hslametshop.restapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hslametshop.restapi.model.entities.Admin;
import com.hslametshop.restapi.model.entities.Member;
import com.hslametshop.restapi.model.entities.User;
import com.hslametshop.restapi.model.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            Member member = convertUserToMember(user);
            members.add(member);
        });
        return members;
    }

    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            Admin admin = convertUserToAdmin(user);
            admins.add(admin);
        });
        return admins;
    }

    private Member convertUserToMember(User user) {
        return new Member();
    }

    private Admin convertUserToAdmin(User user) {
        return new Admin();
    }

}