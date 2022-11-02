package de.gofabian.poifab.core;

import de.gofabian.poifab.option.ParseOptions;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * The composite cell parser contains a list of cell parsers. The first cell parser
 * that is able to provide the value will be used.
 */
public class CompositeCellParser implements CellParser {

    private final List<CellParser> parsers;

    public CompositeCellParser(List<CellParser> parsers) {
        this.parsers = Objects.requireNonNull(parsers);
    }

    @Override
    public boolean supports(Field field, int rowIndex, int columnIndex, ParseOptions options) {
        return parsers.stream()
                .anyMatch(p -> p.supports(field, rowIndex, columnIndex, options));
    }

    @Override
    public Object parse(Field field, int rowIndex, int columnIndex, ParseOptions options) {
        var parser = parsers.stream()
                .filter(p -> p.supports(field, rowIndex, columnIndex, options))
                .findFirst().orElse(null);
        if (parser == null) {
            throw new IllegalArgumentException("No parser supports the arguments: " +
                    "field=" + field + ", rowIndex=" + rowIndex +
                    ", columnIndex=" + columnIndex + ", options=" + options);
        }
        return parser.parse(field, rowIndex, columnIndex, options);
    }
}
