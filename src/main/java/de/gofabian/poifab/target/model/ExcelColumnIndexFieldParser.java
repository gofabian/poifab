package de.gofabian.poifab.target.model;

import de.gofabian.poifab.core.FieldParser;
import de.gofabian.poifab.option.ParseOptions;

import java.lang.reflect.Field;

/**
 * Parse cell values if the given field has the ExcelColumnIndex annotation.
 */
public class ExcelColumnIndexFieldParser implements FieldParser {
    @Override
    public boolean supports(Field field) {
        return field.getAnnotation(ExcelColumnIndex.class) != null;
    }

    @Override
    public Object parse(Field field, int rowIndex, ParseOptions options) {
        var columnIndex = field.getAnnotation(ExcelColumnIndex.class).value();
        return options.cellParser().parse(field, rowIndex, columnIndex, options);
    }
}
