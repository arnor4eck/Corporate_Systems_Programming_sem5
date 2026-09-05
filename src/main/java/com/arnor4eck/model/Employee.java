package com.arnor4eck.model;

import com.arnor4eck.util.enums.Role;

import java.time.LocalDateTime;

public record Employee (
        Long id,
        String fullName,
        Role role,
        String passwordHash,
        boolean isActive,
        LocalDateTime createAt
)
{}
