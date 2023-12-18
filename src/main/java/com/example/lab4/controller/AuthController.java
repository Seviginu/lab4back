package com.example.lab4.controller;

import com.example.lab4.dto.JwtRegisterRequest;
import com.example.lab4.dto.JwtRequest;
import com.example.lab4.dto.JwtResponse;
import com.example.lab4.service.AuthService;
import com.example.lab4.service.UserService;
import com.example.lab4.service.excepions.UserAlreadyExistsException;
import com.example.lab4.service.excepions.UserRegistryException;
import com.example.lab4.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
public class AuthController {
    private final AuthService authService;

    @PostMapping("get-token")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {
        return authService.loginUser(jwtRequest.getUsername(), jwtRequest.getPassword());
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody JwtRegisterRequest request) {
        try {
           return authService.registerUser(request.getUsername(), request.getPassword());
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("User already exists");
        } catch (UserRegistryException e) {
            return ResponseEntity.badRequest().body("Couldn't register user");
        }
    }

}
