package com.arnor4eck;

import java.util.Scanner;

public final class Application {

    private final MenuProvider menu;

    public Application(Scanner scanner) {
        this.menu = new MenuProvider(scanner);
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
