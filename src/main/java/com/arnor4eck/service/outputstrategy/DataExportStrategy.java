package com.arnor4eck.service.outputstrategy;

import com.arnor4eck.model.Plot;
import com.arnor4eck.model.Request;
import com.arnor4eck.service.outputstrategy.concrete.AllValuesOutputStrategy;

import java.util.Map;

public class DataExportStrategy {

    private final Map<String, OutputStrategy> strategies;

    public DataExportStrategy() {
        strategies = Map.ofEntries(
                Map.entry("1", new AllValuesOutputStrategy<Request>(null)), // TODO
                Map.entry("3", new AllValuesOutputStrategy<Plot>(null)) // TODO
        );
    }

    public OutputStrategy find(String argument) {
        return strategies.get(argument);
    }
}
