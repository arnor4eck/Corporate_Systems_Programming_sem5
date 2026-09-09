package com.arnor4eck.model;

import java.time.LocalDateTime;

public record Customer(
        int id,
        String fullName,
        String phone,
        String email,
        LocalDateTime createdAt
) {
}
