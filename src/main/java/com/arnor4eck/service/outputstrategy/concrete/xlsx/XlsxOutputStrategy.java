package com.arnor4eck.service.outputstrategy.concrete.xlsx;

import com.arnor4eck.model.Plot;
import com.arnor4eck.repository.Repository;
import com.arnor4eck.service.outputstrategy.OutputStrategy;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;

public class XlsxOutputStrategy implements OutputStrategy {

    private final Repository<Plot> plotRepository;

    public XlsxOutputStrategy(Repository<Plot> plotRepository) {
        this.plotRepository = plotRepository;
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


            Workbook workbook = pojo.getWorkbook();

            workbook.write(fos);
        } catch (Exception e) {
            return "Не удалось экспортировать файл: %s".formatted(e.getMessage());
        }

        return String.format("Файл созранен как %s", fileName);
    }
}
