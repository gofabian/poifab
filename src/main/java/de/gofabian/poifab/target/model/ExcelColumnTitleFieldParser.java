package de.gofabian.poifab.target.model;

import de.gofabian.poifab.core.FieldParser;
import de.gofabian.poifab.option.ParseOptions;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Parse cell values if the given field has the ExcelColumnTitle annotation.
 */
public class ExcelColumnTitleFieldParser implements FieldParser {

    private Map<String, Integer> columnTitleToIndexMap = null;

    @Override
    public boolean supports(Field field) {
        return field.getAnnotation(ExcelColumnTitle.class) != null;
    }

    @Override
    public Object parse(Field field, int rowIndex, ParseOptions options) {
        var columnTitle = field.getAnnotation(ExcelColumnTitle.class).value();
        var columnIndex = mapColumnTitleToIndex(columnTitle, options);
        return options.cellParser().parse(field, rowIndex, columnIndex, options);
    }

    private int mapColumnTitleToIndex(String title, ParseOptions options) {
        if (columnTitleToIndexMap == null) {
            columnTitleToIndexMap = parseTitles(options);
        }
        var columnIndex = columnTitleToIndexMap.get(title);
        if (columnIndex == null) {
            throw new IllegalArgumentException("Column title cannot be found in Excel sheet: " + title);
        }
        return columnIndex;
    }

    private Map<String, Integer> parseTitles(ParseOptions options) {
        if (options.titleRowRange() == null) {
            return Collections.emptyMap();
        }

        var columnTitleToIndexMap = new HashMap<String, Integer>();

        options.titleRowRange().forEach(rowIndex -> {
            var columnRange = options.getRealColumnRange(rowIndex);
            if (columnRange == null) {
                return;
            }

            columnRange.forEach(columnIndex -> {
                var value = options.cellParser().parse(null, rowIndex, columnIndex, options);
                var title = value == null ? null : value.toString().trim();
                if (title != null && !title.isEmpty()) {
                    columnTitleToIndexMap.put(title, columnIndex);
                }
            });
        });

        return columnTitleToIndexMap;
    }
}