package com.app.kidsdrawing.entity;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "art_age")
public class ArtAge {
    @Id
    @GenericGenerator(name = "id", strategy = "com.app.kidsdrawing.entity.generator.ArtAgeIdGenerator")
    @GeneratedValue(generator = "id")
    @Column(name = "id")
    private Long  id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy="artAges")
    private Set<Contest> contests;

    @OneToMany(mappedBy="artAges")
    private Set<Course> courses;
}
