package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.kidsdrawing.dto.CreateReviewStudentLeaveRequest;
import com.app.kidsdrawing.dto.CreateStudentLeaveRequest;
import com.app.kidsdrawing.dto.GetStudentLeaveResponse;
import com.app.kidsdrawing.service.StudentLeaveService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/student-leave")
public class StudentLeaveController {
    private final StudentLeaveService  studentLeaveService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<ResponseEntity<Map<String, Object>>> getAllStudentLeave() {
        return ResponseEntity.ok().body(studentLeaveService.getAllStudentLeave());
    } 

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createStudentLeave(@RequestBody CreateStudentLeaveRequest createStudentLeaveRequest) {
        Long studentLeaveId = studentLeaveService.createStudentLeave(createStudentLeaveRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{studentLeaveId}")
                .buildAndExpand(studentLeaveId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateStudentLeave(@PathVariable Long id, @RequestBody CreateStudentLeaveRequest createStudentLeaveRequest) {
        Long studentLeaveId = studentLeaveService.updateStudentLeaveById(id,createStudentLeaveRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(studentLeaveId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value = "/admin/{id}")
    public ResponseEntity<String> updateStatusStudentLeave(@PathVariable Long id, @RequestBody CreateReviewStudentLeaveRequest createReviewStudentLeaveRequest) {
        Long studentLeaveId = studentLeaveService.updateStatusStudentLeaveById(id,createReviewStudentLeaveRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(studentLeaveId).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetStudentLeaveResponse> getStudentLeaveById(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentLeaveService.getStudentLeaveById(id));
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteStudentLeaveById(@PathVariable Long id) {
        Long studentLeaveId = studentLeaveService.removeStudentLeaveById(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(studentLeaveId).toUri();
        return ResponseEntity.created(location).build();
    }
    
}