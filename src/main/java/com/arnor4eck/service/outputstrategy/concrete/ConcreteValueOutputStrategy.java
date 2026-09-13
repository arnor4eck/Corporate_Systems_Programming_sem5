package com.arnor4eck.service.outputstrategy.concrete;

import com.arnor4eck.repository.Repository;
import com.arnor4eck.service.outputstrategy.OutputStrategy;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConcreteValueOutputStrategy<T> implements OutputStrategy {

    private final Repository<T> repository;
    private final Scanner scanner;

    public ConcreteValueOutputStrategy(
        Repository<T> repository,
        Scanner scanner
    ) {
        this.repository = repository;
        this.scanner = scanner;
    }

    @Override
    public String act() {
        try {
            int id = scanner.nextInt();
            T val = repository.get(id); // TODO null?

            return val.toString();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return "Некорректный ввод. Введённый символ не является числом";
        }
    }
}
