package strategy;

import com.arnor4eck.model.Employee;
import com.arnor4eck.repository.Repository;
import com.arnor4eck.service.outputstrategy.concrete.ConcreteValueOutputStrategy;
import com.arnor4eck.util.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConcreteValueOutputStrategyTest {

    private ConcreteValueOutputStrategy<Employee> strategy;
    private Repository<Employee> repository;
    private Scanner scanner;

    @BeforeEach
    public void setUp() {
        repository = mock(Repository.class);
        scanner = mock(Scanner.class);
        strategy = new ConcreteValueOutputStrategy<>(repository, scanner);
    }

    @Test
    @DisplayName("При вводе конкретного id, должна возращаться сущность, преобразованная в строку")
    public void testFindById() {
        int expectedId = 1;
        Employee employee = new Employee(expectedId, "name", Role.ADMIN, "password", true, LocalDateTime.now());

        when(scanner.nextInt()).thenReturn(expectedId);
        when(repository.get(eq(expectedId))).thenReturn(employee);

        String expected = strategy.act();

        assertEquals(employee.toString(), expected);
    }
}
