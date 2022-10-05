package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.app.kidsdrawing.entity.StudentLeave;

@Repository
public interface StudentLeaveRepository extends JpaRepository <StudentLeave, UUID>{
    @Query("SELECT e FROM StudentLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer ")
    List<StudentLeave> findAll();

    @Query("SELECT e FROM StudentLeave e JOIN FETCH e.classes cl JOIN FETCH cl.userRegisterTeachSemester urt JOIN FETCH urt.teacher te JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer WHERE te.id = ?1")
    List<StudentLeave> findAllStudentLeaveByTeacher(UUID teacher_id);

    @Query(
		value = "SELECT e FROM StudentLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer ",
		countQuery = "SELECT e FROM StudentLeave e INNER JOIN e.classes INNER JOIN e.section INNER JOIN e.student INNER JOIN e.reviewer "
	)
    Page<StudentLeave> findAll(Pageable pageable);

    @Query("FROM StudentLeave e WHERE e.id = :id")
    Optional<StudentLeave> findById1(UUID id);

    @Query("FROM StudentLeave e JOIN FETCH e.classes JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer WHERE e.id = :id")
    Optional<StudentLeave> findById2(UUID id);
    
    boolean existsById(UUID id);
    void deleteById(UUID id);

    @Query("FROM StudentLeave e JOIN FETCH e.student st WHERE st.id = :id")
    List<StudentLeave> findByStudentId1(UUID id);

    @Query("FROM StudentLeave e JOIN FETCH e.student st JOIN FETCH e.reviewer JOIN FETCH e.classes JOIN FETCH e.section WHERE st.id = :id")
    List<StudentLeave> findByStudentId2(UUID id);

    @Query("FROM StudentLeave e JOIN FETCH e.classes cl WHERE cl.id = :id")
    List<StudentLeave> findByClassesId1(UUID id);

    @Query("FROM StudentLeave e JOIN FETCH e.classes cl JOIN FETCH e.section JOIN FETCH e.student JOIN FETCH e.reviewer WHERE cl.id = :id")
    List<StudentLeave> findByClassesId2(UUID id);

    @Query("FROM StudentLeave e JOIN FETCH e.section se WHERE se.id = :id")
    List<StudentLeave> findBySectionId1(UUID id);

    @Query("FROM StudentLeave e JOIN FETCH e.section se JOIN FETCH e.classes JOIN FETCH e.student JOIN FETCH e.reviewer WHERE se.id = :id")
    List<StudentLeave> findBySectionId2(UUID id);

    @Query("FROM StudentLeave e JOIN FETCH e.classes cl JOIN FETCH e.section JOIN FETCH e.student st JOIN FETCH e.reviewer WHERE cl.id = ?1 AND st.id = ?2")
    List<StudentLeave> findByClassesAndStudent(UUID class_id ,UUID student_id);
}
