package com.arnor4eck.model;

import com.arnor4eck.util.enums.RequestStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Request(
        int id,
        int costumerId,
        int employeeId,
        int plotId,
        String deceasedFullName,
        LocalDate deceasedBirthday,
        LocalDate deceasedDeathday,
        String deceasedCertificate,
        RequestStatus status,
        String totalCost,
        String note,
        LocalDateTime createdAt
) {
}
