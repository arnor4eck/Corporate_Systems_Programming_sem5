package com.arnor4eck.service.outputstrategy.concrete;

import com.arnor4eck.MenuProvider;
import com.arnor4eck.service.outputstrategy.DataExportStrategy;
import com.arnor4eck.service.outputstrategy.OutputStrategy;

import java.util.List;
import java.util.Scanner;

public class MenuProviderOutputStrategy extends MenuProvider implements OutputStrategy {

    private final DataExportStrategy dataExportStrategy;

    public MenuProviderOutputStrategy(
            String topic,
            Scanner scanner,
            List<String> units,
            List<? extends OutputStrategy> strategies
    ) {
        super(topic, scanner, units);
        if(units.size() != strategies.size())
            throw new IllegalArgumentException("Количество заявок и пунктов меню не совпадают");
        this.dataExportStrategy = new DataExportStrategy(strategies);
    }

    @Override
    public String act() {
        int entered = menu();
        return dataExportStrategy.find(String.valueOf(entered)).act();
    }
}
