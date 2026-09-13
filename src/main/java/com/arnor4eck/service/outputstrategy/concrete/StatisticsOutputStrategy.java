package com.arnor4eck.service.outputstrategy.concrete;

import com.arnor4eck.repository.Repository;
import com.arnor4eck.service.outputstrategy.OutputStrategy;

import java.util.Collection;
import java.util.List;

public class StatisticsOutputStrategy implements OutputStrategy {

    private final Repository<?> plotRepository; // TODO
    private final Repository<?> requestRepository; // TODO

    public StatisticsOutputStrategy(
            Repository<?> plotRepository,
            Repository<?> requestRepository
    ) {
        this.plotRepository = plotRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public void act() {
        List<String> statistics = List.of(
            getStatistics(plotRepository, "мест"),
            getStatistics(requestRepository, "запросов")
        );

        String stat = String.join("\n", statistics);

        System.out.println(stat);
    }

    private <T> String getStatistics(Repository<T> repository, String unit) {
        Collection<T> all = repository.getAll();

        return String.format("Всего %s: %d", unit, all.size());
    }
}
