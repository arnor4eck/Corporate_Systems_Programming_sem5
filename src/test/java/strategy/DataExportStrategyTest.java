package strategy;

import com.arnor4eck.repository.Repository;
import com.arnor4eck.service.outputstrategy.DataExportStrategy;
import com.arnor4eck.service.outputstrategy.OutputStrategyFactory;
import com.arnor4eck.service.outputstrategy.concrete.NotExistingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class DataExportStrategyTest {

    private DataExportStrategy dataExportStrategy;

    @BeforeEach
    public void setUp() {
        OutputStrategyFactory outputStrategyFactory = new OutputStrategyFactory(mock(Scanner.class));

        dataExportStrategy = new DataExportStrategy(
            List.of(
                outputStrategyFactory.concreteValue(mock(Repository.class)),
                OutputStrategyFactory.allValues(mock(Repository.class)),
                outputStrategyFactory.concreteValue(mock(Repository.class)),
                OutputStrategyFactory.allValues(mock(Repository.class))
            )
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    @DisplayName("При использовании метода find(), должна возвращаться подходящая стратегия")
    public void testFindStrategy(int argument) {
        var strategy = dataExportStrategy.find(argument);

        assertNotNull(strategy);
        assertFalse(strategy instanceof NotExistingStrategy);
    }

    @Test
    @DisplayName("При попытке найти неизвестную стратегию, должна возвращаться " +
            "особенная пустая стратегия")
    public void testFindNotExistingStrategy() {
        var strategy = dataExportStrategy.find(-1);

        assertInstanceOf(NotExistingStrategy.class, strategy);
    }

    public static IntStream arguments() {
        return IntStream.rangeClosed(1, 4);
    }
}
