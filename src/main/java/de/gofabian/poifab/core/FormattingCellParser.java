package de.gofabian.poifab.core;


import de.gofabian.poifab.option.ParseOptions;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Uses the Poi DataFormatter to read the formatted value of a cell.
 */
public class FormattingCellParser implements CellParser {

    private final DataFormatter dataFormatter;
    private final FormulaEvaluator formulaEvaluator;

    public FormattingCellParser() {
        this(createDefaultDataFormatter(), null);
    }

    public FormattingCellParser(DataFormatter dataFormatter, FormulaEvaluator formulaEvaluator) {
        this.dataFormatter = Objects.requireNonNull(dataFormatter);
        this.formulaEvaluator = formulaEvaluator;
    }

    private static DataFormatter createDefaultDataFormatter() {
        var dataFormatter = new DataFormatter();
        dataFormatter.setUseCachedValuesForFormulaCells(true);
        return dataFormatter;
    }

    @Override
    public boolean supports(Field field, int rowIndex, int columnIndex, ParseOptions options) {
        return true;
    }

    @Override
    public Object parse(Field field, int rowIndex, int columnIndex, ParseOptions options) {
        var cell = options.getCell(rowIndex, columnIndex);
        return dataFormatter.formatCellValue(cell, formulaEvaluator);
    }

}
