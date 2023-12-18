package com.example.lab4.repository;

import com.example.lab4.model.hits.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {
    List<Hit> findAllByUser_Username(String username);
}
