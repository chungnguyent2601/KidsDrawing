package com.app.kidsdrawing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserGradeExerciseSubmission;

@Repository
public interface UserGradeExerciseSubmissionRepository extends JpaRepository <UserGradeExerciseSubmission, UUID>{
    @Query("SELECT e FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission ORDER BY e.id")
    List<UserGradeExerciseSubmission> findAll();

    @Query(
		value = "SELECT e FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission ORDER BY e.id",
		countQuery = "SELECT e FROM UserGradeExerciseSubmission e INNER JOIN e.teacher  INNER JOIN e.exerciseSubmission ORDER BY e.id"
	)
    Page<UserGradeExerciseSubmission> findAll(Pageable pageable);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission WHERE e.teacher = ?1 AND e.exerciseSubmission = ?2")
    Optional<UserGradeExerciseSubmission> findByTeacherIdAndExerciseSubmissionId(UUID teacher_id, UUID exercise_submission_id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  WHERE e.teacher = :id")
    List<UserGradeExerciseSubmission> findByTeacherId1(UUID id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.teacher  JOIN FETCH e.exerciseSubmission WHERE e.teacher = :id")
    List<UserGradeExerciseSubmission> findByTeacherId2(UUID id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission WHERE e.exerciseSubmission = :id")
    List<UserGradeExerciseSubmission> findByExerciseSubmissionId1(UUID id);

    @Query("FROM UserGradeExerciseSubmission e JOIN FETCH e.exerciseSubmission  JOIN FETCH e.teacher  WHERE e.exerciseSubmission = :id")
    List<UserGradeExerciseSubmission> findByExerciseSubmissionId2(UUID id);
    
    boolean existsById(UUID id);
    boolean existsByExerciseSubmissionId(UUID id);


    @Query("DELETE FROM UserGradeExerciseSubmission e WHERE e.teacher = ?1 AND e.exerciseSubmission = ?2")
    void deleteById(UUID teacher_id, UUID exercise_submission_id);
}
