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
                "1. Заявки\n" +
                "2. Места на кладбище\n" +
                "3. Экспорт данных\n" +
                "0. Выход\n" +
                "Выберите действие: ");
    }

    private void showRequestMenu() {
        System.out.print("========= ЗАЯВКИ =========\n" +
                "1. Все заявки\n" +
                "2. Конкретная заявка\n" +
                "3. Фильтрация по статусу заявки\n" +
                "4. Статистика\n" +
                "Выберите действие: ");
    }

    private void showPlotMenu() {
        System.out.print("========= МЕСТА НА КЛАДБИЩЕ =========\n" +
                "1. Все места\n" +
                "2. Конкретное место\n" +
                "3. Фильтрация по статусу места\n" +
                "4. Фильтрация по сектору\n" +
                "Выберите действие: ");
    }

    private void showExportMenu() {
        System.out.print("========= ЭКСПОРТ ДАННЫХ =========\n" +
                "1. Общий экспорт\n" +
                "2. Экспорт заявок\n" +
                "3. Экспорт мест\n" +
                "Выберите действие: ");
    }

    private int getNumber() {
        int num;

        while (true) {
            try{
                num = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Некорректный ввод. Введите число из списка.");
            }
        }

        return num;
    }
}
