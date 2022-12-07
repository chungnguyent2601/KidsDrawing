package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository <Teacher, Long>{
    @Query("SELECT e FROM Teacher e JOIN FETCH e.user u WHERE e.id = ?1 AND (u.deleted = FALSE OR u.deleted IS NULL) ")
    Optional<Teacher> findById1(Long id);

    @Query("SELECT e FROM Teacher e  JOIN FETCH e.user u  WHERE (u.deleted = FALSE OR u.deleted IS NULL)  ORDER BY e.id")
    List<Teacher> findAll();
}
