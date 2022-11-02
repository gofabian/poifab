package de.gofabian.poifab.option;

import de.gofabian.poifab.core.CellParser;
import de.gofabian.poifab.core.FieldParser;
import de.gofabian.poifab.core.TableParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Objects;

/**
 * General options to configure Poifab.
 */
public record ParseOptions(
        Sheet sheet,
        IndexRange titleRowRange,
        IndexRange dataRowRange,
        IndexRange columnRange,
        CellParser cellParser,
        List<FieldParser> fieldParsers,
        TableParser tableParser
) {

    public ParseOptions {
        Objects.requireNonNull(sheet, "Sheet must not be null");
        Objects.requireNonNull(cellParser, "cellParser must not be null");
        Objects.requireNonNull(fieldParsers, "fieldParsers must not be null");
        Objects.requireNonNull(tableParser, "tableParser must not be null");
    }

    public Row getRow(int rowIndex) {
        return sheet.getRow(rowIndex);
    }

    public Cell getCell(int rowIndex, int columnIndex) {
        var row = getRow(rowIndex);
        if (row == null) {
            return null;
        }
        return row.getCell(columnIndex);
    }

    public IndexRange getRealDataRowRange() {
        if (dataRowRange != null) {
            return dataRowRange;
        }
        if (sheet.getFirstRowNum() < 0) {
            return new IndexRange(0, 0);
        }
        return new IndexRange(sheet.getFirstRowNum(), sheet.getLastRowNum());
    }

    public IndexRange getRealColumnRange(int rowIndex) {
        if (columnRange != null) {
            return columnRange;
        }

        var row = sheet.getRow(rowIndex);
        if (row == null || row.getFirstCellNum() < 0) {
            return new IndexRange(0, 0);
        }

        return new IndexRange(row.getFirstCellNum(), row.getLastCellNum() - 1);
    }

}
