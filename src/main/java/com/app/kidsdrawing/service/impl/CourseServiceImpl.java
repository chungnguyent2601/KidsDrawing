package com.app.kidsdrawing.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateCourseRequest;
import com.app.kidsdrawing.dto.GetCourseResponse;
import com.app.kidsdrawing.dto.GetCourseTeacherResponse;
import com.app.kidsdrawing.entity.ArtAge;
import com.app.kidsdrawing.entity.ArtLevel;
import com.app.kidsdrawing.entity.ArtType;
import com.app.kidsdrawing.entity.Class;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.entity.SemesterCourse;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.exception.CourseAlreadyCreateException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ArtAgeRepository;
import com.app.kidsdrawing.repository.ArtLevelRepository;
import com.app.kidsdrawing.repository.ArtTypeRepository;
import com.app.kidsdrawing.repository.ClassRepository;
import com.app.kidsdrawing.repository.CourseRepository;
import com.app.kidsdrawing.repository.ScheduleItemRepository;
import com.app.kidsdrawing.repository.SemesterCourseRepository;
import com.app.kidsdrawing.repository.TeacherTeachSemesterRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.CourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ArtAgeRepository artAgeRepository;
    private final ArtTypeRepository artTypeRepository;
    private final ArtLevelRepository artLevelRepository;
    private final ClassRepository classRepository;
    private final TeacherTeachSemesterRepository teacherTeachSemesterRepository;
    private final SemesterCourseRepository semesterCourseRepository;
    private final ScheduleItemRepository scheduleItemRepository;
    private static String schedule = "";

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourse() {
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        List<Course> pageCourse = courseRepository.findAll();
        pageCourse.forEach(course -> {
            GetCourseResponse courseResponse = GetCourseResponse.builder()
                    .id(course.getId())
                    .name(course.getName())
                    .description(course.getDescription())
                    .max_participant(course.getMax_participant())
                    .num_of_section(course.getNum_of_section())
                    .image_url(course.getImage_url())
                    .price(course.getPrice())
                    .is_enabled(course.getIs_enabled())
                    .art_age_id(course.getArtAges().getId())
                    .art_type_id(course.getArtTypes().getId())
                    .art_level_id(course.getArtLevels().getId())
                    .creator_id(course.getUser().getId())
                    .create_time(course.getCreate_time())
                    .update_time(course.getUpdate_time())
                    .build();
            allCourseResponses.add(courseResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses", allCourseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseByArtTypeId(Long id) {
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        List<Course> pageCourse = courseRepository.findAll();
        pageCourse.forEach(course -> {
            if (course.getArtTypes().getId() == id){
                GetCourseResponse courseResponse = GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .max_participant(course.getMax_participant())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .create_time(course.getCreate_time())
                .update_time(course.getUpdate_time())
                .build();
            allCourseResponses.add(courseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses", allCourseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseByArtAgeId(Long id) {
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        List<Course> pageCourse = courseRepository.findAll();
        pageCourse.forEach(course -> {
            if (course.getArtAges().getId() == id){
                GetCourseResponse courseResponse = GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .max_participant(course.getMax_participant())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .create_time(course.getCreate_time())
                .update_time(course.getUpdate_time())
                .build();
            allCourseResponses.add(courseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses", allCourseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseByArtLevelId(Long id) {
        List<GetCourseResponse> allCourseResponses = new ArrayList<>();
        List<Course> pageCourse = courseRepository.findAll();
        pageCourse.forEach(course -> {
            if (course.getArtLevels().getId() == id){
                GetCourseResponse courseResponse = GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .max_participant(course.getMax_participant())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .create_time(course.getCreate_time())
                .update_time(course.getUpdate_time())
                .build();
            allCourseResponses.add(courseResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("courses", allCourseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllCourseByTeacherId(Long id) {
        List<UserRegisterTeachSemester> listTeacherTeachSemester = teacherTeachSemesterRepository.findAll();
        
        // Danh sach dang ki day cua giao vien
        List<UserRegisterTeachSemester> allTeacherTeachSemesterResponses = new ArrayList<>();
        // Danh sach khoa hoc theo ki giao vien da dang ki
        List<SemesterCourse> allRegisteredSemesterCourseResponses = new ArrayList<>();
        listTeacherTeachSemester.forEach(ele -> {
            if (ele.getTeacher().getId() == id){
                allTeacherTeachSemesterResponses.add(ele);
                allRegisteredSemesterCourseResponses.add(ele.getSemesterCourse());
            }
        });

        // Danh sach khoa hoc theo ki giao vien chua dang ki
        List<SemesterCourse> allNotRegisterSemesterCourse = new ArrayList<>();
        LocalDateTime time_now = LocalDateTime.now();
        List<SemesterCourse> pageSemesterCourse = semesterCourseRepository.findAll();
        pageSemesterCourse.forEach(ele -> {
            if (allRegisteredSemesterCourseResponses.contains(ele) == false && time_now.isAfter(ele.getSemester().getStart_time()) == false ){
                allNotRegisterSemesterCourse.add(ele);
            }
        });

        List<GetCourseTeacherResponse> allNotRegisterSemesterCourseResponses = new ArrayList<>();
        allNotRegisterSemesterCourse.forEach(ele -> {
            schedule = "";
            scheduleItemRepository.findAll().forEach(schedule_item -> {
                if (schedule_item.getSchedule().getId() == ele.getSchedule().getId()){
                    if (schedule == ""){
                        schedule = schedule + "Thứ " + schedule_item.getDate_of_week() + " (" + schedule_item.getLessonTime().getStart_time().toString() + " - " + schedule_item.getLessonTime().getEnd_time().toString() +")";
                    }
                    else {
                        schedule = schedule + ", Thứ " + schedule_item.getDate_of_week() + " (" + schedule_item.getLessonTime().getStart_time().toString() + " - " + schedule_item.getLessonTime().getEnd_time().toString() +")";
                    }
                }
            });
            GetCourseTeacherResponse notRegisterSemesterCourseResponse = GetCourseTeacherResponse.builder()
                .course_id(ele.getCourse().getId())
                .semster_course_id(ele.getId())
                .semester_name(ele.getSemester().getName())
                .description(ele.getCourse().getDescription())
                .name(ele.getCourse().getName())
                .art_age_name(ele.getCourse().getArtAges().getName())
                .art_level_name(ele.getCourse().getArtLevels().getName())
                .art_type_name(ele.getCourse().getArtTypes().getName())
                .price(ele.getCourse().getPrice())
                .registration_deadline(ele.getSemester().getStart_time())
                .max_participant(ele.getCourse().getMax_participant())
                .num_of_section(ele.getCourse().getNum_of_section())
                .schedule(schedule)
                .build();

            allNotRegisterSemesterCourseResponses.add(notRegisterSemesterCourseResponse);
        });

        List<Class> allClassResponses = new ArrayList<>();
        List<Class> listClass = classRepository.findAll();

        // Danh sach dang ki giao vien duoc xep lop
        List<UserRegisterTeachSemester> allTeacherRegisterSuccessfullTeachSemesterResponses = new ArrayList<>();
        // Danh sach khoa hoc theo ki giao vien chua dang ki
        List<SemesterCourse> allRegisterSuccessfullSemesterCourse = new ArrayList<>();
        // Danh sach dang ki nhung khong duoc xep lop
        listClass.forEach(ele -> {
            if (allTeacherTeachSemesterResponses.contains(ele.getTeachSemester())){
                allTeacherRegisterSuccessfullTeachSemesterResponses.add(ele.getTeachSemester());
                allRegisterSuccessfullSemesterCourse.add(ele.getTeachSemester().getSemesterCourse());
                allClassResponses.add(ele);
            }
        });

        List<GetCourseTeacherResponse> allRegisterSuccessfullSemesterCourseResponses = new ArrayList<>();
        allRegisterSuccessfullSemesterCourse.forEach(ele -> {
            schedule = "";
            scheduleItemRepository.findAll().forEach(schedule_item -> {
                if (schedule_item.getSchedule().getId() == ele.getSchedule().getId()){
                    if (schedule == ""){
                        schedule = schedule + "Thứ " + schedule_item.getDate_of_week() + " (" + schedule_item.getLessonTime().getStart_time().toString() + " - " + schedule_item.getLessonTime().getEnd_time().toString() +")";
                    }
                    else {
                        schedule = schedule + ", Thứ " + schedule_item.getDate_of_week() + " (" + schedule_item.getLessonTime().getStart_time().toString() + " - " + schedule_item.getLessonTime().getEnd_time().toString() +")";
                    }
                }
            });
            GetCourseTeacherResponse registerSuccessfullSemesterCourseResponse = GetCourseTeacherResponse.builder()
                .course_id(ele.getCourse().getId())
                .semster_course_id(ele.getId())
                .semester_name(ele.getSemester().getName())
                .description(ele.getCourse().getDescription())
                .name(ele.getCourse().getName())
                .art_age_name(ele.getCourse().getArtAges().getName())
                .art_level_name(ele.getCourse().getArtLevels().getName())
                .art_type_name(ele.getCourse().getArtTypes().getName())
                .price(ele.getCourse().getPrice())
                .registration_deadline(ele.getSemester().getStart_time())
                .max_participant(ele.getCourse().getMax_participant())
                .num_of_section(ele.getCourse().getNum_of_section())
                .schedule(schedule)
                .build();

            allRegisterSuccessfullSemesterCourseResponses.add(registerSuccessfullSemesterCourseResponse);
        });


        Map<String, Object> response = new HashMap<>();
        response.put("not_register_courses", allNotRegisterSemesterCourseResponses);
        response.put("register_successfull_courses", allRegisterSuccessfullSemesterCourseResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetCourseResponse getCourseByName(String name) {
        Optional<Course> courseOpt = courseRepository.findByName(name);
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Course.not_found");
        });

        return GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .max_participant(course.getMax_participant())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .create_time(course.getCreate_time())
                .update_time(course.getUpdate_time())
                .build();
    }

    @Override
    public GetCourseResponse getCourseById(Long id){
        Optional<Course> courseOpt = courseRepository.findById(id);
        Course course = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Course.not_found");
        });

        return GetCourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .max_participant(course.getMax_participant())
                .num_of_section(course.getNum_of_section())
                .image_url(course.getImage_url())
                .price(course.getPrice())
                .is_enabled(course.getIs_enabled())
                .art_age_id(course.getArtAges().getId())
                .art_type_id(course.getArtTypes().getId())
                .art_level_id(course.getArtLevels().getId())
                .creator_id(course.getUser().getId())
                .create_time(course.getCreate_time())
                .update_time(course.getUpdate_time())
                .build();
    }

    @Override
    public Long createCourse(CreateCourseRequest createCourseRequest) {
        if (courseRepository.existsByName(createCourseRequest.getName())) {
            throw new CourseAlreadyCreateException("exception.course.course_taken");
        }

        Optional<User> userOpt = userRepository.findById(createCourseRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Optional<ArtAge> artAgeOpt = artAgeRepository.findById(createCourseRequest.getArt_age_id());
        ArtAge artAge = artAgeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtAge.not_found");
        });

        Optional<ArtType> artTypeOpt = artTypeRepository.findById(createCourseRequest.getArt_type_id());
        ArtType artType = artTypeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtType.not_found");
        });

        Optional<ArtLevel> artLevelOpt = artLevelRepository.findById(createCourseRequest.getArt_level_id());
        ArtLevel artLevel = artLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtLevel.not_found");
        });


        Course savedCourse = Course.builder()
                .name(createCourseRequest.getName())
                .description(createCourseRequest.getDescription())
                .max_participant(createCourseRequest.getMax_participant())
                .num_of_section(createCourseRequest.getNum_of_section())
                .image_url(createCourseRequest.getImage_url())
                .price(createCourseRequest.getPrice())
                .is_enabled(createCourseRequest.getIs_enabled())
                .user(user)
                .artAges(artAge)
                .artTypes(artType)
                .artLevels(artLevel)
                .build();
        courseRepository.save(savedCourse);

        return savedCourse.getId();
    }

    @Override
    public Long removeCourseById(Long id) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Course.not_found");
        });
        courseRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateCourseById(Long id, CreateCourseRequest createCourseRequest) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        Course updatedCourse = courseOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Course.not_found");
        });

        Optional<User> userOpt = userRepository.findById(createCourseRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Optional<ArtAge> artAgeOpt = artAgeRepository.findById(createCourseRequest.getArt_age_id());
        ArtAge artAge = artAgeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtAge.not_found");
        });

        Optional<ArtType> artTypeOpt = artTypeRepository.findById(createCourseRequest.getArt_type_id());
        ArtType artType = artTypeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtType.not_found");
        });

        Optional<ArtLevel> artLevelOpt = artLevelRepository.findById(createCourseRequest.getArt_level_id());
        ArtLevel artLevel = artLevelOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ArtLevel.not_found");
        });
        updatedCourse.setName(createCourseRequest.getName());
        updatedCourse.setDescription(createCourseRequest.getDescription());
        updatedCourse.setMax_participant(createCourseRequest.getMax_participant());
        updatedCourse.setNum_of_section(createCourseRequest.getNum_of_section());
        updatedCourse.setImage_url(createCourseRequest.getImage_url());
        updatedCourse.setPrice(createCourseRequest.getPrice());
        updatedCourse.setUser(user);
        updatedCourse.setArtAges(artAge);
        updatedCourse.setArtTypes(artType);
        updatedCourse.setArtLevels(artLevel);
        courseRepository.save(updatedCourse);

        return updatedCourse.getId();
    }
}
