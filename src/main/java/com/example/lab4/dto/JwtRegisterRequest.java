package com.example.lab4.dto;

import lombok.Data;

@Data
public class JwtRegisterRequest {
    private String username;
    private String password;
}
