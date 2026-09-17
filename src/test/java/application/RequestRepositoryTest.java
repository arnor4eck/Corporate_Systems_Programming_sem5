package application;

import application.util.RequestGenerator;
import com.arnor4eck.model.Request;
import com.arnor4eck.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class RequestRepositoryTest {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private RequestRepository requestRepository;
    private DataSource dataSource;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = Mockito.mock(Connection.class);
        preparedStatement = Mockito.mock(PreparedStatement.class);
        resultSet = Mockito.mock(ResultSet.class);
        dataSource = Mockito.mock(DataSource.class);
        requestRepository = new RequestRepository(dataSource);
        doReturn(connection).when(dataSource).getConnection();
    }

    @Test
    @DisplayName("Успешное получение элемента из базы данных по id")
    public void successGetTest() throws SQLException {
        Request expectedRequest = RequestGenerator.generateRandomRequest();


        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);
        given(preparedStatement.executeQuery()).willReturn(resultSet);

        given(resultSet.next()).willReturn(true);

        given(resultSet.getInt("id")).willReturn(expectedRequest.id());
        given(resultSet.getInt("customer_id")).willReturn(expectedRequest.costumerId());
        given(resultSet.getInt("employee_id")).willReturn(expectedRequest.employeeId());
        given(resultSet.getInt("plot_id")).willReturn(expectedRequest.plotId());
        given(resultSet.getString("deceased_full_name")).willReturn(expectedRequest.deceasedFullName());
        given(resultSet.getObject("deceased_birthday", LocalDate.class))
                .willReturn(expectedRequest.deceasedBirthday());
        given(resultSet.getObject("deceased_deathday", LocalDate.class))
                .willReturn(expectedRequest.deceasedDeathday());
        given(resultSet.getString("deceased_certificate")).willReturn(expectedRequest.deceasedCertificate());
        given(resultSet.getString("status")).willReturn(expectedRequest.status().toString());
        given(resultSet.getString("totalCost")).willReturn(expectedRequest.totalCost());
        given(resultSet.getString("note")).willReturn(expectedRequest.note());
        given(resultSet.getObject("created_at", LocalDateTime.class)).willReturn(expectedRequest.createdAt());

        Optional<Request> result = requestRepository.get(expectedRequest.id());

        assertTrue(result.isPresent());
        assertEquals(expectedRequest, result.get());
    }
    @Test
    @DisplayName("Тест записи нет в базе данных")
    public void nullReturnRequestOnGet() throws SQLException {

        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);
        given(preparedStatement.executeQuery()).willReturn(resultSet);

        given(resultSet.next()).willReturn(false);
        Optional<Request> result = requestRepository.get(1);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Тест ошибки подключения к базе данных в методе get")
    public void connectionErrorTestOnGet() throws SQLException {
        int requestId = 1;
        String sqlErrorMessage = "Connection connection timed out";
        given(connection.prepareStatement(anyString())).willThrow(new SQLException(sqlErrorMessage));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> requestRepository.get(1),
                "Ожидалось исключение RuntimeException"
        );
        String expectedMessage = String.format("SQL exception: %s", sqlErrorMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }
    @Test
    @DisplayName("Успешное получение списка сущностей из базы данных")
    public void successGetAllTest() throws SQLException {


        Request expectedRequest1 = RequestGenerator.generateRandomRequest();

        Request expectedRequest2 = RequestGenerator.generateRandomRequest();

        Collection<Request> expectedList = List.of(expectedRequest1, expectedRequest2);

        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);
        given(preparedStatement.executeQuery()).willReturn(resultSet);

        given(resultSet.next()).willReturn(true, true, false);

        given(resultSet.getInt("id"))
                .willReturn(expectedRequest1.id(), expectedRequest2.id());

        given(resultSet.getInt("customer_id"))
                .willReturn(expectedRequest1.costumerId(), expectedRequest2.costumerId());

        given(resultSet.getInt("employee_id"))
                .willReturn(expectedRequest1.employeeId(), expectedRequest2.employeeId());

        given(resultSet.getInt("plot_id"))
                .willReturn(expectedRequest1.plotId(), expectedRequest2.plotId());

        given(resultSet.getString("deceased_full_name"))
                .willReturn(expectedRequest1.deceasedFullName(), expectedRequest2.deceasedFullName());

        given(resultSet.getObject("deceased_birthday", LocalDate.class))
                .willReturn(expectedRequest1.deceasedBirthday(), expectedRequest2.deceasedBirthday());

        given(resultSet.getObject("deceased_deathday", LocalDate.class))
                .willReturn(expectedRequest1.deceasedDeathday(), expectedRequest2.deceasedDeathday());

        given(resultSet.getString("deceased_certificate"))
                .willReturn(expectedRequest1.deceasedCertificate(), expectedRequest2.deceasedCertificate());

// Если status в ResultSet хранится как строка (enum name):
        given(resultSet.getString("status"))
                .willReturn(expectedRequest1.status().name(), expectedRequest2.status().name());

        given(resultSet.getString("totalCost"))
                .willReturn(expectedRequest1.totalCost(), expectedRequest2.totalCost());

        given(resultSet.getString("note"))
                .willReturn(expectedRequest1.note(), expectedRequest2.note());

        given(resultSet.getObject("created_at", LocalDateTime.class))
                .willReturn(expectedRequest1.createdAt(), expectedRequest2.createdAt());

        Collection<Request> actualRequests = requestRepository.getAll();

        assertNotNull(actualRequests);
        assertEquals(2, actualRequests.size());
        assertArrayEquals(expectedList.toArray(), actualRequests.toArray());
    }
    @Test
    @DisplayName("Тест получения пустого списка из базы данных")
    public void returnEmptyGetAllTest() throws SQLException {
        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);
        given(preparedStatement.executeQuery()).willReturn(resultSet);

        given(resultSet.next()).willReturn(false);
        var result = requestRepository.getAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Тест ошибки подключения к базе данных в методе getAll")
    public void connectionErrorOnGetAllTest() throws SQLException {
        String sqlErrorMessage = "Connection connection timed out";
        doReturn(connection).when(dataSource).getConnection();
        given(connection.prepareStatement(anyString())).willThrow(new SQLException(sqlErrorMessage));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> requestRepository.getAll(),
                "Ожидалось исключение RuntimeException"
        );
        String expectedMessage = String.format("SQL exception: %s", sqlErrorMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }
    @Test
    @DisplayName("Успешное сохранение объекта Request с правильной установкой параметров")
    void shouldSaveRequestSuccessfully() throws SQLException {
        Request requestToSave = RequestGenerator.generateRandomRequest();

        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);

        requestRepository.save(requestToSave);

        verify(preparedStatement).setInt(1, requestToSave.costumerId());
        verify(preparedStatement).setInt(2, requestToSave.employeeId());
        verify(preparedStatement).setInt(3, requestToSave.plotId());

        verify(preparedStatement).setString(4, requestToSave.deceasedFullName());
        verify(preparedStatement).setDate(5, Date.valueOf(requestToSave.deceasedBirthday()));
        verify(preparedStatement).setDate(6, Date.valueOf(requestToSave.deceasedDeathday()));

        verify(preparedStatement).setString(7, requestToSave.deceasedCertificate());
        verify(preparedStatement).setString(8, requestToSave.status().toString());
        verify(preparedStatement).setString(9, requestToSave.totalCost());
        verify(preparedStatement).setString(10, requestToSave.note());

        verify(preparedStatement).setTimestamp(11, Timestamp.valueOf(requestToSave.createdAt()));

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    @DisplayName("Выбрасывает RuntimeException при ошибке в БД")
    void shouldThrowRuntimeExceptionWhenSaveFails() throws SQLException {
        Request request = RequestGenerator.generateRandomRequest();

        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);
        given(preparedStatement.executeUpdate()).willThrow(new SQLException("Database disk full"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> requestRepository.save(request)
        );

        assertTrue(exception.getMessage().contains("SQL exception: Database disk full"));
    }
    @Test
    @DisplayName("delete: Успешное удаление записи по id")
    void shouldDeleteRequestSuccessfully() throws SQLException {
        int targetId = 42;

        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);

        requestRepository.delete(targetId);

        verify(preparedStatement, times(1)).setInt(1, targetId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    @DisplayName("delete: Выбрасывает RuntimeException при ошибке в БД")
    void shouldThrowRuntimeExceptionWhenDeleteFails() throws SQLException {
        int targetId = 42;
        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);
        given(preparedStatement.executeUpdate()).willThrow(new SQLException("Access denied"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> requestRepository.delete(targetId)
        );

        assertTrue(exception.getMessage().contains("SQL exception: Access denied"));
    }
}
