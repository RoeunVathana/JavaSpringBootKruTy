package com.example.projectjava.Repository;

import com.example.projectjava.Model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major, Long>, JpaSpecificationExecutor<Major> {
        Optional<Major> findByName(String name);
}
