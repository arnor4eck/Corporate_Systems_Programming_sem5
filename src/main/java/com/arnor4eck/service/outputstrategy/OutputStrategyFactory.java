package com.arnor4eck.service.outputstrategy;

import com.arnor4eck.repository.Repository;
import com.arnor4eck.service.outputstrategy.concrete.AllValuesOutputStrategy;
import com.arnor4eck.service.outputstrategy.concrete.ConcreteValueOutputStrategy;
import com.arnor4eck.service.outputstrategy.concrete.MenuProviderOutputStrategy;
import com.arnor4eck.service.outputstrategy.concrete.SortOutputStrategy;
import com.arnor4eck.util.OutputStrategyPair;

import java.util.*;

import static java.util.Collections.singletonList;

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

    public static MenuProviderOutputStrategy menuProvider(
            String topic,
            Scanner scanner,
            List<OutputStrategyPair<?>> pairs
    ) {
        return new MenuProviderOutputStrategy(topic, scanner, pairs);
    }

    public static MenuProviderOutputStrategy menuProvider(
            String topic,
            Scanner scanner,
            OutputStrategyPair<?> pair
    ) {
        return new MenuProviderOutputStrategy(topic, scanner, singletonList(pair));
    }

    public static <T> SortOutputStrategy<T> sort(
            Repository<T> repository,
            Comparator<T> comparator
    ) {
        return new SortOutputStrategy<>(repository, comparator);
    }
}
