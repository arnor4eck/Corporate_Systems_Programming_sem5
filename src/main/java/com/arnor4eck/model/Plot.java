package com.arnor4eck.model;

import com.arnor4eck.util.enums.PlotStatus;

public record Plot(
        Long id,
        Sector sector,
        int rowNumber,
        int plot_number ,
        PlotStatus status,
        int lengthCm,
        int widthCm ,
        String coordinates
) {
}
