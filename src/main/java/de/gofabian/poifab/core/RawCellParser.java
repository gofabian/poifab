package de.gofabian.poifab.core;


import de.gofabian.poifab.option.ParseOptions;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import java.lang.reflect.Field;

/**
 * Reads the raw value of a cell.
 */
public class RawCellParser implements CellParser {
    @Override
    public boolean supports(Field field, int rowIndex, int columnIndex, ParseOptions options) {
        return true;
    }

    @Override
    public Object parse(Field field, int rowIndex, int columnIndex, ParseOptions options) {
        var cell = options.getCell(rowIndex, columnIndex);
        if (cell == null) {
            return null;
        }

        var cellType = cell.getCellType();
        if (cellType == CellType.FORMULA) {
            cellType = cell.getCachedFormulaResultType();
        }

        switch (cellType) {
            case BLANK -> {
                return null;
            }
            case BOOLEAN -> {
                return cell.getBooleanCellValue();
            }
            case STRING -> {
                var string = cell.getStringCellValue().trim();
                if (string.isEmpty()) {
                    return null;
                }
                return string;
            }
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            }
            default -> throw new IllegalStateException("unexpected cell type: " + cellType);
        }
    }

}
