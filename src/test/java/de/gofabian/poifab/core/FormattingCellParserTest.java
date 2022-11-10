package de.gofabian.poifab.core;

import de.gofabian.poifab.WorkbookUtil;
import de.gofabian.poifab.option.ParseOptionsBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormattingCellParserTest {

    @Test
    public void supportsEverything() {
        var cellParser = new FormattingCellParser();
        assertTrue(cellParser.supports(null, -1, -1, null));
    }

    @Test
    public void getFormattedCellValue() {
        var cell = WorkbookUtil.createCell(3, 4);
        cell.setBlank();

        var options = new ParseOptionsBuilder().sheet(cell.getSheet()).build();
        var value = new FormattingCellParser().parse(null, 3, 4, options);

        assertEquals("", value);
    }

}
