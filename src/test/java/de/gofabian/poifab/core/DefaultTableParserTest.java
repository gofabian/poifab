package de.gofabian.poifab.core;

import de.gofabian.poifab.WorkbookUtil;
import de.gofabian.poifab.option.ParseOptions;
import de.gofabian.poifab.option.ParseOptionsBuilder;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultTableParserTest {

    @Test
    public void parse() throws NoSuchFieldException {
        var fieldToParserMap = Map.<Field, FieldParser>of(
                Model.class.getDeclaredField("column0"), new FieldParserMock(0),
                Model.class.getDeclaredField("column1"), new FieldParserMock(1)
        );

        var sheet = WorkbookUtil.createSheet(List.of(
                List.of("column0_value0", "column1_value0"),
                List.of("column0_value1", "column1_value1")
        ));
        var options = new ParseOptionsBuilder().sheet(sheet).build();

        var result = new DefaultTableParser().parse(fieldToParserMap, options);

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).size());
        assertEquals("column0_value0", result.get(0).get(Model.class.getDeclaredField("column0")));
        assertEquals(2, result.get(1).size());
        assertEquals("column1_value1", result.get(1).get(Model.class.getDeclaredField("column1")));
    }

    @Test
    public void skipEmptyRows() throws NoSuchFieldException {
        var fieldToParserMap = Map.<Field, FieldParser>of(
                Model.class.getDeclaredField("column0"), new FieldParserMock(0)
        );

        var sheet = WorkbookUtil.createSheet(List.of(
                List.of("value"),
                List.of(""),
                List.of()
        ));
        var options = new ParseOptionsBuilder().sheet(sheet).build();

        var result = new DefaultTableParser().parse(fieldToParserMap, options);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).size());
        assertEquals("value", result.get(0).get(Model.class.getDeclaredField("column0")));
    }


    static class Model {
        String column0;
        String column1;
    }

    record FieldParserMock(int columnIndex) implements FieldParser {
        @Override
        public boolean supports(Field field) {
            return true;
        }

        @Override
        public Object parse(Field field, int rowIndex, ParseOptions options) {
            return options.cellParser().parse(field, rowIndex, columnIndex, options);
        }
    }

}
