package com.arnor4eck;

import java.util.Scanner;

public class RequestMenuProvider extends MenuProvider {
    public RequestMenuProvider(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void showMenu() {
        System.out.print("========= ЗАЯВКИ =========\n" +
                "1. Все заявки\n" +
                "2. Конкретная заявка\n" +
                "3. Фильтрация по статусу заявки\n" +
                "4. Статистика\n" +
                "Выберите действие: ");
    }
}
