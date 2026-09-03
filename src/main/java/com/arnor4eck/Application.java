package com.arnor4eck;

import java.util.Scanner;

public final class Application {

    private final MenuProvider menu;

    public Application(Scanner scanner) {
        this.menu = new MenuProvider(scanner);
    }

    public void run() {
        while (!shouldBeExit(menu.menu())) {
            // TODO
        }
    }

    private boolean shouldBeExit(int num) {
        return num == 0;
    }
}
