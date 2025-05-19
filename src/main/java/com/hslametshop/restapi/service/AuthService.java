package com.hslametshop.restapi.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hslametshop.restapi.helper.requests.LoginRequest;
import com.hslametshop.restapi.helper.requests.RegisterRequest;
import com.hslametshop.restapi.model.entities.Admin;
import com.hslametshop.restapi.model.entities.Member;
import com.hslametshop.restapi.model.interfaces.UserRolesEnum;
import com.hslametshop.restapi.model.repositories.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Member registerMember(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        Member user = new Member();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPass(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserRolesEnum.MEMBER);
        return userRepository.save(user);
    }

    public Admin registerAdmin(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        Admin user = new Admin();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPass(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserRolesEnum.ADMIN);
        return userRepository.save(user);
    }

    public Member authenticateMember(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        return (Member) userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Admin authenticateAdmin(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        return (Admin) userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
