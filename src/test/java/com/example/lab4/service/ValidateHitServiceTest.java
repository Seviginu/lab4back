package com.example.lab4.service;

import com.example.lab4.model.hits.Point;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidateHitServiceTest {

    @Autowired
    private ValidateHitService validateHitService;


    @Test
    void testValidPoint() {
        Point point = new Point(3.0, 2.0, 4.0);
        assertTrue(validateHitService.validate(point));
    }

    @Test
    void testInvalidXCoordinate() {
        Point point = new Point(6.0, 2.0, 4.0);
        assertFalse(validateHitService.validate(point));
    }

    @Test
    void testValidYCoordinate() {
        Point point = new Point(3.0, 4.0, 4.0);
        assertTrue(validateHitService.validate(point));
    }

    @Test
    void testInvalidRValue() {
        Point point = new Point(3.0, 2.0, 6.0);
        assertFalse(validateHitService.validate(point));
    }

    @Test
    void testNullPoint() {
        assertFalse(validateHitService.validate(null));
    }
}