package com.arnor4eck;

import java.util.List;
import java.util.Scanner;

public final class Application {

    private final MenuProvider menu;

    public Application(Scanner scanner) {
        this.menu = new MenuProvider(
                "========= СИСТЕМА УПРАВЛЕНИЯ КЛАДБИЩЕМ =========",
                scanner,
                List.of("Заявки", "Места на кладбище", "Экспорт данных", "Выход")
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
        return num == 0;
    }
}
