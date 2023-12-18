package com.example.lab4.service;

import com.example.lab4.model.User;
import com.example.lab4.repository.UserRepository;
import com.example.lab4.util.JwtTokenUtils;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtTokenUtils tokenUtils;


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean isUserExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User loadUserByToken(String token) {
        if (!tokenUtils.validateToken(token))
            throw new JwtException("Invalid token");
        String username = tokenUtils.getUsername(token);
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Couldn't find user")
        );
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public void createNewUser(User user) {
        userRepository.save(user);
    }
}
