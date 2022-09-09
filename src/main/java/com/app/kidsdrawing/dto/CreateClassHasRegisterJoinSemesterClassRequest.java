package com.app.kidsdrawing.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateClassHasRegisterJoinSemesterClassRequest {
    private Long classes_id;
    private Long user_register_join_semester_id;
    private Integer review_star;
}