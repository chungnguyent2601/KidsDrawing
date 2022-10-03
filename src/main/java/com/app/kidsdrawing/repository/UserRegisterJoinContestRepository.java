package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.UserRegisterJoinContest;

@Repository
public interface UserRegisterJoinContestRepository extends JpaRepository <UserRegisterJoinContest, UUID>{
    @Query("SELECT e FROM UserRegisterJoinContest e JOIN FETCH e.student  JOIN FETCH e.contest ORDER BY e.id")
    List<UserRegisterJoinContest> findAll();

    @Query(
		value = "SELECT e FROM UserRegisterJoinContest e JOIN FETCH e.student  JOIN FETCH e.contest ORDER BY e.id",
		countQuery = "SELECT e FROM UserRegisterJoinContest e INNER JOIN e.student  INNER JOIN e.contest ORDER BY e.id"
	)
    Page<UserRegisterJoinContest> findAll(Pageable pageable);

    @Query("FROM UserRegisterJoinContest e JOIN FETCH e.student  JOIN FETCH e.contest WHERE e.id = :id")
    Optional<UserRegisterJoinContest> findById(UUID id);
    
    @Query("FROM UserRegisterJoinContest e JOIN FETCH e.student  JOIN FETCH e.contest WHERE e.contest = :id")
    List<UserRegisterJoinContest> findByContestId(UUID id);

    @Query("FROM UserRegisterJoinContest e JOIN FETCH e.student  JOIN FETCH e.contest WHERE e.student = :id")
    List<UserRegisterJoinContest> findByStudentId(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);
}