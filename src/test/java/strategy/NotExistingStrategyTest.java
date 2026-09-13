package strategy;

import com.arnor4eck.service.outputstrategy.concrete.NotExistingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotExistingStrategyTest {

    private NotExistingStrategy strategy;

    @BeforeEach
    public void setUp() {
        strategy = new NotExistingStrategy();
    }

    @Test
    @DisplayName("При вызове метода act() должна выозвращаться строка, говорящая, что команда не найдена")
    public void test() {
        String expected = "Неизвестная команда.";
        String actual = strategy.act();

        assertEquals(expected, actual);
    }
}
