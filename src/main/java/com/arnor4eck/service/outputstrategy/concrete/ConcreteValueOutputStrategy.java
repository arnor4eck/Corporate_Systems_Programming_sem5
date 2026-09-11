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
    public void act() {
        try {
            int id = scanner.nextInt();
            T val = repository.get(id); // TODO null?

            System.out.println(val);
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Некорректный ввод. Введённый символ не является числом");
        }
    }
}
