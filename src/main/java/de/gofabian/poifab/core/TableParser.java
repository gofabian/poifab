package de.gofabian.poifab.core;

import de.gofabian.poifab.option.ParseOptions;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Iterates a table (rows and columns) and collects all cell values.
 */
public interface TableParser {
    /**
     * Use the given parsers to parse rows from Excel.
     */
    List<Map<Field, Object>> parse(Map<Field, FieldParser> parsers, ParseOptions options);
}
