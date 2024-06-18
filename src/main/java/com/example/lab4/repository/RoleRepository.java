package com.example.lab4.repository;

import com.example.lab4.model.Role;
import com.example.lab4.model.RoleName;
import org.hibernate.annotations.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);

    List<Role> findAllByName(RoleName name);
}
