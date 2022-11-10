package de.gofabian.poifab.option;

import de.gofabian.poifab.WorkbookUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParseOptionsTest {

    @Test
    public void getRow() {
        var sheet = WorkbookUtil.createSheet();
        sheet.createRow(42);

        var options = new ParseOptionsBuilder()
                .sheet(sheet)
                .build();

        assertNotNull(options.getRow(42));
        assertNull(options.getRow(4242));
    }

    @Test
    public void getCell() {
        var sheet = WorkbookUtil.createSheet();
        var row = sheet.createRow(42);
        row.createCell(14);

        var options = new ParseOptionsBuilder()
                .sheet(sheet)
                .build();

        assertNotNull(options.getCell(42, 14));
        assertNull(options.getCell(42, 1000));
        assertNull(options.getCell(1000, 14));
    }

    @Test
    public void getRealDataRowRangeReturnsDataRowRange() {
        var options = new ParseOptionsBuilder()
                .sheet(WorkbookUtil.createSheet())
                .dataRows(1, 2)
                .build();

        var rowRange = options.getRealDataRowRange();
        assertEquals(1, rowRange.startIndex());
        assertEquals(2, rowRange.endIndex());
    }

    @Test
    public void getRealDataRowRangeReturnsSheetRowRange() {
        var sheet = WorkbookUtil.createSheet();
        sheet.createRow(2);
        sheet.createRow(28);

        var options = new ParseOptionsBuilder()
                .sheet(sheet)
                .build();

        var rowRange = options.getRealDataRowRange();
        assertEquals(2, rowRange.startIndex());
        assertEquals(28, rowRange.endIndex());
    }

    @Test
    public void getRealDataRowRangeReturnsEmptySheetRowRange() {
        var options = new ParseOptionsBuilder()
                .sheet(WorkbookUtil.createSheet())
                .build();

        assertNotNull(options.getRealDataRowRange());
    }

    @Test
    public void getRealColumnRangeReturnsColumnRange() {
        var options = new ParseOptionsBuilder()
                .sheet(WorkbookUtil.createSheet())
                .columns(2, 5)
                .build();

        var columnRange = options.getRealColumnRange(100);
        assertEquals(2, columnRange.startIndex());
        assertEquals(5, columnRange.endIndex());
    }

    @Test
    public void getRealColumnRangeReturnsSheetRowColumnRange() {
        var sheet = WorkbookUtil.createSheet();
        var row = sheet.createRow(2);
        row.createCell(3);
        row.createCell(100);

        var options = new ParseOptionsBuilder()
                .sheet(sheet)
                .build();

        var columnRange = options.getRealColumnRange(2);
        assertEquals(3, columnRange.startIndex());
        assertEquals(100, columnRange.endIndex());
    }

    @Test
    public void getRealColumnRangeReturnsEmptySheetRowColumnRange() {
        var sheet = WorkbookUtil.createSheet();
        sheet.createRow(3);

        var options = new ParseOptionsBuilder()
                .sheet(sheet)
                .build();

        assertNotNull(options.getRealColumnRange(1));
        assertNotNull(options.getRealColumnRange(3));
    }

}
