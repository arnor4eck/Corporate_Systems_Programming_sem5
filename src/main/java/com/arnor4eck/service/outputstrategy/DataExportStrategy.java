package com.arnor4eck.service.outputstrategy;

import com.arnor4eck.model.Plot;
import com.arnor4eck.model.Request;
import com.arnor4eck.service.outputstrategy.concrete.AllValuesOutputStrategy;
import com.arnor4eck.service.outputstrategy.concrete.ConcreteValueOutputStrategy;
import com.arnor4eck.service.outputstrategy.concrete.NotExistingStrategy;

import java.util.Map;

import static java.util.Map.entry;

public class DataExportStrategy {

    private final Map<String, OutputStrategy> strategies;

    public DataExportStrategy() {
        strategies = Map.ofEntries(
                entry("1", new AllValuesOutputStrategy<Request>(null)), // TODO
                entry("2", new ConcreteValueOutputStrategy<Request>(null, null)), // TODO
                entry("3", new AllValuesOutputStrategy<Plot>(null)), // TODO
                entry("4", new ConcreteValueOutputStrategy<Plot>(null, null)) // TODO
        );
    }

    public OutputStrategy find(String argument) {
        OutputStrategy strategy = strategies.get(argument);

        return strategy == null ? new NotExistingStrategy() : strategy;
    }
}
