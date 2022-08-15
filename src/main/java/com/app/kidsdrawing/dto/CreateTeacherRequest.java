package com.app.kidsdrawing.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

@Data
public class CreateTeacherRequest {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String profile_image_url;
    private String sex;
    private String phone;
    private String address;
    private Long parent_id;
    private Set<String> roleNames;
}