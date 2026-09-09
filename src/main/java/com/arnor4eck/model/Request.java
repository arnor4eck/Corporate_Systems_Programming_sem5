package com.arnor4eck.model;

import com.arnor4eck.util.enums.RequestStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Request(
        int id,
        Customer costumer,
        Employee employee,
        Plot plot,
        String deceasedFullName,
        LocalDate deceasedBirthday,
        LocalDate deceasedDeathday,
        String deceased_certificate,
        RequestStatus status,
        LocalDateTime createdAt,
        String totalCost,
        String note
) {
}
