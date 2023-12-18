package com.example.lab4.service;

import com.example.lab4.dto.HitResponse;
import com.example.lab4.model.User;
import com.example.lab4.model.hits.Hit;
import com.example.lab4.model.hits.Point;
import com.example.lab4.repository.HitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HitService {
    private final HitRepository hitRepository;
    private final ValidateHitService validateHitService;
    private final UserService userService;

    private boolean isInside(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();

        return x <= 0 && y >= 0 && x * x + y * y <= r * r
                || x >= 0 && y >= 0 && x <= r && y <= r
                || x >= 0 && y <= 0 && 2 * y - x >= -r;
    }

    public ResponseEntity<?> checkHit(Point point, User user) {
        if (!validateHitService.validate(point)) {
            return ResponseEntity.badRequest().body("Invalid value");
        }
        Hit hit = Hit.builder()
                .user(user)
                .point(point)
                .isInside(isInside(point))
                .build();
        Hit savedHit = hitRepository.save(hit);
        return ResponseEntity.ok(HitResponse
                .builder()
                .creationDate(savedHit.getCreated())
                .isInside(savedHit.getIsInside())
                .point(savedHit.getPoint())
                .build()
        );
    }

    private HitResponse hitResponseParse(Hit hit) {
        return HitResponse.builder()
                .creationDate(hit.getCreated())
                .isInside(hit.getIsInside())
                .point(hit.getPoint())
                .build();
    }

    public ResponseEntity<?> getAllHitsByUsername(String username) {
        if (!userService.isUserExists(username))
            return ResponseEntity.notFound().build();
        List<HitResponse> hits = hitRepository.findAllByUser_Username(username)
                .stream()
                .map(this::hitResponseParse)
                .toList();
        return ResponseEntity.ok(hits);
    }
}
