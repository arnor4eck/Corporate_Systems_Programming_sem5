package com.arnor4eck;

import com.arnor4eck.repository.DataBase;
import com.arnor4eck.repository.RequestRepository;
import com.arnor4eck.service.outputstrategy.DataExportStrategy;
import com.arnor4eck.service.outputstrategy.OutputStrategy;
import com.arnor4eck.service.outputstrategy.OutputStrategyFactory;
import com.arnor4eck.service.outputstrategy.concrete.NotExistingStrategy;

import javax.sql.DataSource;
import java.util.List;
import java.util.Scanner;

public final class Application {

    private final MenuProvider menu;
    private final DataExportStrategy mainMenu;

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
        var dataSource = new DataBase();
        var requestRepository = new RequestRepository();

        this.mainMenu = new DataExportStrategy(
            List.of(
                OutputStrategyFactory.menuProvider(
                        "========= ЗАЯВКИ =========",
                        scanner,
                        List.of("Все заявки", "Конкретная заявка (id)", "Фильтрация по статусу заявки", "Статистика"),
                        List.of(new NotExistingStrategy(), new NotExistingStrategy(), new NotExistingStrategy(), new NotExistingStrategy())
                ),
                OutputStrategyFactory.menuProvider(
                        "========= МЕСТА НА КЛАДБИЩЕ =========",
                        scanner,
                        List.of("Все места", "Конкретное место (id)", "Фильтрация по статусу места", "Фильтрация по сектору"),
                        List.of(new NotExistingStrategy(), new NotExistingStrategy(), new NotExistingStrategy(), new NotExistingStrategy())
                ),
                OutputStrategyFactory.menuProvider(
                        "========= ЭКСПОРТ ДАННЫХ =========",
                        scanner,
                        List.of("Общий экспорт", "Экспорт заявок", "Экспорт мест"),
                        List.of(new NotExistingStrategy(), new NotExistingStrategy(), new NotExistingStrategy())
                )
            )
        );
    }

    public void run() {
        while (true) {
            int enteredNum = menu.menu();
            if (shouldBeExit(enteredNum)) {
                break;
            }

            OutputStrategy strategy = mainMenu.find(enteredNum);
            System.out.println(strategy.act());
        }
    }

    private boolean shouldBeExit(int num) {
        return num == EXIT_CONDITION;
    }
}
