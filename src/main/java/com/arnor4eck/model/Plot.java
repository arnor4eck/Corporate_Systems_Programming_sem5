package com.arnor4eck.model;

import com.arnor4eck.util.enums.PlotStatus;
import org.jetbrains.annotations.Nullable;

public record Plot(
        int id,
        int sectorId,
        int rowNumber,
        int plotNumber,
        PlotStatus status,
        float lengthCm,
        float widthCm ,
        @Nullable String coordinates
) {
    public String toExportString() {
        return String.join("; ",
                String.valueOf(id),
                String.valueOf(sectorId),
                String.valueOf(rowNumber),
                String.valueOf(plotNumber),
                status.getValue(),
                String.valueOf(lengthCm),
                String.valueOf(widthCm),
                emptyIfNull(coordinates)
        );
    }

    private String emptyIfNull(Object string) {
        return string == null ? "" : string.toString();
    }
}
