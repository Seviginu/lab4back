package com.example.lab4.service;

import com.example.lab4.model.Role;
import com.example.lab4.model.RoleName;
import com.example.lab4.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getUserRoles() {
        return roleRepository.findAllByName(RoleName.ROLE_USER);
    }
}
