package de.gofabian.poifab.target.model;

import de.gofabian.poifab.WorkbookUtil;
import de.gofabian.poifab.core.FieldParser;
import de.gofabian.poifab.option.ParseOptionsBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExcelColumnTitleFieldParserTest {

    private final FieldParser parser = new ExcelColumnTitleFieldParser();

    @Test
    public void supportsFieldWithAnnotation() throws NoSuchFieldException {
        class Model {
            @ExcelColumnTitle("column1")
            String supported;
            String unsupported;
        }

        assertTrue(parser.supports(Model.class.getDeclaredField("supported")));
        assertFalse(parser.supports(Model.class.getDeclaredField("unsupported")));
    }

    @Test
    public void getColumnIndexFromTitle() throws NoSuchFieldException {
        class Model {
            @ExcelColumnTitle("column2")
            String name;
        }

        var field = Model.class.getDeclaredField("name");
        var sheet = WorkbookUtil.createSheet(List.of(
                List.of("column1", "column2"),
                List.of("value1", "value2")
        ));
        var options = new ParseOptionsBuilder().sheet(sheet).titleRow(0).build();

        var result = parser.parse(field, 1, options);

        assertEquals("value2", result);
    }

    @Test
    public void unknownTitle() throws NoSuchFieldException {
        class Model {
            @ExcelColumnTitle("unknown")
            String name;
        }

        var field = Model.class.getDeclaredField("name");
        var sheet = WorkbookUtil.createSheet();
        var options = new ParseOptionsBuilder().sheet(sheet).titleRow(0).build();

        assertThrows(IllegalArgumentException.class, () ->
                parser.parse(field, 1, options)
        );
    }

    @Test
    public void undefinedTitleRow() throws NoSuchFieldException {
        class Model {
            @ExcelColumnTitle("undefined")
            String name;
        }

        var field = Model.class.getDeclaredField("name");
        var sheet = WorkbookUtil.createSheet(List.of(
                List.of("undefined"),
                List.of("value")
        ));
        var options = new ParseOptionsBuilder().sheet(sheet).noTitleRows().build();

        assertThrows(IllegalArgumentException.class, () ->
                parser.parse(field, 1, options)
        );
    }

}
