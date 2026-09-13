package com.arnor4eck;

import java.util.List;
import java.util.Scanner;

public final class Application {

    private final MenuProvider menu;

    private static final String EXIT = "Выход";
    private static final List<String> MENU_UNITS = List.of("Заявки", "Места на кладбище", "Экспорт данных", EXIT);
    private static final int EXIT_CONDITION;

    static {
        EXIT_CONDITION = MENU_UNITS.indexOf(EXIT) + 1;
    }

    public Application(Scanner scanner) {
        this.menu = new MenuProvider(
                "========= СИСТЕМА УПРАВЛЕНИЯ КЛАДБИЩЕМ =========",
                scanner,
                MENU_UNITS
        );
    }

    public void run() {
        while (true) {
            int enteredNum = menu.menu();
            if (shouldBeExit(enteredNum)) {
                break;
            }
        }
    }

    private boolean shouldBeExit(int num) {
        return num == EXIT_CONDITION;
    }
}
