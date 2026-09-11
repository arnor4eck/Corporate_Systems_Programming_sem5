package strategy;

import com.arnor4eck.service.outputstrategy.DataExportStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DataExportStrategyTest {

    private DataExportStrategy dataExportStrategy;

    @BeforeEach
    public void setUp() {
        dataExportStrategy = new DataExportStrategy();
    }

    @ParameterizedTest
    @MethodSource("arguments")
    @DisplayName("При использовании метода find(), должна возвращаться подходящая стратегия")
    public void testFindStrategy(int argument) {
        var strategy = dataExportStrategy.find(String.valueOf(argument));

        assertNotNull(strategy);
    }

    public static IntStream arguments() {
        return IntStream.rangeClosed(0, 1);
    }
}
