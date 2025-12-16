package com.crear.auth.dto;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import com.crear.auth.model.Role;

import lombok.Data;

public record UserDto(
                String name,
                String email,
                boolean enable,
                Set<Role> role,
                String image,
                Instant createdAt,
                Instant updatedAt) {

}
