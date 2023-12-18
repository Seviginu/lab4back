package com.example.lab4.service;

import com.example.lab4.dto.JwtResponse;
import com.example.lab4.dto.MessageResponse;
import com.example.lab4.model.User;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final RoleService roleService;
    private final JwtTokenUtils tokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> loginUser(String username, String password){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password)
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Incorrect password"));
        }
        UserDetails userDetails = userService.loadUserByUsername(username);
        String token = tokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Transactional
    public ResponseEntity<?> registerUser(String username, String password) throws UserRegistryException {
        if (userService.isUserExists(username))
            throw new UserAlreadyExistsException();

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roleService.getUserRoles())
                .build();

        userService.createNewUser(user);
        UserDetails userDetails = userService.loadUserByUsername(username);
        JwtResponse response = new JwtResponse(tokenUtils.generateToken(userDetails));
        return ResponseEntity.ok(response);
    }
}
