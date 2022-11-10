package de.gofabian.poifab.core;

import de.gofabian.poifab.WorkbookUtil;
import de.gofabian.poifab.option.ParseOptionsBuilder;
import org.apache.poi.ss.usermodel.FormulaError;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class RawCellParserTest {

    private final RawCellParser rawCellParser = new RawCellParser();

    @Test
    public void supportsEverything() {
        assertTrue(rawCellParser.supports(null, -1, -1, null));
    }

    @Test
    public void parseNullCell() {
        var sheet = WorkbookUtil.createSheet();

        var options = new ParseOptionsBuilder().sheet(sheet).build();
        var value = rawCellParser.parse(null, 0, 0, options);

        assertNull(value);
    }

    @Test
    public void parseBlankValue() {
        var cell = WorkbookUtil.createCell(1, 2);
        cell.setBlank();

        var options = new ParseOptionsBuilder().sheet(cell.getSheet()).build();
        var value = rawCellParser.parse(null, 1, 2, options);

        assertNull(value);
    }

    @Test
    public void parseBooleanValue() {
        var cell = WorkbookUtil.createCell(1, 2);
        cell.setCellValue(true);

        var options = new ParseOptionsBuilder().sheet(cell.getSheet()).build();
        var value = rawCellParser.parse(null, 1, 2, options);

        assertEquals(Boolean.TRUE, value);
    }

    @Test
    public void parseStringValue() {
        var cell = WorkbookUtil.createCell(1, 2);
        cell.setCellValue("a string");

        var options = new ParseOptionsBuilder().sheet(cell.getSheet()).build();
        var value = rawCellParser.parse(null, 1, 2, options);

        assertEquals("a string", value);
    }

    @Test
    public void parseEmptyStringValue() {
        var cell = WorkbookUtil.createCell(1, 2);
        cell.setCellValue("");

        var options = new ParseOptionsBuilder().sheet(cell.getSheet()).build();
        var value = rawCellParser.parse(null, 1, 2, options);

        assertNull(value);
    }

    @Test
    public void parseNumericValue() {
        var cell = WorkbookUtil.createCell(1, 2);
        cell.setCellValue(12.34);

        var options = new ParseOptionsBuilder().sheet(cell.getSheet()).build();
        var value = rawCellParser.parse(null, 1, 2, options);

        assertEquals(12.34, value);
    }

    @Test
    public void parseTimestampValue() {
        var cell = WorkbookUtil.createCell(1, 2);
        var cellStyle = cell.getSheet().getWorkbook().createCellStyle();
        cellStyle.setDataFormat((short) 14);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(LocalDateTime.parse("2020-01-01T10:09:08"));

        var options = new ParseOptionsBuilder().sheet(cell.getSheet()).build();
        var value = rawCellParser.parse(null, 1, 2, options);

        assertEquals(LocalDateTime.parse("2020-01-01T10:09:08"), value);
    }

    @Test
    public void parseCachedFormulaValue() {
        var cell = WorkbookUtil.createCell(1, 2);
        cell.setCellFormula("C64");
        cell.setCellValue("cached value");

        var options = new ParseOptionsBuilder().sheet(cell.getSheet()).build();
        var value = rawCellParser.parse(null, 1, 2, options);

        assertEquals("cached value", value);
    }

    @Test
    public void parseUnexpectedValue() {
        var cell = WorkbookUtil.createCell(1, 2);
        cell.setCellErrorValue(FormulaError.NULL.getCode());

        var options = new ParseOptionsBuilder().sheet(cell.getSheet()).build();

        assertThrows(IllegalStateException.class, () ->
                rawCellParser.parse(null, 1, 2, options)
        );
    }

}
