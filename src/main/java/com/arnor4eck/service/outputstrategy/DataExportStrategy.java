package com.arnor4eck.service.outputstrategy;

import com.arnor4eck.service.outputstrategy.concrete.NotExistingStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataExportStrategy {

    private final Map<String, OutputStrategy> strategies;

    private static final OutputStrategy NOT_EXISTING_STRATEGY = new NotExistingStrategy();

    public DataExportStrategy(List<? extends OutputStrategy> strategies) {
        this.strategies = new HashMap<>(strategies.size(), 1.1f);

        for(int i = 0; i < strategies.size(); ++i) {
            this.strategies.put(String.valueOf(i + 1), strategies.get(i));
        }
    }

    public OutputStrategy find(String argument) {
        return strategies.getOrDefault(argument, NOT_EXISTING_STRATEGY);
    }
}
