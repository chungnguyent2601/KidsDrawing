package com.app.kidsdrawing.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.SemesterClass;

@Repository
public interface SemesterClassRepository extends JpaRepository <SemesterClass, Long>{
    Page<SemesterClass> findAll(Pageable pageable);
    boolean existsById(Long id);
    boolean existsByName(String name);
    void deleteById(Long id);
    List<SemesterClass> findBySemesterId(Long id);
}
