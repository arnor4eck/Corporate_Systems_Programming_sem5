package com.arnor4eck.model;

import com.arnor4eck.util.enums.PlotStatus;

public record Plot(
        int id,
        Sector sector,
        int rowNumber,
        int plotNumber,
        PlotStatus status,
        int lengthCm,
        float widthCm ,
        String coordinates
) {
}
