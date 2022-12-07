package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import com.app.kidsdrawing.dto.CreateExerciseSubmissionRequest;
import com.app.kidsdrawing.dto.GetExerciseSubmissionResponse;
import com.app.kidsdrawing.dto.GetFinalScoreForStudentResponse;
import com.app.kidsdrawing.dto.GetUserGradeExerciseSubmissionResponse;
import com.app.kidsdrawing.entity.ExerciseSubmission;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.Exercise;
import com.app.kidsdrawing.exception.ArtAgeNotDeleteException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.ExerciseRepository;
import com.app.kidsdrawing.repository.ExerciseSubmissionRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.ExerciseSubmissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ExerciseSubmissionServiceImpl implements ExerciseSubmissionService {
    
    private final ExerciseSubmissionRepository exerciseSubmissionRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final ClassesRepository classRepository;
    private static float exam = 0;


    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmission() {
        List<GetExerciseSubmissionResponse> allExerciseSubmissionResponses = new ArrayList<>();
        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAll();
        listExerciseSubmission.forEach(content -> {
            GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                .id(content.getId())
                .exercise_id(content.getExercise().getId())
                .student_id(content.getStudent().getId())
                .image_url(content.getImage_url())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            allExerciseSubmissionResponses.add(exerciseSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("ExerciseSubmission", allExerciseSubmissionResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByExerciseAndStudent(Long exercise_id, Long student_id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findByExerciseIdAndStudentId(exercise_id, student_id);
        listExerciseSubmission.forEach(content -> {
            GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                .id(content.getId())
                .exercise_id(content.getExercise().getId())
                .student_id(content.getStudent().getId())
                .image_url(content.getImage_url())
                .create_time(content.getCreate_time())
                .update_time(content.getUpdate_time())
                .build();
            exerciseResponses.add(exerciseSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("ExerciseSubmission", exerciseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByStudentId(Long id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> exerciseGradeResponses = new ArrayList<>();
        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findByStudentId2(id);

        List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream().filter(animal -> animal.getScore() != null).collect(Collectors.toList());


        listExerciseSubmission.forEach(content -> {
            if (exerciseSubmissionGrade.contains(content)) {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                    .id(content.getId())
                    .exercise_id(content.getExercise().getId())
                    .student_id(content.getStudent().getId())
                    .exercise_name(content.getExercise().getName())
                    .student_name(content.getStudent().getUsername() + " - " + content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .image_url(content.getImage_url())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                exerciseGradeResponses.add(exerciseSubmissionResponse);
            }
            else {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                    .id(content.getId())
                    .exercise_id(content.getExercise().getId())
                    .student_id(content.getStudent().getId())
                    .exercise_name(content.getExercise().getName())
                    .student_name(content.getStudent().getUsername() + " - " +content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .image_url(content.getImage_url())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                exerciseResponses.add(exerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", exerciseResponses);
        response.put("exercise_graded", exerciseGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByExerciseId(Long id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> exerciseGradeResponses = new ArrayList<>();
        List<ExerciseSubmission> exerciseSubmissionByExercise = exerciseSubmissionRepository.findByExerciseId2(id); 
        
        List<ExerciseSubmission> exerciseSubmissionGrade = exerciseSubmissionByExercise.stream().filter(animal -> animal.getScore() != null).collect(Collectors.toList());
        exerciseSubmissionByExercise.forEach(content -> {
            if (exerciseSubmissionGrade.contains(content)) {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                    .id(content.getId())
                    .exercise_id(content.getExercise().getId())
                    .student_id(content.getStudent().getId())
                    .exercise_name(content.getExercise().getName())
                    .student_name(content.getStudent().getUsername() + " - " +content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .image_url(content.getImage_url())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                exerciseGradeResponses.add(exerciseSubmissionResponse);
            }
            else {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                    .id(content.getId())
                    .exercise_id(content.getExercise().getId())
                    .student_id(content.getStudent().getId())
                    .exercise_name(content.getExercise().getName())
                    .student_name(content.getStudent().getUsername() + " - " + content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .image_url(content.getImage_url())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                exerciseResponses.add(exerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", exerciseResponses);
        response.put("exercise_graded", exerciseGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionBySectionAndStudent(Long section_id, Long student_id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> exerciseGradeResponses = new ArrayList<>();

        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAllExerciseSubmissionBySectionAndStudent(section_id, student_id);
        List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream().filter(animal -> animal.getScore() != null).collect(Collectors.toList());

        listExerciseSubmission.forEach(content -> {
            if (exerciseSubmissionGrade.contains(content)) {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                    .id(content.getId())
                    .exercise_id(content.getExercise().getId())
                    .student_id(content.getStudent().getId())
                    .exercise_name(content.getExercise().getName())
                    .student_name(content.getStudent().getUsername() + " - " + content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .image_url(content.getImage_url())
                    .exercise_description(content.getExercise().getDescription())
                    .exercise_deadline(content.getExercise().getDeadline())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                exerciseGradeResponses.add(exerciseSubmissionResponse);
            }
            else {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                    .id(content.getId())
                    .exercise_id(content.getExercise().getId())
                    .student_id(content.getStudent().getId())
                    .exercise_name(content.getExercise().getName())
                    .exercise_description(content.getExercise().getDescription())
                    
                    .exercise_deadline(content.getExercise().getDeadline())
                    .student_name(content.getStudent().getUsername() + " - " + content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .image_url(content.getImage_url())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                exerciseResponses.add(exerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", exerciseResponses);
        response.put("exercise_graded", exerciseGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassAndParent(Long class_id, Long parent_id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> exerciseGradeResponses = new ArrayList<>();
        List<User> pageUser = userRepository.findByParentId(parent_id);
        pageUser.forEach(student -> {
            List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository
                    .findAllExerciseSubmissionByClassAndStudent(class_id, student.getId());

            List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream().filter(animal -> animal.getScore() != null).collect(Collectors.toList());

            listExerciseSubmission.forEach(content -> {
                if (exerciseSubmissionGrade.contains(content)) {
                    GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                            .id(content.getId())
                            .exercise_id(content.getExercise().getId())
                            .student_id(content.getStudent().getId())
                            .exercise_name(content.getExercise().getName())
                            .student_name(
                                content.getStudent().getUsername() + " - " + content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                            .image_url(content.getImage_url())
                            .exercise_description(content.getExercise().getDescription())
                            .exercise_deadline(content.getExercise().getDeadline())
                            .create_time(content.getCreate_time())
                            .update_time(content.getUpdate_time())
                            .build();
                    exerciseGradeResponses.add(exerciseSubmissionResponse);
                } else {
                    GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                            .id(content.getId())
                            .exercise_id(content.getExercise().getId())
                            .student_id(content.getStudent().getId())
                            .exercise_name(content.getExercise().getName())
                            .exercise_description(content.getExercise().getDescription())
                            .exercise_deadline(content.getExercise().getDeadline())
                            .student_name(
                                content.getStudent().getUsername() + " - " + content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                            .image_url(content.getImage_url())
                            .create_time(content.getCreate_time())
                            .update_time(content.getUpdate_time())
                            .build();
                    exerciseResponses.add(exerciseSubmissionResponse);
                }
            });
        });
        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", exerciseResponses);
        response.put("exercise_graded", exerciseGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassAndStudent(Long class_id, Long student_id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> exerciseGradeResponses = new ArrayList<>();

        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAllExerciseSubmissionByClassAndStudent(class_id, student_id);
        List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream().filter(animal -> animal.getScore() != null).collect(Collectors.toList());

        listExerciseSubmission.forEach(content -> {
            if (exerciseSubmissionGrade.contains(content)) {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                    .id(content.getId())
                    .exercise_id(content.getExercise().getId())
                    .student_id(content.getStudent().getId())
                    .exercise_name(content.getExercise().getName())
                    .student_name(content.getStudent().getUsername() + " - " + content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .image_url(content.getImage_url())
                    .exercise_description(content.getExercise().getDescription())
                    
                    .exercise_deadline(content.getExercise().getDeadline())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                exerciseGradeResponses.add(exerciseSubmissionResponse);
            }
            else {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                    .id(content.getId())
                    .exercise_id(content.getExercise().getId())
                    .student_id(content.getStudent().getId())
                    .exercise_name(content.getExercise().getName())
                    .exercise_description(content.getExercise().getDescription())
                    
                    .exercise_deadline(content.getExercise().getDeadline())
                    .student_name(content.getStudent().getUsername() + " - " + content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .image_url(content.getImage_url())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                exerciseResponses.add(exerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", exerciseResponses);
        response.put("exercise_graded", exerciseGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllExerciseSubmissionByClassId(Long id) {
        List<GetExerciseSubmissionResponse> exerciseResponses = new ArrayList<>();
        List<GetExerciseSubmissionResponse> exerciseGradeResponses = new ArrayList<>();

        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAllExerciseSubmissionByClass(id);

        List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream().filter(animal -> animal.getScore() != null).collect(Collectors.toList());

        listExerciseSubmission.forEach(content -> {
            if (exerciseSubmissionGrade.contains(content)) {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                    .id(content.getId())
                    .exercise_id(content.getExercise().getId())
                    .student_id(content.getStudent().getId())
                    .exercise_name(content.getExercise().getName())
                    .student_name(content.getStudent().getUsername() + " - " + content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .image_url(content.getImage_url())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                exerciseGradeResponses.add(exerciseSubmissionResponse);
            }
            else {
                GetExerciseSubmissionResponse exerciseSubmissionResponse = GetExerciseSubmissionResponse.builder()
                    .id(content.getId())
                    .exercise_id(content.getExercise().getId())
                    .student_id(content.getStudent().getId())
                    .exercise_name(content.getExercise().getName())
                    .student_name(content.getStudent().getUsername() + " - " + content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .image_url(content.getImage_url())
                    .create_time(content.getCreate_time())
                    .update_time(content.getUpdate_time())
                    .build();
                exerciseResponses.add(exerciseSubmissionResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("exercise_not_graded", exerciseResponses);
        response.put("exercise_graded", exerciseGradeResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getFinalGradeAndReviewForParentAndClasses(Long parent_id, Long classes_id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<User> listChilds = userRepository.findByParentId(parent_id);
        List<Float> final_score = new ArrayList<>();
        listChilds.forEach(student -> {
            List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAllExerciseSubmissionByClassAndStudent(classes_id, student.getId());
            List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream().filter(animal -> animal.getScore() != null).collect(Collectors.toList());
            List<Exercise> allExerciseByClass = exerciseRepository.findAllExerciseByClass(classes_id);
            exam = (float) 0;
            exerciseSubmissionGrade.forEach(content -> {
                    exam = exam + content.getScore();
                    GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                        .student_id(content.getStudent().getId())
                        .student_name(content.getStudent().getUsername() + " - " + content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                        .exercise_name(content.getExercise().getName())
                        .exercise_id(content.getExercise().getId())
                        .time_submit(content.getUpdate_time())
                        .image_url(content.getImage_url())
                        .description(content.getExercise().getDescription())
                        .deadline(content.getExercise().getDeadline())
                        .exercise_submission_id(content.getId())
                        .feedback(content.getFeedback())
                        .score(content.getScore())
                        .time(content.getTime())
                        .build();
                    allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
            });

            final_score.add(exam / allExerciseByClass.size());
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        response.put("final_grade", final_score);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public ResponseEntity<Map<String, Object>> getFinalGradeAndReviewForStudentAndClasses(Long student_id, Long classes_id) {
        List<GetUserGradeExerciseSubmissionResponse> allUserGradeExerciseSubmissionResponses = new ArrayList<>();
        List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAllExerciseSubmissionByClassAndStudent(classes_id, student_id);
        List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream().filter(animal -> animal.getScore() != null).collect(Collectors.toList());
        List<Exercise> allExerciseByClass = exerciseRepository.findAllExerciseByClass(classes_id);
        exam = (float) 0;
        exerciseSubmissionGrade.forEach(content -> {
                exam = exam + content.getScore() ;
                GetUserGradeExerciseSubmissionResponse userGradeExerciseSubmissionResponse = GetUserGradeExerciseSubmissionResponse.builder()
                    .student_id(content.getStudent().getId())
                    .student_name(content.getStudent().getUsername() + " - " + content.getStudent().getFirstName() + " " + content.getStudent().getLastName())
                    .exercise_name(content.getExercise().getName())
                    .exercise_id(content.getExercise().getId())
                    .time_submit(content.getUpdate_time())
                    .image_url(content.getImage_url())
                    .description(content.getExercise().getDescription())
                    .deadline(content.getExercise().getDeadline())
                    .exercise_submission_id(content.getId())
                    .feedback(content.getFeedback())
                    .score(content.getScore())
                    .time(content.getTime())
                    .build();
                allUserGradeExerciseSubmissionResponses.add(userGradeExerciseSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("UserGradeExerciseSubmission", allUserGradeExerciseSubmissionResponses);
        response.put("final_grade", exam / allExerciseByClass.size());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override 
    public ResponseEntity<Map<String, Object>> getAllFinalGradeAForStudent(Long student_id) {
        List<GetFinalScoreForStudentResponse> allFinalScoreForStudent = new ArrayList<>();
        List<Classes> allClassForStudent = classRepository.findAllByStudent2(student_id);
        
        allClassForStudent.forEach(element -> {
            List<Exercise> allExerciseByClass = exerciseRepository.findAllExerciseByClass(element.getId());
            List<ExerciseSubmission> listExerciseSubmission = exerciseSubmissionRepository.findAllExerciseSubmissionByClassAndStudent(element.getId(), student_id);
            List<ExerciseSubmission> exerciseSubmissionGrade = listExerciseSubmission.stream().filter(animal -> animal.getScore() != null).collect(Collectors.toList());
            exam = (float) 0;
            exerciseSubmissionGrade.forEach(content -> {
                exam = exam + content.getScore() ;
            });
                GetFinalScoreForStudentResponse userGradeExerciseSubmissionResponse = GetFinalScoreForStudentResponse.builder()
                    .final_score(exam / allExerciseByClass.size())  
                    .course_id(element.getUserRegisterTeachSemester().getSemesterClass().getCourse().getId()) 
                    .course_name(element.getUserRegisterTeachSemester().getSemesterClass().getCourse().getName())              
                    .build();
                allFinalScoreForStudent.add(userGradeExerciseSubmissionResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("final_grade", allFinalScoreForStudent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetExerciseSubmissionResponse getExerciseSubmissionById(Long id) {
        Optional<ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById2(id);
        ExerciseSubmission exerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
        });

        return GetExerciseSubmissionResponse.builder()
            .id(exerciseSubmission.getId())
            .exercise_id(exerciseSubmission.getExercise().getId())
            .student_id(exerciseSubmission.getStudent().getId())
            .exercise_name(exerciseSubmission.getExercise().getName())
            .student_name(exerciseSubmission.getStudent().getUsername() + " - " + exerciseSubmission.getStudent().getFirstName() + " " + exerciseSubmission.getStudent().getLastName())
            .image_url(exerciseSubmission.getImage_url())
            .create_time(exerciseSubmission.getCreate_time())
            .update_time(exerciseSubmission.getUpdate_time())
            .build();
    }

    @Override
    public Long createExerciseSubmission(CreateExerciseSubmissionRequest createExerciseSubmissionRequest) {

        Optional <User> studentOpt = userRepository.findById1(createExerciseSubmissionRequest.getStudent_id());
        User student = studentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_student.not_found");
        });

        Optional <Exercise> exerciseOpt = exerciseRepository.findById1(createExerciseSubmissionRequest.getExercise_id());
        Exercise exercise = exerciseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.exercise.not_found");
        });

        LocalDateTime time_now = LocalDateTime.now();

        if (time_now.isAfter((exercise.getDeadline()))) {
            throw new EntityNotFoundException("exception.deadline.not_found");
        }
        else {
            ExerciseSubmission savedExerciseSubmission = ExerciseSubmission.builder()
                .student(student)
                .exercise(exercise)
                .image_url(createExerciseSubmissionRequest.getImage_url())
                .build();
            exerciseSubmissionRepository.save(savedExerciseSubmission);

            return savedExerciseSubmission.getId();
        }
    }

    @Override
    public Long removeExerciseSubmissionById(Long id) {
        Optional<ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById1(id);
        ExerciseSubmission exerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
        });

        LocalDateTime time_now = LocalDateTime.now();
        if (time_now.isAfter(exerciseSubmission.getExercise().getDeadline())) {
            throw new ArtAgeNotDeleteException("exception.Exercise_Deadline.not_delete");
        }

        exerciseSubmissionRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateExerciseSubmissionById(Long id, CreateExerciseSubmissionRequest createExerciseSubmissionRequest) {
        Optional<ExerciseSubmission> exerciseSubmissionOpt = exerciseSubmissionRepository.findById1(id);
        ExerciseSubmission updatedExerciseSubmission = exerciseSubmissionOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ExerciseSubmission.not_found");
        });

        updatedExerciseSubmission.setImage_url(createExerciseSubmissionRequest.getImage_url());

        return updatedExerciseSubmission.getId();
    }
}
