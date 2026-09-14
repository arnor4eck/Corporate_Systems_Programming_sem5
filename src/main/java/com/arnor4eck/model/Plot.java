package com.arnor4eck.model;

import com.arnor4eck.util.enums.PlotStatus;

public record Plot(
        int id,
        int sectorId,
        int rowNumber,
        int plotNumber,
        PlotStatus status,
        float lengthCm,
        float widthCm ,
        String coordinates
) {
}
