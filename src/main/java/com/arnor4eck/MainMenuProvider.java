package com.arnor4eck;

import java.util.Scanner;

public class MainMenuProvider extends MenuProvider {
    public MainMenuProvider(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void showMenu() {
        System.out.print("========= СИСТЕМА УПРАВЛЕНИЯ КЛАДБИЩЕМ =========\n" +
                "1. Заявки\n" +
                "2. Места на кладбище\n" +
                "3. Экспорт данных\n" +
                "0. Выход\n" +
                "Выберите действие: ");
    }
}
