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

    public String toExportString() {
        return String.join("; ",
                String.valueOf(this.id()),
                String.valueOf(this.costumerId()),
                String.valueOf(this.employeeId()),
                String.valueOf(this.plotId()),
                emptyIfNull(this.deceasedFullName()),
                emptyIfNull(this.deceasedBirthday()),
                emptyIfNull(this.deceasedDeathday()),
                emptyIfNull(this.deceasedCertificate()),
                this.status().getValue(),
                this.totalCost(),
                emptyIfNull(this.note()),
                this.createdAt().toString()
        );
    }

    private String emptyIfNull(Object string) {
        return string == null ? "" : string.toString();
    }
}
