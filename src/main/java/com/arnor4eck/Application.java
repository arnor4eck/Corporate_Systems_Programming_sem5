package com.arnor4eck;

import com.arnor4eck.model.Plot;
import com.arnor4eck.model.Request;
import com.arnor4eck.repository.CustomerRepository;
import com.arnor4eck.repository.DataBase;
import com.arnor4eck.repository.PlotRepository;
import com.arnor4eck.repository.RequestRepository;
import com.arnor4eck.service.outputstrategy.DataExportStrategy;
import com.arnor4eck.service.outputstrategy.OutputStrategy;
import com.arnor4eck.service.outputstrategy.OutputStrategyFactory;
import com.arnor4eck.service.outputstrategy.concrete.CreateCustomerOutputStrategy;
import com.arnor4eck.service.outputstrategy.concrete.CreatePlotOutputStrategy;
import com.arnor4eck.service.outputstrategy.concrete.NotExistingStrategy;
import com.arnor4eck.service.outputstrategy.concrete.SortOutputStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public final class Application {

    private final MenuProvider menu;
    private final DataExportStrategy mainMenu;

    private static final String EXIT = "Выход";
    private static final List<String> MENU_UNITS = List.of("Заявители", "Заявки", "Места на кладбище", "Экспорт данных", EXIT);
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
        var requestRepository = new RequestRepository(dataSource);
        var plotRepository = new PlotRepository(dataSource);
        var customerRepository = new CustomerRepository(dataSource);

        this.mainMenu = new DataExportStrategy(
            List.of(
                OutputStrategyFactory.menuProvider(
                        "========= ЗАЯВИТЕЛИ =========",
                        scanner,
                        List.of("Все заявители", "Конкретный заявитель (id)", "Создать заявителя"),
                        List.of(
                                OutputStrategyFactory.allValues(customerRepository),
                                OutputStrategyFactory.concreteValue(customerRepository, scanner),
                                new CreateCustomerOutputStrategy(customerRepository, scanner)
                        )
                ),
                OutputStrategyFactory.menuProvider(
                        "========= ЗАЯВКИ =========",
                        scanner,
                        List.of("Все заявки", "Конкретная заявка (id)", "Сортировка по дате создания", "Фильтрация по статусу заявки", "Статистика"),
                        List.of(
                            OutputStrategyFactory.allValues(requestRepository),
                            OutputStrategyFactory.concreteValue(requestRepository, scanner),
                            OutputStrategyFactory.sort(requestRepository, Comparator.comparing(Request::createdAt).reversed()),
                            new NotExistingStrategy(),
                            new NotExistingStrategy()
                        )
                ),
                OutputStrategyFactory.menuProvider(
                        "========= МЕСТА НА КЛАДБИЩЕ =========",
                        scanner,
                        List.of("Все места", "Конкретное место (id)", "Сортировка по статусу", "Фильтрация по сектору", "Создать место"),
                        List.of(
                            OutputStrategyFactory.allValues(plotRepository),
                            OutputStrategyFactory.concreteValue(plotRepository, scanner),
                            OutputStrategyFactory.sort(plotRepository, Comparator.comparing(Plot::status)),
                            new NotExistingStrategy(),
                            new CreatePlotOutputStrategy(plotRepository, scanner)
                        )
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
