package com.arnor4eck;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class MenuProvider {

    private final Scanner scanner;

    public MenuProvider(Scanner scanner) {
        this.scanner = scanner;
    }

    public int menu() {
        showMenu();
        return getNumber();
    }

    public abstract void showMenu();

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
