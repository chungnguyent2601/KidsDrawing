package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateSemesterClassRequest;
import com.app.kidsdrawing.dto.GetSemesterClassResponse;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.exception.SemesterClassAlreadyCreateException;
import com.app.kidsdrawing.repository.CourseRepository;
import com.app.kidsdrawing.repository.SemesterClassRepository;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.service.SemesterClassService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SemesterClassServiceImpl implements SemesterClassService {

    private final SemesterClassRepository semesterClassRepository;
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClass() {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findAll();
        pageSemesterClass.forEach(semesterClass -> {
            GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                    .id(semesterClass.getId())
                    .name(semesterClass.getName())
                    .creation_id(semesterClass.getSemester().getId())
                    .semester_name(semesterClass.getSemester().getName())
                    .course_id(semesterClass.getCourse().getId())
                    .course_name(semesterClass.getCourse().getName())
                    .max_participant(semesterClass.getMax_participant())
                    .build();
            allSemesterClassResponses.add(semesterClassResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetSemesterClassResponse getSemesterClassById(Long id){
        Optional<SemesterClass> semesterClassOpt = semesterClassRepository.findById(id);
        SemesterClass semesterClass = semesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        return GetSemesterClassResponse.builder()
            .id(semesterClass.getId())
            .name(semesterClass.getName())
            .creation_id(semesterClass.getSemester().getId())
            .semester_name(semesterClass.getSemester().getName())
            .course_id(semesterClass.getCourse().getId())
            .course_name(semesterClass.getCourse().getName())
            .max_participant(semesterClass.getMax_participant())
            .build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassBySemester(Long id) {
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findAll();
        pageSemesterClass.forEach(semesterClass -> {
            if (semesterClass.getSemester().getId() == id){
                GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                    .id(semesterClass.getId())
                    .name(semesterClass.getName())
                    .creation_id(semesterClass.getSemester().getId())
                    .semester_name(semesterClass.getSemester().getName())
                    .course_id(semesterClass.getCourse().getId())
                    .course_name(semesterClass.getCourse().getName())
                    .max_participant(semesterClass.getMax_participant())
                    .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterClassByCourse(Long id){
        List<GetSemesterClassResponse> allSemesterClassResponses = new ArrayList<>();
        List<SemesterClass> pageSemesterClass = semesterClassRepository.findAll();
        pageSemesterClass.forEach(semesterClass -> {
            if (semesterClass.getCourse().getId() == id){
                GetSemesterClassResponse semesterClassResponse = GetSemesterClassResponse.builder()
                    .id(semesterClass.getId())
                    .name(semesterClass.getName())
                    .creation_id(semesterClass.getSemester().getId())
                    .semester_name(semesterClass.getSemester().getName())
                    .course_id(semesterClass.getCourse().getId())
                    .course_name(semesterClass.getCourse().getName())
                    .max_participant(semesterClass.getMax_participant())
                .build();
                allSemesterClassResponses.add(semesterClassResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semester_classes", allSemesterClassResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public Long createSemesterClass(CreateSemesterClassRequest createSemesterClassRequest) {
        Optional<Semester> semesterOpt = semesterRepository.findById(createSemesterClassRequest.getCreation_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester.not_found");
        });

        Optional<Course> courseOpt = courseRepository.findById(createSemesterClassRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        if (semesterClassRepository.existsByName(createSemesterClassRequest.getName())) {
            throw new SemesterClassAlreadyCreateException("exception.semester_name.semester_class_taken");
        }

        SemesterClass savedSemesterClass = SemesterClass.builder()
                .semester(semester)
                .course(course)
                .name(createSemesterClassRequest.getName())
                .max_participant(createSemesterClassRequest.getMax_participant())
                .build();
        semesterClassRepository.save(savedSemesterClass);

        return savedSemesterClass.getId();
    }

    @Override
    public Long removeSemesterClassById(Long id) {
        Optional<SemesterClass> SemesterClassOpt = semesterClassRepository.findById(id);
        SemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        semesterClassRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateSemesterClassById(Long id, CreateSemesterClassRequest createSemesterClassRequest) {
        Optional<SemesterClass> SemesterClassOpt = semesterClassRepository.findById(id);
        SemesterClass updatedSemesterClass = SemesterClassOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.SemesterClass.not_found");
        });

        Optional<Semester> semesterOpt = semesterRepository.findById(createSemesterClassRequest.getCreation_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.semester.not_found");
        });

        Optional<Course> courseOpt = courseRepository.findById(createSemesterClassRequest.getCourse_id());
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.course.not_found");
        });

        if (createSemesterClassRequest.getName().equals(updatedSemesterClass.getName()) == false){
            if (semesterClassRepository.existsByName(createSemesterClassRequest.getName())) {
                throw new SemesterClassAlreadyCreateException("exception.semester_name.semester_class_taken");
            }
        }

        updatedSemesterClass.setSemester(semester);
        updatedSemesterClass.setCourse(course);
        updatedSemesterClass.setMax_participant(createSemesterClassRequest.getMax_participant());
        updatedSemesterClass.setName(createSemesterClassRequest.getName());
        semesterClassRepository.save(updatedSemesterClass);

        return updatedSemesterClass.getId();
    }
}
