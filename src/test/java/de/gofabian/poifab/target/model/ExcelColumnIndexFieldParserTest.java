package de.gofabian.poifab.target.model;

import de.gofabian.poifab.WorkbookUtil;
import de.gofabian.poifab.core.CellParser;
import de.gofabian.poifab.core.FieldParser;
import de.gofabian.poifab.option.ParseOptionsBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExcelColumnIndexFieldParserTest {

    private final FieldParser parser = new ExcelColumnIndexFieldParser();

    @Test
    public void supportsFieldWithAnnotation() throws NoSuchFieldException {
        class Model {
            @ExcelColumnIndex(1)
            String supported;
            String unsupported;
        }

        assertTrue(parser.supports(Model.class.getDeclaredField("supported")));
        assertFalse(parser.supports(Model.class.getDeclaredField("unsupported")));
    }

    @Test
    public void getColumnIndexFromAnnotation() throws NoSuchFieldException {
        class Model {
            @ExcelColumnIndex(42)
            String name;
        }

        var cellParserMock = mock(CellParser.class);
        when(cellParserMock.supports(any(), anyInt(), anyInt(), any())).thenReturn(true);
        when(cellParserMock.parse(any(), anyInt(), anyInt(), any())).thenReturn("value");

        var field = Model.class.getDeclaredField("name");
        var sheet = WorkbookUtil.createSheet();
        var options = new ParseOptionsBuilder().sheet(sheet).addCellParser(cellParserMock).build();

        var result = parser.parse(field, 36, options);

        assertEquals("value", result);
        verify(cellParserMock).parse(field, 36, 42, options);
    }

}
