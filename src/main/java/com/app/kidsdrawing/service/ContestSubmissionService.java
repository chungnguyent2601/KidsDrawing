package com.app.kidsdrawing.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateContestSubmissionRequest;
import com.app.kidsdrawing.dto.GetContestSubmissionResponse;


public interface ContestSubmissionService {
    ResponseEntity<Map<String, Object>> getAllContestSubmission();
    ResponseEntity<Map<String, Object>> getAllContestSubmissionByStudentId(Long id);
    ResponseEntity<Map<String, Object>> getAllContestSubmissionByContestId(Long id);
    ResponseEntity<Map<String, Object>> getAllContestSubmissionByTeacherAndContest(Long teacher_id, Long contest_id);
    GetContestSubmissionResponse getContestSubmissionById(Long id);
    Long generationContestSubmissionForTeacher(Long contest_id);
    Long createContestSubmission(CreateContestSubmissionRequest createContestSubmissionRequest);
    Long removeContestSubmissionById(Long id);
    Long updateContestSubmissionById(Long id, CreateContestSubmissionRequest createContestSubmissionRequest);
}
