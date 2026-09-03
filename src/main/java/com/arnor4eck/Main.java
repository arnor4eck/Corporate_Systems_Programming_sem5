package com.arnor4eck;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            Application app = new Application(scanner);
            app.run();
        }
    }
}