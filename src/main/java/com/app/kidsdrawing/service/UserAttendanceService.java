package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateUserAttendanceRequest;
import com.app.kidsdrawing.dto.GetCheckUserAttendanceResponse;
import com.app.kidsdrawing.dto.GetUserAttendanceResponse;

public interface UserAttendanceService {
    ResponseEntity<Map<String, Object>> getAllUserAttendance();
    ResponseEntity<Map<String, Object>> getAllUserAttendanceBySection(Long id);
    GetCheckUserAttendanceResponse checkUserAttendanceBySectionAndStudent(Long section_id, Long student_id);
    ResponseEntity<Map<String, Object>> getAllUserAttendanceByClassAndStudent(Long classes_id, Long student_id);
    ResponseEntity<Map<String, Object>> getAllUserAttendanceByClass(Long classes_id);
    ResponseEntity<Map<String, Object>> getAllUserAttendanceByStudent(Long id);
    GetUserAttendanceResponse getAllUserAttendanceBySectionAndStudent(Long section_id, Long student_id);
    GetUserAttendanceResponse getUserAttendanceById(Long id);
    Long createUserAttendance(CreateUserAttendanceRequest createUserAttendanceRequest);
    Long removeUserAttendanceById(Long id);
    Long updateUserAttendanceById(Long id, CreateUserAttendanceRequest createUserAttendanceRequest);
    Long updateUserAttendanceBySectionAndStudent(Long section_id, Long student_id);
}
