package com.hslametshop.restapi.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping
    public ResponseEntity<Map<String, String>> hello() {
        return ResponseEntity.ok().body(Map.of("message", "H. Slamet Shop REST API is UP!"));
    }
}
