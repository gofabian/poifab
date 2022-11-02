package de.gofabian.poifab.core;

import de.gofabian.poifab.option.ParseOptions;

import java.lang.reflect.Field;

/**
 * Supports specific columns and reads their cell values.
 */
public interface FieldParser {
    boolean supports(Field field);

    Object parse(Field field, int rowIndex, ParseOptions options);
}
