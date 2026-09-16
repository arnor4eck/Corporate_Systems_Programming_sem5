package application;

import com.arnor4eck.MenuProvider;
import com.arnor4eck.model.Request;
import com.arnor4eck.repository.RequestRepository;
import com.arnor4eck.util.enums.RequestStatus;
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
import java.util.Scanner;

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
    public void setUp() {
        connection = Mockito.mock(Connection.class);
        preparedStatement = Mockito.mock(PreparedStatement.class);
        resultSet = Mockito.mock(ResultSet.class);
        dataSource = Mockito.mock(DataSource.class);
        requestRepository = new RequestRepository(dataSource);
    }

    @Test
    @DisplayName("Успешное получение элемента из базы данных по id")
    public void successGetTest() throws SQLException {
        int requestId = 1;
        int expectedId = 1;
        int expectedCustomerId = 10;
        int expectedEmployeeId = 20;
        int expectedPlotId = 30;
        String expectedDeceasedFullName = "Иванов Иван Иванович";
        LocalDate expectedBirthday = LocalDate.of(1950, 1, 1);
        LocalDate expectedDeathday = LocalDate.of(2023, 5, 15);
        String expectedCertificate = "CERT-123456";
        String expectedStatusString = "NEW";
        String expectedTotalCost = "15000.00";
        String expectedNote = "Тестовая заметка";
        LocalDateTime expectedCreatedAt = LocalDateTime.of(2023, 5, 16, 10, 0);
        Request expected = new Request(
            expectedId, expectedCustomerId, expectedEmployeeId, expectedPlotId, expectedDeceasedFullName,
                expectedBirthday, expectedDeathday, expectedCertificate,
                RequestStatus.fromString(expectedStatusString),
                expectedTotalCost, expectedNote, expectedCreatedAt
        );


        doReturn(connection).when(dataSource).getConnection();
        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);
        given(preparedStatement.executeQuery()).willReturn(resultSet);

        given(resultSet.next()).willReturn(true);

        given(resultSet.getInt("id")).willReturn(expectedId);
        given(resultSet.getInt("customer_id")).willReturn(expectedCustomerId);
        given(resultSet.getInt("employee_id")).willReturn(expectedEmployeeId);
        given(resultSet.getInt("plot_id")).willReturn(expectedPlotId);
        given(resultSet.getString("deceased_full_name")).willReturn(expectedDeceasedFullName);
        given(resultSet.getObject("deceased_birthday", LocalDate.class)).willReturn(expectedBirthday);
        given(resultSet.getObject("deceased_deathday", LocalDate.class)).willReturn(expectedDeathday);
        given(resultSet.getString("deceased_certificate")).willReturn(expectedCertificate);
        given(resultSet.getString("status")).willReturn(expectedStatusString);
        given(resultSet.getString("totalCost")).willReturn(expectedTotalCost);
        given(resultSet.getString("note")).willReturn(expectedNote);
        given(resultSet.getObject("created_at", LocalDateTime.class)).willReturn(expectedCreatedAt);

        Optional<Request> result = requestRepository.get(requestId);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }
    @Test
    @DisplayName("Тест записи нет в базе данных")
    public void nullReturnRequestOnGet() throws SQLException {

        doReturn(connection).when(dataSource).getConnection();
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
        doReturn(connection).when(dataSource).getConnection();
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
        int expectedId1 = 1;
        int expectedCustomerId1 = 10;
        int expectedEmployeeId1 = 100;
        int expectedPlotId1 = 1000;
        String expectedFullName1 = "Иванов Иван Иванович";
        LocalDate expectedBirthday1 = LocalDate.of(1980, 1, 1);
        LocalDate expectedDeathday1 = LocalDate.of(2023, 5, 10);
        String expectedCertificate1 = "CERT-111";
        String expectedStatusString1 = "NEW";
        String expectedTotalCost1 = "15000.00";
        String expectedNote1 = "Заметка 1";
        LocalDateTime expectedCreatedAt1 = LocalDateTime.of(2023, 5, 11, 10, 0);

        int expectedId2 = 2;
        int expectedCustomerId2 = 20;
        int expectedEmployeeId2 = 200;
        int expectedPlotId2 = 2000;
        String expectedFullName2 = "Петров Петр Петрович";
        LocalDate expectedBirthday2 = LocalDate.of(1990, 2, 2);
        LocalDate expectedDeathday2 = LocalDate.of(2023, 6, 20);
        String expectedCertificate2 = "CERT-222";
        String expectedStatusString2 = "PROCESSING";
        String expectedTotalCost2 = "25000.00";
        String expectedNote2 = "Заметка 2";
        LocalDateTime expectedCreatedAt2 = LocalDateTime.of(2023, 6, 21, 12, 0);

        Request expectedRequest1 = new Request(
                expectedId1,
                expectedCustomerId1,
                expectedEmployeeId1,
                expectedPlotId1,
                expectedFullName1,
                expectedBirthday1,
                expectedDeathday1,
                expectedCertificate1,
                RequestStatus.fromString(expectedStatusString1),
                expectedTotalCost1,
                expectedNote1,
                expectedCreatedAt1
        );

        Request expectedRequest2 = new Request(
                expectedId2,
                expectedCustomerId2,
                expectedEmployeeId2,
                expectedPlotId2,
                expectedFullName2,
                expectedBirthday2,
                expectedDeathday2,
                expectedCertificate2,
                RequestStatus.fromString(expectedStatusString2),
                expectedTotalCost2,
                expectedNote2,
                expectedCreatedAt2
        );

        Collection<Request> expectedList = List.of(expectedRequest1, expectedRequest2);
        doReturn(connection).when(dataSource).getConnection();
        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);
        given(preparedStatement.executeQuery()).willReturn(resultSet);

        given(resultSet.next()).willReturn(true, true, false);

        given(resultSet.getInt("id")).willReturn(expectedId1, expectedId2);
        given(resultSet.getInt("customer_id")).willReturn(expectedCustomerId1, expectedCustomerId2);
        given(resultSet.getInt("employee_id")).willReturn(expectedEmployeeId1, expectedEmployeeId2);
        given(resultSet.getInt("plot_id")).willReturn(expectedPlotId1, expectedPlotId2);

        given(resultSet.getString("deceased_full_name")).willReturn(expectedFullName1, expectedFullName2);
        given(resultSet.getObject("deceased_birthday", LocalDate.class)).willReturn(expectedBirthday1, expectedBirthday2);
        given(resultSet.getObject("deceased_deathday", LocalDate.class)).willReturn(expectedDeathday1, expectedDeathday2);
        given(resultSet.getString("deceased_certificate")).willReturn(expectedCertificate1, expectedCertificate2);

        given(resultSet.getString("status")).willReturn(expectedStatusString1, expectedStatusString2);
        given(resultSet.getString("totalCost")).willReturn(expectedTotalCost1, expectedTotalCost2);
        given(resultSet.getString("note")).willReturn(expectedNote1, expectedNote2);
        given(resultSet.getObject("created_at", LocalDateTime.class)).willReturn(expectedCreatedAt1, expectedCreatedAt2);

        Collection<Request> actualRequests = requestRepository.getAll();

        assertNotNull(actualRequests);
        assertEquals(2, actualRequests.size());
        assertArrayEquals(expectedList.toArray(), actualRequests.toArray());
    }
    @Test
    @DisplayName("Тест получения пустого списка из базы данных")
    public void returnEmptyGetAllTest() throws SQLException {
        doReturn(connection).when(dataSource).getConnection();
        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);
        given(preparedStatement.executeQuery()).willReturn(resultSet);

        given(resultSet.next()).willReturn( false);
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
        Request requestToSave = new Request(
                1,
                10,
                20,
                30,
                "Иванов Иван Иванович",
                LocalDate.of(1980, 1, 1),
                LocalDate.of(2023, 5, 10),
                "CERT-12345",
                RequestStatus.NEW,
                "15000.00",
                "Тестовая заметка",
                LocalDateTime.of(2023, 5, 11, 10, 0)
        );

        doReturn(connection).when(dataSource).getConnection();
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
        Request request = new Request(
                1, 10, 20, 30, "Петров П.П.",
                LocalDate.of(1990, 1, 1), LocalDate.of(2023, 1, 1),
                "CERT-1", RequestStatus.NEW, "100", "Note", LocalDateTime.now()
        );

        doReturn(connection).when(dataSource).getConnection();
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
        // 1. Arrange
        int targetId = 42;

        doReturn(connection).when(dataSource).getConnection();
        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);

        requestRepository.delete(targetId);

        verify(preparedStatement, times(1)).setInt(1, targetId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    @DisplayName("delete: Выбрасывает RuntimeException при ошибке в БД")
    void shouldThrowRuntimeExceptionWhenDeleteFails() throws SQLException {
        int targetId = 42;

        doReturn(connection).when(dataSource).getConnection();
        given(connection.prepareStatement(anyString())).willReturn(preparedStatement);
        given(preparedStatement.executeUpdate()).willThrow(new SQLException("Access denied"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> requestRepository.delete(targetId)
        );

        assertTrue(exception.getMessage().contains("SQL exception: Access denied"));
    }
}
