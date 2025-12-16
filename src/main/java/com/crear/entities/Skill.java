package com.crear.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "skills", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "skill")
    private Set<StudentSkill> studentSkills;
}
