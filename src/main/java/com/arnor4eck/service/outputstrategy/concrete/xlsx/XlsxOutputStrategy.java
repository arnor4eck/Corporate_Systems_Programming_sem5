package com.arnor4eck.service.outputstrategy.concrete.xlsx;

import com.arnor4eck.model.Customer;
import com.arnor4eck.model.Plot;
import com.arnor4eck.model.Request;
import com.arnor4eck.model.Sector;
import com.arnor4eck.repository.Repository;
import com.arnor4eck.service.outputstrategy.OutputStrategy;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;

public class XlsxOutputStrategy implements OutputStrategy {

    private final Repository<Plot> plotRepository;
    private final Repository<Request> requestRepository;
    private final Repository<Customer> customerRepository;
    private final Repository<Sector> sectorRepository;

    public XlsxOutputStrategy(
            Repository<Plot> plotRepository,
            Repository<Request> requestRepository,
            Repository<Customer> customerRepository,
            Repository<Sector> sectorRepository
    ) {
        this.plotRepository = plotRepository;
        this.requestRepository = requestRepository;
        this.customerRepository = customerRepository;
        this.sectorRepository = sectorRepository;
    }

    @Override
    public String act() {
        String fileName = "output.xlsx";

        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            XlsxPojo pojo = new XlsxPojo();
            pojo.addSheet("Места", plotRepository, plot -> String.join("; ",
                    String.valueOf(plot.id()),
                    String.valueOf(plot.sectorId()),
                    String.valueOf(plot.rowNumber()),
                    String.valueOf(plot.plotNumber()),
                    plot.status().getValue(),
                    String.valueOf(plot.lengthCm()),
                    String.valueOf(plot.widthCm()),
                    plot.coordinates()
            ));

            pojo.addSheet("Заявки",
                    requestRepository,
                    Request::toExportString);
            pojo.addSheet("Клиенты",
                    customerRepository,
                    Customer::toExportString);
            pojo.addSheet("Сектора",
                    sectorRepository,
                    Sector::toExportString);


            Workbook workbook = pojo.getWorkbook();

            workbook.write(fos);
        } catch (Exception e) {
            return "Не удалось экспортировать файл: %s".formatted(e.getMessage());
        }

        return String.format("Файл созранен как %s", fileName);
    }
}
