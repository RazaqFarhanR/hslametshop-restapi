package com.hslametshop.restapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hslametshop.restapi.helper.requests.UpdateAddressRequest;
import com.hslametshop.restapi.helper.requests.UpdatePasswordRequest;
import com.hslametshop.restapi.helper.requests.UpdateProfileRequest;
import com.hslametshop.restapi.model.entities.Admin;
import com.hslametshop.restapi.model.entities.Member;
import com.hslametshop.restapi.model.entities.User;
import com.hslametshop.restapi.model.repositories.UserRepository;
import com.hslametshop.restapi.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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

    @PutMapping("/update-profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticated = (User) authentication.getPrincipal();
        if (authenticated instanceof Admin) {
            Admin updatedAdmin = userService.updateAdminProfile(authenticated.getId(), request);
            return ResponseEntity.ok(updatedAdmin);
        } else if (authenticated instanceof Member) {
            Member updatedMember = userService.updateMemberProfile(authenticated.getId(), request);
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.badRequest().body("Invalid user type");
        }
    }

    @PutMapping("/update-address")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Member> updateAddress(@RequestBody UpdateAddressRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member authenticatedMember = (Member) authentication.getPrincipal();
        Member updatedMember = userService.updateMemberAddress(authenticatedMember.getId(), request);
        return ResponseEntity.ok(updatedMember);
    }

    @PutMapping("/update-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticated = (User) authentication.getPrincipal();
        if (authenticated instanceof Admin) {
            Admin updatedAdmin = userService.updateAdminPassword(authenticated.getId(), request);
            return ResponseEntity.ok(updatedAdmin);
        } else if (authenticated instanceof Member) {
            Member updatedMember = userService.updateMemberPassword(authenticated.getId(), request);
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.badRequest().body("Invalid user type");
        }
    }

    @PutMapping("/update-profile/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProfileByAdmin(@RequestBody UpdateProfileRequest request,
            @PathVariable("id") UUID id) {

        User authenticated = (User) userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (authenticated instanceof Admin) {
            Admin updatedAdmin = null;
            if (request != null) {
                updatedAdmin = userService.updateAdminProfile(authenticated.getId(), request);
            }
            return ResponseEntity.ok(updatedAdmin);
        } else if (authenticated instanceof Member) {
            Member updatedMember = null;
            if (request != null) {
                updatedMember = userService.updateMemberProfile(authenticated.getId(), request);
            }
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.badRequest().body("Invalid user type");
        }
    }

    @PutMapping("/update-address/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Member> updateAddressByAdmin(@RequestBody UpdateAddressRequest request,
            @PathVariable("id") UUID id) {
        Member member = (Member) userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Member updatedMember = userService.updateMemberAddress(member.getId(), request);
        return ResponseEntity.ok(updatedMember);
    }

    @PutMapping("/update-password/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePasswordByAdmin(@RequestBody UpdatePasswordRequest request,
            @PathVariable("id") UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user instanceof Admin) {
            Admin updatedAdmin = userService.updateAdminPassword(user.getId(), request);
            return ResponseEntity.ok(updatedAdmin);
        } else if (user instanceof Member) {
            Member updatedMember = userService.updateMemberPassword(user.getId(), request);
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.badRequest().body("Invalid user type");
        }
    }

    @PutMapping("/ban/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> banMember(@PathVariable("id") UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user instanceof Admin) {
            return ResponseEntity.badRequest().body("Cannot ban an admin");
        } else if (user instanceof Member) {
            userService.banMember(user.getId(), true);
            return ResponseEntity.ok("Member banned successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid user type");
        }
    }

    @PutMapping("/unban/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> unBanMember(@PathVariable("id") UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user instanceof Admin) {
            return ResponseEntity.badRequest().body("Cannot ban an admin");
        } else if (user instanceof Member) {
            userService.banMember(user.getId(), false);
            return ResponseEntity.ok("Member unbanned successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid user type");
        }
    }

}
