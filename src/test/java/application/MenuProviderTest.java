package application;

import com.arnor4eck.MenuProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MenuProviderTest {

    private MenuProvider menuProvider;
    private Scanner scanner;

    @BeforeEach
    public void setUp() {
        scanner = Mockito.mock(Scanner.class);
        menuProvider = new MenuProvider(scanner);
    }

    @Test
    @DisplayName("При вводе числа с клавиатуры, метод должен возвращать это же число")
    public void menuProviderTest() {
        int expected = 5;
        when(scanner.nextInt()).thenReturn(expected);

        int actual = menuProvider.menu();
        assertEquals(expected, actual);
    }
}
