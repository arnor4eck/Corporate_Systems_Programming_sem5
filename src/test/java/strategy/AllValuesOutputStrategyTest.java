package strategy;

import com.arnor4eck.model.Employee;
import com.arnor4eck.repository.Repository;
import com.arnor4eck.service.outputstrategy.concrete.AllValuesOutputStrategy;
import com.arnor4eck.util.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AllValuesOutputStrategyTest {

    private AllValuesOutputStrategy<Employee> strategy;
    private Repository<Employee> repository;

    @BeforeEach
    public void setUp() {
        repository = mock(Repository.class);
        strategy = new AllValuesOutputStrategy<>(repository);
    }

    @Test
    @DisplayName("При вызове метода act() должна возвращаться строка, содержащая все записи, разделённые переносом строки")
    public void testAllValuesOutputStrategy() {
        Collection<Employee> employees = List.of(
                createEmployee(1), createEmployee(2), createEmployee(3)
        );
        String expected = employees.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
        when(repository.getAll()).thenReturn(employees);

        String actual = strategy.act();

        assertEquals(actual, expected);
    }

    private Employee createEmployee(int id) {
        return new Employee(id, String.valueOf(id),
                Role.ADMIN, String.valueOf(id), String.valueOf(id),
                true, LocalDateTime.now());
    }
}
