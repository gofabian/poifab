package de.gofabian.poifab.core;


import de.gofabian.poifab.option.ParseOptions;

import java.lang.reflect.Field;

/**
 * Reads the value from a single cell.
 */
public interface CellParser {
    boolean supports(Field field, int rowIndex, int columnIndex, ParseOptions options);

    Object parse(Field field, int rowIndex, int columnIndex, ParseOptions options);
}
