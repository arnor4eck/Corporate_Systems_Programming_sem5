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
}
