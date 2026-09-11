package com.arnor4eck.service.outputstrategy.concrete;

import com.arnor4eck.service.outputstrategy.OutputStrategy;

public class NotExistingStrategy implements OutputStrategy {
    @Override
    public void act() {
        System.out.println("Неизвестная команда.");
    }
}
