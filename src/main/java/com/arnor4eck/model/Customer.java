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
    public String toExportString() {
        return String.join("; ",
                String.valueOf(id),
                fullName,
                phone,
                emptyIfNull(email),
                createdAt.toString()
        );
    }

    private String emptyIfNull(Object string) {
        return string == null ? "" : string.toString();
    }
}
