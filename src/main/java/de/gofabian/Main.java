package de.gofabian;

import de.gofabian.poifab.option.ParseOptionsBuilder;
import de.gofabian.poifab.target.model.ExcelColumnIndex;
import de.gofabian.poifab.target.model.ModelParser;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * todo: this file should be deleted
 */
public class Main {

    public record Model(
            @ExcelColumnIndex(0)
            String text
    ) {
    }

    public static void main(String[] args) throws IOException {
        try (var workbook = new XSSFWorkbook()) {
            var sheet = workbook.createSheet();
            var cell1 = sheet.createRow(0).createCell(0);
            cell1.setCellValue("one");
            var cell2 = sheet.createRow(1).createCell(0);
            cell2.setCellValue("two");

            var options = new ParseOptionsBuilder()
                    .sheet(sheet)
//                    .titleRow(0)
//                .dataRows(1, 1)
//                .column(0)
                    .rawData()
                    .build();

            var result = new ModelParser().parse(Model.class, options);
            System.out.println(result);
        }
    }

}