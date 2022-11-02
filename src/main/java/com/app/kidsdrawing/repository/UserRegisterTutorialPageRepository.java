package com.app.kidsdrawing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kidsdrawing.entity.UserRegisterTutorialPage;

@Repository
public interface UserRegisterTutorialPageRepository extends JpaRepository <UserRegisterTutorialPage, Long>{
    @Query("SELECT e FROM UserRegisterTutorialPage e JOIN FETCH e.userRegisterTutorial ORDER BY e.id")
    List<UserRegisterTutorialPage> findAll();

    @Query(
		value = "SELECT e FROM UserRegisterTutorialPage e JOIN FETCH e.userRegisterTutorial ORDER BY e.id",
		countQuery = "SELECT e FROM UserRegisterTutorialPage e INNER JOIN e.userRegisterTutorial "
	)
    Page<UserRegisterTutorialPage> findAll(Pageable pageable);

    @Query("FROM UserRegisterTutorialPage e WHERE e.id = ?1")
    Optional<UserRegisterTutorialPage> findById1(Long id);

    @Query("FROM UserRegisterTutorialPage e JOIN FETCH e.userRegisterTutorial WHERE e.id = ?1")
    Optional<UserRegisterTutorialPage> findById2(Long id);

    @Query("FROM UserRegisterTutorialPage e JOIN FETCH e.userRegisterTutorial urt WHERE urt.id = ?1 ORDER BY e.id")
    List<UserRegisterTutorialPage> findByUserRegisterTutorialId(Long id);
    
    boolean existsById(Long id);
    void deleteById(Long id);
}
