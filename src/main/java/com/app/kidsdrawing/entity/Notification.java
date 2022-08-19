package com.app.kidsdrawing.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Builder.Default()
    @Column(name = "time")
    @CreationTimestamp
    private LocalDateTime time = LocalDateTime.now();

    @OneToOne(mappedBy="notification")
    private UserReadNotification userReadNotification;

    @OneToOne(mappedBy="notification")
    private AnonymousNotification anonymousNotification;

    @OneToOne
    @JoinTable(name = "class_notification", joinColumns = {
            @JoinColumn(name = "nontification_id", referencedColumnName = "id", unique = true)
    }, inverseJoinColumns = {
            @JoinColumn(name = "class_id", referencedColumnName = "id")
    })
    private Class classes;
}