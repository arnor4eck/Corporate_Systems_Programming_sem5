package com.arnor4eck.service.outputstrategy.concrete;

import com.arnor4eck.repository.Repository;
import com.arnor4eck.service.outputstrategy.OutputStrategy;

public class AllValuesOutputStrategy<T> implements OutputStrategy {

    public final Repository<T> repository;

    public AllValuesOutputStrategy(Repository<T> repository) {
        this.repository = repository;
    }

    @Override
    public void act() {
        for(var item : repository.getAll()) {
            System.out.println(item);
        }
    }
}
