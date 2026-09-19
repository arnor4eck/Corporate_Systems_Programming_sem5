package application.util;

import com.arnor4eck.model.Request;
import com.arnor4eck.util.enums.RequestStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RequestGenerator {

    private static final Random RANDOM = new Random();

    // Массивы вариантов для случайных строк
    private static final String[] NAMES = {
            "Иванов Иван Иванович",
            "Петров Петр Петрович",
            "Сидоров Алексей Сергеевич",
            "Смирнова Анна Владимировна",
            "Кузнецов Дмитрий Игоревич"
    };

    private static final String[] NOTES = {
            "Срочный заказ",
            "Требуется уточнение деталей",
            "Оплачено частично",
            "Документы предоставлены",
            "Обычная заявка"
    };

    public static Request generateRandomRequest() {
        int id = RANDOM.nextInt(1, 10_000);
        int customerId = RANDOM.nextInt(1, 1_000);
        int employeeId = RANDOM.nextInt(1, 100);
        int plotId = RANDOM.nextInt(1, 500);

        String deceasedFullName = NAMES[RANDOM.nextInt(NAMES.length)];

        LocalDate deceasedBirthday = getRandomDate(
                LocalDate.of(1940, 1, 1),
                LocalDate.of(2000, 1, 1)
        );

        LocalDate deceasedDeathday = getRandomDate(
                LocalDate.of(2020, 1, 1),
                LocalDate.now()
        );

        String deceasedCertificate = "CERT-" + RANDOM.nextInt(100_000, 999_999);

        RequestStatus[] statuses = RequestStatus.values();
        RequestStatus status = statuses[RANDOM.nextInt(statuses.length)];

        String totalCost = String.format("%.2f", RANDOM.nextDouble(1000.0, 100000.0)).replace(',', '.');

        String note = NOTES[RANDOM.nextInt(NOTES.length)];

        LocalDateTime createdAt = LocalDateTime.now().minusDays(RANDOM.nextInt(0, 30));

        return new Request(
                id,
                customerId,
                employeeId,
                plotId,
                deceasedFullName,
                deceasedBirthday,
                deceasedDeathday,
                deceasedCertificate,
                status,
                totalCost,
                note,
                createdAt
        );
    }

    private static LocalDate getRandomDate(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}