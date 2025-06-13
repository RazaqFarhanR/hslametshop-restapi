package com.hslametshop.restapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hslametshop.restapi.helper.requests.UpdateAddressRequest;
import com.hslametshop.restapi.helper.requests.UpdatePasswordRequest;
import com.hslametshop.restapi.helper.requests.UpdateProfileRequest;
import com.hslametshop.restapi.model.entities.Admin;
import com.hslametshop.restapi.model.entities.Member;
import com.hslametshop.restapi.model.entities.User;
import com.hslametshop.restapi.model.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            if (user instanceof Member) {
                Member member = (Member) user;
                members.add(member);
            }
        });
        return members;
    }

    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            if (user instanceof Admin) {
                Admin admin = (Admin) user;
                admins.add(admin);
            }
        });
        return admins;
    }

    // Update Profile Member
    public Member updateMemberProfile(UUID memberId, UpdateProfileRequest request) {
        Optional<User> userOpt = userRepository.findById(memberId);
        if (userOpt.isEmpty() || !(userOpt.get() instanceof Member)) {
            throw new IllegalArgumentException("Member not found");
        }
        Member member = (Member) userOpt.get();
        if (request.getName() != null)
            member.setName(request.getName());
        if (request.getEmail() != null)
            member.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null)
            member.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(member);
        return member;
    }

    // Update Address Member
    public Member updateMemberAddress(UUID memberId, UpdateAddressRequest request) {
        Optional<User> userOpt = userRepository.findById(memberId);
        if (userOpt.isEmpty() || !(userOpt.get() instanceof Member)) {
            throw new IllegalArgumentException("Member not found");
        }
        Member member = (Member) userOpt.get();
        if (request.getAddress() != null)
            member.setAddress(request.getAddress());
        userRepository.save(member);
        return member;
    }

    // Update Password Member
    public Member updateMemberPassword(UUID memberId, UpdatePasswordRequest request) {
        Optional<User> userOpt = userRepository.findById(memberId);
        if (userOpt.isEmpty() || !(userOpt.get() instanceof Member)) {
            throw new IllegalArgumentException("Member not found");
        }
        Member member = (Member) userOpt.get();
        if (!passwordEncoder.matches(request.getOldPassword(), member.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        member.setPass(passwordEncoder.encode(request.getPassword()));
        userRepository.save(member);
        return member;
    }

    // Update Profile Admin
    public Admin updateAdminProfile(UUID adminId, UpdateProfileRequest request) {
        Optional<User> userOpt = userRepository.findById(adminId);
        if (userOpt.isEmpty() || !(userOpt.get() instanceof Admin)) {
            throw new IllegalArgumentException("Admin not found");
        }
        Admin admin = (Admin) userOpt.get();
        if (request.getName() != null)
            admin.setName(request.getName());
        if (request.getEmail() != null)
            admin.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null)
            admin.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(admin);
        return admin;
    }

    // Update Password Admin
    public Admin updateAdminPassword(UUID adminId, UpdatePasswordRequest request) {
        Optional<User> userOpt = userRepository.findById(adminId);
        if (userOpt.isEmpty() || !(userOpt.get() instanceof Admin)) {
            throw new IllegalArgumentException("Admin not found");
        }
        Admin admin = (Admin) userOpt.get();
        if (!passwordEncoder.matches(request.getOldPassword(), admin.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        admin.setPass(passwordEncoder.encode(request.getPassword()));
        userRepository.save(admin);
        return admin;
    }

    public Member banMember(UUID id, boolean isBanned) {
        Optional<Member> userOpt = userRepository.findById(id)
                .filter(user -> user instanceof Member)
                .map(user -> (Member) user);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        Member member = userOpt.get();
        member.setIsBanned(isBanned);
        userRepository.save(member);
        return member;
    }

}