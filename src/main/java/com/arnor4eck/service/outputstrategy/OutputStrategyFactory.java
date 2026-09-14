package com.arnor4eck.service.outputstrategy;

import com.arnor4eck.repository.Repository;
import com.arnor4eck.service.outputstrategy.concrete.AllValuesOutputStrategy;
import com.arnor4eck.service.outputstrategy.concrete.ConcreteValueOutputStrategy;

import java.util.Scanner;

public class OutputStrategyFactory {

    private OutputStrategyFactory() {
        throw new UnsupportedOperationException("Not supported");
    }

    public static <T> AllValuesOutputStrategy<T> allValues(Repository<T> repository) {
        return new AllValuesOutputStrategy<>(repository);
    }

    public static <T> ConcreteValueOutputStrategy<T> concreteValue(
            Repository<T> repository,
            Scanner scanner
    ) {
        return new ConcreteValueOutputStrategy<>(repository, scanner);
    }
}
