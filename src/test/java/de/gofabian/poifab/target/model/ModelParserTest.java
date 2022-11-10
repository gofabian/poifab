package de.gofabian.poifab.target.model;

import de.gofabian.poifab.WorkbookUtil;
import de.gofabian.poifab.option.ParseOptionsBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModelParserTest {

    @Test
    public void parse() {
        var sheet = WorkbookUtil.createSheet(List.of(
                List.of("false", "false"),
                List.of("true", "true")
        ));

        var options = new ParseOptionsBuilder().sheet(sheet).build();
        var result = new ModelParser().parse(Model.class, options);

        assertEquals(2, result.size());
        assertEquals("false", result.get(0).string);
        assertEquals(false, result.get(0).bool);
        assertEquals("true", result.get(1).string);
        assertEquals(true, result.get(1).bool);
    }

    @Test
    public void missingFieldParser() {
        var sheet = WorkbookUtil.createSheet(List.of(
                List.of("false", "false")
        ));

        var options = new ParseOptionsBuilder().sheet(sheet).build();
        options.fieldParsers().clear();

        assertThrows(IllegalArgumentException.class, () ->
                new ModelParser().parse(Model.class, options)
        );
    }

    record Model(
            @ExcelColumnIndex(0)
            String string,
            @ExcelColumnIndex(1)
            Boolean bool
    ) {
    }

}
