package com.arnor4eck;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuProvider {

    private final Scanner scanner;

    public MenuProvider(Scanner scanner) {
        this.scanner = scanner;
    }

    public int menu() {
        showMenu();
        return getNumber();
    }

    private void showMenu() {
        System.out.print("========= СИСТЕМА УПРАВЛЕНИЯ КЛАДБИЩЕМ =========\n" +
                "1. Заявки на место\n" +
                "2. Конкретная заявка на место (по id)\n" +
                "3. Места на кладбище\n" +
                "4. Конкретное место на кладбище (по id)\n" +
                "5. Фильтрация по статусу места\n" +
                "6. Статистика\n" +
                "7. Экспорт данных\n" +
                "0. Выход\n" +
                "Выберите действие: ");
    }

    private int getNumber() {
        int num;

        while (true) {
            try{
                num = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод. Введите число из списка.");
            }
        }

        return num;
    }
}
