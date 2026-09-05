package com.arnor4eck.model;

import java.time.LocalDateTime;

public record Sector(
        Long id,
        String name,
        String description,
        LocalDateTime createAt
) {

}
