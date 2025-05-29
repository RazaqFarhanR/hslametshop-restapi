package com.hslametshop.restapi.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hslametshop.restapi.helper.requests.LoginRequest;
import com.hslametshop.restapi.helper.requests.LogoutRequest;
import com.hslametshop.restapi.helper.requests.RefreshTokenRequest;
import com.hslametshop.restapi.helper.requests.RegisterRequest;
import com.hslametshop.restapi.helper.responses.LoginResponse;
import com.hslametshop.restapi.helper.responses.RefreshTokenResponse;
import com.hslametshop.restapi.model.entities.Admin;
import com.hslametshop.restapi.model.entities.Member;
import com.hslametshop.restapi.model.entities.RefreshToken;
import com.hslametshop.restapi.model.entities.User;
import com.hslametshop.restapi.model.repositories.RefreshTokenRepository;
import com.hslametshop.restapi.model.repositories.UserRepository;
import com.hslametshop.restapi.service.AuthService;
import com.hslametshop.restapi.service.JwtService;

import jakarta.transaction.Transactional;

@Transactional
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;

    private final AuthService authService;

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    public AuthController(JwtService jwtService, AuthService authService,
            RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authService = authService;
        this.refreshTokenRepository = refreshTokenRepository;
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

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUser(authenticatedMember);
        refreshTokenEntity.setExpiryDate(System.currentTimeMillis() + jwtService.getExpiration() + 900000);
        refreshTokenRepository.save(refreshTokenEntity);
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

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setAdmin(authenticatedAdmin);
        refreshTokenEntity.setExpiryDate(System.currentTimeMillis() + jwtService.getExpiration() + 900000);
        refreshTokenRepository.save(refreshTokenEntity);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest token) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(token.getRefreshToken()).get();

        if (refreshTokenEntity == null) {
            System.err.println("Refresh token not found");
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }
        if (refreshTokenEntity.getExpiryDate() < System.currentTimeMillis()) {
            refreshTokenRepository.deleteByToken(refreshTokenEntity.getToken());
            System.err.println("Refresh token expired");
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }
        Admin admin;
        Member member;
        String username = jwtService.extractUsername(refreshTokenEntity.getToken());
        User user = userRepository.findByEmail(username).get();
        String newToken;
        String newRefreshToken;
        if (user instanceof Admin) {
            admin = (Admin) user;
            newToken = jwtService.generateToken(admin);
            newRefreshToken = jwtService.refreshToken(newToken);
            refreshTokenRepository.deleteByToken(refreshTokenEntity.getToken());
            RefreshToken r = new RefreshToken();
            r.setToken(newRefreshToken);
            r.setAdmin(admin);
            r.setExpiryDate(jwtService.getExpiration() + 900000);
            refreshTokenRepository.save(r);
        } else if (user instanceof Member) {
            member = (Member) user;
            newToken = jwtService.generateToken(member);
            newRefreshToken = jwtService.refreshToken(newToken);
            refreshTokenRepository.deleteByToken(refreshTokenEntity.getToken());
            RefreshToken r = new RefreshToken();
            r.setToken(newRefreshToken);
            r.setUser(member);
            r.setExpiryDate(jwtService.getExpiration() + 900000);
            refreshTokenRepository.save(r);
        } else {
            System.err.println("User not found");
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }
        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
        refreshTokenResponse.setToken(newToken);
        refreshTokenResponse.setExpiration(jwtService.getExpiration());
        refreshTokenResponse.setRefreshToken(newRefreshToken);

        return ResponseEntity.ok(refreshTokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest token) {
        refreshTokenRepository.deleteByToken(token.getToken());
        return ResponseEntity.ok("Logged out successfully");
    }
}
