package de.gofabian.poifab.core;

import de.gofabian.poifab.option.ParseOptions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompositeCellParserTest {

    @Test
    public void supportsIfAnySupports() {
        var cellParser = new CompositeCellParser(List.of(
                new CellParserMock(false, "parser1"),
                new CellParserMock(false, "parser2"),
                new CellParserMock(true, "parser3")
        ));

        assertTrue(cellParser.supports(null, 1, 1, null));
    }

    @Test
    public void doesNotSupportIfNoneSupports() {
        var cellParser = new CompositeCellParser(List.of(
                new CellParserMock(false, "parser1"),
                new CellParserMock(false, "parser2"),
                new CellParserMock(false, "parser3")
        ));

        assertFalse(cellParser.supports(null, 1, 1, null));
    }

    @Test
    public void parseWithFirstSupportingParser() {
        var cellParser = new CompositeCellParser(List.of(
                new CellParserMock(false, "parser1"),
                new CellParserMock(true, "parser2"),
                new CellParserMock(true, "parser3")
        ));

        var value = cellParser.parse(null, 1, 1, null);
        assertEquals("parser2", value);
    }

    @Test
    public void parseWithNoSupportingParser() {
        var cellParser = new CompositeCellParser(List.of(
                new CellParserMock(false, "parser1"),
                new CellParserMock(false, "parser2"),
                new CellParserMock(false, "parser3")
        ));

        assertThrows(IllegalArgumentException.class, () ->
                cellParser.parse(null, 1, 1, null)
        );
    }

    static class CellParserMock implements CellParser {
        final boolean supports;
        final String value;

        CellParserMock(boolean supports, String value) {
            this.supports = supports;
            this.value = value;
        }

        @Override
        public boolean supports(Field field, int rowIndex, int columnIndex, ParseOptions options) {
            return supports;
        }

        @Override
        public Object parse(Field field, int rowIndex, int columnIndex, ParseOptions options) {
            return value;
        }
    }

}
