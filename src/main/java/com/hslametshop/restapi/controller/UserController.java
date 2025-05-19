package com.hslametshop.restapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hslametshop.restapi.model.entities.Admin;
import com.hslametshop.restapi.model.entities.Member;
import com.hslametshop.restapi.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Member> authenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Member authenticatedMember = (Member) authentication.getPrincipal();
        return ResponseEntity.ok(authenticatedMember);
    }

    @GetMapping("/me-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Admin> authenticatedAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Admin authenticatedAdmin = (Admin) authentication.getPrincipal();
        return ResponseEntity.ok(authenticatedAdmin);
    }

    @GetMapping("/all-member")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = userService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/all-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = userService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

}
