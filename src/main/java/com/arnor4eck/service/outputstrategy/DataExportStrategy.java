package com.arnor4eck.service.outputstrategy;

import java.util.Map;

public class DataExportStrategy {

    private final Map<String, OutputStrategy> strategies;

    public DataExportStrategy() {
        strategies = Map.ofEntries();
    }

    public OutputStrategy find(String argument) {
        return strategies.get(argument);
    }
}
