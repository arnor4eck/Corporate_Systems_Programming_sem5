package com.arnor4eck.util.enums;

public enum RequestStatus {
    NEW,
    PROCESSING,
    APPROVED,
    REJECTED,
    COMPLETED;
    public static RequestStatus fromString(String str){
        if (str == null) {
            throw new IllegalArgumentException("Error with parse RequestStatus");
        }
        for (RequestStatus requestStatus : RequestStatus.values()) {
            if (requestStatus.name().equalsIgnoreCase(str.trim())) {
                return requestStatus;
            }
        }
        throw new IllegalArgumentException("Error with parse RequestStatus");
    }
}
