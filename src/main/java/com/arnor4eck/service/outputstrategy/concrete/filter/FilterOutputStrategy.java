package com.arnor4eck.service.outputstrategy.concrete.filter;

import com.arnor4eck.repository.Repository;
import com.arnor4eck.service.outputstrategy.OutputStrategy;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class FilterOutputStrategy<T> implements OutputStrategy {

    private final Repository<T> repository;

    public FilterOutputStrategy(
            Repository<T> repository
    ) {
        this.repository = repository;
    }

    @Override
    public String act() {
        return repository.getAll()
                .stream()
                .filter(predicate())
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    public abstract Predicate<T> predicate();
}
