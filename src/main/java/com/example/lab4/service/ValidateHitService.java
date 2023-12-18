package com.example.lab4.service;

import com.example.lab4.model.hits.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateHitService {
    @Value("${validator.xMin}")
    private Double X_MIN;
    @Value("${validator.xMax}")
    private Double X_MAX;
    @Value("${validator.yMin}")
    private Double Y_MIN;
    @Value("${validator.yMax}")
    private Double Y_MAX;
    @Value("${validator.rMin}")
    private Double R_MIN;
    @Value("${validator.rMax}")
    private Double R_MAX;

    public boolean validate(Point point) {
        return point != null &&
                X_MIN <= point.getX() && point.getX() <= X_MAX
                && Y_MIN <= point.getY() && point.getY() <= Y_MAX
                && R_MIN <= point.getR() && point.getR() <= R_MAX;
    }
}
