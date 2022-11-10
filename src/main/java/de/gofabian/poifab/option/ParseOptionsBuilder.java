package de.gofabian.poifab.option;

import de.gofabian.poifab.core.*;
import de.gofabian.poifab.target.model.ExcelColumnIndexFieldParser;
import de.gofabian.poifab.target.model.ExcelColumnTitleFieldParser;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ParseOptionsBuilder {

    private static final List<FieldParser> defaultFieldParsers = Arrays.asList(
            new ExcelColumnIndexFieldParser(),
            new ExcelColumnTitleFieldParser()
    );

    private Sheet sheet;
    private IndexRange titleRowRange;
    private IndexRange dataRowRange;
    private IndexRange columnRange;

    private CellParser defaultCellParser = new RawCellParser();
    private final List<CellParser> cellParsers = new ArrayList<>();
    private final List<FieldParser> fieldParsers = new ArrayList<>();
    private TableParser tableParser = new DefaultTableParser();


    public ParseOptionsBuilder sheet(Sheet sheet) {
        this.sheet = sheet;
        return this;
    }

    public ParseOptionsBuilder noTitleRows() {
        titleRowRange = null;
        return this;
    }

    public ParseOptionsBuilder titleRow(int rowIndex) {
        titleRowRange = new IndexRange(rowIndex, rowIndex);
        return this;
    }

    public ParseOptionsBuilder titleRows(int startRowIndex, int endRowIndex) {
        titleRowRange = new IndexRange(startRowIndex, endRowIndex);
        return this;
    }

    public ParseOptionsBuilder allDataRows() {
        dataRowRange = null;
        return this;
    }

    public ParseOptionsBuilder dataRows(int startRowIndex, int endRowIndex) {
        dataRowRange = new IndexRange(startRowIndex, endRowIndex);
        return this;
    }

    public ParseOptionsBuilder allColumns() {
        columnRange = null;
        return this;
    }

    public ParseOptionsBuilder columns(int startColumnIndex, int endColumnIndex) {
        columnRange = new IndexRange(startColumnIndex, endColumnIndex);
        return this;
    }

    public ParseOptionsBuilder rawData() {
        defaultCellParser = new RawCellParser();
        return this;
    }

    public ParseOptionsBuilder formattedData() {
        defaultCellParser = new FormattingCellParser();
        return this;
    }

    public ParseOptionsBuilder resetCellParsers() {
        cellParsers.clear();
        return this;
    }

    public ParseOptionsBuilder addCellParser(CellParser cellParser) {
        cellParsers.add(Objects.requireNonNull(cellParser));
        return this;
    }

    public ParseOptionsBuilder addCellParsers(List<CellParser> cellParsers) {
        this.cellParsers.addAll(Objects.requireNonNull(cellParsers));
        return this;
    }

    public ParseOptionsBuilder resetFieldParsers() {
        fieldParsers.clear();
        return this;
    }

    public ParseOptionsBuilder addFieldParser(FieldParser fieldParser) {
        fieldParsers.add(Objects.requireNonNull(fieldParser));
        return this;
    }

    public ParseOptionsBuilder addFieldParsers(List<FieldParser> fieldParsers) {
        this.fieldParsers.addAll(Objects.requireNonNull(fieldParsers));
        return this;
    }

    public ParseOptionsBuilder tableParser(TableParser tableParser) {
        this.tableParser = Objects.requireNonNull(tableParser);
        return this;
    }

    public ParseOptions build() {
        var cellParsers = new ArrayList<>(this.cellParsers);
        cellParsers.add(defaultCellParser);
        var fieldParsers = new ArrayList<>(this.fieldParsers);
        fieldParsers.addAll(defaultFieldParsers);
        return new ParseOptions(
                sheet, titleRowRange, dataRowRange, columnRange,
                new CompositeCellParser(cellParsers),
                fieldParsers,
                tableParser
        );
    }

}
