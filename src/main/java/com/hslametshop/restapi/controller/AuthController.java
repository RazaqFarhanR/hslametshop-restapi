package com.hslametshop.restapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hslametshop.restapi.helper.requests.LoginRequest;
import com.hslametshop.restapi.helper.requests.RegisterRequest;
import com.hslametshop.restapi.helper.responses.LoginResponse;
import com.hslametshop.restapi.model.entities.Admin;
import com.hslametshop.restapi.model.entities.Member;
import com.hslametshop.restapi.service.AuthService;
import com.hslametshop.restapi.service.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;

    private final AuthService authService;

    public AuthController(JwtService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/register/member")
    public ResponseEntity<Member> registerMember(@RequestBody RegisterRequest member) {
        Member registeredMember = authService.registerMember(member);
        return ResponseEntity.ok(registeredMember);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<Admin> registerAdmin(@RequestBody RegisterRequest admin) {
        Admin registeredAdmin = authService.registerAdmin(admin);
        return ResponseEntity.ok(registeredAdmin);
    }

    @PostMapping("/login/member")
    public ResponseEntity<LoginResponse> loginMember(@RequestBody LoginRequest member) {
        Member authenticatedMember = authService.authenticateMember(member);
        String token = jwtService.generateToken(authenticatedMember);
        String refreshToken = jwtService.refreshToken(token);

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setToken(token);
        loginResponse.setExpiration(jwtService.getExpiration());
        loginResponse.setRefreshToken(refreshToken);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login/admin")
    public ResponseEntity<LoginResponse> loginAdmin(@RequestBody LoginRequest admin) {
        Admin authenticatedAdmin = authService.authenticateAdmin(admin);
        String token = jwtService.generateToken(authenticatedAdmin);
        String refreshToken = jwtService.refreshToken(token);

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setToken(token);
        loginResponse.setExpiration(jwtService.getExpiration());
        loginResponse.setRefreshToken(refreshToken);
        return ResponseEntity.ok(loginResponse);
    }

}
