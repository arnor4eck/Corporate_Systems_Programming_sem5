package com.arnor4eck;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int num;
        Scanner in = new Scanner(System.in);
        do {
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
            num = in.nextInt();
        } while (num != 0);

    }
}