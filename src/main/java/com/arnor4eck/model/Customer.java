package com.arnor4eck.model;

import java.time.LocalDateTime;

public record Customer(
        Long id,
        String fullName,
        String phone,
        String email,
        LocalDateTime createAt
) {
}
