package com.app.kidsdrawing.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "User_Register_Join_Contest")
public class UserRegisterJoinContest {
    @Id
    @GenericGenerator(name = "id", strategy = "com.app.kidsdrawing.entity.generator.UserRegisterJoinContestIdGenerator")
    @GeneratedValue(generator = "id")
    @Column(name = "id")
    private Long  id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "contest_id", referencedColumnName = "id")
    private Contest contest;
}
