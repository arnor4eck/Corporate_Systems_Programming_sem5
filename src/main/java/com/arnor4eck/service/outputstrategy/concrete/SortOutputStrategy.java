package com.arnor4eck.service.outputstrategy.concrete;

import com.arnor4eck.repository.Repository;
import com.arnor4eck.service.outputstrategy.OutputStrategy;

import java.util.Comparator;
import java.util.stream.Collectors;

public class SortOutputStrategy<T> implements OutputStrategy {

    private final Repository<T> repository;
    private final Comparator<T> comparator;

    public SortOutputStrategy(
            Repository<T> repository,
            Comparator<T> comparator
    ) {
        this.repository = repository;
        this.comparator = comparator;
    }

    @Override
    public String act() {
        return repository.getAll()
                .stream()
                .sorted(comparator)
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
