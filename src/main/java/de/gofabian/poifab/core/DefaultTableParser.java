package de.gofabian.poifab.core;

import de.gofabian.poifab.option.ParseOptions;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Iterates rows and columns. Collects non-empty rows.
 */
public class DefaultTableParser implements TableParser {
    @Override
    public List<Map<Field, Object>> parse(Map<Field, FieldParser> fieldToParserMap, ParseOptions options) {
        var tableValues = new ArrayList<Map<Field, Object>>();

        options.getRealDataRowRange().forEach(rowIndex -> {
            var rowValues = new HashMap<Field, Object>();

            fieldToParserMap.forEach((field, parser) -> {
                var value = parser.parse(field, rowIndex, options);
                rowValues.put(field, value);
            });

            if (rowValues.values().stream().anyMatch(Objects::nonNull)) {
                tableValues.add(rowValues);
            }
        });

        return tableValues;
    }
}
