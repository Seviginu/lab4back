package com.example.lab4.controller;

import com.example.lab4.dto.HitCheckRequest;
import com.example.lab4.model.User;
import com.example.lab4.service.HitService;
import com.example.lab4.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hits")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*")
public class MainRestController {
    private final HitService hitService;
    private final UserService userService;

    @PostMapping("hit")
    public ResponseEntity<?> hit(@RequestBody HitCheckRequest hitCheckRequest,
                                 @RequestHeader("Authorization") String token) {
        User user = userService.loadUserByToken(token);
        return hitService.checkHit(hitCheckRequest.getPoint(), user);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token) {
        User user = userService.loadUserByToken(token);
        return hitService.getAllHitsByUsername(user.getUsername());
    }

    @GetMapping("{username}/all")
    public ResponseEntity<?> getAllByName(@PathVariable String username) {
        return hitService.getAllHitsByUsername(username);
    }
}
