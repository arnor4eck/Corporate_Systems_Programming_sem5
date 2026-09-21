package com.arnor4eck.model;

import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

public record Customer(
        int id,
        String fullName,
        String phone,
        @Nullable String email,
        LocalDateTime createdAt
) {
}
