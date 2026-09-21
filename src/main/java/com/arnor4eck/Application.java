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
import com.arnor4eck.service.outputstrategy.concrete.StatisticsOutputStrategy;
import com.arnor4eck.service.outputstrategy.concrete.create.CreateCustomerOutputStrategy;
import com.arnor4eck.service.outputstrategy.concrete.create.CreatePlotOutputStrategy;
import com.arnor4eck.service.outputstrategy.concrete.NotExistingStrategy;
import com.arnor4eck.service.outputstrategy.concrete.filter.CustomerByFullNameFilterStrategy;
import com.arnor4eck.service.outputstrategy.concrete.filter.CustomerByPhoneFilterStrategy;
import com.arnor4eck.service.outputstrategy.concrete.filter.PlotByStatusFilterStrategy;
import com.arnor4eck.service.outputstrategy.concrete.xlsx.XlsxOutputStrategy;
import com.arnor4eck.util.OutputStrategyPair;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public final class Application {

    private final MenuProvider menu;
    private final DataExportStrategy mainMenu;

    private static final String EXIT = "Выход";
    private static final List<String> MENU_UNITS = List.of("Заявители", "Заявки", "Места на кладбище", "Экспорт данных", "Статистика", EXIT);
    private static final int EXIT_CONDITION = MENU_UNITS.indexOf(EXIT) + 1;

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
                        List.of(
                            OutputStrategyPair.of("Все заявители", OutputStrategyFactory.allValues(customerRepository)),
                            OutputStrategyPair.of("Конкретный заявитель (id)", OutputStrategyFactory.concreteValue(customerRepository, scanner)),
                            OutputStrategyPair.of("Поиск по содержанию текста в ФИО", new CustomerByFullNameFilterStrategy(customerRepository, scanner)),
                            OutputStrategyPair.of("Поиск по номеру телефона", new CustomerByPhoneFilterStrategy(customerRepository, scanner)),
                            OutputStrategyPair.of("Создать заявителя", new CreateCustomerOutputStrategy(customerRepository, scanner))
                        )
                ),
                OutputStrategyFactory.menuProvider(
                        "========= ЗАЯВКИ =========",
                        scanner,
                        List.of(
                            OutputStrategyPair.of("Все заявки", OutputStrategyFactory.allValues(requestRepository)),
                            OutputStrategyPair.of("Конкретная заявка (id)", OutputStrategyFactory.concreteValue(requestRepository, scanner)),
                            OutputStrategyPair.of("Сортировка по дате создания", OutputStrategyFactory.sort(requestRepository, Comparator.comparing(Request::createdAt).reversed()))
                        )
                ),
                OutputStrategyFactory.menuProvider(
                        "========= МЕСТА НА КЛАДБИЩЕ =========",
                        scanner,
                        List.of(
                            OutputStrategyPair.of("Все места", OutputStrategyFactory.allValues(plotRepository)),
                            OutputStrategyPair.of("Конкретное место (id)", OutputStrategyFactory.concreteValue(plotRepository, scanner)),
                            OutputStrategyPair.of("Сортировка по статусу", OutputStrategyFactory.sort(plotRepository, Comparator.comparing(Plot::status))),
                            OutputStrategyPair.of("Фильтрация по статусу", new PlotByStatusFilterStrategy(plotRepository, scanner)),
                            OutputStrategyPair.of("Создать место", new CreatePlotOutputStrategy(plotRepository, scanner))
                        )
                ),
                OutputStrategyFactory.menuProvider(
                        "========= ЭКСПОРТ ДАННЫХ =========",
                        scanner,
                        List.of(
                            OutputStrategyPair.of("Общий экспорт", new XlsxOutputStrategy(plotRepository))
                        )
                ),
                OutputStrategyFactory.menuProvider(
                        "========= СТАТИСТИКА =========",
                        scanner,
                        List.of(
                            OutputStrategyPair.of("Общая статистика", new StatisticsOutputStrategy(plotRepository, requestRepository))
                        )
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
