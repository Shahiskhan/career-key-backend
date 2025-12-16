package com.crear.auth.model;

public enum Role {
    STUDENT,
    UNIVERSITY,
    ADMIN,
    HEC
}

// import jakarta.persistence.*;
// import lombok.*;

// import java.util.UUID;

// @Entity
// @Table(name = "roles")
// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
// @Builder
// public class Role {
// @Id
// @Builder.Default
// private UUID id = UUID.randomUUID();
// @Column(unique = true, nullable = false)
// private String name;

// }