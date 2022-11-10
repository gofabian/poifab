package de.gofabian.poifab;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Collections;
import java.util.List;

public class WorkbookUtil {

    public static Sheet createSheet() {
        return createSheet(Collections.emptyList());
    }

    public static Sheet createSheet(List<List<String>> values) {
        //noinspection resource
        var sheet = new XSSFWorkbook().createSheet();
        for (var rowIndex = 0; rowIndex < values.size(); rowIndex++) {
            var row = sheet.createRow(rowIndex);
            var rowValues = values.get(rowIndex);

            for (var columnIndex = 0; columnIndex < rowValues.size(); columnIndex++) {
                var cell = row.createCell(columnIndex);
                var cellValue = rowValues.get(columnIndex);
                cell.setCellValue(cellValue);
            }
        }
        return sheet;
    }

    public static Row createRow(int rowIndex) {
        return createSheet().createRow(rowIndex);
    }

    public static Cell createCell(int rowIndex, int columnIndex) {
        return createRow(rowIndex).createCell(columnIndex);
    }


}
