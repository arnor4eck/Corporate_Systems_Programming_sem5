package com.arnor4eck.model;

import java.time.LocalDateTime;

public record Sector(
        int id,
        String name,
        LocalDateTime createdAt
) {

}
