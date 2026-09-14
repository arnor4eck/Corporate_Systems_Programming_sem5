package com.arnor4eck.util.enums;

import com.arnor4eck.model.Plot;

public enum PlotStatus {
    FREE,
    RESERVED,
    OCCUPIED;
    public static PlotStatus fromString(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Error with parse Plot");
        }
        for (PlotStatus plotStatus : PlotStatus.values()) {
            if (plotStatus.name().equalsIgnoreCase(str.trim())) {
                return plotStatus;
            }
        }
        throw new IllegalArgumentException("Error with parse Plot");
    }
}
